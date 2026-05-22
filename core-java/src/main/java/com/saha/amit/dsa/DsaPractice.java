package com.saha.amit.dsa;

import java.util.BitSet;

public class DsaPractice {

    //change the 5th bit of 54656 to 1
    public static void main(String[] args) {
        int i = 54656;
        int mask = 1 << 5;
        int result = i | mask;
        System.out.println("Original Value: " + Integer.toBinaryString(i));
        System.out.println("Masked Value:   " + Integer.toBinaryString(mask));
        System.out.println("Result Value:   " + Integer.toBinaryString(result));
    }
}
