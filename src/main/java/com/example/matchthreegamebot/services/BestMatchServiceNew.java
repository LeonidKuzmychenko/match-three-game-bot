package com.example.matchthreegamebot.services;

import com.example.matchthreegamebot.dto.Swap;
import com.example.matchthreegamebot.utils.MatchItem;
import org.springframework.stereotype.Service;

@Service
public class BestMatchServiceNew {
    public Swap bestStep(MatchItem[][] board) {
        int[] bestMove = findBestMove(board);
        if (bestMove == null){
            return null;
        }
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
                    if (matches == 5) {
                        return new int[]{i, j, i, j + 1}; // Немедленный возврат при нахождении 5 совпадений
                    }
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
                    if (matches == 5) {
                        return new int[]{i, j, i + 1, j}; // Немедленный возврат при нахождении 5 совпадений
                    }
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
                    int count = checkLine(board, i, j, checked, 0, 1); // Проверка вправо
                    if (count >= 3) totalMatches += count;
                }
            }
        }

        // Проверка вертикальных совпадений
        for (int j = 0; j < board[0].length; j++) {
            for (int i = 0; i < board.length; i++) {
                if (!checked[i][j]) {
                    int count = checkLine(board, i, j, checked, 1, 0); // Проверка вниз
                    if (count >= 3) totalMatches += count;
                }
            }
        }

        // Проверка угловых и T-образных совпадений
        totalMatches += checkCornerMatches(board);

        return totalMatches; // Возвращаем общее количество совпадений
    }

    private int checkLine(MatchItem[][] board, int i, int j, boolean[][] checked, int dx, int dy) {
        int count = 1;
        int x = i + dx;
        int y = j + dy;

        while (x < board.length && y < board[0].length && board[i][j].equals(board[x][y])) {
            count++;
            checked[x][y] = true;
            x += dx;
            y += dy;
        }

        return count;
    }

    private int checkCornerMatches(MatchItem[][] board) {
        int cornerMatches = 0;
        int rows = board.length;
        int cols = board[0].length;

        // Проверка форм "L" и "T"
        for (int i = 0; i < rows - 2; i++) {
            for (int j = 0; j < cols - 2; j++) {
                if (isTOrLShape(board, i, j)) {
                    cornerMatches += 5;
                }
            }
        }
        return cornerMatches;
    }

    private boolean isTOrLShape(MatchItem[][] board, int i, int j) {
        MatchItem item = board[i][j];

        // Проверка формы "L" и "T" в различных направлениях
        return (board[i][j + 1].equals(item) && board[i][j + 2].equals(item) &&
                board[i + 1][j].equals(item) && board[i + 2][j].equals(item)) ||
               (board[i + 1][j].equals(item) && board[i + 2][j].equals(item) &&
                board[i][j + 1].equals(item) && board[i][j + 2].equals(item));
    }
}
