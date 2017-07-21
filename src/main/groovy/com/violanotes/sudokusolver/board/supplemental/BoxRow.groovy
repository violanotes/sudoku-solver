package com.violanotes.sudokusolver.board.supplemental

import com.violanotes.sudokusolver.board.basic.BoardState
import com.violanotes.sudokusolver.board.entity.BoardEntity
import com.violanotes.sudokusolver.exceptions.AssociationException
import groovy.transform.InheritConstructors

/**
 * Created by pc on 7/20/2017.
 */
@InheritConstructors
class BoxRow extends BoardEntity {
    private List<Box> boxes
    private List<Row> rows
    private Integer index

    @Override
    void initializeToEmpty() {
        boxes = new ArrayList<>()
        rows = new ArrayList<>()
    }

    @Override
    void associate(BoardEntity entity) throws AssociationException {
        switch (entity.class) {
            case Box: boxes.add entity
                break
            case Row: rows.add entity
                break
            case BoardState: boardState = entity
                break
            default:
                throw new AssociationException(entity, this)
        }
    }
}
