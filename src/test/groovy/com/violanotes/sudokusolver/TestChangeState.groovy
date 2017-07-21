package com.violanotes.sudokusolver

import com.violanotes.sudokusolver.board.basic.BoardState
import com.violanotes.sudokusolver.board.basic.Hypothetical
import com.violanotes.sudokusolver.board.basic.Hypothetical.HypotheticalState
import com.violanotes.sudokusolver.board.basic.Square
import com.violanotes.sudokusolver.exceptions.BoardStateValidationException
import org.junit.Test

import static TestUtils.getJson
import static org.junit.Assert.assertEquals
import static org.junit.Assert.fail

/**
 * Created by pc on 7/21/2017.
 */
class TestChangeState {

    @Test
    void changeState_FromAvailable_ToAvailable_ThrowsException() throws Exception {
        BoardState boardState = BoardState.createBasic(getJson("/boardStateSimple.json"))

        try {
            boardState.squares[0].hypotheticals[0].changeState(Hypothetical.HypotheticalState.AVAILABLE)
            fail "Exception should have been caught, but was not"
        } catch (BoardStateValidationException e) {
            e.printStackTrace()
            println "Exception caught as intended."
        }

        println "boardState: ${boardState.json()}"
    }

    @Test
    void changeState_FromAvailable_ToEliminated_Success() throws Exception {
        BoardState boardState = BoardState.createBasic(getJson("/boardStateSimple.json"))

        boardState.squares[0].hypotheticals[0].changeState(HypotheticalState.ELIMINATED)

        assertEquals(HypotheticalState.ELIMINATED, boardState.squares[0].hypotheticals[0].state)
        assertEquals(Square.SquareState.AVAILABLE, boardState.squares[0].state)

        println "boardState: ${boardState.json()}"
    }
}
