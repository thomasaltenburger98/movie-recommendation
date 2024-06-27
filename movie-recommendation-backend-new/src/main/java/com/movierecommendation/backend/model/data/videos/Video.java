package com.movierecommendation.backend.model.data.videos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Video {
    private String iso_639_1;
    private String iso_3166_1;
    private String name;
    private String key;
    private String site;
    private int size;
    private String type;
    private boolean official;
    private String published_at;
    private String id;
}