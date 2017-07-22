package com.violanotes.sudokusolver.board.geometric;

import com.violanotes.sudokusolver.board.basic.BoardState;
import com.violanotes.sudokusolver.board.basic.Square;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.violanotes.sudokusolver.board.entity.Associable;
import com.violanotes.sudokusolver.board.entity.BoardEntity;
import com.violanotes.sudokusolver.exceptions.AssociationException;
import com.violanotes.sudokusolver.exceptions.BoardEntityException;
import com.violanotes.sudokusolver.exceptions.BoardEntityValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
public class BoxRow extends BoardEntity {

    public BoxRow(boolean initializeToEmpty) throws BoardEntityException {
        super(initializeToEmpty);
    }

    public BoxRow() throws BoardEntityException {
        super();
    }

    @Override
    public void initializeToEmpty() {
        boxes = new ArrayList<Box>();
        rows = new ArrayList<Row>();
        squares = new ArrayList<Square>();
    }

    @Override
    public void doValidate() throws BoardEntityValidationException {

    }

    @Override
    public void doAssociate(Associable<BoardEntity> entity) throws AssociationException {
        if (entity instanceof Box) {
            boxes.add((Box)entity);
            squares.addAll(((Box) entity).getSquares());
        } else if (entity instanceof Row) {
            rows.add((Row)entity);
        } else if (entity instanceof BoardState) {
            boardState = ((BoardState) (entity));
        } else {
            throw new AssociationException(entity, this);
        }
    }

    @Override
    public String getId() {
        return "BoxRow " + index;
    }

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

    public List<Square> getSquares() {
        return squares;
    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @JsonIgnore
    private List<Box> boxes;
    @JsonIgnore
    private List<Row> rows;
    @JsonIgnore
    private List<Square> squares;
    private Integer index;
}
