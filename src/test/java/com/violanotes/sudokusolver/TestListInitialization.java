package com.violanotes.sudokusolver;

import com.violanotes.sudokusolver.board.basic.BoardState;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by pc on 7/20/2017.
 */
public class TestListInitialization {
    @Test
    public void initializeListToEmpty() throws Exception {
        BoardState boardState = new BoardState(false);

        Assert.assertNull(boardState.getSquares());

        boardState.initializeToEmpty();

        Assert.assertNotNull(boardState.getSquares());
    }

}
