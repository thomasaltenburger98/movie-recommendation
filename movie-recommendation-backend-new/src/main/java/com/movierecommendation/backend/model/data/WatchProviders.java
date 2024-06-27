package com.movierecommendation.backend.model.data;

import lombok.Data;

import java.util.List;

@Data
public class WatchProviders {
    private String link;
    private List<Provider> flatrate;
    private List<Provider> rent;
    private List<Provider> buy;

    @Data
    public static class Provider {
        private String logo_path;
        private int provider_id;
        private String provider_name;
        private int display_priority;
    }
}
