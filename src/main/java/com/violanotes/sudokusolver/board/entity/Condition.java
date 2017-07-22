package com.violanotes.sudokusolver.board.entity;

import com.violanotes.sudokusolver.exceptions.QueryException;

/**
 * Created by pc on 7/21/2017.
 */
@FunctionalInterface
public interface Condition<T> {
    boolean evaluate(T entity) throws QueryException;
}
