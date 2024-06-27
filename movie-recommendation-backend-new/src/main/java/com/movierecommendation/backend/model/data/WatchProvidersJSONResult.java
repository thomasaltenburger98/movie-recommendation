package com.movierecommendation.backend.model.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Data
@Getter
@Setter
public class WatchProvidersJSONResult {

    private int id;
    private Map<String, WatchProviders> results;

}
