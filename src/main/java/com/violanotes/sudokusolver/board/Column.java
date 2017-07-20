package com.violanotes.sudokusolver.board;

import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
public class Column {
    private List<Square> squares;
    private BoxColumn boxColumn;

    public List<Square> getSquares() {
        return squares;
    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
    }

    public BoxColumn getBoxColumn() {
        return boxColumn;
    }

    public void setBoxColumn(BoxColumn boxColumn) {
        this.boxColumn = boxColumn;
    }
}
