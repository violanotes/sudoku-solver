package com.violanotes.sudokusolver.board.basic

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.violanotes.sudokusolver.board.entity.State
import com.violanotes.sudokusolver.board.supplemental.Box2
import com.violanotes.sudokusolver.board.supplemental.Column
import com.violanotes.sudokusolver.board.supplemental.Row
import com.violanotes.sudokusolver.board.entity.BoardEntity
import com.violanotes.sudokusolver.exceptions.AssociationException
import groovy.transform.InheritConstructors

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@InheritConstructors
class Square extends BoardEntity {
    List<Hypothetical> hypotheticals
    Integer index
    Integer number
    SquareState state

    Row row
    Column column
    Box2 box

    enum SquareState {
        @JsonProperty("available") AVAILABLE,
        @JsonProperty("filled") FILLED
    }

    @Override
    void initializeToEmpty() {
        hypotheticals = []
    }

//    @Override
    void setInitialState() {
        state = SquareState.AVAILABLE
    }

//    @Override
    void changeState(State newState) {
        println "changing state to: ${newState.getState().class.simpleName} : ${newState.getState()}"
    }

    @Override
    void associate(BoardEntity entity) throws AssociationException {
        switch (entity.class) {
            case Hypothetical: hypotheticals.add entity
                break
            case BoardState: boardState = entity
                break
            case Row: row = entity
                break
            case Column: column = entity
                break
            case Box2: box = entity
                break
            default:
                throw new AssociationException(entity, this)
        }
    }

    @Override
    String toString() {
        return "Square{" +
                // "hypotheticals=" + hypotheticals +
                ", index=" + index +
                ", number=" + number +
                ", state=" + state +
                ", row=" + row.index +
                ", column=" + column.index +
                ", box=" + box.index +
                ", boxColumn=" + column.boxColumn.index +
                ", boxRow=" + box.boxRow.index +
                '}'
    }
}
