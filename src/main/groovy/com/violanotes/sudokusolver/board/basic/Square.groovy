package com.violanotes.sudokusolver.board.basic

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.violanotes.sudokusolver.board.associative.Box
import com.violanotes.sudokusolver.board.associative.Column
import com.violanotes.sudokusolver.board.associative.Row
import com.violanotes.sudokusolver.board.entity.BoardEntity
import com.violanotes.sudokusolver.exceptions.AssociationException
import com.violanotes.sudokusolver.exceptions.QueryException
import groovy.transform.InheritConstructors

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@InheritConstructors
class Square extends BoardEntity {
    BoardState boardState
    List<Hypothetical> hypotheticals
    Integer index
    Integer number
    SquareState state

    Row row
    Column column
    Box box

    enum SquareState {
        @JsonProperty("available") AVAILABLE,
        @JsonProperty("filled") FILLED
    }

    @Override
    void initializeToEmpty() {
        hypotheticals = []
    }

    @Override
    void associate(BoardEntity entity) throws AssociationException {
        switch (entity.class) {
            case (BoardState.class):
                entity.squares.add(this)
                break
            default:
                throw new AssociationException(entity, this)
        }
    }

    @Override
    BoardEntity[] queryForClass(Class<BoardEntity> clazz, Closure<Boolean> condition) throws QueryException {
        return null
    }

    @Override
    String toString() {
        return "Square{" +
                "hypotheticals=" + hypotheticals +
                ", index=" + index +
                ", number=" + number +
                ", state=" + state +
                '}'
    }
}
