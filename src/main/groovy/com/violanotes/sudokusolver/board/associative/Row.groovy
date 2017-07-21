package com.violanotes.sudokusolver.board.associative

import com.violanotes.sudokusolver.board.basic.Square
import com.violanotes.sudokusolver.board.entity.BoardEntity
import com.violanotes.sudokusolver.exceptions.AssociationException

/**
 * Created by pc on 7/20/2017.
 */
class Row extends BoardEntity {
    private List<Square> squares
    private BoxRow boxRow
    private Integer index

    @Override
    void initializeToEmpty() {
        squares = new ArrayList<>()
    }

    @Override
    void associate(BoardEntity entity) throws AssociationException {
        switch (entity.class) {
            case Square: squares.add(entity)
                break
            case BoxRow: boxRow = entity
        }
    }
}
