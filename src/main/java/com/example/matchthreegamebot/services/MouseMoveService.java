package com.example.matchthreegamebot.services;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

@Service
public class MouseMoveService {
//    public void mouseMove(int fex, int fey, int sex, int sey) {
//        try {
//            Robot robot = new Robot();
//            robot.mouseMove(fex, fey);
//            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//            robot.delay(500);
//            robot.mouseMove(sex, sey);
//            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//    }

    public void mouseMove(int startX, int startY, int endX, int endY) {
        try {
            Random random = new Random();
            Robot robot = new Robot();
            int steps = 80; // Количество шагов
            int deltaX = (endX - startX);
            int deltaY = (endY - startY);

            // Начальное положение
            robot.mouseMove(startX, startY);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

            for (int i = 0; i < steps; i++) {
                // Рассчитайте новое положение
                double progress = (double) i / steps;
                int nextX = (int) (startX + deltaX * progress + random.nextInt(2) - 1); // Случайное отклонение
                int nextY = (int) (startY + deltaY * progress + random.nextInt(2) - 1); // Случайное отклонение

                robot.mouseMove(nextX, nextY);

                // Случайная задержка для имитации человеческого движения
                int delay = 1;
                robot.delay(delay);
            }

            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
