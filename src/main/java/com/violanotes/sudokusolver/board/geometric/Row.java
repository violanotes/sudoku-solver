package com.violanotes.sudokusolver.board.geometric;

import com.violanotes.sudokusolver.board.basic.BoardState;
import com.violanotes.sudokusolver.board.basic.Square;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.violanotes.sudokusolver.board.entity.Associable;
import com.violanotes.sudokusolver.board.entity.BoardEntity;
import com.violanotes.sudokusolver.exceptions.AssociationException;
import com.violanotes.sudokusolver.exceptions.BoardEntityException;
import com.violanotes.sudokusolver.exceptions.BoardEntityValidationException;
import com.violanotes.sudokusolver.exceptions.QueryException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
public class Row extends BoardEntity {

    public Row(boolean initializeToEmpty) throws BoardEntityException {
        super(initializeToEmpty);
    }

    public Row() throws BoardEntityException {
        super();
    }

    @Override
    public void initializeToEmpty() {
        squares = new ArrayList<Square>();
    }

    @Override
    public void doValidate() throws BoardEntityValidationException {
        try {
            for (int i = 1; i < 10; i++) {
                List<Square> squares = this.queryForClass(Square.class, (Square square, Object[] args) ->
                                square.getNumber() == args[0]
                        , i);

                if (squares.size() > 1)
                    throw new BoardEntityValidationException("Row " + index +
                            " should have only one square filled in with number " +
                            i + ", but has " + squares.size());
            }
        } catch (QueryException e) {
            throw new BoardEntityValidationException(e);
        }
    }

    @Override
    public void doAssociate(Associable<BoardEntity> entity) throws AssociationException {
        if (entity instanceof BoardState) {
            boardState = ((BoardState) (entity));
        } else if (entity instanceof Square) {
            squares.add((Square)entity);
        } else if (entity instanceof BoxRow) {
            boxRow = ((BoxRow) (entity));
        } else {
            throw new AssociationException(entity, this);
        }
    }


    @Override
    public String getId() {
        return "Row " + index;
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @JsonIgnore
    private List<Square> squares;
    private BoxRow boxRow;
    private Integer index;
}
