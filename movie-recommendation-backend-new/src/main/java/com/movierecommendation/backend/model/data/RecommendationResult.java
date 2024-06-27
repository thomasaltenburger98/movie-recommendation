package com.movierecommendation.backend.model.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Data
@Getter
@Setter
public class RecommendationResult {

    private ArrayList<Long[]> result;

}
