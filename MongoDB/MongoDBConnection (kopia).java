package com.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class MongoDBConnection {
    public static void main(String[] args) {

        // كلمة السر من متغير بيئي بدل كتابتها مباشرة
        String connectionString = System.getenv("MONGODB_URI");

        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("sample_mflix");
        var collection = database.getCollection("movies");

        List<Document> docs = collection.find(new Document("year", 1975)).into(new ArrayList<>());

        List<Movie> movieList = docs.stream().map(doc -> {
            String title = doc.getString("title");
            int year = doc.getInteger("year", 0);
            int runtime = doc.getInteger("runtime", 0);
            List<String> genres = doc.getList("genres", String.class, new ArrayList<>());
            List<String> cast = doc.getList("cast", String.class, new ArrayList<>());
            List<String> languages = doc.getList("languages", String.class, new ArrayList<>());
            double imdbRating = 0;
            Document imdb = (Document) doc.get("imdb");
            if (imdb != null && imdb.get("rating") != null) {
                imdbRating = ((Number) imdb.get("rating")).doubleValue();
            }
            return new Movie(title, year, runtime, genres, cast, languages, imdbRating);
        }).toList();

        System.out.println("Antal filmer:- " + MovieAnalyzer.countMoviesFrom1975(movieList));
        System.out.println("Längsta film (minuter):- " + MovieAnalyzer.longestRuntime(movieList));
        System.out.println("Antal unika arter:- " + MovieAnalyzer.uniqueGenres(movieList));
        System.out.println("Filmens högst rankade skådespelare:- " + MovieAnalyzer.castOfHighestRated(movieList));
        System.out.println("Filmen med minst antal skådespelare:- " + MovieAnalyzer.movieWithLeastCast(movieList));
        System.out.println("Skådespelare i mer än en film:- " + MovieAnalyzer.actorsInMoreThanOneMovie(movieList));
        System.out.println("Mest förekommande skådespelare:- " + MovieAnalyzer.mostFrequentActor(movieList));
        System.out.println("Antal unika språk:- " + MovieAnalyzer.uniqueLanguages(movieList));
        System.out.println("Det finns filmer med samma namn:- " + MovieAnalyzer.duplicateTitles(movieList));

        System.out.println("\n - Higher Order Functions -");
        List<Movie> longMovies = MovieAnalyzer.filterMovies(movieList, m -> m.getRuntime() > 120);
        System.out.println("Filmer längre än 120 minuter:- " + longMovies.size());

        List<String> titles = MovieAnalyzer.extractFromMovies(movieList, Movie::getTitle);
        System.out.println("Första 3 titlarna:- " + titles.subList(0, 3));

        MovieAnalyzer.findMovieBy(movieList,
                        Comparator.comparingDouble(Movie::getImdbRating))
                .ifPresent(m -> System.out.println("Den högst rankade filmen:- " + m.getTitle()));

        mongoClient.close();
    }
}