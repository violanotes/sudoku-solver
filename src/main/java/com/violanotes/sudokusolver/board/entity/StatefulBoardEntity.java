package com.violanotes.sudokusolver.board.entity;

import com.violanotes.sudokusolver.exceptions.BoardEntityException;

/**
 * Created by pc on 7/21/2017.
 */
public abstract class StatefulBoardEntity extends BoardEntity implements Stateful {
    public StatefulBoardEntity(boolean initializeToEmpty) throws BoardEntityException {
        super(initializeToEmpty);
    }

    public StatefulBoardEntity() throws BoardEntityException {
    }

    {
        setInitialState();
    }
}
