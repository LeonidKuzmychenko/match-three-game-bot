package com.example.matchthreegamebot.services;

import com.example.matchthreegamebot.dto.Swap;
import com.example.matchthreegamebot.utils.MatchItem;
import com.example.matchthreegamebot.utils.MatchPriorityItem;
import org.springframework.stereotype.Service;

@Service
public class BestMatchService {
    public Swap bestStep(MatchPriorityItem[][] board) {
        int[] bestMove = findBestMove(board);
        return new Swap(bestMove[0], bestMove[1], bestMove[2], bestMove[3]);
    }

    private int[] findBestMove(MatchPriorityItem[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        int[] bestMove = null;
        int maxScore = 0;
        int[] fallbackMove = null;  // Для хранения первого возможного хода

        // Проходим по всем элементам доски
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Проверка на возможность обмена с правым элементом
                if (j < cols - 1) {
                    if (fallbackMove == null) {
                        fallbackMove = new int[]{i, j, i, j + 1};  // Запоминаем первый возможный ход
                    }
                    swap(board, i, j, i, j + 1);
                    int score = calculateTotalScore(board);
                    if (score > maxScore) {
                        maxScore = score;
                        bestMove = new int[]{i, j, i, j + 1};
                    }
                    swap(board, i, j, i, j + 1); // Возврат на место
                }

                // Проверка на возможность обмена с нижним элементом
                if (i < rows - 1) {
                    if (fallbackMove == null) {
                        fallbackMove = new int[]{i, j, i + 1, j};  // Запоминаем первый возможный ход
                    }
                    swap(board, i, j, i + 1, j);
                    int score = calculateTotalScore(board);
                    if (score > maxScore) {
                        maxScore = score;
                        bestMove = new int[]{i, j, i + 1, j};
                    }
                    swap(board, i, j, i + 1, j); // Возврат на место
                }
            }
        }

        // Если не найдено ходов с совпадениями, возвращаем первый допустимый ход
        return (bestMove != null) ? bestMove : fallbackMove;
    }

    private void swap(MatchPriorityItem[][] board, int x1, int y1, int x2, int y2) {
        MatchPriorityItem temp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = temp;
    }

    private int calculateTotalScore(MatchPriorityItem[][] board) {
        int totalScore = 0;
        boolean[][] checked = new boolean[board.length][board[0].length];

        // Проверка горизонтальных совпадений
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!checked[i][j]) {
                    int score = checkLine(board, i, j, checked, 0, 1); // Проверка вправо
                    totalScore += score;
                }
            }
        }

        // Проверка вертикальных совпадений
        for (int j = 0; j < board[0].length; j++) {
            for (int i = 0; i < board.length; i++) {
                if (!checked[i][j]) {
                    int score = checkLine(board, i, j, checked, 1, 0); // Проверка вниз
                    totalScore += score;
                }
            }
        }

        // Проверка угловых и T-образных совпадений
        totalScore += checkCornerMatches(board);

        return totalScore; // Возвращаем итоговый приоритетный счет
    }

    private int checkLine(MatchPriorityItem[][] board, int i, int j, boolean[][] checked, int dx, int dy) {
        int count = 1;
        int prioritySum = board[i][j].getPriority(); // Суммируем приоритет
        MatchItem matchItem = board[i][j].getMatchItem(); // Сравниваем только по типу MatchItem
        checked[i][j] = true;

        int x = i + dx;
        int y = j + dy;

        // Сравнение по типу MatchItem, игнорируя приоритет
        while (x < board.length && y < board[0].length && board[x][y].getMatchItem().equals(matchItem)) {
            count++;
            prioritySum += board[x][y].getPriority(); // Учет приоритета
            checked[x][y] = true;
            x += dx;
            y += dy;
        }

        if (count >= 3) {
            return count * 10 + prioritySum; // Важность: количество совпадений * 10 + сумма приоритетов
        }

        return 0;
    }

    private int checkCornerMatches(MatchPriorityItem[][] board) {
        int cornerMatchesScore = 0;
        int rows = board.length;
        int cols = board[0].length;

        // Проверка форм "L" и "T"
        for (int i = 0; i < rows - 2; i++) {
            for (int j = 0; j < cols - 2; j++) {
                int score = checkLAndTShapes(board, i, j);
                if (score > 0) {
                    cornerMatchesScore += score;
                }
            }
        }
        return cornerMatchesScore;
    }

    private int checkLAndTShapes(MatchPriorityItem[][] board, int i, int j) {
        MatchItem item = board[i][j].getMatchItem();
        int prioritySum = board[i][j].getPriority();

        // Проверка формы "L"
        if (board[i][j + 1].getMatchItem().equals(item) && board[i][j + 2].getMatchItem().equals(item) &&
            board[i + 1][j].getMatchItem().equals(item) && board[i + 2][j].getMatchItem().equals(item)) {
            prioritySum += board[i][j + 1].getPriority() + board[i][j + 2].getPriority() +
                           board[i + 1][j].getPriority() + board[i + 2][j].getPriority();
            return 50 + prioritySum; // "L" образные шаги оцениваются в 50 очков + сумма приоритетов
        }

        // Проверка формы "T"
        if (board[i + 1][j].getMatchItem().equals(item) && board[i + 2][j].getMatchItem().equals(item) &&
            board[i][j + 1].getMatchItem().equals(item) && board[i][j + 2].getMatchItem().equals(item)) {
            prioritySum += board[i + 1][j].getPriority() + board[i + 2][j].getPriority() +
                           board[i][j + 1].getPriority() + board[i][j + 2].getPriority();
            return 50 + prioritySum; // "T" образные шаги также оцениваются в 50 очков + сумма приоритетов
        }

        return 0;
    }
}
