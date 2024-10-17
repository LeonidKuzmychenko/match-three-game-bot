package com.example.matchthreegamebot.services;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.example.matchthreegamebot.utils.Constants.*;

@Service
public class ScreenCaptureService {

    public String screenshot() {
        try {
            // Определите область экрана для скриншота (x, y, ширина, высота)
            Rectangle screenRect = new Rectangle(
                    SCREEN_LEFT_PADDING,
                    SCREEN_TOP_PADDING,
                    SCREEN_WIDTH,
                    SCREEN_HEIGHT);
            Robot robot = new Robot();

            // Снимите изображение
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);

            // Сохраните изображение в файл
            ImageIO.write(screenFullImage, "png", new File(SCREEN_PATH));

            System.out.println("Скриншот успешно сохранен: screenshot.png");
            return SCREEN_PATH;
        } catch (AWTException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
