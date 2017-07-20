package com.violanotes.sudokusolver;

import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
public class Square {
    private List<Hypothetical> hypotheticals;
    private SquareState squareState;

    public enum SquareState {
        AVAILABLE,
        FILLED;
    }
}
