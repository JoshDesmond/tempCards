package jadesmond;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.model.Stack;

public class CardToFoundation extends Move {

    Stack source;
    Pile target;
    Card card;

    @Override
    public boolean doMove(Solitaire game) {
        if (!valid(game)) {
            return false;
        }

        Card card = source.get();
        target.add(card);
        return true;
    }

    @Override
    public boolean undo(Solitaire game) {
        Card c = target.get();
        source.add(c);
        return true;
    }

    @Override
    public boolean valid(Solitaire game) {
        // TODO
        return true;
    }

}
