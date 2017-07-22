package com.violanotes.sudokusolver.board.basic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.violanotes.sudokusolver.board.entity.BoardEntity;
import com.violanotes.sudokusolver.board.entity.State;
import com.violanotes.sudokusolver.board.entity.StatefulBoardEntity;
import com.violanotes.sudokusolver.exceptions.AssociationException;
import com.violanotes.sudokusolver.board.supplemental.Box;
import com.violanotes.sudokusolver.board.supplemental.Column;
import com.violanotes.sudokusolver.board.supplemental.Row;
import com.violanotes.sudokusolver.exceptions.BoardEntityException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Square extends StatefulBoardEntity {

    private List<Hypothetical> hypotheticals;
    private Integer index;
    private Integer number;
    private SquareState state;
    private Row row;
    private Column column;
    private Box box;

    public Square(boolean initializeToEmpty) throws BoardEntityException {
        super(initializeToEmpty);
    }

    public Square() throws BoardEntityException {
    }

    @Override
    public void initializeToEmpty() {
        hypotheticals = new ArrayList<>();
    }

    public void setInitialState() {
        state = SquareState.AVAILABLE;
    }

    public void changeState(final State newState) {
        System.out.println("changing state to: " + newState.getState().getClass().getSimpleName() + " : " + newState.getState());
    }

    public enum SquareState implements State {
        @JsonProperty("available") AVAILABLE,
        @JsonProperty("filled") FILLED;


        @Override
        public SquareState getState() {
            return this;
        }
    }

    @Override
    public void associate(BoardEntity entity) throws AssociationException {
        if (entity instanceof Hypothetical) {
            hypotheticals.add((Hypothetical)entity);
        } else if (entity instanceof BoardState) {
            boardState = ((BoardState) (entity));
        } else if (entity instanceof Row) {
            row = ((Row) (entity));
        } else if (entity instanceof Column) {
            column = ((Column) (entity));
        } else if (entity instanceof Box) {
            box = ((Box) (entity));
        } else {
            throw new AssociationException(entity, this);
        }
    }

    @Override
    public String toString() {
        return "Square{" +
                ", index=" + index +
                ", number=" + number +
                ", state=" + state +
                ", row=" + ((row == null) ? "null" : row.getIndex()) +
                ", column=" + ((column == null) ? "null" : column.getIndex()) +
                ", box=" + ((box == null) ? "null" : box.getIndex()) +
                ", boxColumn=" + ((column != null && column.getBoxColumn() != null) ? column.getIndex() : "null") +
                ", boxRow=" + ((row != null && row.getBoxRow() != null) ? column.getIndex() : "null") +
                "}";
    }

    public List<Hypothetical> getHypotheticals() {
        return hypotheticals;
    }

    public void setHypotheticals(List<Hypothetical> hypotheticals) {
        this.hypotheticals = hypotheticals;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public SquareState getState() {
        return state;
    }

    public void setState(SquareState state) {
        this.state = state;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }
}
