package com.example.matchthreegamebot.utils;

import java.util.concurrent.atomic.AtomicBoolean;

public class Constants {

//    public final static Integer ROWS = 6;
//    public final static Integer COLUMNS = 5;
//    public final static Integer SCREEN_LEFT_PADDING = 830;
//    public final static Integer SCREEN_TOP_PADDING = 405;
//    public final static Integer SCREEN_WIDTH = 269;
//    public final static Integer SCREEN_HEIGHT = 309;
//    public final static Integer HORIZONTAL_PADDING = 11;
//    public final static Integer VERTICAL_PADDING = 10;

    public static AtomicBoolean KEY_PRESS_STATE = new AtomicBoolean(false);

    public final static Integer ROWS = 8;

    public final static Integer COLUMNS = 8;
    public final static Integer SCREEN_LEFT_PADDING = 224;
    public final static Integer SCREEN_TOP_PADDING = 131;
    public final static Integer SCREEN_WIDTH = 721;
    public final static Integer SCREEN_HEIGHT = 721;
    public final static Integer HORIZONTAL_PADDING = 0;
    public final static Integer VERTICAL_PADDING = 0;

    public final static Integer ITEM_WIDTH = (SCREEN_WIDTH - (COLUMNS - 1) * VERTICAL_PADDING) / COLUMNS;
    public final static Integer ITEM_HEIGHT = (SCREEN_HEIGHT - (ROWS - 1) * HORIZONTAL_PADDING) / ROWS;

    public final static String LIBRARY_PATH = "C:\\Users\\lleon\\Desktop\\opencv\\build\\java\\x64\\opencv_java490.dll";
    public final static String SCREEN_PATH = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\output\\screenshot.png";
    public final static String SPLIT_DIRECTORY_PATH = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\output\\matrix\\";



    //BLACK
    public final static String SAMPLE_BLACK_PATH_0 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\black-0.png";
    public final static String SAMPLE_BLACK_PATH_1 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\black-1.png";
    public final static String SAMPLE_BLACK_PATH_2 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\black-2.png";


    //BLUE
    public final static String SAMPLE_BLUE_PATH_0 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\blue-0.png";
    public final static String SAMPLE_BLUE_PATH_1 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\blue-1.png";


    //GREEN
    public final static String SAMPLE_GREEN_PATH_0 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\green-0.png";
    public final static String SAMPLE_GREEN_PATH_1 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\green-1.png";
    public final static String SAMPLE_GREEN_PATH_2 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\green-2.png";


    //RED
    public final static String SAMPLE_RED_PATH_0 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\red-0.png";
    public final static String SAMPLE_RED_PATH_1 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\red-1.png";
    public final static String SAMPLE_RED_PATH_2 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\red-2.png";


    //VIOLET
    public final static String SAMPLE_VIOLET_PATH_0 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\violet-0.png";
    public final static String SAMPLE_VIOLET_PATH_1 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\violet-1.png";
    public final static String SAMPLE_VIOLET_PATH_2 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\violet-2.png";

    //YELLOW
    public final static String SAMPLE_VIOLET_YELLOW_0 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\yellow-0.png";
    public final static String SAMPLE_VIOLET_YELLOW_1 = "C:\\IntelijIdeaProjects\\match-three-game-bot\\src\\main\\resources\\input\\yellow-1.png";




    public final static String[] SAMPLE_IMAGE_PATHS = {
            SAMPLE_BLUE_PATH_0,
            SAMPLE_BLUE_PATH_1,
//            SAMPLE_BLUE_PATH_2,
            SAMPLE_GREEN_PATH_0,
            SAMPLE_GREEN_PATH_1,
            SAMPLE_GREEN_PATH_2,
            SAMPLE_RED_PATH_0,
            SAMPLE_RED_PATH_1,
            SAMPLE_RED_PATH_2,
            SAMPLE_VIOLET_PATH_0,
            SAMPLE_VIOLET_PATH_1,
            SAMPLE_VIOLET_PATH_2,
            SAMPLE_BLACK_PATH_0,
            SAMPLE_BLACK_PATH_1,
            SAMPLE_BLACK_PATH_2,
            SAMPLE_VIOLET_YELLOW_0,
            SAMPLE_VIOLET_YELLOW_1,
//            SAMPLE_VIOLET_YELLOW_2
    };

}
