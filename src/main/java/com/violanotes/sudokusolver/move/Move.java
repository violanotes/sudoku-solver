package com.violanotes.sudokusolver.move;

import com.violanotes.sudokusolver.board.basic.Hypothetical;
import com.violanotes.sudokusolver.board.basic.Hypothetical.HypotheticalState;

import java.util.List;

/**
 * Created by pc on 7/22/2017.
 */
public class Move {
    private Hypothetical hypothetical;
    private HypotheticalState newHypotheticalState;
    private List<Strategy> strategies;

    public Hypothetical getHypothetical() {
        return hypothetical;
    }

    public void setHypothetical(Hypothetical hypothetical) {
        this.hypothetical = hypothetical;
    }

    public HypotheticalState getNewHypotheticalState() {
        return newHypotheticalState;
    }

    public void setNewHypotheticalState(HypotheticalState newHypotheticalState) {
        this.newHypotheticalState = newHypotheticalState;
    }

    public List<Strategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<Strategy> strategies) {
        this.strategies = strategies;
    }
}
