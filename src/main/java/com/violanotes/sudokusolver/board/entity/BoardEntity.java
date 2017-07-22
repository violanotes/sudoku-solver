package com.violanotes.sudokusolver.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.violanotes.sudokusolver.board.basic.BoardState;
import com.violanotes.sudokusolver.exceptions.BoardEntityException;
import com.violanotes.sudokusolver.exceptions.QueryException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 7/20/2017.
 */
public abstract class BoardEntity implements Associable<BoardEntity>, InitializableToEmpty, Queryable<BoardEntity> {

    /**
     * all board entities should have a reference to the current board state
     */
    @JsonIgnore
    protected BoardState boardState;

    public BoardEntity(boolean initializeToEmpty) throws BoardEntityException {
        if (initializeToEmpty) this.initializeToEmpty();
        QueryForClassImpl.populateListsMap(this);
    }

    public BoardEntity() throws BoardEntityException {
        initializeToEmpty();
    }

    /**
     * instance initializer
     */
    {
        QueryForClassImpl.populateListsMap(this);
    }

    @Override
    public <Q extends BoardEntity> Q queryForSingle(final Class<Q> clazz, Condition<Q> condition) throws QueryException {
        List<Q> results = this.queryForClass(clazz, condition);

        if (results != null && results.size() == 1) {
            return results.get(0);
        }


        throw new QueryException("Queried for single entity of class '" + clazz.getSimpleName() + "' but received " + results.size() + " results.");
    }

    @Override
    public <Q extends BoardEntity> List<Q> queryForMultiple(Class<Q> clazz, Condition<Q> condition) throws QueryException {
        List<Q> results = this.queryForClass(clazz, condition);

        if (results != null && results.size() > 1) {
            return results;
        }


        throw new QueryException("Queried for multiple entities of class '" + clazz.getSimpleName() + "' but received " + results.size() + " results.");
    }

    @Override
    public <Q extends BoardEntity> List<Q> queryForMultiple(Class<Q> clazz, int count, Condition<Q> condition) throws QueryException {
        List<Q> results = this.queryForClass(clazz, condition);

        if (results != null && results.size() == count) {
            return results;
        }


        throw new QueryException("Queried for " + String.valueOf(count) + " entities of class '" + clazz.getSimpleName() + "' but received " + results.size() + " results.");
    }

    @Override
    public <Q extends BoardEntity> Q queryForNone(Class<Q> clazz, Condition<Q> condition) throws QueryException {
        List<Q> results = this.queryForClass(clazz, condition);

        if (results != null && results.size() == 0) {
            return null;
        }


        throw new QueryException("Queried for 0 entities of class \'" + clazz.getSimpleName() + "' but received " + results.size() + " results.");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Q extends BoardEntity> List<Q> queryForClass(Class<Q> clazz, Condition<Q> condition) throws QueryException {
        System.out.println("querying entity of class '" + this.getClass().getSimpleName() + "' for " + clazz.getSimpleName());
        final List<Q> results = new ArrayList<>();
        List<Q> list = QueryForClassImpl.getList(this.getClass(), clazz);

        if (list == null) throw new QueryException("Error querying for class name: " + clazz.getName());

        for (Q entity : list) {
            if (condition.evaluate(entity)) {
                results.add(entity);
            }
        }


        System.out.println("queryForClass returned " + results.size() + " results: " + results);
        return results;
    }

    public BoardState getBoardState() {
        return boardState;
    }

    public void setBoardState(BoardState boardState) {
        this.boardState = boardState;
    }

    public static class QueryForClassImpl {
        private QueryForClassImpl() {
        }

        public static Map<String, Map<String, List<BoardEntity>>> getSubclassListsMap() {
            if (subclassListsMap == null) subclassListsMap = new HashMap<String, Map<String, List<BoardEntity>>>();
            return subclassListsMap;
        }

        public static void addList(String subclassName, String listClassName, List<BoardEntity> list) {
            if (getMapForSubclass(subclassName) == null) {
                addSubclass(subclassName);
            }


            getSubclassListsMap().get(subclassName).put(listClassName, list);
        }

        @SuppressWarnings("unchecked")
        public static <Q> List<Q> getList(Class subclass, Class<Q> listClass) {
            return (List<Q>)getSubclassListsMap()
                    .get(subclass.getName())
                    .get(listClass.getName());
        }

        public static void addSubclass(String subclassName) {
//            if (getSubclassListsMap().get(subclassName) == null)
                getSubclassListsMap().put(subclassName, new HashMap<>());
        }

        public static Map<String, List<BoardEntity>> getMapForSubclass(String subclassName) {
            return getSubclassListsMap().get(subclassName);
        }

        public static void populateListsMap(BoardEntity root) throws BoardEntityException {

//            println "populating ListsMap for root: '${root.class.simpleName}'"

            for (Field field : root.getClass().getDeclaredFields()) {

                // to qualify, the field must by of class List<>
                // and must have type parameter <BoardEntity>
                try {
                    if (List.class.isAssignableFrom(field.getType())) {
                        Class clazz = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                        if (BoardEntity.class.isAssignableFrom(clazz)) {
                            field.setAccessible(true);

                            @SuppressWarnings("unchecked")
                            List<BoardEntity> list = (List<BoardEntity>) field.get(root);
                            addList(root.getClass().getName(), clazz.getName(), list);
                        }
                    }

                } catch (IllegalAccessException e) {
                    throw new BoardEntityException("Unable to access field '" + field.getName()
                            + "' in entity '" + root.getClass().getSimpleName());
                }
            }

//            System.out.println("subclassListsMap for class '" + root.getClass().getName() + "':" + subclassListsMap);
        }

        private static Map<String, Map<String, List<BoardEntity>>> subclassListsMap;
    }
}
