package jadesmond.move;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;

/**
 * Move card from top of stock pile to the waste pile
 * 
 * @author Josh Desmond
 */
public class FlipStockPileMove extends Move {

    Pile stockPile;
    Pile wastePile;

    /**
     * 
     * @param stockPile
     *            Stock Pile for where the card is being flipped from.
     * @param wastePile
     *            Waste Pile where the card is being placed to.
     */
    public FlipStockPileMove(Pile stockPile, Pile wastePile) {
        if (stockPile == null || wastePile == null) {
            throw new IllegalArgumentException("Null Pile was given");
        }

        this.stockPile = stockPile;
        this.wastePile = wastePile;
    }

    @Override
    public boolean doMove(Solitaire game) {
        if (!valid(game)) {
            return false;
        }

        Card card = stockPile.get();
        wastePile.add(card);
        return true;
    }

    @Override
    public boolean undo(Solitaire game) {
        Card c = wastePile.get();
        stockPile.add(c);
        return true;
    }

    @Override
    public boolean valid(Solitaire game) {
        if (stockPile.empty()) {
            return false;
        }

        return true;
    }

}
