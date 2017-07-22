package com.violanotes.sudokusolver.board.geometric;

import com.violanotes.sudokusolver.board.basic.BoardState;
import com.violanotes.sudokusolver.board.basic.Square;
import com.violanotes.sudokusolver.board.basic.Square.SquareState;
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
public class Column extends BoardEntity {

    public Column(boolean initializeToEmpty) throws BoardEntityException {
        super(initializeToEmpty);
    }

    public Column() throws BoardEntityException {
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
                    throw new BoardEntityValidationException("Column " + index +
                            " should have only one square filled in with number " +
                            i + ", but has " + squares.size());
            }
        } catch (QueryException e) {
            throw new BoardEntityValidationException(e);
        }
    }

    @Override
    public void doAssociate(Associable<BoardEntity> entity) throws AssociationException {
        if (entity instanceof Square) {
            squares.add((Square)entity);
        } else if (entity instanceof BoxColumn) {
            boxColumn = ((BoxColumn) (entity));
        } else if (entity instanceof BoardState) {
            boardState = ((BoardState) (entity));
        } else {
            throw new AssociationException(entity, this);
        }
    }


    @Override
    public String getId() {
        return "BoxColumn " + index;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
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
    private BoxColumn boxColumn;
    private Integer index;
}
