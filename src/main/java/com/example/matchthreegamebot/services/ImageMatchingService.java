package com.example.matchthreegamebot.services;

import com.example.matchthreegamebot.dto.ColorMomentsPair;
import jakarta.annotation.PostConstruct;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.matchthreegamebot.utils.Constants.SAMPLE_IMAGE_PATHS;

@Primary
@Service
public class ImageMatchingService {

    private List<ColorMomentsPair> colorMomentsPairs = null;

    @PostConstruct
    public void init() {
        colorMomentsPairs = Arrays.stream(SAMPLE_IMAGE_PATHS).map(path -> {
            Mat mat = Imgcodecs.imread(path, Imgcodecs.IMREAD_UNCHANGED);
            mat = setMatColorType(mat);
            mat = compressMat(mat);
            mat = getCenterRegion(mat, 70, 70);
            double[] colorMoments = computeColorMoments(mat);
            double[] huMoments = computeHuMoments(mat);
            double[] combinedFeatures = combineFeatures(colorMoments, huMoments);
            return new ColorMomentsPair(combinedFeatures, path);
        }).collect(Collectors.toList());
    }

    public String compare(String path) {
        Mat screenshot = Imgcodecs.imread(path);

        if (screenshot.empty()) {
            throw new IllegalArgumentException("Скриншот не найден по пути: " + path);
        }

        Mat centerRegion = getCenterRegion(screenshot, 70, 70); // Измените размер, если нужно
        double[] screenshotMoments = computeColorMoments(centerRegion);
        double[] screenshotHuMoments = computeHuMoments(centerRegion);
        double[] combinedScreenshotFeatures = combineFeatures(screenshotMoments, screenshotHuMoments);

        ColorMomentsPair pair = colorMomentsPairs.stream()
                .parallel()
                .min(Comparator.comparingDouble(it -> calculateCosineDistance(combinedScreenshotFeatures, it.getDistances())))
                .orElseThrow(() -> new RuntimeException("No templates available for comparison"));
        return pair.getPath();
    }

    private Mat getCenterRegion(Mat image, int width, int height) {
        int x = (image.width() - width) / 2;
        int y = (image.height() - height) / 2;
        return image.submat(new Rect(x, y, width, height));
    }

    private double[] computeColorMoments(Mat image) {
        List<Mat> channels = new ArrayList<>();
        Core.split(image, channels);

        double[] moments = new double[9];

        for (int i = 0; i < 3; i++) {
            moments[i * 3] = Core.mean(channels.get(i)).val[0];
            Mat temp = new Mat();
            Core.subtract(channels.get(i), new Scalar(moments[i * 3]), temp);
            Mat temp2 = new Mat();
            Core.multiply(temp, temp, temp2);
            moments[i * 3 + 1] = Math.sqrt(Core.mean(temp2).val[0]);
            moments[i * 3 + 2] = calculateSkewness(channels.get(i), moments[i * 3], moments[i * 3 + 1]);
        }

        return normalizeMoments(moments);
    }

    private double calculateSkewness(Mat channel, double mean, double stdDev) {
        Mat temp = new Mat();
        Core.subtract(channel, new Scalar(mean), temp);
        Mat temp2 = new Mat();
        Core.multiply(temp, temp, temp2);
        Core.multiply(temp2, temp2, temp2);
        double skewness = Core.mean(temp2).val[0] / (stdDev * stdDev * stdDev);
        return skewness;
    }

    private double[] computeHuMoments(Mat image) {
        // Преобразуем изображение в оттенки серого
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Вычисляем моменты
        Moments moments = Imgproc.moments(grayImage);

        // Вычисляем Hu моменты (создаем массив на 7 элементов)
        double[] huMoments = new double[7];
        Mat hu = new Mat();

        // Вызываем правильный метод для заполнения массива Hu моментов
        Imgproc.HuMoments(moments, hu);

        // Конвертируем значения из Mat hu в массив
        for (int i = 0; i < huMoments.length; i++) {
            huMoments[i] = hu.get(i, 0)[0];
        }

        return huMoments;
    }

    private double[] combineFeatures(double[] colorMoments, double[] huMoments) {
        double[] combinedFeatures = new double[colorMoments.length + huMoments.length];
        System.arraycopy(colorMoments, 0, combinedFeatures, 0, colorMoments.length);
        System.arraycopy(huMoments, 0, combinedFeatures, colorMoments.length, huMoments.length);
        return combinedFeatures;
    }

    private double calculateCosineDistance(double[] moments1, double[] moments2) {
        double dotProduct = 0;
        double normMoments1 = 0;
        double normMoments2 = 0;

        for (int i = 0; i < moments1.length; i++) {
            dotProduct += moments1[i] * moments2[i];
            normMoments1 += Math.pow(moments1[i], 2);
            normMoments2 += Math.pow(moments2[i], 2);
        }

        return 1 - (dotProduct / (Math.sqrt(normMoments1) * Math.sqrt(normMoments2)));
    }

    private double[] normalizeMoments(double[] moments) {
        double norm = 0;
        for (double moment : moments) {
            norm += Math.pow(moment, 2);
        }
        norm = Math.sqrt(norm);

        for (int i = 0; i < moments.length; i++) {
            moments[i] /= norm;
        }

        return moments;
    }

    private Mat setMatColorType(Mat mat) {
        if (mat.channels() == 3) {
            Mat changed = new Mat();
            Imgproc.cvtColor(mat, changed, Imgproc.COLOR_BGR2BGRA);
            return changed;
        }
        return mat;
    }

    private Mat compressMat(Mat mat) {
        Mat resized = new Mat();
        Imgproc.resize(mat, resized, new Size(90, 90));
        return resized;
    }
}
