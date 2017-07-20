package com.violanotes.sudokusolver;

/**
 * Created by pc on 7/20/2017.
 */
public class Hypothetical {

    private HypotheticalState hypotheticalState;

    public enum HypotheticalState {
        AVAILABLE,
        ELIMINATED,
        FILLED;
    }
}
