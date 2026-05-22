package com.saha.amit.dsa;

import java.util.BitSet;

public class DsaPractice {


    //find the 5th bit of 439802
    public static void main(String[] args) {
        int i = 439802;
        int mask = 1 << 5;
        System.out.println(Integer.toBinaryString(mask));
        if ((i & mask) ==0)
            System.out.println("5th element is 0");
        else
            System.out.println("5th element is 1");
        System.out.println(Integer.toBinaryString(i));
    }
}
