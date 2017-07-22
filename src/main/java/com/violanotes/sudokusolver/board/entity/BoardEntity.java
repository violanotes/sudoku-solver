package com.violanotes.sudokusolver.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.violanotes.sudokusolver.board.basic.BoardState;
import com.violanotes.sudokusolver.exceptions.BoardEntityException;
import com.violanotes.sudokusolver.exceptions.QueryException;

import javax.management.Query;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 7/20/2017.
 */
public abstract class BoardEntity implements
        Associable<BoardEntity>,
        InitializableToEmpty,
        Queryable<BoardEntity>,
        Validatable {

    /**
     * all board entities should have a reference to the current board state
     */
    @JsonIgnore
    protected BoardState boardState;

    public BoardEntity(boolean initializeToEmpty) throws BoardEntityException {
        if (initializeToEmpty) {
            this.initializeToEmpty();
        }
        QueryForClassImpl.populateListsMap(this);
    }

    public BoardEntity() throws BoardEntityException {
        initializeToEmpty();
        QueryForClassImpl.populateListsMap(this);
    }

    @Override
    public <Q extends BoardEntity> Q queryForSingle(final Class<Q> clazz, Condition<Q> condition, Object...args) throws QueryException {
        List<Q> results = this.queryForClass(clazz, condition, args);

        if (results != null && results.size() == 1) {
            return results.get(0);
        }


        throw new QueryException("Queried for single entity of class '" + clazz.getSimpleName() + "' but received " + results.size() + " results.");
    }

    @Override
    public <Q extends BoardEntity> List<Q> queryForMultiple(Class<Q> clazz, Condition<Q> condition, Object...args) throws QueryException {
        List<Q> results = this.queryForClass(clazz, condition, args);

        if (results != null && results.size() > 1) {
            return results;
        }


        throw new QueryException("Queried for multiple entities of class '" + clazz.getSimpleName() + "' but received " + results.size() + " results.");
    }

    @Override
    public <Q extends BoardEntity> List<Q> queryForMultiple(Class<Q> clazz, int count, Condition<Q> condition, Object...args) throws QueryException {
        List<Q> results = this.queryForClass(clazz, condition, args);

        if (results != null && results.size() == count) {
            return results;
        }


        throw new QueryException("Queried for " + String.valueOf(count) + " entities of class '" + clazz.getSimpleName() + "' but received " + results.size() + " results.");
    }

    @Override
    public <Q extends BoardEntity> Q queryForNone(Class<Q> clazz, Condition<Q> condition, Object...args) throws QueryException {
        List<Q> results = this.queryForClass(clazz, condition, args);

        if (results != null && results.size() == 0) {
            return null;
        }


        throw new QueryException("Queried for 0 entities of class \'" + clazz.getSimpleName() + "' but received " + results.size() + " results.");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Q extends BoardEntity> List<Q> queryForClass(Class<Q> clazz, Condition<Q> condition, Object...args) throws QueryException {
        System.out.println("querying entity of class '" + this.getClass().getSimpleName() + "' for " + clazz.getSimpleName());
        final List<Q> results = new ArrayList<>();
        List<Q> list = QueryForClassImpl.getList(this, clazz);

        if (list == null) throw new QueryException("Error querying for class name: " + clazz.getSimpleName());

        for (Q entity : list) {
            if (condition.evaluate(entity, args)) {  //TODO insert args here?
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

    /**
     * This class handles the maps that hold information on
     * which items can be queried from a class.
     *
     * Each entity class name will have its own map.
     * This map will map the queried-for class name with the
     * concrete List.
     */
    public static class QueryForClassImpl {

        /**
         * each entity instance (determined by hash code) will have
         * its own instance of the QueryForClass implementation.
         */
        private static Map<BoardEntity, QueryForClassImpl> instances;

        private Map<Class<? extends BoardEntity>, List<? extends BoardEntity>> listMap;

        /**
         * This class will be instantiated internally when classes
         * call it to populate the field.
         */
        private QueryForClassImpl() {}

        // instance methods

        public <T extends BoardEntity> void addList(Class<T> listItemClass, List<T> list) {
            getListMap().put(listItemClass, list);
        }

        @SuppressWarnings("unchecked")
        public <Q> List<Q> getList(Class<Q> listItemClass) {
            return (List<Q>) getListMap().get(listItemClass);
        }

        public Map<Class<? extends BoardEntity>, List<? extends BoardEntity>> getListMap() {
            if (listMap == null) {
                listMap = new HashMap<>();
            }
            return listMap;
        }

        // static methods

        public static <T extends BoardEntity> void addList(BoardEntity entity, Class<T> listItemClass, List<T> list) {
            getInstance(entity).getListMap().put(listItemClass, list);
        }

        @SuppressWarnings("unchecked")
        public static <Q> List<Q> getList(BoardEntity entity, Class<Q> listClass) {
            return (List<Q>)getInstance(entity).getList(listClass);
        }

        private static Map<Class<? extends BoardEntity>, List<? extends BoardEntity>> getListMap(BoardEntity entity) {
            return getInstance(entity).getListMap();
        }

        public static QueryForClassImpl getInstance(BoardEntity entity) {
            if (getInstances().get(entity) == null) {
                getInstances().put(entity, new QueryForClassImpl());
            }
            return getInstances().get(entity);
        }

        public static Map<BoardEntity, QueryForClassImpl> getInstances() {
            if (instances == null) {
                instances = new HashMap<>();
            }

            return instances;
        }

        public static void populateListsMap(BoardEntity entity) throws BoardEntityException {

            System.out.println("populating ListsMap for entity: " + entity.getClass().getSimpleName() + "'");

            for (Field field : entity.getClass().getDeclaredFields()) {

                // to qualify, the field must by of class List<>
                // and must have type parameter <BoardEntity>
                try {
                    if (List.class.isAssignableFrom(field.getType())) {
                        Class typeParamClazz = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                        if (BoardEntity.class.isAssignableFrom(typeParamClazz)) {
                            field.setAccessible(true);

                            @SuppressWarnings("unchecked")
                            List<BoardEntity> list = (List<BoardEntity>) field.get(entity);
                            addList(entity, typeParamClazz, list);
                        }
                    }

                } catch (IllegalAccessException e) {
                    throw new BoardEntityException("Unable to access field '" + field.getName()
                            + "' in entity '" + entity.getClass().getSimpleName());
                }
            }

            System.out.println("listMap for class '" + entity.getClass().getName() + "':" + getListMap(entity));
        }

        public static void setInstances(Map<BoardEntity, QueryForClassImpl> instances) {
            QueryForClassImpl.instances = instances;
        }

        public void setListMap(Map<Class<? extends BoardEntity>, List<? extends BoardEntity>> listMap) {
            this.listMap = listMap;
        }
    }
}
