package com.violanotes.sudokusolver.board.basic

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.violanotes.sudokusolver.board.entity.BoardEntity
import com.violanotes.sudokusolver.exceptions.AssociationException
import com.violanotes.sudokusolver.exceptions.QueryException
import groovy.transform.InheritConstructors

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@InheritConstructors
class Hypothetical extends BoardEntity {

    Integer number
    HypotheticalState state
    Square square

    enum HypotheticalState {
        @JsonProperty("available") AVAILABLE,
        @JsonProperty("eliminated") ELIMINATED,
        @JsonProperty("filled") FILLED
    }

    @Override
    void initializeToEmpty() {}

    @Override
    void associate(BoardEntity entity) throws AssociationException {
        switch(entity.class) {
            case Square:
                entity.hypotheticals.add(this)
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
        return "Hypothetical{" +
                "number=" + number +
                ", state=" + state +
                '}'
    }
}