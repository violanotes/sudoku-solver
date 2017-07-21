package com.violanotes.sudokusolver.exceptions

import com.violanotes.sudokusolver.board.entity.BoardEntity
import groovy.transform.InheritConstructors;

/**
 * Created by pc on 7/20/2017.
 */
@InheritConstructors
class AssociationException extends BoardEntityException {
    AssociationException(BoardEntity entity1, BoardEntity entity2) {
        super("cannot associate entity1 (${entity1.class.simpleName}) with entity 2 (${entity2.class.simpleName})")
    }
}