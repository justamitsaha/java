package com.saha.amit.collection;

import java.util.*;

/*
This class sorts the list of movies based on their rating and earnings.
1. We will be using the Comparable interface to sort the movies based on their rating, natural order
2. we will be using the Comparator interface to sort the movies based on their earnings.
 */
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
            return "{" + id + "." + name + ", rating='" + rating + ", earnings='" + earnings + "}/\n";
        }


        @Override
        public int compareTo(Movies o) {
            return Float.compare(this.rating, o.rating);
        }
    }


    public static void main(String[] args) {
        var movies = generateMoviesList();
        System.out.println("Unsorted Movie List->\n" + movies);
        Collections.sort(movies);
        System.out.println("Sorted movie list based on rating->\n" + movies);
        Collections.sort(movies, new EarningComparator());
        System.out.println("Sorted movie list based on earning->\n" + movies);
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
