package com.example.matchthreegamebot.services;

import com.example.matchthreegamebot.dto.ColorMomentsPair;
import jakarta.annotation.PostConstruct;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
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
public class ImageCompareService {

    private List<ColorMomentsPair> colorMomentsPairs = null;

    @PostConstruct
    public void init() {
        colorMomentsPairs = Arrays.stream(SAMPLE_IMAGE_PATHS).map(path -> {
            Mat mat = Imgcodecs.imread(path, Imgcodecs.IMREAD_UNCHANGED);
            mat = setMatColorType(mat);
            mat = compressMat(mat);
            mat = getCenterRegion(mat, 70, 70);
            double[] colorMoments = computeColorMoments(mat);
            return new ColorMomentsPair(colorMoments, path);
        }).collect(Collectors.toList());
    }

    public String compare(String path) {
        Mat screenshot = Imgcodecs.imread(path);

        System.out.println(screenshot.width());
        System.out.println(screenshot.height());
        Mat centerRegion = getCenterRegion(screenshot, 70, 70); // Измените размер, если нужно
        double[] screenshotMoments = computeColorMoments(centerRegion);

        ColorMomentsPair pair = colorMomentsPairs.stream()
                .parallel()
                .min(Comparator.comparingDouble(it -> calculateDistance(screenshotMoments, it.getDistances())))
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

        return moments;
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

    private double calculateDistance(double[] moments1, double[] moments2) {
        double distance = 0;
        for (int i = 0; i < moments1.length; i++) {
            distance += Math.pow(moments1[i] - moments2[i], 2);
        }
        return Math.sqrt(distance);
    }

    private Mat setMatColorType(Mat mat){
        if (mat.channels() == 3) {
            Mat changed = new Mat();
            Imgproc.cvtColor(mat, changed, Imgproc.COLOR_BGR2BGRA);
            return changed;
        }
        return mat;
    }

    private Mat compressMat(Mat mat){
        Mat resized = new Mat();
        Imgproc.resize(mat, resized, new Size(90, 90));
        return resized;
    }
}
