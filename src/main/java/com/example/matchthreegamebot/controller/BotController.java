package com.example.matchthreegamebot.controller;

import com.example.matchthreegamebot.dto.Swap;
import com.example.matchthreegamebot.services.*;
import com.example.matchthreegamebot.utils.MatchItem;
import com.example.matchthreegamebot.utils.MatchPriorityItem;
import com.example.matchthreegamebot.utils.Utils;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.example.matchthreegamebot.utils.Constants.*;

@RestController
@AllArgsConstructor
public class BotController {

//    private final ImageCompareService imageCompare;
    private final ImageMatchingService imageCompare;//new
    private final ScreenCaptureService screenCapture;
    private final ImageSplitterService imageSplitter;
    private final FileListerService fileLister;
    private final BestMatchService bestMatch;//new
//    private final BestMatchServiceOld bestMatch;
    private final MouseMoveService mouseMove;
    private final Map<String, MatchPriorityItem> match;

    @PostConstruct
    public void init() {
        new Thread(this::start).start();
    }

    @SneakyThrows
    private void start() {
        Random random = new Random();
        while (true) {
            if (!KEY_PRESS_STATE.get()) {
                System.out.println(KEY_PRESS_STATE);
                Thread.sleep(2000);
                continue;
            }
            String screenshotPath = screenCapture.screenshot();
            String splitPath = imageSplitter.split(screenshotPath);
//            String splitPath = imageSplitter.split(SCREEN_PATH);
            List<MatchPriorityItem> matchItemList = fileLister.listFilesInDirectory(splitPath).stream()
                    .parallel()
                    .map(it -> {
                        String bestMatch = imageCompare.compare(it);
                        return match.get(bestMatch);
                    }).toList();
            MatchPriorityItem[][] matchItems = new MatchPriorityItem[ROWS][COLUMNS];

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    matchItems[i][j] = matchItemList.get(i * COLUMNS + j);
                }
            }
            Utils.printTwoDimensionalArray(matchItems);
            Swap swap = bestMatch.bestStep(matchItems);
            if (swap !=null){
                int firstHeightClick = (int) (SCREEN_TOP_PADDING + swap.getFirstColumn() * (ITEM_HEIGHT + HORIZONTAL_PADDING) + ITEM_HEIGHT / 2.);
                int firstWidthClick = (int) (SCREEN_LEFT_PADDING + swap.getFirstRow() * (ITEM_WIDTH + VERTICAL_PADDING) + ITEM_WIDTH / 2.);

                int secondHeightClick = (int) (SCREEN_TOP_PADDING + swap.getSecondColumn() * (ITEM_HEIGHT + HORIZONTAL_PADDING) + ITEM_HEIGHT / 2.);
                int secondWidthClick = (int) (SCREEN_LEFT_PADDING + swap.getSecondRow() * (ITEM_WIDTH + VERTICAL_PADDING) + ITEM_WIDTH / 2.);

                mouseMove.mouseMove(firstWidthClick, firstHeightClick, secondWidthClick, secondHeightClick);
                Thread.sleep(random.nextInt(100) + 20);
            }
        }
    }
}
