package com.violanotes.sudokusolver.board

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.violanotes.sudokusolver.exceptions.AssociationException
import com.violanotes.sudokusolver.exceptions.BoardEntityException

/**
 * Created by pc on 7/20/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class BoardState extends BoardEntity {
    private List<Square> squares
    private Integer sequence

    private List<Row> rows
    private List<Column> columns
    private List<Box> boxes
    private List<BoxRow> boxRows
    private List<BoxColumn> boxColumns

    BoardState() {}

    BoardState(Closure<Void> closure) {
        super(closure)
    }


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
            BoardState boardState = new ObjectMapper().readValue(json, BoardState.class)

            // now set up the board
            setupBoardState()

            return boardState
        } catch (IOException e) {
            throw new BoardEntityException(e)
        }
    }

    static BoardState createBasic(String json) throws BoardEntityException {
        try {
            List<Integer> numbers = Arrays.asList(new ObjectMapper().readValue(json, Integer[].class))


            if (numbers.size() != 81) {
                throw new BoardEntityException("${numbers.size()} numbers provided.  Should be 81.")
            }

            // create 81 squares with the specified numbers
            // and default hypotheticals

            BoardState boardState = newEmpty(BoardState)

            (1..81).each {
                // create the square and associate it to the board
                Square square = newEmpty(Square)
                square.associate(boardState)
                square.number = numbers[it - 1]

                (1..9).each {
                    // create the hypothetical and associate it with the square
                    Hypothetical hypothetical = newEmpty(Hypothetical)
                    hypothetical.associate(square)
                    hypothetical.number = it
                }
            }

            return boardState


        } catch (IOException e) {
            throw new BoardEntityException(e)
        }
    }

    private static void setupBoardState() throws BoardEntityException {



        // create 9 Rows


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
        switch(entity) {
            case (entity instanceof Square) : getSequence()
                break
            default: throw new AssociationException(entity, this)
        }
    }

    List<Square> getSquares() {
        return squares
    }

    void setSquares(List<Square> squares) {
        this.squares = squares
    }

    Integer getSequence() {
        return sequence
    }

    void setSequence(Integer sequence) {
        this.sequence = sequence
    }

    List<Row> getRows() {
        return rows
    }

    void setRows(List<Row> rows) {
        this.rows = rows
    }

    List<Column> getColumns() {
        return columns
    }

    void setColumns(List<Column> columns) {
        this.columns = columns
    }

    List<Box> getBoxes() {
        return boxes
    }

    void setBoxes(List<Box> boxes) {
        this.boxes = boxes
    }

    List<BoxRow> getBoxRows() {
        return boxRows
    }

    void setBoxRows(List<BoxRow> boxRows) {
        this.boxRows = boxRows
    }

    List<BoxColumn> getBoxColumns() {
        return boxColumns
    }

    void setBoxColumns(List<BoxColumn> boxColumns) {
        this.boxColumns = boxColumns
    }

    @Override
    String toString() {
        return "BoardState{" +
                "squares=" + squares +
                ", sequence=" + sequence +
                '}'
    }
}
