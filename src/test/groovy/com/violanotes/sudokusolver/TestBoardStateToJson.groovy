package com.violanotes.sudokusolver

import com.fasterxml.jackson.databind.ObjectMapper
import com.violanotes.sudokusolver.board.basic.BoardState
import org.junit.Test

import static TestUtils.getJson
import static org.junit.Assert.*

/**
 * Created by pc on 7/20/2017.
 */
class TestBoardStateToJson {

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
    void buildJsonFromBoardState() throws Exception {
        BoardState boardState = BoardState.createBasic(getJson("/boardStateSimple.json"))
        String json = boardState.json()

        assertTrue(json.startsWith("{"))

        println "boardState json: $json"
    }


}
