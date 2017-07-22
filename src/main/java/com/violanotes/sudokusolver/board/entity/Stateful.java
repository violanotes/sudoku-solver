package com.violanotes.sudokusolver.board.entity;

import com.violanotes.sudokusolver.exceptions.BoardEntityStateChangeException;

/**
 * Created by pc on 7/20/2017.
 */
public interface Stateful<T> {

    default void changeState(State newState, T...args) throws BoardEntityStateChangeException {
        validateStateChange(newState, args);
        doChangeState(newState, args);
    }

    default void validateStateChange(State newState, T...args) throws BoardEntityStateChangeException {
        // may be optionally implemented by subclass
    }

    void doChangeState(State newState, T...args) throws BoardEntityStateChangeException;

    void setInitialState();
}
