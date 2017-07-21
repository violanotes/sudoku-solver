package com.violanotes.sudokusolver

import com.violanotes.sudokusolver.board.basic.BoardState
import com.violanotes.sudokusolver.board.basic.Square
import com.violanotes.sudokusolver.exceptions.QueryException
import org.junit.Before
import org.junit.Test

import static TestUtils.getJson
import static org.junit.Assert.*

/**
 * Created by pc on 7/20/2017.
 */
class TestQueryBoardEntities {

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
    void testQueryForSingle_WithMultipleResults_ThrowsException() throws Exception {

        // try to queryForClass single for all squares.  It should throw an exception

        try {
            Square result = boardState.queryForSingle(Square) {Square square ->
                square.number == 9
            }
            println result
            fail "Exception should have been caught, but was not"
        } catch (QueryException e) {
            e.printStackTrace()
            println "Exception caught as intended."
        }
    }

    @Test
    void testQueryForSingle_WithOneResult_Success() throws Exception {

        Square result = boardState.queryForSingle(Square) {Square square ->
            square.index == 4
        }

        assertNotNull(result)
        println result
    }

    @Test
    void testQueryForMultiple_WithOneResults_ThrowsException() throws Exception {

        try {
            Square[] results = boardState.queryForMultiple(Square) {Square square ->
                square.index == 4
            }
            println results
            fail "Exception should have been caught, but was not"
        } catch (QueryException e) {
            e.printStackTrace()
            println "Exception caught as intended."
        }
    }

    @Test
    void testQueryForMultiple_WithMultipleResults_Success() throws Exception {

        Square[] results = boardState.queryForMultiple(Square) {Square square ->
            square.number == 8
        }
        println results

        assertNotNull(results)
        assertEquals(9, results.size())
    }

    @Test
    void testQueryForMultiple_ExpectCount_WithOneResults_ThrowsException() throws Exception {

        try {
            Square[] results = boardState.queryForMultiple(Square, 9) {Square square ->
                square.index == 4
            }
            println results
            fail "Exception should have been caught, but was not"
        } catch (QueryException e) {
            e.printStackTrace()
            println "Exception caught as intended."
        }
    }

    @Test
    void testQueryForMultiple_ExpectCount_ReceiveCount_Success() throws Exception {

        Square[] results = boardState.queryForMultiple(Square, 9) {Square square ->
            square.number == 8
        }
        println results

        assertNotNull(results)
        assertEquals(9, results.size())
    }

    @Test
    void testQueryForNoResults_WithOneResults_ThrowsException() throws Exception {

        try {
            Square[] results = boardState.queryForNone(Square) {Square square ->
                square.index == 4
            }
            println results
            fail "Exception should have been caught, but was not"
        } catch (QueryException e) {
            e.printStackTrace()
            println "Exception caught as intended."
        }
    }

    @Test
    void testQueryForNone_WithNoResults_Success() throws Exception {

        Square[] results = boardState.queryForNone(Square) {Square square ->
            square.number == 25
        }
        println results

        assertNull(results)
    }
}
