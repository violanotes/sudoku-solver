package com.violanotes.sudokusolver.board

/**
 * Created by pc on 7/20/2017.
 */
class BoxRow {
    private List<Box> boxes
    private List<Row> rows
    private Integer index

    List<Box> getBoxes() {
        return boxes
    }

    void setBoxes(List<Box> boxes) {
        this.boxes = boxes
    }

    List<Row> getRows() {
        return rows
    }

    void setRows(List<Row> rows) {
        this.rows = rows
    }

    Integer getIndex() {
        return index
    }

    void setIndex(Integer index) {
        this.index = index
    }
}
