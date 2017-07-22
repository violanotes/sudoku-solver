package com.violanotes.sudokusolver;

import com.violanotes.sudokusolver.board.basic.BoardState;
import com.violanotes.sudokusolver.board.basic.Square;
import com.violanotes.sudokusolver.board.entity.BoardEntity;
import com.violanotes.sudokusolver.exceptions.QueryException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.violanotes.sudokusolver.TestUtils.getJson;
import static org.junit.Assert.*;

/**
 * Created by pc on 7/20/2017.
 */
public class TestQueryBoardEntities {
    private BoardState boardState;

    @Before
    public void setup() throws Exception {
        String json = getJson("/boardStateSimple.json");
        boardState = BoardState.createBasic(json);

        assertNotNull(boardState);

        assertNotNull(boardState.getSquares());
        assertEquals(81, boardState.getSquares().size());

        assertNotNull(boardState.getSquares().get(0));
        assertNotNull(boardState.getSquares().get(0).getHypotheticals());
        assertEquals(9, boardState.getSquares().get(0).getHypotheticals().size());

        System.out.println(boardState);
    }

    @Test
	public void testQueryForSingle_WithMultipleResults_ThrowsException() throws Exception {

        // try to queryForClass single for all getSquares().  It should throw an exception

        try {
            Square result = boardState.queryForSingle(Square.class, (Square square) ->
                square.getNumber() == 9
            );
            System.out.println(result);
            fail("Exception should have been caught, but was not");
        } catch (QueryException e) {
            e.printStackTrace();
            System.out.println("Exception caught as intended.");
        }
    }

    @Test
	public void testQueryForSingle_WithOneResult_Success() throws Exception {

        Square result = boardState.queryForSingle(Square.class, (Square square) ->
                square.getIndex() == 4
        );

        assertNotNull(result);
        System.out.println(result);
    }

    @Test
	public void testQueryForMultiple_WithOneResults_ThrowsException() throws Exception {

        try {
            List<Square> results = boardState.queryForMultiple(Square.class, (Square square) ->
                    square.getIndex() == 4
            );
            System.out.println(results);
            fail("Exception should have been caught, but was not");
        } catch (QueryException e) {
            e.printStackTrace();
            System.out.println("Exception caught as intended.");
        }
    }

    @Test
	public void testQueryForMultiple_WithMultipleResults_Success() throws Exception {

        List<Square> results = boardState.queryForMultiple(Square.class, (Square square) ->
            square.getNumber() == 8
        );
        System.out.println(results);

        assertNotNull(results);
        assertEquals(9, results.size());
    }

    @Test
	public void testQueryForMultiple_ExpectCount_WithOneResults_ThrowsException() throws Exception {

        try {
            List<Square> results = boardState.queryForMultiple(Square.class, 9, (Square square) ->
                square.getIndex() == 4
            );
            System.out.println(results);
            fail("Exception should have been caught, but was not");
        } catch (QueryException e) {
            e.printStackTrace();
            System.out.println("Exception caught as intended.");
        }
    }

    @Test
	public void testQueryForMultiple_ExpectCount_ReceiveCount_Success() throws Exception {

        List<Square> results = boardState.queryForMultiple(Square.class, 9, (Square square) ->
            square.getNumber() == 8
        );
        System.out.println(results);

        assertNotNull(results);
        assertEquals(9, results.size());
    }

    @Test
	public void testQueryForNoResults_WithOneResults_ThrowsException() throws Exception {

        try {
            Square results = boardState.queryForNone(Square.class, (Square square) ->
                square.getIndex() == 4
            );
            System.out.println(results);
            fail("Exception should have been caught, but was not");
        } catch (QueryException e) {
            e.printStackTrace();
            System.out.println("Exception caught as intended.");
        }
    }

    @Test
	public void testQueryForNone_WithNoResults_Success() throws Exception {

        Square results = boardState.queryForNone(Square.class, (Square square) ->
            square.getNumber() == 25
        );
        System.out.println(results);

        assertNull(results);
    }
}
