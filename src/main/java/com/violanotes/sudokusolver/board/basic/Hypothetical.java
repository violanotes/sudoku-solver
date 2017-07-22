package com.violanotes.sudokusolver.board.basic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.violanotes.sudokusolver.board.entity.BoardEntity;
import com.violanotes.sudokusolver.board.entity.State;
import com.violanotes.sudokusolver.board.entity.StatefulBoardEntity;
import com.violanotes.sudokusolver.exceptions.AssociationException;
import com.violanotes.sudokusolver.exceptions.BoardEntityException;
import com.violanotes.sudokusolver.exceptions.BoardStateValidationException;

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hypothetical extends StatefulBoardEntity {

    private Integer number;
    private HypotheticalState state;
    @JsonIgnore private Square square;

    public enum HypotheticalState implements State{
        @JsonProperty("available") AVAILABLE,
        @JsonProperty("eliminated")ELIMINATED,
        @JsonProperty("filled")FILLED;

        @Override
        public HypotheticalState getState() {
            return this;
        }

    }

    public Hypothetical(boolean initializeToEmpty) throws BoardEntityException {
        super(initializeToEmpty);
    }

    public Hypothetical() throws BoardEntityException {
    }

    @Override
    public void initializeToEmpty() {

    }

    @Override
    public void setInitialState() {
        state = HypotheticalState.AVAILABLE;
    }

    @Override
    public void associate(BoardEntity entity) throws AssociationException {
        if (entity instanceof BoardState) {
            boardState = (BoardState)entity;
        } else if (entity instanceof Square) {
            square = (Square)entity;
        } else {
            throw new AssociationException(entity, this);
        }
    }

    @Override
    public void changeState(final State newState) throws BoardStateValidationException {

        System.out.println("changing state to: " + newState.getState().getClass().getSimpleName() + " : " + newState.getState());
        System.out.println("previous state: " + state);


        if (!(newState.getState() instanceof HypotheticalState)) {
            throw new BoardStateValidationException("State of type '" + newState.getState().getClass().getSimpleName() + "' not a valid hypothetical state");
        }

        if (newState.getState() == HypotheticalState.AVAILABLE) {
            if (state != null) {
                throw new BoardStateValidationException("Hypothetical state may only be changed to AVAILABLE from NULL");
            }
            state = HypotheticalState.AVAILABLE;
        } else if (newState.getState() == HypotheticalState.ELIMINATED) {
            if (!(state == null || state.equals(HypotheticalState.AVAILABLE))) {
                throw new BoardStateValidationException("Hypothetical state may only be changed to ELIMINATED from AVAILABLE or NULL");
            }
            state = HypotheticalState.ELIMINATED;
        } else if (newState.getState() == HypotheticalState.FILLED) {
            if (!(state == null || state.equals(HypotheticalState.FILLED))) {
                throw new BoardStateValidationException("Hypothetical state may only be changed to ELIMINATED from AVAILABLE or NULL");
            }
            state = HypotheticalState.FILLED;
        }
    }

    @Override
    public String toString() {
        return "Hypothetical{" + "number=" + number + ", state=" + state + "}";
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public HypotheticalState getState() {
        return state;
    }

    public void setState(HypotheticalState state) {
        this.state = state;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }
}
