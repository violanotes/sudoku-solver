package com.violanotes.sudokusolver.board.entity;

import com.violanotes.sudokusolver.exceptions.AssociationException;

/**
 * Created by pc on 7/20/2017.
 */
public interface Associable<T> {
    void associate(T entity) throws AssociationException;

    @SuppressWarnings("unchecked")
    static void associate(Associable entity1, Associable entity2) throws AssociationException {
        entity1.associate(entity2);
        entity2.associate(entity1);
    }
}
