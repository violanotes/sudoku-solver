package com.violanotes.sudokusolver.exceptions;

import com.violanotes.sudokusolver.board.entity.BoardEntity;

/**
 * Created by pc on 7/20/2017.
 */

public class AssociationException extends BoardEntityException {
    public AssociationException(final BoardEntity entity1, final BoardEntity entity2) {
        super("cannot associate entity1 (" + entity1.getClass().getSimpleName() + ") with com.violanotes.sudokusolver.board.entity 2 (" + entity2.getClass().getSimpleName() + ")");
    }

    public AssociationException() {
        super();
    }

    public AssociationException(String message) {
        super(message);
    }

    public AssociationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssociationException(Throwable cause) {
        super(cause);
    }
}
