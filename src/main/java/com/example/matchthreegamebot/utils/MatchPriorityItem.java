package com.example.matchthreegamebot.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchPriorityItem {

    private MatchItem matchItem;
    private Integer priority;

}
