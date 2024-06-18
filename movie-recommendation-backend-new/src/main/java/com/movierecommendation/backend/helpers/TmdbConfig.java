package com.movierecommendation.backend.helpers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TmdbConfig {

    @JsonProperty("images")
    private Images images;

    @JsonProperty("change_keys")
    private List<String> changeKeys;

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public List<String> getChangeKeys() {
        return changeKeys;
    }

    public void setChangeKeys(List<String> changeKeys) {
        this.changeKeys = changeKeys;
    }

    public static class Images {

        @JsonProperty("base_url")
        private String baseUrl;

        @JsonProperty("secure_base_url")
        private String secureBaseUrl;

        @JsonProperty("backdrop_sizes")
        private List<String> backdropSizes;

        @JsonProperty("logo_sizes")
        private List<String> logoSizes;

        @JsonProperty("poster_sizes")
        private List<String> posterSizes;

        @JsonProperty("profile_sizes")
        private List<String> profileSizes;

        @JsonProperty("still_sizes")
        private List<String> stillSizes;

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getSecureBaseUrl() {
            return secureBaseUrl;
        }

        public void setSecureBaseUrl(String secureBaseUrl) {
            this.secureBaseUrl = secureBaseUrl;
        }

        public List<String> getBackdropSizes() {
            return backdropSizes;
        }

        public void setBackdropSizes(List<String> backdropSizes) {
            this.backdropSizes = backdropSizes;
        }

        public List<String> getLogoSizes() {
            return logoSizes;
        }

        public void setLogoSizes(List<String> logoSizes) {
            this.logoSizes = logoSizes;
        }

        public List<String> getPosterSizes() {
            return posterSizes;
        }

        public void setPosterSizes(List<String> posterSizes) {
            this.posterSizes = posterSizes;
        }

        public List<String> getProfileSizes() {
            return profileSizes;
        }

        public void setProfileSizes(List<String> profileSizes) {
            this.profileSizes = profileSizes;
        }

        public List<String> getStillSizes() {
            return stillSizes;
        }

        public void setStillSizes(List<String> stillSizes) {
            this.stillSizes = stillSizes;
        }
    }
}
