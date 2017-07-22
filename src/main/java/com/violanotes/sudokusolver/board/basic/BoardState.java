package com.violanotes.sudokusolver.board.basic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.violanotes.sudokusolver.board.entity.Associable;
import com.violanotes.sudokusolver.board.geometric.Box;
import com.violanotes.sudokusolver.board.geometric.Row;
import com.violanotes.sudokusolver.board.entity.BoardEntity;
import com.violanotes.sudokusolver.exceptions.AssociationException;
import com.violanotes.sudokusolver.exceptions.BoardEntityException;
import com.violanotes.sudokusolver.exceptions.BoardEntityValidationException;
import com.violanotes.sudokusolver.board.geometric.*;
import com.violanotes.sudokusolver.move.Move;

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
        super();
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
    public void doAssociate(Associable<BoardEntity> entity) throws AssociationException {
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

    @Override
    public void doValidate() throws BoardEntityValidationException {

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
            square.validate();
        }

        for (Row row : rows) {
            row.validate();
        }


        for (Column column : columns) {
            column.validate();
        }

        for (Box box : boxes) {
            box.validate();
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
                Square square = new Square();
                square.associate(boardState);
                square.setIndex(i);

                for (int j = 0; j < 9; j++) {
                    // create the hypothetical and associate it with the square
                    Hypothetical hypothetical = new Hypothetical();
                    square.associate(hypothetical);
                    hypothetical.setNumber(j);
                }

                //TODO use Moves to set the numbers
                Move move = new Move();
                Hypothetical hypothetical = square.queryForSingle(Hypothetical.class, (Hypothetical h, Object[] args) ->
                                h.getNumber() == numbers.get((int)args[0])
                        , i);

                move.setHypothetical(hypothetical);
                move.setNewHypotheticalState(Hypothetical.HypotheticalState.FILLED);
                hypothetical.changeState(Hypothetical.HypotheticalState.FILLED, move);

//                square.setNumber(numbers.get(i));
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
            row.associate(this);
        }


        // create 9 Columns
        for (int i = 0; i < 9; i++) {
            Column column = new Column(true);
            column.setIndex(i);
            column.associate(this);
        }

        // create 9 Boxes
        for (int i = 0; i < 9; i++) {
            Box box = new Box(true);
            box.setIndex(i);
            box.associate(this);
        }

        // create 3 BoxRows
        for (int i = 0; i < 3; i++) {
            BoxRow boxRow = new BoxRow(true);
            boxRow.setIndex(i);
            boxRow.associate(this);
        }

        // create 3 BoxColumns
        for (int i = 0; i < 3; i++) {
            BoxColumn boxColumn = new BoxColumn(true);
            boxColumn.setIndex(i);
            boxColumn.associate(this);
        }

        // take each box and associate it with the appropriate
        // boxRow
        // boxColumn
        for (int i = 0; i < 9; i++) {
            boxes.get(i).associate(boxRows.get(i / 3));
            boxes.get(i).associate(boxColumns.get(i % 3));

            rows.get(i).associate(boxRows.get(i / 3));
            columns.get(i).associate(boxColumns.get(i / 3));
        }



        // take each square and associate it in the appropriate:

        // row
        // column
        // box
        for (int i = 0; i < 81; i++) {
            squares.get(i).associate(rows.get(i / 9));
            squares.get(i).associate(columns.get(i % 9));

            int boxRow = ((i / 9) / 3);
            int boxColumn = (i % 9) / 3;

            squares.get(i).associate(boxes.get((3 * boxRow) + boxColumn));
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
    public String getId() {
        return "BoardState";
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
