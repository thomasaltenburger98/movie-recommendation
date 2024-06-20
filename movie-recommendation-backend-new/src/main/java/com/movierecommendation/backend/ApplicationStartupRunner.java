package com.movierecommendation.backend;

import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.model.Genre;
import com.movierecommendation.backend.model.User;
import com.movierecommendation.backend.repository.FilmRepository;
import com.movierecommendation.backend.repository.GenreRepository;
import com.movierecommendation.backend.repository.UserRepository;
import com.movierecommendation.backend.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthService authService;

    private Map<Long, Long> filmTmbdAssociation = new HashMap<>();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        addTestUser();
        loadTmdbData();
        loadFilmData();
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
        String csvFile = "../ml-latest-small/links.csv";
        String line;
        String csvSplitBy = ",";
        int i = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                if (i > 1000) {
                    break;
                }
                i++;
                String[] filmData = line.split(csvSplitBy);
                if (filmData.length == 3) {
                    Long filmId = Long.parseLong(filmData[0]);
                    Long tmdbId = Long.parseLong(filmData[2]);
                    filmTmbdAssociation.put(filmId, tmdbId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFilmData() {
        System.out.println("Loading film data");
        String csvFile = "../ml-latest-small/movies.csv";
        String line;
        String csvSplitBy = ",";
        int i = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                if (i > 1000) {
                    break;
                }
                i++;
                String[] filmData = line.split(csvSplitBy);
                if (filmData.length == 3) {
                    Film film = new Film();
                    film.setId(Long.parseLong(filmData[0]));
                    film.setTitle(filmData[1]);
                    //film.setBeschreibung("");
                    //film.setErscheinungsjahr(new Date());

                    String[] genreNames = filmData[2].split("\\|");
                    List<Genre> genres = new ArrayList<>();
                    for (String genreName : genreNames) {
                        Genre genre = genreRepository.findByName(genreName);
                        if (genre == null) {
                            genre = new Genre();
                            genre.setName(genreName);
                            genreRepository.save(genre);
                        }
                        genres.add(genre);
                    }
                    film.setGenres(genres);

                    // set TMDB id from filmTmbdAssociation
                    Long tmdbId = filmTmbdAssociation.get(film.getId());
                    if (tmdbId != null) {
                        film.setTmdbId(tmdbId);
                    }

                    filmRepository.save(film);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
