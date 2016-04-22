package jadesmond.move;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Pile;
import ks.common.model.Stack;

public class CardToAceFoundationMove extends CardToFoundationMove {

    public CardToAceFoundationMove(Stack source, Pile target, Card card) {
        super(source, target, card);
    }

    @Override
    public boolean valid(Solitaire game) {
        if (!card.sameSuit(target.peek())) {
            return false;
        }

        return (card.compareTo(target.peek()) == 1);
    }

}
