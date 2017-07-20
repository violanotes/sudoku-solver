package com.violanotes.sudokusolver.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hypothetical {

    private Integer number;
    private HypotheticalState state;
    private Square square;

    public enum HypotheticalState {
        @JsonProperty("available") AVAILABLE,
        @JsonProperty("eliminated") ELIMINATED,
        @JsonProperty("filled") FILLED;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public HypotheticalState getState() {
        return state;
    }

    public void setState(HypotheticalState state) {
        this.state = state;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    @Override
    public String toString() {
        return "Hypothetical{" +
                "number=" + number +
                ", state=" + state +
                '}';
    }
}
