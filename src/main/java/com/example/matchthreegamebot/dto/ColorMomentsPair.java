package com.example.matchthreegamebot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorMomentsPair {

    private double[] distances;
    private String path;
}
