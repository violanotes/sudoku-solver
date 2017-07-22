package com.violanotes.sudokusolver.board.basic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.violanotes.sudokusolver.board.entity.Associable;
import com.violanotes.sudokusolver.board.supplemental.Box;
import com.violanotes.sudokusolver.board.supplemental.Row;
import com.violanotes.sudokusolver.board.entity.BoardEntity;
import com.violanotes.sudokusolver.exceptions.AssociationException;
import com.violanotes.sudokusolver.exceptions.BoardEntityException;
import com.violanotes.sudokusolver.exceptions.BoardStateValidationException;
import com.violanotes.sudokusolver.board.supplemental.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardState extends BoardEntity {

    private Integer sequence;
    private List<Square> squares;
    private List<Row> rows;
    private List<Column> columns;
    private List<Box> boxes;
    private List<BoxRow> boxRows;
    private List<BoxColumn> boxColumns;

    public BoardState(boolean initializeToEmpty) throws BoardEntityException {
        super(initializeToEmpty);
    }

    public BoardState() throws BoardEntityException {
    }

    @Override
    public void initializeToEmpty() {
        squares = new ArrayList<>();
        rows = new ArrayList<>();
        columns = new ArrayList<>();
        boxes = new ArrayList<>();
        boxRows = new ArrayList<>();
        boxColumns = new ArrayList<>();
    }

    @Override
    public void associate(BoardEntity entity) throws AssociationException {
        if (entity instanceof Square) {
            squares.add((Square)entity);
        }
        else if (entity instanceof Row) {
            rows.add((Row)entity);
        }
        else if (entity instanceof Column) {
            columns.add((Column)entity);
        }
        else if (entity instanceof Box) {
            boxes.add((Box)entity);
        }
        else if (entity instanceof BoxRow) {
            boxRows.add((BoxRow)entity);
        }
        else if (entity instanceof BoxColumn) {
            boxColumns.add((BoxColumn)entity);
        }
        else {
            throw new AssociationException(entity, this);
        }
    }

    public static BoardState create(String json) throws BoardEntityException {
        try {
            BoardState boardState = new ObjectMapper().readValue(json, BoardState.class);

            // now set up the board
            boardState.performBoardAssociations();

            return boardState;
        } catch (IOException e) {
            throw new BoardEntityException(e);
        }

    }

    public void validate() throws BoardStateValidationException {
        // enforce:
        // 1 filled-in hypothetical per square
        // 1 of each filled-in number per
        // row
        // column
        // box
        // all available squares have at least one available hypothetical
        // that is not already filled in in the square's
        // row
        // column
        // box

        for (Square square : squares) {
            int filledInCount = 0;// max should be 1
            int availableCount = 0;// should be more than 0 if filledIn is 0
            int eliminatedCount = 0;

            if (square.getHypotheticals().size() != 9) {
                throw new BoardStateValidationException("Square " + String.valueOf(square.getIndex()) + " should have 9 hypotheticals, but has " + String.valueOf(square.getHypotheticals().size()), this);
            }


            for (Hypothetical hypothetical : square.getHypotheticals()) {
                switch (hypothetical.getState()) {
                    case FILLED:
                        filledInCount = filledInCount++;
                        break;
                    case AVAILABLE:
                        availableCount = availableCount++;
                        break;
                    case ELIMINATED:
                        eliminatedCount = eliminatedCount++;
                        break;
                    default:
                        throw new BoardStateValidationException("Unacceptable HypotheticalState \'" + String.valueOf(hypothetical.getState()) + "\'", this);
                }
            }

            if (filledInCount > 1) {
                throw new BoardStateValidationException("Square " + String.valueOf(square.getIndex()) + " should have no more than 1 fille-in hypothetical, but has " + String.valueOf(filledInCount), this);
            }


            if (filledInCount == 1) {
                if (availableCount == 0) {
                    throw new BoardStateValidationException("Square " + String.valueOf(square.getIndex()) + " should have at least 1 available hypothetical, but has " + String.valueOf(availableCount), this);
                }

            }

        }


        for (Row row : rows) {
            List<Integer> filledIn = new ArrayList<Integer>();

            for (Square square : row.getSquares()) {
                if (square.getState().equals(com.violanotes.sudokusolver.board.basic.Square.SquareState.FILLED)) {
                    if (!((ArrayList<Integer>) filledIn).contains(square.getNumber())) {
                        ((ArrayList<Integer>) filledIn).add(square.getNumber());
                    } else {
                        throw new BoardStateValidationException("Row " + String.valueOf(row.getIndex()) + " should have only one square filled in with number " + String.valueOf(square.getNumber()) + ", but has more than one", this);
                    }

                }

            }

        }


        for (Column column : columns) {
            List<Integer> filledIn = new ArrayList<Integer>();

            for (Square square : column.getSquares()) {
                if (square.getState().equals(com.violanotes.sudokusolver.board.basic.Square.SquareState.FILLED)) {
                    if (!((ArrayList<Integer>) filledIn).contains(square.getNumber())) {
                        ((ArrayList<Integer>) filledIn).add(square.getNumber());
                    } else {
                        throw new BoardStateValidationException("Column " + String.valueOf(column.getIndex()) + " should have only one square filled in with number " + String.valueOf(square.getNumber()) + ", but has more than one", this);
                    }

                }

            }

        }


        for (Box box : boxes) {
            List<Integer> filledIn = new ArrayList<Integer>();

            for (Square square : box.getSquares()) {
                if (square.getState().equals(com.violanotes.sudokusolver.board.basic.Square.SquareState.FILLED)) {
                    if (!((ArrayList<Integer>) filledIn).contains(square.getNumber())) {
                        ((ArrayList<Integer>) filledIn).add(square.getNumber());
                    } else {
                        throw new BoardStateValidationException("Box " + String.valueOf(box.getIndex()) + " should have only one square filled in with number " + String.valueOf(square.getNumber()) + ", but has more than one", this);
                    }

                }

            }

        }


    }

    public static BoardState createBasic(String json) throws BoardEntityException {
        try {
            // turn the json into a list of numbers
            final List<Integer> numbers = Arrays.asList(new ObjectMapper().readValue(json, Integer[].class));

            // make sure there are exactly 81 numbers
            if (numbers.size() != 81) {
                throw new BoardEntityException(String.valueOf(numbers.size()) + " numbers provided.  Should be 81.");
            }


            // create 81 squares with the specified numbers
            // and default hypotheticals

            final BoardState boardState = new BoardState(true);

            for (int i = 0; i < 81; i++) {
                // create the square and associate it to the board
                final Square square = new Square();
                Associable.associate(square, boardState);
                square.setIndex(i);
                square.setNumber(numbers.get(i));

                for (int j = 0; j < 9; j++) {
                    // create the hypothetical and associate it with the square
                    Hypothetical hypothetical = new Hypothetical(true);
                    Associable.associate(square, hypothetical);
                    hypothetical.setNumber(j);
                }
            }

            boardState.performBoardAssociations();

            return boardState;

        } catch (IOException e) {
            throw new BoardEntityException(e);
        }

    }

    private void performBoardAssociations() throws BoardEntityException {

        // create 9 Rows
        for (int i = 0; i < 9; i++) {
            Row row = new Row(true);
            row.setIndex(i);
            Associable.associate(row, BoardState.this);
        }


        // create 9 Columns
        for (int i = 0; i < 9; i++) {
            Column column = new Column(true);
            column.setIndex(i);
            Associable.associate(column, BoardState.this);
        }

        // create 9 Boxes
        for (int i = 0; i < 9; i++) {
            Box box = new Box(true);
            box.setIndex(i);
            Associable.associate(box, BoardState.this);
        }

        // create 3 BoxRows
        for (int i = 0; i < 3; i++) {
            BoxRow boxRow = new BoxRow(true);
            boxRow.setIndex(i);
            Associable.associate(boxRow, BoardState.this);
        }

        // create 3 BoxColumns
        for (int i = 0; i < 3; i++) {
            BoxColumn boxColumn = new BoxColumn(true);
            boxColumn.setIndex(i);
            Associable.associate(boxColumn, BoardState.this);
        }

        // take each box and associate it with the appropriate
        // boxRow
        // boxColumn
        for (int i = 0; i < 9; i++) {
            Associable.associate(boxes.get(i), boxRows.get(i / 3));
            Associable.associate(boxes.get(i), boxColumns.get(i % 3));

            Associable.associate(rows.get(i), boxRows.get(i / 3));
            Associable.associate(columns.get(i), boxColumns.get(i / 3));
        }



        // take each square and associate it in the appropriate:

        // row
        // column
        // box
        for (int i = 0; i < 81; i++) {
            Associable.associate(squares.get(i), rows.get(i / 9));
            Associable.associate(squares.get(i), columns.get(i % 9));

            int boxRow = ((i / 9) / 3);
            int boxColumn = (i % 9) / 3;

            Associable.associate(squares.get(i), boxes.get((3 * boxRow) + boxColumn));
        }

    }

    public String json() throws BoardEntityException {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new BoardEntityException(e);
        }
    }

    @Override
    public String toString() {
        return "BoardState{" + "squares=" + squares + ", sequence=" + sequence + "}";
    }


    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
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

}
