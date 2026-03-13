package com.saha.amit.basic;

public class Tricks {
    /*
In Java, double follows the IEEE‑754 binary floating‑point standard. Due to which  Most decimal fractions cannot be represented exactly in binary. Reason
- Decimal → base‑10
- Computer → base‑2
- Just like 1/3 = 0.333… never ends in decimal,
- 0.1 = 0.000110011… never ends in binary
- So this is not Java problem but programming problem happening in other languages as well
- So 0.1 , 0.2 and 0.3 are not exact in binary.
- In case of 0.1 and 0.3 the approximation cancels each other hence match1 prints but match2 doesn't, this doesn't happen for 0.1 and 0.2

Possible solutions
        d = 0.1 + 0.2;
        if (Math.abs(d - 0.3) < 1e-9) {
            System.out.println("match3");
        }

        BigDecimal e = new BigDecimal("0.1")
                .add(new BigDecimal("0.2"));

        if (e.compareTo(new BigDecimal("0.3")) == 0) {
            System.out.println("match4");
        }

 */
    public static void main(String[] args) {
        double d = 0.1 + 0.3;
        if (d == 0.4)
            System.out.println("match1");
        d = 0.1 + 0.2;
        if (d == 0.3)
            System.out.println("match2");
    }
}
