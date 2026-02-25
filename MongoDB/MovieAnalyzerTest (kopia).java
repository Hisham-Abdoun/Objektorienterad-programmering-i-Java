package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

public class MovieAnalyzerTest {

    private List<Movie> testMovies;

    @BeforeEach
    void setUp() {
        testMovies = List.of(
                new Movie("Film A", 1975, 120, List.of("Drama", "Action"), List.of("Actor1", "Actor2", "Actor3"), List.of("English", "French"), 8.5),
                new Movie("Film B", 1975, 230, List.of("Comedy", "Drama"), List.of("Actor1", "Actor4"), List.of("English", "Spanish"), 7.0),
                new Movie("Film C", 1975, 90, List.of("Horror"), List.of("Actor5"), List.of("German"), 9.0),
                new Movie("Film A", 1975, 100, List.of("Action"), List.of("Actor2", "Actor6"), List.of("French"), 6.0)
        );
    }

    @Test
    void testCountMoviesFrom1975() {
        assertEquals(4, MovieAnalyzer.countMoviesFrom1975(testMovies));
    }

    @Test
    void testLongestRuntime() {
        assertEquals(230, MovieAnalyzer.longestRuntime(testMovies));
    }

    @Test
    void testUniqueGenres() {
        assertEquals(4, MovieAnalyzer.uniqueGenres(testMovies));
    }

    @Test
    void testCastOfHighestRated() {
        assertTrue(MovieAnalyzer.castOfHighestRated(testMovies).contains("Actor5"));
    }

    @Test
    void testMovieWithLeastCast() {
        assertEquals("Film C", MovieAnalyzer.movieWithLeastCast(testMovies));
    }

    @Test
    void testActorsInMoreThanOneMovie() {
        assertEquals(2, MovieAnalyzer.actorsInMoreThanOneMovie(testMovies));
    }

    @Test
    void testMostFrequentActor() {
        assertTrue(MovieAnalyzer.mostFrequentActor(testMovies).equals("Actor1") ||
                MovieAnalyzer.mostFrequentActor(testMovies).equals("Actor2"));
    }

    @Test
    void testUniqueLanguages() {
        assertEquals(4, MovieAnalyzer.uniqueLanguages(testMovies));
    }

    @Test
    void testDuplicateTitles() {
        assertTrue(MovieAnalyzer.duplicateTitles(testMovies));
    }

    @Test
    void testFilterMovies() {
        assertEquals(2, MovieAnalyzer.filterMovies(testMovies, m -> m.getRuntime() > 100).size());
    }

    @Test
    void testExtractFromMovies() {
        assertTrue(MovieAnalyzer.extractFromMovies(testMovies, Movie::getTitle).contains("Film A"));
    }

    @Test
    void testFindMovieBy() {
        Optional<Movie> result = MovieAnalyzer.findMovieBy(testMovies,
                Comparator.comparingDouble(Movie::getImdbRating));
        assertTrue(result.isPresent());
        assertEquals("Film C", result.get().getTitle());
    }
        @Test
        void testEmptyList() {
            List<Movie> empty = List.of();
            assertEquals(0, MovieAnalyzer.countMoviesFrom1975(empty));
            assertEquals(0, MovieAnalyzer.longestRuntime(empty));
            assertFalse(MovieAnalyzer.duplicateTitles(empty));
        }
}