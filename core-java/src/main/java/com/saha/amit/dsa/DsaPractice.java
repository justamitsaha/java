package com.saha.amit.dsa;

public class DsaPractice {


    public static void main(String[] args) {
        //All these are divide by 2
        int i = 128;
        System.out.println(i / 2);
        System.out.println(i = i / 2);
        i = 128;
        System.out.println(i >> 1);
        System.out.println(i >> 2); //divide by 4

        System.out.println(i << 2); //multiply by 4

        System.out.println((i & 1)); //odd even test
        System.out.println(i);

    }
}
