package com.violanotes.sudokusolver;

import com.violanotes.sudokusolver.board.basic.BoardState;
import com.violanotes.sudokusolver.exceptions.BoardStateValidationException;
import groovy.ui.SystemOutputInterceptor;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by pc on 7/21/2017.
 */
public class TestBoardStateValidation {
    @Test
    public void validateBoard_Invalid() throws Exception {
        BoardState boardState = BoardState.createBasic(TestUtils.getJson("/boardStateSimple.json"));

        try {
            boardState.validate();
            //TODO finish proper validation!
            Assert.fail("Exception should have been caught, but was not");
        } catch (BoardStateValidationException e) {
            e.printStackTrace();
            System.out.println("Exception caught as intended.");
        }


        System.out.println("boardState: " + boardState.json());
    }

}
