package com.saha.amit.dsa;

public class DsaPractice {

    public static void main(String[] args) {
        decimalToBinary(-7);
        binaryToDecimal("101100010");

    }

    public static void decimalToBinary(int n) {
        String binary = Integer.toBinaryString(n);
        System.out.println(binary);

        String result = "";
        for (int i = n; i >= 1; i = i / 2)
            if (i % 2 == 0)
                result = "0" + result;
            else
                result = "1" + result;
        System.out.println(result);

    }

    public static void binaryToDecimal(String s) {
        int value = Integer.parseInt(s, 2);
        System.out.println(value);
        int val2 = 0;
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++)
            val2 = val2 + Integer.parseInt(String.valueOf(c[i])) * (int) Math.pow(2, c.length - (i + 1));
        System.out.println(val2);
    }


}
