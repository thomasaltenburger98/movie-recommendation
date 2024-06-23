package com.movierecommendation.backend;

import com.movierecommendation.backend.factory.FilmFactory;
import com.movierecommendation.backend.repository.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.model.Genre;
import com.movierecommendation.backend.model.Rating;
import com.movierecommendation.backend.model.User;
import com.movierecommendation.backend.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AuthService authService;

    private Map<Long, Long> filmTmbdAssociation = new HashMap<>();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        clearDatabase();
        long count = filmRepository.count();
        System.out.println("count of films: " + count);
        loadTmdbData();
        loadFilmData();
        loadRatingData();
        addTestUser();
    }

    public void clearDatabase() {
        System.out.println("Clearing database");
        ratingRepository.deleteAllEntities();
        genreRepository.deleteAllEntities();
        filmRepository.deleteAllEntities();
        userRepository.deleteAllEntities();
        tokenRepository.deleteAllEntities();
        System.out.println("Database cleared");
    }

    private void addTestUser() {
        authService.register("test123", "#test123");
    }

    private void loadFilmData2() {
        String filePath = System.getProperty("user.dir");
        System.out.println("Current file path: " + filePath);
    }

    private void loadTmdbData() {
        System.out.println("Loading tmdb data");
        // TODO use CSVDataManager to get file path
        String csvFile = "../ml-latest-small/links.csv";
        try (Reader in = new FileReader(csvFile)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in);
            int i = 1;
            for (CSVRecord record : records) {
                if (i > 1000) {
                    break;
                }
                i++;
                if (record.get("tmdbId").isEmpty()) {
                    continue;
                }
                Long filmId = Long.parseLong(record.get("movieId"));
                Long tmdbId = Long.parseLong(record.get("tmdbId"));
                filmTmbdAssociation.put(filmId, tmdbId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Tmdb data loaded");
    }

    private void loadFilmData() {
        System.out.println("Loading film data");
        // TODO use CSVDataManager to get file path
        String csvFile = "../ml-latest-small/movies.csv";
        try (Reader in = new FileReader(csvFile)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in);
            int i = 1;
            FilmFactory filmFactory = new FilmFactory();
            Map<String, Genre> genreMap = new HashMap<>();
            genreRepository.findAll().forEach(genre -> genreMap.put(genre.getName(), genre));
            for (CSVRecord record : records) {
                if (i > 1000) {
                    break;
                }
                i++;
                Film film = filmFactory.createFromRecord(record, genreMap);
                List<Genre> unsavedGenres = new ArrayList<>();
                for (Genre genre: film.getGenres()) {
                    if (!genreMap.containsKey(genre.getName())) {
                        genreMap.put(genre.getName(), genre);
                        if (genre.getId() == null) {
                            unsavedGenres.add(genre);
                        }
                    }
                }
                if (!unsavedGenres.isEmpty()) {
                    genreRepository.saveAll(unsavedGenres);
                }
                Long tmdbId = filmTmbdAssociation.get(film.getId());
                if (tmdbId != null) {
                    film.setTmdbId(tmdbId);
                }
                filmRepository.save(film);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Film data loaded");
    }

    private void loadRatingData() {
        System.out.println("Loading rating data");
        // TODO use CSVDataManager to get file path
        String csvFile = "../ml-latest-small/ratings.csv";
        Map<Long, User> userMap = new HashMap<>();

        try (Reader in = new FileReader(csvFile)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in);
            int i = 1;
            for (CSVRecord record : records) {
                if (i > 1000) {
                    break;
                }
                i++;
                Long userId = Long.parseLong(record.get("userId"));
                User user;
                if (!userMap.containsKey(userId)) {
                    user = addUser("user" + userId, userId);
                    userMap.put(userId, user);
                } else {
                    user = userMap.get(userId);
                }

                Long filmId = Long.parseLong(record.get("movieId"));
                float ratingValue = Float.parseFloat(record.get("rating"));
                Rating rating = new Rating();
                rating.setUser(user);
                Film film = filmRepository.findById(filmId.intValue()).orElse(null);
                rating.setFilm(film);
                rating.setRatingValue(ratingValue);
                ratingRepository.save(rating);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Rating data loaded");
    }

    private User addUser(String username, long userID) {
        User user = new User();
        // TODO set canLogin to false
        user.setUsername(username);
        user.setPassword("#" + username);
        userRepository.save(user);
        return user;
    }

}
