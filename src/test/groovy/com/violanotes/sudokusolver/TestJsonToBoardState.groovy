package com.violanotes.sudokusolver

import com.fasterxml.jackson.databind.ObjectMapper
import com.violanotes.sudokusolver.board.basic.BoardState
import com.violanotes.sudokusolver.exceptions.BoardEntityException
import org.junit.Test

import static com.violanotes.sudokusolver.TestUtils.getJson
import static org.junit.Assert.*

/**
 * Created by pc on 7/20/2017.
 */
class TestJsonToBoardState {

    @Test
    void getJsonFromResourceFileGroovy() throws Exception {
        String json = getJson("/boardState1.json")

        assertNotNull(json)
        assertTrue(json.startsWith("{"))
    }

    @Test
    void buildBoardStateFromJsonGroovy() throws Exception {
        String json = getJson("/boardState1.json")

        BoardState boardState = new ObjectMapper().readValue(json, BoardState.class)
        println "boardState: ${boardState}"

        assertNotNull(boardState)
        assertNotNull(boardState.sequence)
        assertEquals(new Integer(0), boardState.sequence)

        assertNotNull(boardState.squares)
        assertEquals(1, boardState.squares.size())

        assertNotNull(boardState.squares.get(0))
        assertNotNull(boardState.squares.get(0).hypotheticals)
        assertEquals(2, boardState.squares.get(0).hypotheticals.size())
        System.out.println(json)
        System.out.println(boardState)
    }

    @Test
    void buildBoardStateFromNumbers_WithTooFewNumbers() throws Exception {
        String json = getJson("/boardStateSimpleTooFew.json")

        try {
            BoardState boardState = BoardState.createBasic(json)
            fail("exception should have been caught, but was not.")
        } catch (BoardEntityException e) {
            e.printStackTrace()
            println("exception caught as intended")
        } catch (Exception e) {
            e.printStackTrace()
            fail("unintended exception thrown")
        }
    }

    @Test
    void buildBoardStateFromNumbers_WithEnoughNumbers() throws Exception {
        String json = getJson("/boardStateSimple.json")

        BoardState boardState = BoardState.createBasic(json)

        assertNotNull(boardState)

        assertNotNull(boardState.squares)
        assertEquals(81, boardState.squares.size())

        assertNotNull(boardState.squares[0])
        assertNotNull(boardState.squares[0].hypotheticals)
        assertEquals(9, boardState.squares[0].hypotheticals.size())

        println boardState
    }
}
