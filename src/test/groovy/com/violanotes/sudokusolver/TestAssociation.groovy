package com.violanotes.sudokusolver

import com.violanotes.sudokusolver.board.basic.BoardState
import com.violanotes.sudokusolver.board.basic.Square
import com.violanotes.sudokusolver.exceptions.QueryException
import org.junit.Before
import org.junit.Test

import static com.violanotes.sudokusolver.TestUtils.getJson
import static org.junit.Assert.*

/**
 * Created by pc on 7/20/2017.
 */
class TestAssociation {

    private BoardState boardState

    @Before
    void setup() {
        String json = getJson("/boardStateSimple.json")
        boardState = BoardState.createBasic(json)

        assertNotNull(boardState)

        assertNotNull(boardState.squares)
        assertEquals(81, boardState.squares.size())

        assertNotNull(boardState.squares[0])
        assertNotNull(boardState.squares[0].hypotheticals)
        assertEquals(9, boardState.squares[0].hypotheticals.size())

        println boardState
    }

    @Test
    void squaresToBoardState() {
        for (Square square : boardState.squares) {
            assertEquals(square.boardState, boardState)
        }

        assertNotNull(boardState.squares)
        assertEquals(81, boardState.squares.size())
    }

    @Test
    void squaresToRow() {
        int row = 0
        0.step 80, 9, {
            (0..8).each {i ->
                assertEquals("row: $row it: $it i: $i", row, boardState.squares[it + i].row.index)
            }
            row++
        }
    }

    @Test
    void squaresToBoxes() {
        int boxIndex = 0
        0.step 80, 27, {
            0.step 8, 3, {i ->
                assertEquals("square: ${it + i} i: $i", boxIndex, boardState.squares[it + i].box.index)
                boxIndex ++
            }
        }
    }

    @Test
    void squaresToColumns() {
        0.step 80, 9, {
            (0..8).each {i ->
                assertEquals("square: ${it + i} i: $i", i, boardState.squares[it + i].column.index)
            }
        }
    }

    @Test
    void rowsToBoxRows() {
        int boxRowIndex = 0
        0.step 9, 3, {
            (0..2).each {i ->
                assertEquals("row: ${it + i} i: $i", boxRowIndex, boardState.rows[it + i].boxRow.index)
            }
            boxRowIndex ++
        }
    }

    @Test
    void columnsToBoxColumns() {
        0.step 9, 3, {
            (0..2).each {i ->
                assertEquals("column: ${it + i} i: $i", i, boardState.squares[it + i].column.index)
            }
        }
    }
}
