package com.violanotes.sudokusolver.board.entity;

import com.violanotes.sudokusolver.exceptions.QueryException;

import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
public interface Queryable<T> {
    <Q extends T> List<Q> queryForClass(Class<Q> clazz, Condition<Q> condition, Object...args) throws QueryException;

    <Q extends T> Q queryForSingle(Class<Q> clazz, Condition<Q> condition, Object...args) throws QueryException;

    <Q extends T> List<Q> queryForMultiple(Class<Q> clazz, Condition<Q> condition, Object...args) throws QueryException;

    <Q extends T> List<Q> queryForMultiple(Class<Q> clazz, int count, Condition<Q> condition, Object...args) throws QueryException;

    <Q extends T> Q queryForNone(Class<Q> clazz, Condition<Q> condition, Object...args) throws QueryException;
}
