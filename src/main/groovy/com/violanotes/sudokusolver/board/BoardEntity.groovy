package com.violanotes.sudokusolver.board

import com.violanotes.sudokusolver.exceptions.BoardEntityException

/**
 * Created by pc on 7/20/2017.
 */
abstract class BoardEntity implements Associable, InitializableToEmpty {
    BoardEntity(){}

    BoardEntity(Closure<Void> closure) {
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.delegate = this
    }

    static newEmpty(Class clazz) throws BoardEntityException {
        if (BoardEntity.class.isAssignableFrom(clazz)) {
            BoardEntity entity = clazz.newInstance()
            entity.initializeToEmpty()
            return entity
        }
        throw new BoardEntityException("provided class '${clazz.simpleName}' not an instance of class ${BoardEntity.simpleName}")
    }
}
