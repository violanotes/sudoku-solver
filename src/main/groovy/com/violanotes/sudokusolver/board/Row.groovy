package com.violanotes.sudokusolver.board

/**
 * Created by pc on 7/20/2017.
 */
class Row {
    private List<Square> squares
    private BoxRow boxRow
    private Integer index

    List<Square> getSquares() {
        return squares
    }

    void setSquares(List<Square> squares) {
        this.squares = squares
    }

    BoxRow getBoxRow() {
        return boxRow
    }

    void setBoxRow(BoxRow boxRow) {
        this.boxRow = boxRow
    }

    Integer getIndex() {
        return index
    }

    void setIndex(Integer index) {
        this.index = index
    }
}
