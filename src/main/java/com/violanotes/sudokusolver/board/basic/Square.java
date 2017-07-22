package com.violanotes.sudokusolver.board.basic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.violanotes.sudokusolver.board.entity.*;
import com.violanotes.sudokusolver.exceptions.AssociationException;
import com.violanotes.sudokusolver.board.geometric.Box;
import com.violanotes.sudokusolver.board.geometric.Column;
import com.violanotes.sudokusolver.board.geometric.Row;
import com.violanotes.sudokusolver.exceptions.BoardEntityException;
import com.violanotes.sudokusolver.exceptions.BoardEntityStateChangeException;
import com.violanotes.sudokusolver.exceptions.BoardEntityValidationException;
import com.violanotes.sudokusolver.move.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Square extends StatefulBoardEntity<Move> implements HasAddress {

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
        super();
    }

    @Override
    public void initializeToEmpty() {
        hypotheticals = new ArrayList<>();
    }

    public void setInitialState() {
        state = SquareState.AVAILABLE;
    }


    @Override
    public void validateStateChange(State newState, Move...moves) throws BoardEntityStateChangeException {
        //TODO validate the state change

        if (moves == null) {
            throw new BoardEntityStateChangeException("State change should have exactly one accompanying move, but had " + moves.length);
        }

        if (moves.length != 1) {
            throw new BoardEntityStateChangeException("State change should have exactly one accompanying move, but had " + moves.length);
        }

    }

    @Override
    public void doChangeState(final State newState, Move...moves) {
        System.out.println("changing state to: " + newState.getState().getClass().getSimpleName() + " : " + newState.getState());

        number = moves[0].getHypothetical().getNumber();

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
    public void doValidate() throws BoardEntityValidationException {
        int filledInCount = 0;// max should be 1
        int availableCount = 0;// should be more than 0 if filledIn is 0
        int eliminatedCount = 0;

        if (hypotheticals.size() != 9) {
            throw new BoardEntityValidationException("Square " + index + " should have 9 hypotheticals, but has " + hypotheticals.size());
        }


        for (Hypothetical hypothetical : hypotheticals) {
            switch (hypothetical.getState()) {
                case FILLED:
                    filledInCount = filledInCount++;
                    break;
                case AVAILABLE:
                    availableCount = availableCount++;
                    break;
                case ELIMINATED:
                    eliminatedCount = eliminatedCount++;
                    break;
                default:
                    throw new BoardEntityValidationException("Unacceptable HypotheticalState '" + hypothetical.getState() + "'");
            }
        }

        if (filledInCount > 1) {
            throw new BoardEntityValidationException("Square " + index + " should have no more than 1 fille-in hypothetical, but has " + filledInCount);
        }


        if (filledInCount == 1) {
            if (availableCount == 0) {
                throw new BoardEntityValidationException("Square " + index + " should have at least 1 available hypothetical, but has " + availableCount);
            }

        }
    }

    @Override
    public void doAssociate(Associable<BoardEntity> entity) throws AssociationException {
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
    public String getAddress() {
        return "Square @ " + getIndex();
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
