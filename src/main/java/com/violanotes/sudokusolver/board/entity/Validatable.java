package com.violanotes.sudokusolver.board.entity;

import com.violanotes.sudokusolver.exceptions.BoardEntityValidationException;

/**
 * Created by pc on 7/22/2017.
 */
public interface Validatable {
    default void validate() throws BoardEntityValidationException {
        System.out.println("validate() called on class '" + this.getClass().getSimpleName() + '"');
        doValidate();
    }

    void doValidate() throws BoardEntityValidationException;
}
