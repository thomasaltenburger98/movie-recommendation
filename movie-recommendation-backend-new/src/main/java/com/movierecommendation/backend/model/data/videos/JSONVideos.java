package com.movierecommendation.backend.model.data.videos;

import lombok.Data;

@Data
public class JSONVideos {
    private Video[] results;

    public Video[] getResults() {
        return results;
    }

    public void setResults(Video[] results) {
        this.results = results;
    }
}
