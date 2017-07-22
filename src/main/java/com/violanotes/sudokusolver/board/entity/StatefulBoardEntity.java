package com.violanotes.sudokusolver.board.entity;

import com.violanotes.sudokusolver.exceptions.BoardEntityException;

/**
 * Created by pc on 7/21/2017.
 */
public abstract class StatefulBoardEntity<T> extends BoardEntity implements Stateful<T> {
    public StatefulBoardEntity(boolean initializeToEmpty) throws BoardEntityException {
        super(initializeToEmpty);
        setInitialState();
    }

    public StatefulBoardEntity() throws BoardEntityException {
        super();
        setInitialState();
    }
}
