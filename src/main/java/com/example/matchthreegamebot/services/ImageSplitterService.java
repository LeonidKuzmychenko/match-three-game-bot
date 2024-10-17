package com.example.matchthreegamebot.services;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.example.matchthreegamebot.utils.Constants.*;

@Service
public class ImageSplitterService {

    public String split(String screenshotPath) {
        try {
            // Загружаем исходное изображение
            BufferedImage originalImage = ImageIO.read(new File(screenshotPath));

            // Получаем размеры каждой части, включая отступы
            int partWidth = (originalImage.getWidth() - (COLUMNS - 1) * HORIZONTAL_PADDING) / COLUMNS;
            int partHeight = (originalImage.getHeight() - (ROWS - 1) * VERTICAL_PADDING) / ROWS;

            // Разделяем изображение на части
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLUMNS; col++) {
                    // Определяем координаты для каждой части, учитывая отступы
                    int x = col * (partWidth + HORIZONTAL_PADDING);
                    int y = row * (partHeight + VERTICAL_PADDING);

                    // Создаем новую часть изображения
                    BufferedImage partImage = originalImage.getSubimage(x, y, partWidth, partHeight);

                    // Сохраняем часть изображения в файл
                    String outputFilePath = SPLIT_DIRECTORY_PATH + row + "_" + col + ".png";
                    ImageIO.write(partImage, "png", new File(outputFilePath));
                }
            }
            System.out.println("Изображение успешно разделено на части!");
            return SPLIT_DIRECTORY_PATH;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
