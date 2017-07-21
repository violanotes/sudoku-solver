package com.violanotes.sudokusolver.board.supplemental

import com.violanotes.sudokusolver.board.basic.BoardState
import com.violanotes.sudokusolver.board.entity.BoardEntity
import com.violanotes.sudokusolver.exceptions.AssociationException
import groovy.transform.InheritConstructors

/**
 * Created by pc on 7/20/2017.
 */
@InheritConstructors
class BoxColumn extends BoardEntity {
    private List<Box> boxes
    private List<Column> columns
    private Integer index

    @Override
    void initializeToEmpty() {
        boxes = new ArrayList<>()
        columns = new ArrayList<>()
    }

    @Override
    void associate(BoardEntity entity) throws AssociationException {
        switch (entity.class) {
            case Box: boxes.add entity
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
