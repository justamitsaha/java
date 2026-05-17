package com.saha.amit.dsa;

import java.util.BitSet;

public class DsaPractice {


    public static void main(String[] args) {
        int a = 10, b = 20;
        a = a + b; // a = 30
        b = a - b; // b = 30 - 20 = 10
        a = a - b; // a = 30 - 10 = 20

        System.out.println("a: " + a + ", b: " + b); // Prints: a: 10, b: 5
    }
}
