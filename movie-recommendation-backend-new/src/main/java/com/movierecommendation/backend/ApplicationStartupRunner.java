package com.movierecommendation.backend;

import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.model.Genre;
import com.movierecommendation.backend.repository.FilmRepository;
import com.movierecommendation.backend.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        loadFilmData();
    }

    private void loadFilmData2() {
        String filePath = System.getProperty("user.dir");
        System.out.println("Current file path: " + filePath);
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
                    film.setBeschreibung("");
                    film.setErscheinungsjahr(new Date());

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
                    filmRepository.save(film);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
