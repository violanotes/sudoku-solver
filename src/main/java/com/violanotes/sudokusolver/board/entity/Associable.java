package com.violanotes.sudokusolver.board.entity;

import com.violanotes.sudokusolver.exceptions.AssociationException;

/**
 * Created by pc on 7/20/2017.
 */
public interface Associable<T extends Associable<T>> {
    default void associate(Associable<T> entity) throws AssociationException {
        this.doAssociate(entity);
        entity.doAssociate(this);
    }

    @SuppressWarnings("unchecked")
    void doAssociate(Associable<T> entity1) throws AssociationException;
}
