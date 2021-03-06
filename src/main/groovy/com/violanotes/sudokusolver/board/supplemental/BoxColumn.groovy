package com.violanotes.sudokusolver.board.supplemental

import com.fasterxml.jackson.annotation.JsonIgnore
import com.violanotes.sudokusolver.board.basic.BoardState
import com.violanotes.sudokusolver.board.basic.Square
import com.violanotes.sudokusolver.board.entity.BoardEntity
import com.violanotes.sudokusolver.exceptions.AssociationException
import groovy.transform.InheritConstructors

/**
 * Created by pc on 7/20/2017.
 */
@InheritConstructors
class BoxColumn extends BoardEntity {
    @JsonIgnore List<Box2> boxes
    @JsonIgnore List<Column> columns
    @JsonIgnore List<Square> squares
    Integer index

    @Override
    void initializeToEmpty() {
        boxes = new ArrayList<>()
        columns = new ArrayList<>()
        squares = new ArrayList<>()
    }

    @Override
    void associate(BoardEntity entity) throws AssociationException {
        switch (entity.class) {
            case Box2:
                boxes.add entity
                squares.addAll(((Box2)entity).squares)
                break
            case Column: columns.add entity
                break
            case BoardState: boardState = entity
                break
            default:
                throw new AssociationException(entity, this)
        }
    }
}
