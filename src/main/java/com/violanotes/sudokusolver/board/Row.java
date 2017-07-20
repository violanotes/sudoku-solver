package com.violanotes.sudokusolver.board;

import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
public class Row {
    private List<Square> squares;
    private BoxRow boxRow;

    public List<Square> getSquares() {
        return squares;
    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
    }

    public BoxRow getBoxRow() {
        return boxRow;
    }

    public void setBoxRow(BoxRow boxRow) {
        this.boxRow = boxRow;
    }
}
