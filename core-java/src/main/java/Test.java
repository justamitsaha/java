@POST
@Path("/accountSummary" + JaxRsConstants.URL_EXTENSION)
@Produces("application/json")
public SignAcctSummaryDTO accountSummary(@Context HttpServletRequest request,
                                         @Context HttpServletResponse response) {

    /* -------------------------------------------------------------
     * Start timing the API execution (for performance logging)
     * ------------------------------------------------------------- */
    long startTime = System.currentTimeMillis();
    long stTime = System.currentTimeMillis();

    /* -------------------------------------------------------------
     * Log the entry point of Account Summary call (if enabled)
     * ------------------------------------------------------------- */
    if (timeLog.isInfoEnabled()) {
        timeLog.info("***** Account Summary jws call begins ***** " + stTime);
    }

    /* -------------------------------------------------------------
     * Create request DTO from HttpServletRequest
     * This DTO will be passed to service layer / downstream calls
     * ------------------------------------------------------------- */
    AcctSummaryRequestDTO acctSummaryRequestDTO = new AcctSummaryRequestDTO(request);

    /* -------------------------------------------------------------
     * Flags used to decide whether to refresh account summary data
     * pullToRefresh       -> user explicitly refreshed screen
     * accountRefreshAPI   -> API refresh requested explicitly
     * fromFPSDeepDrop     -> internal flag to handle special flows
     * ------------------------------------------------------------- */
    String pullToRefresh = null;
    String accountRefreshAPI = null;
    boolean fromFPSDeepDrop = false;

    /* -------------------------------------------------------------
     * Check if request contains Pull-To-Refresh flag.
     * If yes, set pullToRefresh to REQUEST_YES.
     * ------------------------------------------------------------- */
    if (MobileConstants.TRUE.equalsIgnoreCase(
            request.getParameter(MobileConstants.SPK_PULL_TO_REFRESH))) {
        pullToRefresh = MobileConstants.REQUEST_YES;
    }

    /* -------------------------------------------------------------
     * Check if request explicitly asks to call Account Refresh API.
     * If yes, set accountRefreshAPI to REQUEST_YES.
     * ------------------------------------------------------------- */
    if (MobileConstants.TRUE.equalsIgnoreCase(
            request.getParameter(MobileConstants.ACCOUNT_REF_API))) {
        accountRefreshAPI = MobileConstants.REQUEST_YES;
        log.debug("In account refresh api call");
    }

    /* -------------------------------------------------------------
     * Store refresh flags into the request DTO
     * ------------------------------------------------------------- */
    acctSummaryRequestDTO.setPullToRefresh(pullToRefresh);
    acctSummaryRequestDTO.setAccountRefreshAPI(accountRefreshAPI);

    /* -------------------------------------------------------------
     * Create response DTO and fetch user session profile data
     * baseProfile -> thread-local user/session context
     * business    -> business unit information
     * ------------------------------------------------------------- */
    SignAcctSummaryDTO signAcctSummaryDTO = new SignAcctSummaryDTO();
    BaseProfile baseProfile = BaseProfileThreadLocal.getBaseProfile();
    BigDataDTO bigDataDTO = new BigDataDTO();
    BusinessUnit business = MobileUtils.getBuSID(baseProfile);

    /* -------------------------------------------------------------
     * Initialize other helper variables
     * ------------------------------------------------------------- */
    String liveBankUrl = "false";
    String busCode = business.getBusinessCode();
    String rmMapped = null;
    String vreRMFlag = null;

    /* -------------------------------------------------------------
     * Check if request asks to refresh balance information.
     * This is another refresh trigger besides pull-to-refresh.
     * ------------------------------------------------------------- */
    String refreshBalanceInfo = request.getParameter("refreshBalanceInfo");

    /* -------------------------------------------------------------
     * If refreshBalanceInfo is TRUE OR coreAccountRefreshFlag is set in baseProfile:
     * - Force pull-to-refresh
     * - Clear cached core account summary response and refresh flags
     * ------------------------------------------------------------- */
    if ((refreshBalanceInfo != null
            && MobileConstants.TRUE.equalsIgnoreCase(refreshBalanceInfo))
            || (baseProfile.getProperty("coreAccountRefreshFlag") != null
            && (Boolean) baseProfile.getProperty("coreAccountRefreshFlag"))) {

        acctSummaryRequestDTO.setPullToRefresh(MobileConstants.REQUEST_YES);
        baseProfile.removeProperty("coreAccountSummaryResponse");
        baseProfile.removeProperty("coreAccountRefreshFlag");
    }

    /* -------------------------------------------------------------
     * If accountRefreshAPI flag is REQUEST_YES:
     * - Clear cached account summary response
     * - Clear related refresh/cached objects used by account summary
     * ------------------------------------------------------------- */
    if (accountRefreshAPI != null
            && MobileConstants.REQUEST_YES.equals(accountRefreshAPI)) {

        baseProfile.removeProperty("coreAccountSummaryResponse");
        baseProfile.removeProperty("coreAccountRefreshFlag");
        baseProfile.removeProperty("acctSumDataMap");
        baseProfile.removeProperty("coreSortedCCList");
        baseProfile.removeProperty(MobileConstants.CONTAINS_AS_RESPONSE);
    }

    /* -------------------------------------------------------------
     * If apiAccountRefreshEnableFlag is enabled in baseProfile:
     * AND any refresh trigger is present (refreshBalanceInfo OR accountRefreshAPI OR pullToRefresh)
     * - Convert this into a "private API refresh call"
     * - Disable pull-to-refresh and enforce accountRefreshAPI = REQUEST_YES
     * - Clear cached response and supporting cached data
     * ------------------------------------------------------------- */
    if (baseProfile.getProperty("apiAccountRefreshEnableFlag") != null
            && MobileConstants.TRUE.equalsIgnoreCase(
            (String) baseProfile.getProperty("apiAccountRefreshEnableFlag"))) {

        log.debug("In apiAccountRefreshEnableFlag");

        if (refreshBalanceInfo != null || accountRefreshAPI != null || pullToRefresh != null) {

            log.debug("In private api call set");

            acctSummaryRequestDTO.setPullToRefresh(null);
            acctSummaryRequestDTO.setAccountRefreshAPI(MobileConstants.REQUEST_YES);

            baseProfile.removeProperty("coreAccountSummaryResponse");
            baseProfile.removeProperty("coreAccountRefreshFlag");
            baseProfile.removeProperty("acctSumDataMap");
            baseProfile.removeProperty("coreSortedCCList");
            baseProfile.removeProperty(MobileConstants.CONTAINS_AS_RESPONSE);
        }
    }

    try {

        /* -------------------------------------------------------------
         * Decide whether to use cached Account Summary response OR call service layer.
         *
         * If baseProfile already contains ACCOUNTSUMMARYRESPONSE
         * AND refreshBalanceInfo is not requested
         * AND accountRefreshAPI is not requested:
         * -> Use cached SignAcctSummaryDTO from baseProfile.
         *
         * Else:
         * -> Call getAccountSummary() and fetch fresh data.
         * ------------------------------------------------------------- */
        if (baseProfile.containsProperty(MobileConstants.ACCOUNTSUMMARYRESPONSE)
                && refreshBalanceInfo == null
                && accountRefreshAPI == null) {

            signAcctSummaryDTO =
                    (SignAcctSummaryDTO) baseProfile.getProperty(
                            MobileConstants.ACCOUNTSUMMARYRESPONSE);

            /* -------------------------------------------------------------
             * Graceful degradation / role handling:
             *
             * 1) If STANDIN_AUTHCODE exists:
             *    -> assign Customer SDA role
             *
             * 2) Else if user is FRIEND + TouchID + FaceID + EnhancedTouchID flags are enabled:
             *    -> check transmit related role
             *    -> if weakProfileIndicator is true -> assign Weak Role
             *    -> else assign normal Customer Role
             * ------------------------------------------------------------- */
            if (baseProfile.containsProperty("STANDIN_AUTHCODE")) {
                MobileRoleUtil.setCustomerSDARole(baseProfile);
            } else if (JPUtils.FRIEND.equalsIgnoreCase(
                    JPUtils.getUserType(baseProfile))
                    && MobileConstants.TRUE.equals(
                    baseProfile.getProperty(MobileConstants.TOUCHID_ROLEFLAG))
                    && MobileConstants.TRUE.equals(
                    baseProfile.getProperty(FaceIDConstants.FACEID_ROLEFLAG))
                    && MobileConstants.TRUE.equals(
                    baseProfile.getProperty(FaceIDConstants.ENHANCED_TOUCHID_ROLEFLAG))) {

                if (!MobileUtils.isTransmitRelatedRoleCheck()) {
                    if (MobileConstants.TRUE.equals(
                            (String) baseProfile.getProperty("weakProfileIndicator"))) {
                        MobileRoleUtil.setCustomerWeakRole(baseProfile);
                    } else {
                        MobileRoleUtil.setCustomerRole(baseProfile);
                    }
                }
            }

            /* -------------------------------------------------------------
             * Remove cached response so it doesn't keep getting reused
             * ------------------------------------------------------------- */
            baseProfile.removeProperty(MobileConstants.ACCOUNTSUMMARYRESPONSE);

        } else {

            /* -------------------------------------------------------------
             * Cache not available OR refresh requested
             * -> call backend/service to fetch account summary
             * ------------------------------------------------------------- */
            log.debug("In account summary call");
            signAcctSummaryDTO = getAccountSummary(acctSummaryRequestDTO);
        }

        /* -------------------------------------------------------------
         * FPS / M63 Dashboard handling:
         *
         * This block executes only when:
         * - client is NOT thin client
         * - AND user is coming from SPK enroll-from-joining flow
         * ------------------------------------------------------------- */
        if (MobileConstants.FALSE.equals(MobileUtils.isThinClient(baseProfile))
                && MobileConstants.TRUE.equals(
                baseProfile.getProperty(MobileConstants.SPK_ENROLL_FROM_JOING))) {

            /* -------------------------------------------------------------
             * If user is eligible for Sneak Peek and M3 is supported:
             * - set common elements and role parameters
             * - get module redirect
             * - set additional flow info and navigation request objects
             * ------------------------------------------------------------- */
            if (MobileConstants.TRUE.equalsIgnoreCase(
                    (String) baseProfile.getProperty(MobileConstants.SP_SNEAK_PEEK_ELIGIBLE))
                    && MobileUtils.isM3SupportEnabled(signAcctSummaryDTO)) {

                MobileUtils.setCommonElements(signAcctSummaryDTO);
                MobileUtils.setUserRoleBasedParameters(signAcctSummaryDTO);

                String mktModuleRedirect =
                        MobileUtils.getModuleRedirect(
                                MobileConstants.RT_ACCOUNTSUMMARY,
                                signAcctSummaryDTO);

                log.debug("mkt_redirect_module set in baseDTO =>" + mktModuleRedirect);
                setASDPFlowInfo(signAcctSummaryDTO);
                setCreditCardNavigationReq(signAcctSummaryDTO);
            }

            /* -------------------------------------------------------------
             * If user should land on M63 Dashboard:
             * - Check M63 dashboard flag in profile
             * - Check org code status is valid
             * - Ensure request is NOT from FPS deep drop
             *
             * If yes:
             * - Mark account as card-only
             * - Set transaction ID as M63_DASHBOARD
             * - Set M63FLOW flag in baseProfile
             * ------------------------------------------------------------- */
            if (MobileConstants.TRUE.equalsIgnoreCase(
                    MobileUtils.getM63Dashboard(baseProfile))
                    && MobileConstants.TRUE.equalsIgnoreCase(
                    getOrgCodeStatus(signAcctSummaryDTO))
                    && !fromFPSDeepDrop) {

                signAcctSummaryDTO.setIsCardOnlyAcc(true);
                signAcctSummaryDTO.setTransID(MobileConstants.M63_DASHBOARD);
                baseProfile.setProperty(MobileConstants.M63FLOW, MobileConstants.TRUE);

            }
            /* -------------------------------------------------------------
             * Else if user should land on M63 Retail Dashboard:
             * - Mark account as NOT card-only
             * - If BAU transId flag is set, remove it and do not override transId
             * - Else set transId to M63RETAIL_DASHBOARD
             * - Set M63RETAILFLOW in baseProfile
             * ------------------------------------------------------------- */
            else if (MobileConstants.TRUE.equalsIgnoreCase(
                    M63CoreUtils.getM63RetailDashboard(baseProfile))) {

                signAcctSummaryDTO.setIsCardOnlyAcc(false);

                if (baseProfile.getProperty("setBAUTransIdForAS") != null
                        && (Boolean) baseProfile.getProperty("setBAUTransIdForAS")) {
                    baseProfile.removeProperty("setBAUTransIdForAS");
                } else {
                    signAcctSummaryDTO.setTransID(
                            MobileConstants.M63RETAIL_DASHBOARD);
                }

                baseProfile.setProperty(
                        MobileConstants.M63RETAILFLOW, MobileConstants.TRUE);
            }
        }

        /* -------------------------------------------------------------
         * Supported MFA handling:
         * If supportedMfaTypeList exists in baseProfile:
         * - attach it into response DTO so UI can render MFA options
         * ------------------------------------------------------------- */
        if (baseProfile.getProperty("supportedMfaTypeList") != null) {
            List<MfaDetails> mfaListDTO =
                    (List<MfaDetails>) baseProfile.getProperty("supportedMfaTypeList");
            signAcctSummaryDTO.setMfaDetails(mfaListDTO);
        }

        /* -------------------------------------------------------------
         * Current MFA type handling:
         * If currentMfaType exists in baseProfile:
         * - set it into response DTO so UI knows active MFA type
         * ------------------------------------------------------------- */
        if (baseProfile.getProperty("currentMfaType") != null) {
            String currentMfaType =
                    (String) baseProfile.getProperty("currentMfaType");
            signAcctSummaryDTO.setCurrentMfaType(currentMfaType);
        }

        /* -------------------------------------------------------------
         * Mobile number masking:
         * - Read mobile number and country code from profile
         * - Prefer customer_phone_number_countrycode
         * - Fallback to FPSConstants.COUNTRYCODE_FOR_MOBILENUMBER
         * - Mask last digits using replaceNumWithX
         * ------------------------------------------------------------- */
        String mobileNo = null;
        String phoneNumberCountryCode = null;

        if (baseProfile.getProperty(MobileConstants.CUSTOMER_MOBILE_NUMBER) != null) {
            mobileNo = (String) baseProfile.getProperty(
                    MobileConstants.CUSTOMER_MOBILE_NUMBER);
        }

        if (baseProfile.getProperty(
                MobileConstants.CUSTOMER_PHONE_NUMBER_COUNTRYCODE) != null) {
            phoneNumberCountryCode =
                    (String) baseProfile.getProperty(
                            MobileConstants.CUSTOMER_PHONE_NUMBER_COUNTRYCODE);
        } else if (baseProfile.getProperty(
                FPSConstants.COUNTRYCODE_FOR_MOBILENUMBER) != null) {
            phoneNumberCountryCode =
                    (String) baseProfile.getProperty(
                            FPSConstants.COUNTRYCODE_FOR_MOBILENUMBER);
        }

        /* -------------------------------------------------------------
         * Log the country code values coming from different sources
         * (JFX response vs Live Bank API)
         * ------------------------------------------------------------- */
        log.error("phoneNumberCountryCode from JFX response is : "
                + baseProfile.getProperty(
                MobileConstants.CUSTOMER_PHONE_NUMBER_COUNTRYCODE)
                + " from Live Bank API is : "
                + baseProfile.getProperty(
                FPSConstants.COUNTRYCODE_FOR_MOBILENUMBER));

        /* -------------------------------------------------------------
         * If mobile number is present:
         * - If country code is present and mobile starts with that country code:
         *      -> remove country code from mobile number
         *      -> mask remaining digits and prefix with +countryCode
         * - Else:
         *      -> mask full mobile number directly
         * ------------------------------------------------------------- */
        if (mobileNo != null) {
            if (StringUtils.isNotEmpty(phoneNumberCountryCode)
                    && mobileNo.startsWith(phoneNumberCountryCode)) {

                String mobileNoNeedMasked =
                        mobileNo.substring(phoneNumberCountryCode.length());

                signAcctSummaryDTO.setMaskedMobileNo(
                        MobileConstants.PLUS + phoneNumberCountryCode
                                + MobileUtils.replaceNumWithX(
                                mobileNoNeedMasked, digits: 4)); // ⚠ verify digits

            } else {
                signAcctSummaryDTO.setMaskedMobileNo(
                        MobileUtils.replaceNumWithX(mobileNo, digits: 4)); // ⚠ verify digits
            }
        }

        /* -------------------------------------------------------------
         * Beta functions list:
         * If betaFunctionsList exists in baseProfile:
         * - copy it into response DTO
         * ------------------------------------------------------------- */
        if (baseProfile.getProperty("betaFunctionsList") != null) {
            if (log.isDebugEnabled()) {
                log.debug("betaFunctionsList in AS is not null.");
            }
            signAcctSummaryDTO.setBetaFunctionsList(
                    (List<String>) baseProfile.getProperty("betaFunctionsList"));
        }

        /* -------------------------------------------------------------
         * Staff / encryption logic:
         *
         * If staffIndFlag exists in response DTO:
         * - Read encryption applicability config
         * - Check multiple profile conditions (thick app, windows wrapper, tellUsStaffFlag)
         * - If all conditions match:
         *      -> encrypt staffIndFlag and mark encryptionApplicable = true
         *      -> remove tellUsStaffFlag from baseProfile
         * - Else:
         *      -> encryptionApplicable = false
         * ------------------------------------------------------------- */
        try {
            if (StringUtils.isNotEmpty(signAcctSummaryDTO.getStaffIndFlag())) {

                String gmoB2EApplicable =
                        MobileProperties.getProperty(
                                MobileConstants.IS_E2E_ENCRYPTION_APPLICABLE);

                if (baseProfile != null
                        && MobileConstants.TRUE.equals(gmoB2EApplicable)
                        && MobileConstants.TRUE.equals(
                        baseProfile.getProperty(
                                MobileConstants.GLBMOBILE_THICK_APPID))
                        && MobileConstants.TRUE.equals(
                        baseProfile.getProperty(
                                MobileConstants.ISWINDOWSWRAPPER))
                        && MobileConstants.TRUE.equalsIgnoreCase(
                        (String) baseProfile.getProperty("tellUsStaffFlag"))) {

                    log.error("StaffIndFlag encryption starts");

                    String encStaffIndFlag =
                            DigipassUtil.getEncryptedActivationPassword(
                                    signAcctSummaryDTO.getStaffIndFlag());

                    signAcctSummaryDTO.setStaffIndFlag(encStaffIndFlag);
                    signAcctSummaryDTO.setEncryptionApplicable("true");
                    baseProfile.removeProperty("tellUsStaffFlag");

                } else {
                    signAcctSummaryDTO.setEncryptionApplicable("false");
                }
            }
        } catch (Exception e) {
            /* -------------------------------------------------------------
             * If encryption processing fails, log error and continue
             * (do not break account summary response)
             * ------------------------------------------------------------- */
            if (log.isErrorEnabled()) {
                log.error("Exception in accountSummary() ======= AccountSummaryResource == "
                        + ExceptionUtils.getStackTrace(e));
            }
        }

        /* -------------------------------------------------------------
         * Set common response fields and role-based parameters
         * before returning to UI
         * ------------------------------------------------------------- */
        MobileUtils.setCommonElements(signAcctSummaryDTO);
        MobileUtils.setUserRoleBasedParameters(signAcctSummaryDTO);

        /* -------------------------------------------------------------
         * Log total time taken for account summary call
         * ------------------------------------------------------------- */
        if (timeLog.isInfoEnabled()) {
            timeLog.info("Time Taken for AS jws call : "
                    + (System.currentTimeMillis() - startTime) + " ms");
        }

        /* -------------------------------------------------------------
         * Return successful account summary response
         * ------------------------------------------------------------- */
        return signAcctSummaryDTO;

    } catch (Exception e) {

        /* -------------------------------------------------------------
         * Global error handling:
         * If any exception occurs:
         * - Return error response with generic error code
         * - Log stacktrace for debugging
         * ------------------------------------------------------------- */
        signAcctSummaryDTO = new SignAcctSummaryDTO();
        signAcctSummaryDTO.setStatus(MobileConstants.ERROR);
        signAcctSummaryDTO.setErrorCode(MobileConstants.GENERIC_ERROR);
        signAcctSummaryDTO.setTransID(MobileConstants.TRANSID_TRSEXCEPTION);

        if (log.isErrorEnabled()) {
            log.error("Exception in accountSummary() ======= AccountSummaryResource == "
                    + ExceptionUtils.getStackTrace(e));
        }

        return signAcctSummaryDTO;
    }
}
