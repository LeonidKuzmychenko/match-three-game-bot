package com.example.matchthreegamebot.utils;

import java.util.Arrays;

public class Utils {

    public static void printTwoDimensionalArray(Object[][] array){
        String result = Arrays.deepToString(array);
        // Форматируем результат, заменяя запятые и открытые скобки для лучшей читаемости
        result = result
                .replace("], ", "],\n")  // Перенос строки после каждого массива
                .replace("[[", "[\n[")   // Перенос после открытия основного массива
                .replace("]]", "]\n]");  // Перенос перед закрытием основного массива
        // Выводим результат с переносами строк
        System.out.println(result);
    }
}
