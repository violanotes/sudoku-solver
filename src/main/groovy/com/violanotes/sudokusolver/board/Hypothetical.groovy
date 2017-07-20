package com.violanotes.sudokusolver.board

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.violanotes.sudokusolver.exceptions.AssociationException

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Hypothetical extends BoardEntity {

    private Integer number
    private HypotheticalState state
    private Square square

    enum HypotheticalState {
        @JsonProperty("available") AVAILABLE,
        @JsonProperty("eliminated") ELIMINATED,
        @JsonProperty("filled") FILLED
    }

    @Override
    void initializeToEmpty() {

    }

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

    Integer getNumber() {
        return number
    }

    void setNumber(Integer number) {
        this.number = number
    }

    HypotheticalState getState() {
        return state
    }

    void setState(HypotheticalState state) {
        this.state = state
    }

    Square getSquare() {
        return square
    }

    void setSquare(Square square) {
        this.square = square
    }

    @Override
    String toString() {
        return "Hypothetical{" +
                "number=" + number +
                ", state=" + state +
                '}'
    }
}
