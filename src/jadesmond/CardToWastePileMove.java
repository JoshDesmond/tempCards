package jadesmond;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

public class CardToWastePileMove extends Move {

    Pile wastePile;
    Column source;
    Card card;

    public CardToWastePileMove(Pile wastePile, Column source, Card card) {
        super();
        this.wastePile = wastePile;
        this.source = source;
        this.card = card;
    }

    @Override
    public boolean doMove(Solitaire game) {
        if (!valid(game)) {
            return false;
        }

        Card card = source.get();
        wastePile.add(card);
        return true;
    }

    @Override
    public boolean undo(Solitaire game) {
        Card c = wastePile.get();
        source.add(c);
        return true;
    }

    @Override
    public boolean valid(Solitaire game) {
        // TODO
        return true;
    }

}
