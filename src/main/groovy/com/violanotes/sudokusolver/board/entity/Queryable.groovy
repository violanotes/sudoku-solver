package com.violanotes.sudokusolver.board.entity

import com.violanotes.sudokusolver.exceptions.QueryException

/**
 * Created by pc on 7/20/2017.
 */
interface Queryable {
    BoardEntity[] queryForClass(Class<BoardEntity> clazz, Closure<Boolean> condition) throws QueryException
    BoardEntity queryForSingle(Class<BoardEntity> clazz, Closure<Boolean> condition) throws QueryException
    BoardEntity[] queryForMultiple(Class<BoardEntity> clazz, Closure<Boolean> condition) throws QueryException
    BoardEntity[] queryForMultiple(Class<BoardEntity> clazz, int count, Closure<Boolean> condition) throws QueryException
    BoardEntity queryForNone(Class<BoardEntity> clazz, Closure<Boolean> condition) throws QueryException
}