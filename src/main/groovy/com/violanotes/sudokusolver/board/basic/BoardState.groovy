package com.violanotes.sudokusolver.board.basic

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.violanotes.sudokusolver.board.associative.Box
import com.violanotes.sudokusolver.board.associative.BoxColumn
import com.violanotes.sudokusolver.board.associative.BoxRow
import com.violanotes.sudokusolver.board.associative.Column
import com.violanotes.sudokusolver.board.associative.Row
import com.violanotes.sudokusolver.board.entity.BoardEntity
import com.violanotes.sudokusolver.exceptions.AssociationException
import com.violanotes.sudokusolver.exceptions.BoardEntityException
import com.violanotes.sudokusolver.exceptions.QueryException
import groovy.transform.InheritConstructors

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@InheritConstructors
class BoardState extends BoardEntity {
    List<Square> squares
    Integer sequence

    List<Row> rows
    List<Column> columns
    List<Box> boxes
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

    static BoardState create(String json) throws BoardEntityException {
        try {
            BoardState boardState = new ObjectMapper().readValue(json, BoardState)

            // now set up the board
            performBoardAssociations()

            return boardState
        } catch (IOException e) {
            throw new BoardEntityException(e)
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
                square.associate(boardState)
                square.index = it
                square.number = numbers[it]

                (1..9).each {
                    // create the hypothetical and associate it with the square
                    Hypothetical hypothetical = new Hypothetical(true)
                    hypothetical.associate(square)
                    hypothetical.number = it
                }
            }

            return boardState

        } catch (IOException e) {
            throw new BoardEntityException(e)
        }
    }

    private static void performBoardAssociations() throws BoardEntityException {

        // create 9 Rows
        (0..8).each {
            Row row = new Row()
        }

        // create 9 Columns

        // create 9 Boxes

        // create 3 BoxRows

        // create 3 BoxColumns

        // take each box and associate it with the appropriate
        // boxRow
        // boxColumn

        // take each square and associate it in the appropriate:

        // row
        // column
        // box
    }

    @Override
    void associate(BoardEntity entity) {
        switch(entity.class) {
            case (Square) : getSequence()
                break
            default: throw new AssociationException(entity, this)
        }
    }

    @Override
    String toString() {
        return "BoardState{" +
                "squares=" + squares +
                ", sequence=" + sequence +
                '}'
    }
}
