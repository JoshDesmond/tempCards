package jadesmond.move;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;

public class ResetStockPileMove extends Move {

    Pile stockPile;
    Pile wastePile;

    @Override
    public boolean doMove(Solitaire game) {
        if (!valid(game)) {
            return false;
        }

        while (!wastePile.empty()) {
            Card c = wastePile.get();
            c.setFaceUp(false);
            stockPile.add(c);
        }
        return true;

    }

    @Override
    public boolean undo(Solitaire game) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean valid(Solitaire game) {
        // If stockPile is empty
        // & #stocks > 0
        // TODO
        return false;
    }

}
