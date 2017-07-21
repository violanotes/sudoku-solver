package com.violanotes.sudokusolver

import com.violanotes.sudokusolver.board.basic.BoardState
import com.violanotes.sudokusolver.exceptions.BoardStateValidationException
import org.junit.Test

import static TestUtils.getJson
import static org.junit.Assert.fail

/**
 * Created by pc on 7/21/2017.
 */
class TestBoardStateValidation {

    @Test
    void validateBoard_Invalid() throws Exception {
        BoardState boardState = BoardState.createBasic(getJson("/boardStateSimple.json"))

        try {
            boardState.validate()
            fail "Exception should have been caught, but was not"
        } catch (BoardStateValidationException e) {
            e.printStackTrace()
            println "Exception caught as intended."
        }

        println "boardState: ${boardState.json()}"
    }
}
