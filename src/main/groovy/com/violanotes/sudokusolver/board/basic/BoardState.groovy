package com.violanotes.sudokusolver.board.basic

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.violanotes.sudokusolver.board.supplemental.Box2
import com.violanotes.sudokusolver.board.supplemental.BoxColumn
import com.violanotes.sudokusolver.board.supplemental.BoxRow
import com.violanotes.sudokusolver.board.supplemental.Column
import com.violanotes.sudokusolver.board.supplemental.Row
import com.violanotes.sudokusolver.board.basic.Hypothetical.HypotheticalState
import com.violanotes.sudokusolver.board.basic.Square.SquareState
import com.violanotes.sudokusolver.board.entity.BoardEntity
import com.violanotes.sudokusolver.exceptions.AssociationException
import com.violanotes.sudokusolver.exceptions.BoardEntityException
import com.violanotes.sudokusolver.exceptions.BoardStateValidationException
import groovy.transform.InheritConstructors

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@InheritConstructors
class BoardState extends BoardEntity {
    Integer sequence

    List<Square> squares
    List<Row> rows
    List<Column> columns
    List<Box2> boxes
    List<BoxRow> boxRows
    List<BoxColumn> boxColumns

    @Override
    void initializeToEmpty() {
        squares = []
        rows = []
        columns = []
        boxes = []
        boxRows = []
        boxColumns = []
    }

    @Override
    void associate(BoardEntity entity) {
        switch(entity.class) {
            case Square: squares.add entity
                break
            case Row: rows.add entity
                break
            case Column: columns.add entity
                break
            case Box2: boxes.add entity
                break
            case BoxRow: boxRows.add entity
                break
            case BoxColumn: boxColumns.add entity
                break

            default:
                throw new AssociationException(entity, this)
        }
    }

    static BoardState create(String json) throws BoardEntityException {
        try {
            BoardState boardState = new ObjectMapper().readValue(json, BoardState)

            // now set up the board
            boardState.performBoardAssociations()

            return boardState
        } catch (IOException e) {
            throw new BoardEntityException(e)
        }
    }

    void validate() throws BoardStateValidationException {
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
            int filledInCount = 0  // max should be 1
            int availableCount = 0 // should be more than 0 if filledIn is 0
            int eliminatedCount = 0

            if (square.hypotheticals.size() != 9) {
                throw new BoardStateValidationException(
                        "Square $square.index should have 9 hypotheticals, but has ${square.hypotheticals.size()}", this)
            }

            for (Hypothetical hypothetical : square.hypotheticals) {
                switch (hypothetical.state) {
                    case HypotheticalState.FILLED:
                        filledInCount++
                        break
                    case HypotheticalState.AVAILABLE:
                        availableCount++
                        break
                    case HypotheticalState.ELIMINATED:
                        eliminatedCount++
                        break
                    default:
                        throw new BoardStateValidationException("Unacceptable HypotheticalState '$hypothetical.state'", this)
                }
            }

            if (filledInCount > 1) {
                throw new BoardStateValidationException(
                        "Square $square.index should have no more than 1 fille-in hypothetical, but has $filledInCount", this)
            }

            if (filledInCount == 1) {
                if (availableCount == 0) {
                    throw new BoardStateValidationException(
                            "Square $square.index should have at least 1 available hypothetical, but has $availableCount", this)
                }
            }
        }

        for (Row row : rows) {
            List<Integer> filledIn = new ArrayList<>()

            for (Square square : row.squares) {
                if (square.state == SquareState.FILLED) {
                    if (!filledIn.contains(square.number)) {
                        filledIn.add(square.number)
                    } else {
                        throw new BoardStateValidationException(
                                "Row $row.index should have only one square filled in with number $square.number, but has more than one", this)
                    }
                }
            }
        }

        for (Column column : columns) {
            List<Integer> filledIn = new ArrayList<>()

            for (Square square : column.squares) {
                if (square.state == SquareState.FILLED) {
                    if (!filledIn.contains(square.number)) {
                        filledIn.add(square.number)
                    } else {
                        throw new BoardStateValidationException(
                                "Column $column.index should have only one square filled in with number $square.number, but has more than one", this)
                    }
                }
            }
        }

        for (Box2 box : boxes) {
            List<Integer> filledIn = new ArrayList<>()

            for (Square square : box.squares) {
                if (square.state == SquareState.FILLED) {
                    if (!filledIn.contains(square.number)) {
                        filledIn.add(square.number)
                    } else {
                        throw new BoardStateValidationException(
                                "Box2 $box.index should have only one square filled in with number $square.number, but has more than one", this)
                    }
                }
            }
        }

    }

    static BoardState createBasic(String json) throws BoardEntityException {
        try {
            // turn the json into a list of numbers
            List<Integer> numbers = Arrays.asList(new ObjectMapper().readValue(json, Integer[]))

            // make sure there are exactly 81 numbers
            if (numbers.size() != 81) {
                throw new BoardEntityException("${numbers.size()} numbers provided.  Should be 81.")
            }

            // create 81 squares with the specified numbers
            // and default hypotheticals

            BoardState boardState = new BoardState(true)

            (0..80).each {
                // create the square and associate it to the board
                Square square = new Square(true)
                associate(square, boardState)
                square.index = it
                square.number = numbers[it]

                (1..9).each {
                    // create the hypothetical and associate it with the square
                    Hypothetical hypothetical = new Hypothetical(true)
                    associate(square, hypothetical)
                    hypothetical.number = it
                }
            }

            boardState.performBoardAssociations()

            return boardState

        } catch (IOException e) {
            throw new BoardEntityException(e)
        }
    }

    private void performBoardAssociations() throws BoardEntityException {

        // create 9 Rows
        (0..8).each {
            Row row = new Row(true)
            row.index = it
            associate(row, this)
        }

        // create 9 Columns
        (0..8).each {
            Column column = new Column(true)
            column.index = it
            associate(column, this)
        }

        // create 9 Boxes
        (0..8).each {
            Box2 box = new Box2(true)
            box.index = it
            associate(box, this)
        }

        // create 3 BoxRows
        (0..2).each {
            BoxRow boxRow = new BoxRow(true)
            boxRow.index = it
            associate(boxRow, this)
        }

        // create 3 BoxColumns
        (0..2).each {
            BoxColumn boxColumn = new BoxColumn(true)
            boxColumn.index = it
            associate(boxColumn, this)
        }

        // take each box and associate it with the appropriate
        // boxRow
        // boxColumn
        (0..8).each {
            associate(boxes[it], boxRows[it.intdiv(3)])
            associate(boxes[it], boxColumns[it % 3])

            associate(rows[it], boxRows[it.intdiv(3)])
            associate(columns[it], boxColumns[it.intdiv(3)])
        }


        // take each square and associate it in the appropriate:

        // row
        // column
        // box
        (0..80).each {
            associate(squares[it], rows[it.intdiv(9)])
            associate(squares[it], columns[it % 9])

            int boxRow = it.intdiv(9).intdiv(3)
            int boxColumn = (it % 9).intdiv(3)

            associate(squares[it], boxes[(3 * boxRow) + boxColumn])
        }
    }

    String json() {
        return new ObjectMapper().writeValueAsString(this)
    }

    @Override
    String toString() {
        return "BoardState{" +
                "squares=" + squares +
                ", sequence=" + sequence +
                '}'
    }
}
