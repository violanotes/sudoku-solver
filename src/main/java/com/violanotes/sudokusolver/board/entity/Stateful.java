package com.violanotes.sudokusolver.board.entity;

import com.violanotes.sudokusolver.exceptions.BoardStateValidationException;

/**
 * Created by pc on 7/20/2017.
 */
public interface Stateful {
    void changeState(State newState) throws BoardStateValidationException;

    void setInitialState();
}
