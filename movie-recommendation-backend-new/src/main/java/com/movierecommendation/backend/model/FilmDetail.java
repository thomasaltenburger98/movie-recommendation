package com.movierecommendation.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class FilmDetail {
    private boolean adult;
    private String backdrop_path;
    private Collection belongs_to_collection;
    private int budget;
    private List<Genre> genres;
    private String homepage;
    private int id;
    private String imdb_id;
    private List<String> origin_country;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private List<ProductionCompany> production_companies;
    private List<ProductionCountry> production_countries;
    private String release_date;
    private int revenue;
    private int runtime;
    private List<SpokenLanguage> spoken_languages;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;
    private Credits credits;
    private String image_url;

    @Data
    public static class Collection {
        private int id;
        private String name;
        private String poster_path;
        private String backdrop_path;
    }

    @Data
    public static class Genre {
        private int id;
        private String name;
    }

    @Data
    public static class ProductionCompany {
        private int id;
        private String logo_path;
        private String name;
        private String origin_country;
    }

    @Data
    public static class ProductionCountry {
        private String iso_3166_1;
        private String name;
    }

    @Data
    public static class SpokenLanguage {
        private String english_name;
        private String iso_639_1;
        private String name;
    }

    @Data
    public static class Credits {
        private List<Cast> cast;
        private List<Crew> crew;

        @Data
        public static class Cast {
            private boolean adult;
            private int gender;
            private int id;
            private String known_for_department;
            private String name;
            private String original_name;
            private double popularity;
            private String profile_path;
            private int cast_id;
            private String character;
            private String credit_id;
            private int order;
        }

        @Data
        public static class Crew {
            private boolean adult;
            private int gender;
            private int id;
            private String known_for_department;
            private String name;
            private String original_name;
            private double popularity;
            private String profile_path;
            private String credit_id;
            private String department;
            private String job;
        }
    }
}