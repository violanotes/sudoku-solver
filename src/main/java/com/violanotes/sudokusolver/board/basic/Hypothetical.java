package com.violanotes.sudokusolver.board.basic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.violanotes.sudokusolver.board.entity.*;
import com.violanotes.sudokusolver.exceptions.AssociationException;
import com.violanotes.sudokusolver.exceptions.BoardEntityException;
import com.violanotes.sudokusolver.exceptions.BoardEntityStateChangeException;
import com.violanotes.sudokusolver.exceptions.BoardEntityValidationException;
import com.violanotes.sudokusolver.move.Move;

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hypothetical extends StatefulBoardEntity<Move> implements HasAddress {

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
        super();
    }

    @Override
    public void initializeToEmpty() {

    }

    @Override
    public void setInitialState() {
        state = HypotheticalState.AVAILABLE;
    }

    @Override
    public void doChangeState(final State newState, Move...moves) throws BoardEntityStateChangeException {

        // verify the move
        if (moves.length == 0 || moves.length > 1) {
            throw new BoardEntityStateChangeException("Exactly one move should accompany this state change, but there were " + moves.length);
        }

        if (moves[0].getHypothetical() == null) {
            throw new BoardEntityStateChangeException("The accompanying move to this state change should contain a hypothetical, but it did not.");
        }

        if (moves[0].getHypothetical() != this) {
            throw new BoardEntityStateChangeException("The accompanying move to this state change should refer to this hypothetical: " +
                    address() + ", but instead referred to: " + moves[0].getHypothetical().address());
        }

        System.out.println("changing state to: " + newState.getState().getClass().getSimpleName() + " : " + newState.getState());
        System.out.println("previous state: " + state);


        if (!(newState.getState() instanceof HypotheticalState)) {
            throw new BoardEntityStateChangeException("State of type '" + newState.getState().getClass().getSimpleName() + "' not a valid hypothetical state");
        }

        if (newState.getState() == HypotheticalState.AVAILABLE) {
            if (state != null) {
                throw new BoardEntityStateChangeException("Hypothetical state may only be changed to AVAILABLE from NULL");
            }
            state = HypotheticalState.AVAILABLE;
        } else if (newState.getState() == HypotheticalState.ELIMINATED) {
            if (!(state == null || state.equals(HypotheticalState.AVAILABLE))) {
                throw new BoardEntityStateChangeException("Hypothetical state may only be changed to ELIMINATED from AVAILABLE or NULL");
            }
            state = HypotheticalState.ELIMINATED;
        } else if (newState.getState() == HypotheticalState.FILLED) {
            if ((state.equals(HypotheticalState.ELIMINATED))) {
                throw new BoardEntityStateChangeException("Hypothetical state may only be changed to ELIMINATED from AVAILABLE or NULL");
            }
            state = HypotheticalState.FILLED;

            // pass on the state to the square
            square.changeState(Square.SquareState.FILLED, moves[0]);
        }
    }

    @Override
    public void doValidate() throws BoardEntityValidationException {

    }

    @Override
    public void doAssociate(Associable<BoardEntity> entity) throws AssociationException {
        if (entity instanceof BoardState) {
            boardState = (BoardState)entity;
        } else if (entity instanceof Square) {
            square = (Square)entity;
        } else {
            throw new AssociationException(entity, this);
        }
    }

    @Override
    public String getAddress() {
        String address = "Hypothetical @ square:";
        if (square == null) {
            address += null + " ";
        } else {
            address += square.getIndex() + " ";
        }

        address += "hypothetical: " + number;

        return address;
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
