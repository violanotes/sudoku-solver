package com.violanotes.sudokusolver.exceptions;

import com.violanotes.sudokusolver.board.entity.Associable;

/**
 * Created by pc on 7/20/2017.
 */

public class AssociationException extends BoardEntityException {
    public AssociationException(Associable entity1, Associable entity2) {
        super("cannot doAssociate entity1 (" + entity1.getClass().getSimpleName() + ") with entity 2 (" + entity2.getClass().getSimpleName() + ")");
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
