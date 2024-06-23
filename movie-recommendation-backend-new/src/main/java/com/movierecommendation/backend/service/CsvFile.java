package com.movierecommendation.backend.service;

public enum CsvFile {
    MOVIES("movies.csv"),
    RATINGS("ratings.csv"),
    LINKS("links.csv"),
    TAGS("tags.csv");

    private final String fileName;

    CsvFile(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}
