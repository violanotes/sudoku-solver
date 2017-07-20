package com.violanotes.sudokusolver.board

/**
 * Created by pc on 7/20/2017.
 */
class Column {
    private List<Square> squares
    private BoxColumn boxColumn
    private Integer index

    List<Square> getSquares() {
        return squares
    }

    void setSquares(List<Square> squares) {
        this.squares = squares
    }

    BoxColumn getBoxColumn() {
        return boxColumn
    }

    void setBoxColumn(BoxColumn boxColumn) {
        this.boxColumn = boxColumn
    }

    Integer getIndex() {
        return index
    }

    void setIndex(Integer index) {
        this.index = index
    }
}
