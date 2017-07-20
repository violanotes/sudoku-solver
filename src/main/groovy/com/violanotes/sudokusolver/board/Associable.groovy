package com.violanotes.sudokusolver.board

import com.violanotes.sudokusolver.exceptions.AssociationException

/**
 * Created by pc on 7/20/2017.
 */
interface Associable {
    void associate(BoardEntity entity) throws AssociationException
}