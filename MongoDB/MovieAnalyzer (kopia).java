package com.example;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MovieAnalyzer {


    public static long countMoviesFrom1975(List<Movie> movies) {
        return movies.stream()
                .filter(m -> m.getYear() == 1975)
                .count();
    }


    public static int longestRuntime(List<Movie> movies) {
        return movies.stream()
                .mapToInt(Movie::getRuntime)
                .max()
                .orElse(0);
    }
public static long uniqueGenres(List<Movie> movies) {
        return movies.stream()
                .flatMap(m -> m.getGenres().stream())
                .distinct()
                .count();
    }


    public static List<String> castOfHighestRated(List<Movie> movies) {
        return movies.stream()
                .max((a, b) -> Double.compare(a.getImdbRating(), b.getImdbRating()))
                .map(Movie::getCast)
                .orElse(List.of());
    }


    public static String movieWithLeastCast(List<Movie> movies) {
        return movies.stream()
                .min((a, b) -> Integer.compare(a.getCast().size(), b.getCast().size()))
                .map(Movie::getTitle)
                .orElse("inte tillgänglig");
    }


    public static long actorsInMoreThanOneMovie(List<Movie> movies) {
        return movies.stream()
                .flatMap(m -> m.getCast().stream())
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()))
                .values().stream()
                .filter(count -> count > 1)
                .count();
    }


    public static String mostFrequentActor(List<Movie> movies) {
        return movies.stream()
                .flatMap(m -> m.getCast().stream())
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("inte tillgänglig");
    }


    public static long uniqueLanguages(List<Movie> movies) {
        return movies.stream()
                .flatMap(m -> m.getLanguages().stream())
                .distinct()
                .count();
    }


    public static boolean duplicateTitles(List<Movie> movies) {
        return movies.stream()
                .collect(Collectors.groupingBy(Movie::getTitle, Collectors.counting()))
                .values().stream()
                .anyMatch(count -> count > 1);
    }


    public static List<Movie> filterMovies(List<Movie> movies, Predicate<Movie> predicate) {
        return movies.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static <T> List<T> extractFromMovies(List<Movie> movies, Function<Movie, T> extractor) {
        return movies.stream()
                .map(extractor)
                .collect(Collectors.toList());
    }

    public static Optional<Movie> findMovieBy(List<Movie> movies, Comparator<Movie> comparator) {
        return movies.stream()
                .max(comparator);
    }
}