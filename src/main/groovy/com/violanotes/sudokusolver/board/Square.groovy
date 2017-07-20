package com.violanotes.sudokusolver.board

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.violanotes.sudokusolver.exceptions.AssociationException

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Square extends BoardEntity {
    private BoardState boardState
    private List<Hypothetical> hypotheticals
    private Integer index
    private Integer number
    private SquareState state

    private Row row
    private Column column
    private Box box

    enum SquareState {
        @JsonProperty("available") AVAILABLE,
        @JsonProperty("filled") FILLED
    }

    @Override
    void initializeToEmpty() {
        hypotheticals = []
    }

    @Override
    void associate(BoardEntity entity) throws AssociationException {
        switch (entity.class) {
            case (BoardState.class):
                entity.squares.add(this)
                break
            default:
                throw new AssociationException(entity, this)
        }
    }

    List<Hypothetical> getHypotheticals() {
        return hypotheticals
    }

    void setHypotheticals(List<Hypothetical> hypotheticals) {
        this.hypotheticals = hypotheticals
    }

    Integer getIndex() {
        return index
    }

    void setIndex(Integer index) {
        this.index = index
    }

    Integer getNumber() {
        return number
    }

    void setNumber(Integer number) {
        this.number = number
    }

    SquareState getState() {
        return state
    }

    void setState(SquareState state) {
        this.state = state
    }

    Row getRow() {
        return row
    }

    void setRow(Row row) {
        this.row = row
    }

    Column getColumn() {
        return column
    }

    void setColumn(Column column) {
        this.column = column
    }

    Box getBox() {
        return box
    }

    void setBox(Box box) {
        this.box = box
    }

    BoardState getBoardState() {
        return boardState
    }

    void setBoardState(BoardState boardState) {
        this.boardState = boardState
    }

    @Override
    String toString() {
        return "Square{" +
                "hypotheticals=" + hypotheticals +
                ", index=" + index +
                ", number=" + number +
                ", state=" + state +
                '}'
    }
}
