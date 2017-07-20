package com.violanotes.sudokusolver

import com.violanotes.sudokusolver.board.BoardState
import org.junit.Test

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertNull

/**
 * Created by pc on 7/20/2017.
 */
class TestListInitialization {

    @Test
    void initializeListToEmpty() {
        BoardState boardState = new BoardState()

        assertNull(boardState.squares)

        boardState.initializeToEmpty()

        assertNotNull(boardState.squares)
    }
}
