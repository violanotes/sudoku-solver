package com.violanotes.sudokusolver.board

/**
 * Created by pc on 7/20/2017.
 */
class Box {
    private List<Square> squares
    private Integer index

    List<Square> getSquares() {
        return squares
    }

    void setSquares(List<Square> squares) {
        this.squares = squares
    }

    Integer getIndex() {
        return index
    }

    void setIndex(Integer index) {
        this.index = index
    }
}
