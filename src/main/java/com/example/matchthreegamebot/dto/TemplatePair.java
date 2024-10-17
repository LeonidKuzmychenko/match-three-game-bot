package com.example.matchthreegamebot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opencv.core.Mat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplatePair {

    private Mat template;
    private String path;
}
