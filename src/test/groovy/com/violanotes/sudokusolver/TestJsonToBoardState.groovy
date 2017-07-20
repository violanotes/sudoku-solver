package com.violanotes.sudokusolver

import com.fasterxml.jackson.databind.ObjectMapper
import com.violanotes.sudokusolver.board.BoardState
import com.violanotes.sudokusolver.exceptions.BoardEntityException
import org.apache.commons.io.IOUtils
import org.junit.Test
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource

import static org.junit.Assert.*

/**
 * Created by pc on 7/20/2017.
 */
class TestJsonToBoardState {


    String getJson(String path) throws Exception {
        Resource resource = new ClassPathResource(path)
        return IOUtils.toString(resource.getURL())
    }

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

        assertNotNull(boardState)
        assertNotNull(boardState.getSequence())
        assertEquals(new Integer(0), boardState.getSequence())

        assertNotNull(boardState.getSquares())
        assertEquals(1, boardState.getSquares().size())

        assertNotNull(boardState.getSquares().get(0))
        assertNotNull(boardState.getSquares().get(0).getHypotheticals())
        assertEquals(2, boardState.getSquares().get(0).getHypotheticals().size())
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

        assertNotNull(boardState.getSquares())
        assertEquals(81, boardState.getSquares().size())

        assertNotNull(boardState.getSquares().get(0))
        assertNotNull(boardState.getSquares().get(0).getHypotheticals())
        assertEquals(9, boardState.getSquares().get(0).getHypotheticals().size())
    }
}
