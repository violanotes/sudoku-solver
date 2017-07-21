package com.violanotes.sudokusolver.board.entity

import com.violanotes.sudokusolver.exceptions.QueryException

/**
 * Created by pc on 7/20/2017.
 */
interface Queryable<T> {
    T[] queryForClass(Class<T> clazz, Closure<Boolean> condition) throws QueryException
    T queryForSingle(Class<T> clazz, Closure<Boolean> condition) throws QueryException
    T[] queryForMultiple(Class<T> clazz, Closure<Boolean> condition) throws QueryException
    T[] queryForMultiple(Class<T> clazz, int count, Closure<Boolean> condition) throws QueryException
    T queryForNone(Class<T> clazz, Closure<Boolean> condition) throws QueryException
}