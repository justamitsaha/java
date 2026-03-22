package com.saha.amit.collection;


import java.util.LinkedHashMap;
import java.util.SequencedMap;

public class SequenceTest {
    public static void main(String[] args) {
        SequencedMap<Integer, String> stringSequencedMap = new LinkedHashMap<>();

        for (int i =0; i< 5;i++){
            stringSequencedMap.put(i,"hello"+i);
        }

        System.out.println(stringSequencedMap);
        System.out.println("<----------------");
        System.out.println(stringSequencedMap.reversed());
        System.out.println("<----------------");
        System.out.println(stringSequencedMap.pollFirstEntry());
        System.out.println(stringSequencedMap.pollLastEntry());
        System.out.println(stringSequencedMap.firstEntry());
        System.out.println(stringSequencedMap.lastEntry());
    }
}
