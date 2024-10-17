package com.example.matchthreegamebot.configuration;


import com.example.matchthreegamebot.utils.MatchItem;
import com.example.matchthreegamebot.utils.MatchPriorityItem;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.matchthreegamebot.utils.Constants.*;
import static com.example.matchthreegamebot.utils.Constants.SAMPLE_GREEN_PATH_0;
import static com.example.matchthreegamebot.utils.MatchItem.*;

@Configuration
public class AppConfiguration {

    static {
        System.setProperty("java.awt.headless", "false");
        System.load(LIBRARY_PATH);
    }

    @Bean
    public Map<String, MatchPriorityItem> matchMap() {
        Map<String, MatchPriorityItem> map = new HashMap<>();
        map.put(SAMPLE_BLACK_PATH_0, new MatchPriorityItem(BLACK, 0));
        map.put(SAMPLE_BLACK_PATH_1, new MatchPriorityItem(BLACK, 1));
        map.put(SAMPLE_BLACK_PATH_2, new MatchPriorityItem(BLACK, 1));
        map.put(SAMPLE_RED_PATH_0, new MatchPriorityItem(RED, 0));
        map.put(SAMPLE_RED_PATH_1, new MatchPriorityItem(RED, 1));
        map.put(SAMPLE_RED_PATH_2, new MatchPriorityItem(RED, 1));
        map.put(SAMPLE_GREEN_PATH_0, new MatchPriorityItem(GREEN, 0));
        map.put(SAMPLE_GREEN_PATH_1, new MatchPriorityItem(GREEN, 1));
        map.put(SAMPLE_GREEN_PATH_2, new MatchPriorityItem(GREEN, 1));
        map.put(SAMPLE_BLUE_PATH_0, new MatchPriorityItem(BLUE, 0));
        map.put(SAMPLE_BLUE_PATH_1, new MatchPriorityItem(BLUE, 1));
//        map.put(SAMPLE_BLUE_PATH_2, new MatchPriorityItem(BLUE));
        map.put(SAMPLE_VIOLET_PATH_0, new MatchPriorityItem(VIOLET, 0));;
        map.put(SAMPLE_VIOLET_PATH_1, new MatchPriorityItem(VIOLET, 1));
        map.put(SAMPLE_VIOLET_PATH_2, new MatchPriorityItem(VIOLET, 1));
        map.put(SAMPLE_VIOLET_YELLOW_0, new MatchPriorityItem(YELLOW, 0));
        map.put(SAMPLE_VIOLET_YELLOW_1, new MatchPriorityItem(YELLOW, 1));
//        map.put(SAMPLE_VIOLET_YELLOW_2, YELLOW));
        return map;
    }

}
