package com.violanotes.sudokusolver.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardState {
    private List<Square> squares;
    private Integer sequence;

    private List<Row> rows;
    private List<Column> columns;
    private List<Box> boxes;
    private List<BoxRow> boxRows;
    private List<BoxColumn> boxColumns;

    public List<Square> getSquares() {
        return squares;
    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public List<BoxRow> getBoxRows() {
        return boxRows;
    }

    public void setBoxRows(List<BoxRow> boxRows) {
        this.boxRows = boxRows;
    }

    public List<BoxColumn> getBoxColumns() {
        return boxColumns;
    }

    public void setBoxColumns(List<BoxColumn> boxColumns) {
        this.boxColumns = boxColumns;
    }

    @Override
    public String toString() {
        return "BoardState{" +
                "squares=" + squares +
                ", sequence=" + sequence +
                '}';
    }
}
