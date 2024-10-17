package com.example.matchthreegamebot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Swap {
    private int firstColumn;
    private int firstRow;
    private int secondColumn;
    private int secondRow;
    private int matchCount;

    public Swap(int firstColumn, int firstRow, int secondColumn, int secondRow) {
        this.firstColumn = firstColumn;
        this.firstRow = firstRow;
        this.secondColumn = secondColumn;
        this.secondRow = secondRow;
    }
}
