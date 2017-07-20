package com.violanotes.sudokusolver.board;

import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
public class Box {
    private List<Square> squares;

    public List<Square> getSquares() {
        return squares;
    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
    }
}
