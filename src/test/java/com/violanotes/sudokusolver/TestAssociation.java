package com.violanotes.sudokusolver;

import com.violanotes.sudokusolver.board.basic.BoardState;
import com.violanotes.sudokusolver.board.basic.Square;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by pc on 7/20/2017.
 */
public class TestAssociation {

    private BoardState boardState;

    @Before
    public void setup() throws Exception {
        String json = TestUtils.getJson("/boardStateSimple.json");
        boardState = ((BoardState) (DefaultGroovyMethods.invokeMethod(BoardState.class, "createBasic", new Object[]{json})));

        Assert.assertNotNull(boardState);

        Assert.assertNotNull(boardState.getSquares());
        assertEquals(81, boardState.getSquares().size());

        Assert.assertNotNull(DefaultGroovyMethods.getAt(boardState.getSquares(), 0));
        Assert.assertNotNull(DefaultGroovyMethods.getAt(boardState.getSquares(), 0).getHypotheticals());
        assertEquals(9, DefaultGroovyMethods.getAt(boardState.getSquares(), 0).getHypotheticals().size());

        DefaultGroovyMethods.println(this, boardState);
    }

    @Test
    public void squaresToBoardState() {
        for (Square square : boardState.getSquares()) {
            assertEquals(square.getBoardState(), boardState);
        }

        Assert.assertNotNull(boardState.getSquares());
        assertEquals(81, boardState.getSquares().size());
    }

    @Test
    public void squaresToRow() {
        Integer row = 0;
        for (int i = 0; i < 81; i += 9) {
            for (int j = 0; j < 9; j++) {
                assertEquals("row: " + row + " it: " + i + " i: " + j,
                        row, boardState.getSquares().get(i + j).getRow().getIndex());
            }
            row++;
        }
    }

    @Test
	public void squaresToBoxes() {
        Integer boxIndex = 0;
        for (int i = 0; i < 81; i += 27) {
            for (int j = 0; j < 9; j += 3) {
                assertEquals("square: ${it + i} i: $i",
                        boxIndex, boardState.getSquares().get(i + j).getBox().getIndex());
                boxIndex ++;
            }
        }
    }

    @Test
	public void squaresToColumns() {
        for (int i = 0; i < 81; i += 9) {
            for (int j = 0; j < 9; j++) {
                assertEquals("square: ${it + i} i: $i",
                        new Integer(j), boardState.getSquares().get(i + j).getColumn().getIndex());
            }
        }
    }

    @Test
	public void rowsToBoxRows() {
        Integer boxRowIndex = 0;
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 3; j++) {
                assertEquals("row: ${it + i} i: $i",
                        boxRowIndex, boardState.getRows().get(i + j).getBoxRow().getIndex());
            }
            boxRowIndex++;
        }
    }

    @Test
	public void columnsToBoxColumns() {
        Integer boxColumnIndex = 0;
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 3; j++) {
                assertEquals("column: ${it + i} i: $i",
                        boxColumnIndex, boardState.getColumns().get(i + j).getBoxColumn().getIndex());
            }
            boxColumnIndex++;
        }
    }
}
