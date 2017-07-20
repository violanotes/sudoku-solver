package com.violanotes.sudokusolver.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Square {
    private List<Hypothetical> hypotheticals;
    private Integer index;
    private Integer number;
    private SquareState state;

    private Row row;
    private Column column;
    private Box box;

    public enum SquareState {
        @JsonProperty("available") AVAILABLE,
        @JsonProperty("filled") FILLED;
    }

    public List<Hypothetical> getHypotheticals() {
        return hypotheticals;
    }

    public void setHypotheticals(List<Hypothetical> hypotheticals) {
        this.hypotheticals = hypotheticals;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public SquareState getState() {
        return state;
    }

    public void setState(SquareState state) {
        this.state = state;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    @Override
    public String toString() {
        return "Square{" +
                "hypotheticals=" + hypotheticals +
                ", index=" + index +
                ", number=" + number +
                ", state=" + state +
                '}';
    }
}
