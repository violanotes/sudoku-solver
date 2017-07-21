package com.violanotes.sudokusolver.board.entity

import com.violanotes.sudokusolver.exceptions.QueryException

import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType

/**
 * Created by pc on 7/20/2017.
 */
abstract class BoardEntity implements Associable, InitializableToEmpty, Queryable {

    static Map<String, Map<String, List<BoardEntity>>> subclassListsMap

    BoardEntity(boolean initializeToEmpty = false){
        if (initializeToEmpty) this.initializeToEmpty()
        populateListsMap()
    }

    static {
        subclassListsMap = new HashMap<>()
    }

    void populateListsMap() {

        if (subclassListsMap.get(this.class.name) == null) {
            subclassListsMap.put(this.class.name, new HashMap<String, List<BoardEntity>>())

            for (Field field : this.getClass().getDeclaredFields()) {

                if (List.class.isAssignableFrom(field.type)) {

                    Class clazz = ((ParameterizedType)field.genericType).actualTypeArguments[0]
                    if (BoardEntity.isAssignableFrom(clazz)) {
                        field.setAccessible(true)
                        subclassListsMap.get(this.class.name).put(clazz.name, field.get(this))
                    }
                }
            }

            println "subclassListsMap for class '${this.class.simpleName}': ${subclassListsMap}"
        }
    }

    @Override
    BoardEntity queryForSingle(Class<BoardEntity> clazz, Closure<Boolean> condition) throws QueryException {
        BoardEntity[] results = this.queryForClass(clazz, condition)

        if (results != null && results.length == 1) {
            return results[0]
        }

        throw new QueryException("Queried for single entity of class '${clazz.simpleName}' " +
                "but received ${results.length} results.")
    }

    @Override
    BoardEntity[] queryForMultiple(Class<BoardEntity> clazz, Closure<Boolean> condition) throws QueryException {
        BoardEntity[] results = this.queryForClass(clazz, condition)

        if (results != null && results.length > 1) {
            return results
        }

        throw new QueryException("Queried for multiple entities of class '${clazz.simpleName}' " +
                "but received ${results.length} results.")
    }

    @Override
    BoardEntity[] queryForMultiple(Class<BoardEntity> clazz, int count, Closure<Boolean> condition) throws QueryException {
        BoardEntity[] results = this.queryForClass(clazz, condition)

        if (results != null && results.length == count) {
            return results
        }

        throw new QueryException("Queried for ${count} entities of class '${clazz.simpleName}' " +
                "but received ${results.length} results.")
    }

    @Override
    BoardEntity queryForNone(Class<BoardEntity> clazz, Closure<Boolean> condition) throws QueryException {
        BoardEntity[] results = this.queryForClass(clazz, condition)

        if (results != null && results.length == 0) {
            return null
        }

        throw new QueryException("Queried for 0 entities of class '${clazz.simpleName}' " +
                "but received ${results.length} results.")
    }

    @Override
    BoardEntity[] queryForClass(Class<BoardEntity> clazz, Closure<Boolean> condition) throws QueryException {

        println "querying entity of class '${this.class.simpleName}' for ${clazz.simpleName}"
        List<BoardEntity> results = new ArrayList<>()
        List<BoardEntity> list = subclassListsMap.get(this.class.name).get(clazz.name)

        if (list == null)
            throw new QueryException("Error querying for class name: ${clazz.name}")

        try {
            list.each {
                if (condition(it)) {
                    results.add(it)
                }
            }
        } catch (QueryException e) {
            throw new QueryException(e)
        }

        println "queryForClass returned ${results.size()} results: ${results}"
        return results
    }
}
