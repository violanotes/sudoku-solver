package com.violanotes.sudokusolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.violanotes.sudokusolver.board.basic.BoardState;
import com.violanotes.sudokusolver.exceptions.BoardEntityException;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.junit.Assert;
import org.junit.Test;

import static com.violanotes.sudokusolver.TestUtils.getJson;
import static org.assertj.core.api.Fail.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by pc on 7/20/2017.
 */
public class TestJsonToBoardState {
    @Test
	public void getJsonFromResourceFileGroovy() throws Exception {
        String json = getJson("/boardState1.json");

        assertNotNull(json);
        assertTrue(json.startsWith("{"));
    }

    @Test
	public void buildBoardStateFromJsonGroovy() throws Exception {
        String json = getJson("/boardState1.json");

        BoardState boardState = new ObjectMapper().readValue(json, BoardState.class);
        System.out.println("boardState: ${boardState}");

        assertNotNull(boardState);
        assertNotNull(boardState.getSequence());
        assertEquals(new Integer(0), boardState.getSequence());

        assertNotNull(boardState.getSquares());
        assertEquals(1, boardState.getSquares().size());

        assertNotNull(boardState.getSquares().get(0));
        assertNotNull(boardState.getSquares().get(0).getHypotheticals());
        assertEquals(2, boardState.getSquares().get(0).getHypotheticals().size());
        System.out.println(json);
        System.out.println(boardState);
    }

    @Test
	public void buildBoardStateFromNumbers_WithTooFewNumbers() throws Exception {
        String json = getJson("/boardStateSimpleTooFew.json");

        try {
            BoardState boardState = BoardState.createBasic(json);
            fail("exception should have been caught, but was not.");
        } catch (BoardEntityException e) {
            e.printStackTrace();
            System.out.println("exception caught as intended");
        } catch (Exception e) {
            e.printStackTrace();
            fail("unintended exception thrown");
        }
    }

    @Test
	public void buildBoardStateFromNumbers_WithEnoughNumbers() throws Exception {
        String json = getJson("/boardStateSimple.json");

        BoardState boardState = BoardState.createBasic(json);

        assertNotNull(boardState);

        assertNotNull(boardState.getSquares());
        assertEquals(81, boardState.getSquares().size());

        assertNotNull(boardState.getSquares().get(0));
        assertNotNull(boardState.getSquares().get(0).getHypotheticals());
        assertEquals(9, boardState.getSquares().get(0).getHypotheticals().size());

        System.out.println(boardState);
    }
}
