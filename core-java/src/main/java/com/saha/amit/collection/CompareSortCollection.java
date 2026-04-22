package com.saha.amit.collection;

import java.util.*;

public class CompareSortCollection {
    static class Movies implements Comparable<Movies> {
        String id;
        String name;
        int earnings;
        float rating;

        public Movies(String id, String name, int earnings, float rating) {
            this.id = id;
            this.name = name;
            this.earnings = earnings;
            this.rating = rating;
        }

        @Override
        public String toString() {
            return "Movies{id='" + id + '\'' + ", name='" + name + "}";
        }


        @Override
        public int compareTo(Movies o) {
            return Float.compare(this.rating, o.rating);
        }
    }


    public static void main(String[] args) {
        var movies = generateMoviesList();
        System.out.println(movies);
        Collections.sort(movies);
        System.out.println(movies);
        Collections.sort(movies, new EarningComparator());
        System.out.println(movies);
    }

    public static List<Movies> generateMoviesList() {
        List<Movies> moviesList = new ArrayList<>();
        moviesList.add(new Movies("1", "No Country for old men", 300_000, 9.3f));
        moviesList.add(new Movies("2", "Shawshank redemption", 30_000, 9.8f));
        moviesList.add(new Movies("3", "Transformer", 30_000_000, 4.8f));
        moviesList.add(new Movies("4", "Avatar", 300_000_000, 6.8f));
        return moviesList;
    }

    static class EarningComparator implements Comparator<Movies> {

        @Override
        public int compare(Movies o1, Movies o2) {
            return o1.earnings - o2.earnings;
        }
    }
}
