package com.violanotes.sudokusolver;

import com.violanotes.sudokusolver.board.basic.BoardState;
import com.violanotes.sudokusolver.board.basic.Hypothetical;
import com.violanotes.sudokusolver.board.basic.Square;
import com.violanotes.sudokusolver.exceptions.BoardStateValidationException;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.junit.Assert;
import org.junit.Test;

import static com.violanotes.sudokusolver.TestUtils.getJson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by pc on 7/21/2017.
 */
public class TestChangeState {
    @Test
    public void changeState_FromAvailable_ToAvailable_ThrowsException() throws Exception {
        BoardState boardState = BoardState.createBasic(getJson("/boardStateSimple.json"));

        try {
            boardState.getSquares().get(0).getHypotheticals().get(0).changeState(Hypothetical.HypotheticalState.AVAILABLE);
            fail("Exception should have been caught, but was not");
        } catch (BoardStateValidationException e) {
            e.printStackTrace();
            System.out.println("Exception caught as intended.");
        }

        System.out.println("boardState: ${boardState.json()}");
    }

    @Test
	public void changeState_FromAvailable_ToEliminated_Success() throws Exception {
        BoardState boardState = BoardState.createBasic(getJson("/boardStateSimple.json"));

        boardState.getSquares().get(0).getHypotheticals().get(0).changeState(Hypothetical.HypotheticalState.ELIMINATED);

        assertEquals(Hypothetical.HypotheticalState.ELIMINATED, boardState.getSquares().get(0).getHypotheticals().get(0).getState());
        assertEquals(Square.SquareState.AVAILABLE, boardState.getSquares().get(0).getState());

        System.out.println("boardState: ${boardState.json()}");
    }
}
