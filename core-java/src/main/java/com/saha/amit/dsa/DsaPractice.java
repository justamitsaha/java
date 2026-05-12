package com.saha.amit.dsa;

public class DsaPractice {

    public static void main(String[] args) {
        // Note: Start at 1 to avoid the 0-loop trap

        // Loop 1: Bitwise
        long startTime = System.nanoTime();
        int count1 = 0;
        for (long i = 1; i < Integer.MAX_VALUE; i = i << 1) count1++;
        long durationShift = System.nanoTime() - startTime;

        // Loop 2: Multiplication
        startTime = System.nanoTime();
        int count2 = 0;
        for (long i = 1; i < Integer.MAX_VALUE; i = i * 2) count2++;
        long durationMult = System.nanoTime() - startTime;

        System.out.println("Shift Time: " + durationShift + " ns");
        System.out.println("Mult Time:  " + durationMult + " ns");
    }


}
