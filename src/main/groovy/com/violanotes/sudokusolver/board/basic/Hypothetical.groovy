package com.violanotes.sudokusolver.board.basic

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.violanotes.sudokusolver.board.entity.BoardEntity
import com.violanotes.sudokusolver.board.entity.State
import com.violanotes.sudokusolver.board.entity.StatefulBoardEntity
import com.violanotes.sudokusolver.exceptions.AssociationException
import com.violanotes.sudokusolver.exceptions.BoardStateValidationException
import groovy.transform.InheritConstructors

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@InheritConstructors
class Hypothetical extends BoardEntity {

    Integer number
    HypotheticalState state
    @JsonIgnore Square square

    enum HypotheticalState implements State {
        @JsonProperty("available") AVAILABLE,
        @JsonProperty("eliminated") ELIMINATED,
        @JsonProperty("filled") FILLED

        @Override
        getState() {
            return this
        }
    }

    @Override
    void initializeToEmpty() {

    }

//    @Override
    void setInitialState() {
        state = HypotheticalState.AVAILABLE
    }

//    @Override
    void changeState(State newState) {

        println "changing state to: ${newState.getState().class.simpleName} : ${newState.getState()}"
        println "previous state: ${state}"


        if (!(newState.getState() instanceof HypotheticalState)) {
            throw new BoardStateValidationException("State of type '${newState.getState().class.simpleName}' not a valid hypothetical state")
        }

        switch (newState.getState()) {
            case HypotheticalState.AVAILABLE:
                if (!(state == null || state == HypotheticalState.AVAILABLE)) {
                    throw new BoardStateValidationException("Hypothetical state may only be changed to AVAILABLE from NULL")
                }
                break
            case HypotheticalState.ELIMINATED:
                if (!(state == null || state == HypotheticalState.AVAILABLE)) {
                    throw new BoardStateValidationException("Hypothetical state may only be changed to ELIMINATED from AVAILABLE or NULL")
                }
                break
            case HypotheticalState.FILLED:
                if (!(state == null || state == HypotheticalState.FILLED)) {
                    throw new BoardStateValidationException("Hypothetical state may only be changed to ELIMINATED from AVAILABLE or NULL")
                }
        }
    }

    @Override
    void associate(BoardEntity entity) throws AssociationException {
        switch(entity.class) {
            case BoardState: boardState
                break
            case Square: square = entity
                break
            default:
                throw new AssociationException(entity, this)
        }
    }

    @Override
    String toString() {
        return "Hypothetical{" +
                "number=" + number +
                ", state=" + state +
                '}'
    }
}
