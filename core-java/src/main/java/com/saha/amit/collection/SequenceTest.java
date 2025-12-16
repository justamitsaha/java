package com.saha.amit.collection;

import com.github.javafaker.Faker;

import java.util.LinkedHashMap;
import java.util.SequencedMap;

public class SequenceTest {
    public static void main(String[] args) {
        SequencedMap<Integer, String> stringSequencedMap = new LinkedHashMap<>();
        Faker faker = new Faker();
        for (int i =0; i< 5;i++){
            stringSequencedMap.put(i,faker.gameOfThrones().character());
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
