package com.movierecommendation.backend.service;

import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.model.Rating;
import com.movierecommendation.backend.model.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CSVDataManager {
    private static final String PATH_TO_FOLDER = "../ml-latest-small";
    private List<Rating> RATINGS_CACHE = new ArrayList<>();
    private List<Rating> DELETE_RATINGS_CACHE = new ArrayList<>();
    private boolean fileIsLocked = false;

    public CSVDataManager() {
    }

    public String getFilePathOfCSVFile(CsvFile csvFile) {
        return PATH_TO_FOLDER + "/" + csvFile.getFileName();
    }

    public void writeRatingToCSV(Rating rating) {
        RATINGS_CACHE.add(rating);
    }
    public void removeRatingToCSV(Rating rating) {
        DELETE_RATINGS_CACHE.add(rating);
    }
//    private void resetRatingCache() {
//        RATINGS_CACHE = new ArrayList<>();
//    }

    // TODO use this class to get CSV file in ApplicationStartupRunner.java

    @Scheduled(fixedDelay = 10000)
    protected void writeRatingsToCSVFile() {
        System.out.println("Writing ratings to CSV file");
        String csvFile = getFilePathOfCSVFile(CsvFile.RATINGS);
        System.out.println(csvFile);
        try {
            if (!RATINGS_CACHE.isEmpty()) {
                if (this.fileIsLocked) {
                    System.out.println("File is locked");
                    return;
                }
                this.fileIsLocked = true;
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, true));
                     //CSVPrinter printer = new CSVPrinter(bw, CSVFormat.DEFAULT)
                ) {
                    Iterator<Rating> iterator = RATINGS_CACHE.iterator();
                    while (iterator.hasNext()) {
                        Rating rating = iterator.next();
                        writer.write(rating.toCSVString());
                        writer.newLine();
                        writer.flush();
                        iterator.remove();
                        RATINGS_CACHE.remove(rating);
                    }
                }
                this.fileIsLocked = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.fileIsLocked = false;
        }
//        resetRatingCache();
    }

    @Scheduled(fixedDelay = 10000)
    protected void removeRatingsFromCSVFile() {
        System.out.println("Removing ratings from CSV file");
        String csvFile = getFilePathOfCSVFile(CsvFile.RATINGS);
        String tempFile = csvFile + ".tmp";
        System.out.println(csvFile);
        try {
            if (!DELETE_RATINGS_CACHE.isEmpty()) {
                if (this.fileIsLocked) {
                    System.out.println("File is locked");
                    return;
                }
                this.fileIsLocked = true;
                try (Reader in = new FileReader(csvFile);
                     BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                    Iterable<CSVRecord> records = CSVFormat.DEFAULT
                            .withFirstRecordAsHeader()
                            .parse(in);
                    writer.write("userId,movieId,rating,timestamp");
                    writer.newLine();
                    writer.flush();
                    for (CSVRecord record : records) {
                        Long userId = Long.parseLong(record.get("userId"));
                        Long filmId = Long.parseLong(record.get("movieId"));
                        float ratingValue = Float.parseFloat(record.get("rating"));
                        Rating rating = new Rating();
                        rating.setUser(new User(userId));
                        rating.setFilm(new Film(filmId));
                        rating.setTimestampString(record.get("timestamp"));
                        rating.setRatingValue(ratingValue);
                        if (!DELETE_RATINGS_CACHE.contains(rating)) {
                            writer.write(rating.toCSVString());
                            writer.newLine();
                            writer.flush();
                        }
                    }
                }

                DELETE_RATINGS_CACHE.clear();

                Files.delete(Paths.get(csvFile));
                Files.move(Paths.get(tempFile), Paths.get(csvFile));
               // Files.delete(Paths.get(tempFile));
                this.fileIsLocked = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.fileIsLocked = false;
        }
    }

}
