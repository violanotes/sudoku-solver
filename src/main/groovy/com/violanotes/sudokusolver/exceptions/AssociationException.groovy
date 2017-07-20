package com.violanotes.sudokusolver.exceptions

import com.violanotes.sudokusolver.board.BoardEntity;

/**
 * Created by pc on 7/20/2017.
 */
class AssociationException extends BoardEntityException {
    AssociationException(Throwable cause) {
        super(cause)
    }

    AssociationException(String message) {
        super(message)
    }

    AssociationException(String message, Throwable cause) {
        super(message, cause)
    }

    AssociationException(BoardEntity entity1, BoardEntity entity2) {
        super("cannot associate entity1 (${entity1.class.simpleName}) with entity 2 (${entity2.class.simpleName})")
    }
}
