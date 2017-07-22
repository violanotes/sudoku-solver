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
public class BoxColumn extends BoardEntity {


    public BoxColumn(boolean initializeToEmpty) throws BoardEntityException {
        super(initializeToEmpty);
    }

    public BoxColumn() throws BoardEntityException {
        super();
    }

    @Override
    public void initializeToEmpty() {
        boxes = new ArrayList<Box>();
        columns = new ArrayList<Column>();
        squares = new ArrayList<Square>();
    }

    @Override
    public void doValidate() throws BoardEntityValidationException {
        List<Integer> filledIn = new ArrayList<>();

        for (Square square : squares) {
            if (square.getState().equals(Square.SquareState.FILLED)) {
                if (!filledIn.contains(square.getNumber())) {
                    filledIn.add(square.getNumber());
                } else {
                    throw new BoardEntityValidationException("Box " + index + " should have only one square filled in with number " + square.getNumber() + ", but has more than one");
                }
            }
        }
    }

    @Override
    public void doAssociate(Associable<BoardEntity> entity) throws AssociationException {
        if (entity instanceof Box) {
            boxes.add((Box)entity);
            squares.addAll(((Box) entity).getSquares());
        } else if (entity instanceof Column) {
            columns.add((Column)entity);
        } else if (entity instanceof BoardState) {
            boardState = ((BoardState) (entity));
        } else {
            throw new AssociationException(entity, this);
        }
    }

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
    private List<Column> columns;
    @JsonIgnore
    private List<Square> squares;
    private Integer index;
}
