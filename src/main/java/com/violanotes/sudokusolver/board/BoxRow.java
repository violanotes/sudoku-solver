package com.violanotes.sudokusolver.board;

import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
public class BoxRow {
    private List<Box> boxes;
    private List<Row> rows;

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
}
