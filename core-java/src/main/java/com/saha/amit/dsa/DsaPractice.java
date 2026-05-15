package com.saha.amit.dsa;

public class DsaPractice {

    public static void main(String[] args) {
        String s = "1001101";
        int value = Integer.parseInt(s, 2);
        System.out.println(value);

        int val2 = 0;
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++)
            val2 = val2 + Integer.parseInt(String.valueOf(c[i])) * (int) Math.pow(2, c.length - (i + 1));
        System.out.println(val2);

        // Optimized manual approach (Horner's Method)
        val2 = 0;
        // Iterate through each character of the binary string
        for (int i = 0; i < s.length(); i++) {
            // Get the numeric value of the current bit ('0' or '1')
            int bit = s.charAt(i) - '0';

            // Horner's Method: Multiply current result by base (2) and add next digit
            val2 = (val2 * 2) + bit;
        }
        System.out.println(val2);
    }


    //        // Note: Start at 1 to avoid the 0-loop trap
//
//        // Loop 1: Bitwise
//        long startTime = System.nanoTime();
//        int count1 = 0;
//        for (long i = 1; i < Integer.MAX_VALUE; i = i << 1) count1++;
//        long durationShift = System.nanoTime() - startTime;
//
//        // Loop 2: Multiplication
//        startTime = System.nanoTime();
//        int count2 = 0;
//        for (long i = 1; i < Integer.MAX_VALUE; i = i * 2) count2++;
//        long durationMult = System.nanoTime() - startTime;
//
//        System.out.println("Shift Time: " + durationShift + " ns");
//        System.out.println("Mult Time:  " + durationMult + " ns");


}
