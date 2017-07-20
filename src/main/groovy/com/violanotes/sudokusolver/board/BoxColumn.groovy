package com.violanotes.sudokusolver.board

import java.util.List

/**
 * Created by pc on 7/20/2017.
 */
class BoxColumn {
    private List<Box> boxes
    private List<Column> columns
    private Integer index

    List<Box> getBoxes() {
        return boxes
    }

    void setBoxes(List<Box> boxes) {
        this.boxes = boxes
    }

    List<Column> getColumns() {
        return columns
    }

    void setColumns(List<Column> columns) {
        this.columns = columns
    }

    Integer getIndex() {
        return index
    }

    void setIndex(Integer index) {
        this.index = index
    }
}
