package com.violanotes.sudokusolver.board.supplemental

import com.violanotes.sudokusolver.board.basic.BoardState
import com.violanotes.sudokusolver.board.basic.Square
import com.violanotes.sudokusolver.board.entity.BoardEntity
import com.violanotes.sudokusolver.exceptions.AssociationException
import groovy.transform.InheritConstructors

/**
 * Created by pc on 7/20/2017.
 */
@InheritConstructors
class Column extends BoardEntity {
    private List<Square> squares
    private BoxColumn boxColumn
    private Integer index

    @Override
    void initializeToEmpty() {
        squares = new ArrayList<>()
    }

    @Override
    void associate(BoardEntity entity) throws AssociationException {
        switch (entity.class) {
            case Square: squares.add entity
                break
            case BoxColumn: boxColumn = entity
                break
            case BoardState: boardState = entity
                break
            default:
                throw new AssociationException(entity, this)
        }
    }
}
