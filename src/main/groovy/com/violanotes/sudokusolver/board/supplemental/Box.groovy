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
class Box extends BoardEntity {
    List<Square> squares
    BoxRow boxRow
    BoxColumn boxColumn
    Integer index

    @Override
    void initializeToEmpty() {
        squares = new ArrayList<>()
    }

    @Override
    void associate(BoardEntity entity) throws AssociationException {
        switch (entity.class) {
            case Square: squares.add entity
                break
            case BoardState: boardState = entity
                break
            case BoxRow: boxRow = entity
                break
            case BoxColumn: boxColumn = entity
                break
            default:
                throw new AssociationException(entity, this)
        }
    }
}