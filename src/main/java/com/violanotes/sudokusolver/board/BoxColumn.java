package com.violanotes.sudokusolver.board;

import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
public class BoxColumn {
    private List<Box> boxes;
    private List<Column> columns;

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
