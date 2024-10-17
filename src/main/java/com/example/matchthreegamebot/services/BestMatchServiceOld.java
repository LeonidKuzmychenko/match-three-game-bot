package com.example.matchthreegamebot.services;

import com.example.matchthreegamebot.dto.Swap;
import com.example.matchthreegamebot.utils.MatchItem;
import org.springframework.stereotype.Service;

@Service
public class BestMatchServiceOld {
    public Swap bestStep(MatchItem[][] board) {
        int[] bestMove = findBestMove(board);
        return new Swap(bestMove[0], bestMove[1], bestMove[2], bestMove[3]);
    }

    private int[] findBestMove(MatchItem[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        int[] bestMove = null;
        int maxMatches = 0;

        // Проходим по всем элементам доски
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Проверка на возможность обмена с правым элементом
                if (j < cols - 1) {
                    swap(board, i, j, i, j + 1);
                    int matches = countMatches(board);
                    if (matches > maxMatches) {
                        maxMatches = matches;
                        bestMove = new int[]{i, j, i, j + 1};
                    }
                    swap(board, i, j, i, j + 1); // Возврат на место
                }

                // Проверка на возможность обмена с нижним элементом
                if (i < rows - 1) {
                    swap(board, i, j, i + 1, j);
                    int matches = countMatches(board);
                    if (matches > maxMatches) {
                        maxMatches = matches;
                        bestMove = new int[]{i, j, i + 1, j};
                    }
                    swap(board, i, j, i + 1, j); // Возврат на место
                }
            }
        }
        return bestMove; // Возвращаем лучший ход
    }

    private void swap(MatchItem[][] board, int x1, int y1, int x2, int y2) {
        MatchItem temp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = temp;
    }

    private int countMatches(MatchItem[][] board) {
        int totalMatches = 0;
        boolean[][] checked = new boolean[board.length][board[0].length];

        // Проверка горизонтальных совпадений
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!checked[i][j]) {
                    int count = 1;

                    // Проверка последовательных элементов вправо
                    for (int k = j + 1; k < board[i].length && board[i][k].equals(board[i][j]); k++) {
                        count++;
                        checked[i][k] = true;
                    }

                    if (count >= 3) {
                        totalMatches += count; // Добавляем количество совпадений
                        checked[i][j] = true;
                    }
                }
            }
        }

        // Проверка вертикальных совпадений
        for (int j = 0; j < board[0].length; j++) {
            for (int i = 0; i < board.length; i++) {
                if (!checked[i][j]) {
                    int count = 1;

                    // Проверка последовательных элементов вниз
                    for (int k = i + 1; k < board.length && board[k][j].equals(board[i][j]); k++) {
                        count++;
                        checked[k][j] = true;
                    }

                    if (count >= 3) {
                        totalMatches += count; // Добавляем количество совпадений
                        checked[i][j] = true;
                    }
                }
            }
        }

        // Проверка угловых совпадений (например, "L" или "T")
        totalMatches += checkCornerMatches(board);

        return totalMatches; // Возвращаем общее количество совпадений
    }

    private int checkCornerMatches(MatchItem[][] board) {
        int cornerMatches = 0;
        int rows = board.length;
        int cols = board[0].length;

        // Проверка угловых форм "L"
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < cols - 1; j++) {
                // Проверка формы "L" в трех вариантах
                if (board[i][j].equals(board[i + 1][j]) && board[i][j].equals(board[i][j + 1])) {
                    cornerMatches++;
                }
                if (board[i][j].equals(board[i][j + 1]) && board[i][j].equals(board[i + 1][j + 1])) {
                    cornerMatches++;
                }
                if (board[i][j].equals(board[i + 1][j + 1]) && board[i][j].equals(board[i + 1][j])) {
                    cornerMatches++;
                }
            }
        }

        return cornerMatches; // Возвращаем количество угловых совпадений
    }
}
