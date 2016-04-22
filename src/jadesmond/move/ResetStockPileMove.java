package jadesmond.move;

import ks.common.games.Solitaire;
import ks.common.model.Move;
import ks.common.model.MutableInteger;
import ks.common.model.Pile;

public class ResetStockPileMove extends Move {

    Pile stockPile;
    Pile wastePile;
    // FIXME unsure about this one?
    MutableInteger stocksRemaining;

    @Override
    public boolean doMove(Solitaire game) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean undo(Solitaire game) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean valid(Solitaire game) {
        // TODO Auto-generated method stub
        return false;
    }

}
