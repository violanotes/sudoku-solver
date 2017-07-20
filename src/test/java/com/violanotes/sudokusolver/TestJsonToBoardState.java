package com.violanotes.sudokusolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.violanotes.sudokusolver.board.BoardState;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by pc on 7/20/2017.
 */
public class TestJsonToBoardState {


    public String getJson(String path) throws Exception {
        Resource resource = new ClassPathResource(path);
        return IOUtils.toString(resource.getURL());
    }

    @Test
    public void getJsonFromResourceFile() throws Exception {
        String json = getJson("/boardState1.json");

        assertNotNull(json);
        assertTrue(json.startsWith("{"));
    }

    @Test
    public void buildBoardStateFromJson() throws Exception {
        String json = getJson("/boardState1.json");

        BoardState boardState = new ObjectMapper().readValue(json, BoardState.class);

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
}
