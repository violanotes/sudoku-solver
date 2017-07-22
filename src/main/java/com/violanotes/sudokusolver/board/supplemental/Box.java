package com.violanotes.sudokusolver.board.supplemental;

import com.violanotes.sudokusolver.board.basic.BoardState;
import com.violanotes.sudokusolver.board.basic.Square;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.violanotes.sudokusolver.board.entity.BoardEntity;
import com.violanotes.sudokusolver.exceptions.AssociationException;
import com.violanotes.sudokusolver.exceptions.BoardEntityException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
public class Box extends BoardEntity {
    public Box(boolean initializeToEmpty) throws BoardEntityException {
        super(initializeToEmpty);
    }

    public Box() throws BoardEntityException {}

    @Override
    public void initializeToEmpty() {
        squares = new ArrayList<>();
    }

    @Override
    public void associate(BoardEntity entity) throws AssociationException {
        if (entity instanceof Square) {
            squares.add((Square)entity);
        } else if (entity instanceof BoardState) {
            boardState = ((BoardState) (entity));
        } else if (entity instanceof BoxRow) {
            boxRow = ((BoxRow) (entity));
        } else if (entity instanceof BoxColumn) {
            boxColumn = ((BoxColumn) (entity));
        } else {
            throw new AssociationException(entity, this);
        }
    }

    public List<Square> getSquares() {
        return squares;
    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
    }

    public BoxRow getBoxRow() {
        return boxRow;
    }

    public void setBoxRow(BoxRow boxRow) {
        this.boxRow = boxRow;
    }

    public BoxColumn getBoxColumn() {
        return boxColumn;
    }

    public void setBoxColumn(BoxColumn boxColumn) {
        this.boxColumn = boxColumn;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @JsonIgnore
    private List<Square> squares;
    private BoxRow boxRow;
    private BoxColumn boxColumn;
    private Integer index;
}
