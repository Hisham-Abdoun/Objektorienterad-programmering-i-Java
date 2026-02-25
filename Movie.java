package com.example;

import java.util.List;

public final class Movie {
    private final String title;
    private final int year;
    private final int runtime;
    private final List<String> genres;
    private final List<String> cast;
    private final List<String> languages;
    private final double imdbRating;

    public Movie(String title, int year, int runtime, List<String> genres, List<String> cast, List<String> languages, double imdbRating) {
        this.title = title;
        this.year = year;
        this.runtime = runtime;
        this.genres = List.copyOf(genres);
        this.cast = List.copyOf(cast);
        this.languages = List.copyOf(languages);
        this.imdbRating = imdbRating;
    }

    public String getTitle() { return title; }
    public int getYear() { return year; }
    public int getRuntime() { return runtime; }
    public List<String> getGenres() { return genres; }
    public List<String> getCast() { return cast; }
    public List<String> getLanguages() { return languages; }
    public double getImdbRating() { return imdbRating; }
}