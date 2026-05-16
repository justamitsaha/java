package com.saha.amit.dsa;

public class DsaPractice {

    public static void main(String[] args) {
        int n = 77;
        System.out.println(Integer.toBinaryString(n));

        // 2. Optimized manual approach
        if (n == 0) {
            System.out.println("0");
            return;
        }

        // Since we are concatenating StringBuilder  is better than String
        StringBuilder sb = new StringBuilder();
        // We use a temp variable to keep 'n' intact if needed later
        for (int i = n; i > 0; i = i >> 2) {
            sb.append(i % 2);
        }

        // Since we appended to the end, we must reverse it
        System.out.println(sb.reverse().toString());
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
