package com.violanotes.sudokusolver;

import com.violanotes.sudokusolver.board.basic.BoardState;
import com.violanotes.sudokusolver.board.basic.Hypothetical;
import com.violanotes.sudokusolver.board.basic.Square;
import com.violanotes.sudokusolver.exceptions.BoardEntityStateChangeException;
import com.violanotes.sudokusolver.exceptions.BoardEntityValidationException;
import com.violanotes.sudokusolver.move.Move;
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

            Move move = new Move();
            move.setHypothetical(boardState.getSquares().get(0).getHypotheticals().get(0));
            move.setNewHypotheticalState(Hypothetical.HypotheticalState.AVAILABLE);

            boardState.getSquares().get(0).getHypotheticals().get(0).changeState(Hypothetical.HypotheticalState.AVAILABLE, move);
            fail("Exception should have been caught, but was not");
        } catch (BoardEntityStateChangeException e) {
            e.printStackTrace();
            System.out.println("Exception caught as intended.");
        }

        System.out.println("boardState: ${boardState.json()}");
    }

    @Test
	public void changeState_FromAvailable_ToEliminated_Success() throws Exception {
        BoardState boardState = BoardState.createBasic(getJson("/boardStateSimple.json"));

        Move move = new Move();
        move.setHypothetical(boardState.getSquares().get(0).getHypotheticals().get(1));
        move.setNewHypotheticalState(Hypothetical.HypotheticalState.ELIMINATED);

        boardState.getSquares().get(0).getHypotheticals().get(1).changeState(Hypothetical.HypotheticalState.ELIMINATED, move);

        assertEquals(Hypothetical.HypotheticalState.ELIMINATED, boardState.getSquares().get(0).getHypotheticals().get(1).getState());
        assertEquals(Square.SquareState.AVAILABLE, boardState.getSquares().get(1).getState());

        System.out.println("boardState: ${boardState.json()}");
    }
}
