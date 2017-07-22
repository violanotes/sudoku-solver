package com.violanotes.sudokusolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.violanotes.sudokusolver.board.basic.BoardState;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by pc on 7/20/2017.
 */
public class TestBoardStateToJson {
    @Test
    public void getJsonFromResourceFileGroovy() throws Exception {
        String json = TestUtils.getJson("/boardState1.json");

        Assert.assertNotNull(json);
        Assert.assertTrue(json.startsWith("{"));
    }

    @Test
    public void buildBoardStateFromJsonGroovy() throws Exception {
        String json = TestUtils.getJson("/boardState1.json");

        final BoardState boardState = new ObjectMapper().readValue(json, BoardState.class);
        DefaultGroovyMethods.println(this, "boardState: " + String.valueOf(boardState));

        Assert.assertNotNull(boardState);
        Assert.assertNotNull(boardState.getSequence());
        Assert.assertEquals(new Integer(0), boardState.getSequence());

        Assert.assertNotNull(boardState.getSquares());
        Assert.assertEquals(1, boardState.getSquares().size());

        Assert.assertNotNull(boardState.getSquares().size());
        Assert.assertNotNull(boardState.getSquares().get(0).getHypotheticals());
        Assert.assertEquals(2, boardState.getSquares().get(0).getHypotheticals().size());
        System.out.println(boardState);
    }

    @Test
    public void buildJsonFromBoardState() throws Exception {
        BoardState boardState = BoardState.createBasic(TestUtils.getJson("/boardStateSimple.json"));
        String json = boardState.json();
        Assert.assertTrue(json.startsWith("{"));
        DefaultGroovyMethods.println(this, "boardState json: " + json);
    }

}
