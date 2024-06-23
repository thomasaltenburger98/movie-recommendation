package com.movierecommendation.backend.service;

import com.movierecommendation.backend.model.Rating;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVDataManager {
    private static final String PATH_TO_FOLDER = "../ml-latest-small";
    private List<Rating> RATINGS_CACHE = new ArrayList<>();

    public CSVDataManager() {
    }

    public String getFilePathOfCSVFile(CsvFile csvFile) {
        return PATH_TO_FOLDER + "/" + csvFile.getFileName();
    }

    public void writeRatingToCSV(Rating rating) {
        RATINGS_CACHE.add(rating);
    }
    private void resetRatingCache() {
        RATINGS_CACHE = new ArrayList<>();
    }

    // TODO use this class to get CSV file in ApplicationStartupRunner.java

    @Scheduled(fixedDelay = 10000)
    protected void writeRatingsToCSVFile() {
        System.out.println("Writing ratings to CSV file");
        String csvFile = getFilePathOfCSVFile(CsvFile.RATINGS);
        System.out.println(csvFile);
        try {
            if (!RATINGS_CACHE.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, true));
                     //CSVPrinter printer = new CSVPrinter(bw, CSVFormat.DEFAULT)
                ) {
                    for (Rating rating : RATINGS_CACHE) {
                        writer.write(rating.toCSVString());
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resetRatingCache();
    }
}
