package com.violanotes.sudokusolver.board.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.violanotes.sudokusolver.board.basic.BoardState
import com.violanotes.sudokusolver.exceptions.QueryException

import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType

/**
 * Created by pc on 7/20/2017.
 */
abstract class BoardEntity implements Associable, InitializableToEmpty, Queryable<BoardEntity> {

    /**
     * all board entities should have a reference to the current board state
     */
    @JsonIgnore
    BoardState boardState

    BoardEntity(boolean initializeToEmpty = false){
        if (initializeToEmpty) this.initializeToEmpty()
        QueryForClassImpl.populateListsMap(this)
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
        List<BoardEntity> list = QueryForClassImpl.getList(this.class.name, clazz.name)

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

    static class QueryForClassImpl {

        private static Map<String, Map<String, List<BoardEntity>>> subclassListsMap

        private QueryForClassImpl() {}

        static Map<String, Map<String, List<BoardEntity>>> getSubclassListsMap() {
            if (subclassListsMap == null)
                subclassListsMap = new HashMap<>()
            return subclassListsMap
        }

        static void addList(String subclassName, String listClassName, List<BoardEntity> list) {
            if (getMapForSubclass(subclassName) == null) {
                addSubclass(subclassName)
            }

            getSubclassListsMap().get(subclassName).put(listClassName, list)
        }

        static List<BoardEntity> getList(String subclassName, String listClassName) {
            return getSubclassListsMap().get(subclassName).get(listClassName)
        }

        static void addSubclass(String subclassName) {
            if (getSubclassListsMap().get(subclassName) == null)
                getSubclassListsMap().put(subclassName, new HashMap<String, List<BoardEntity>>())
        }

        static Map<String, List<BoardEntity>> getMapForSubclass(String subclassName) {
            return getSubclassListsMap().get(subclassName)
        }

        static void populateListsMap(BoardEntity root) {

//            println "populating ListsMap for root: '${root.class.simpleName}'"


            for (Field field : root.getClass().getDeclaredFields()) {

                // to qualify, the field must by of class List<>
                // and must have type parameter <BoardEntity>
                if (List.class.isAssignableFrom(field.type)) {
                    Class clazz = ((ParameterizedType)field.genericType).actualTypeArguments[0]
                    if (BoardEntity.isAssignableFrom(clazz)) {
                        field.setAccessible(true)
                        addList(root.class.name, clazz.name, field.get(root))
                    }
                }
            }

//            println "subclassListsMap for class '${root.class.simpleName}': ${getSubclassListsMap()}"

        }
    }

    static void associate(BoardEntity entity1, BoardEntity entity2) {
        entity1.associate(entity2)
        entity2.associate(entity1)
    }
}
