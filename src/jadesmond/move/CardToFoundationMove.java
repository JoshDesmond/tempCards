package jadesmond.move;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.model.Stack;

public abstract class CardToFoundationMove extends Move {

	Stack source;
	Pile target;
	Card card;

	public CardToFoundationMove(Stack source, Pile target, Card card) {
		super();
		if (source == null || target == null || card == null) {
			throw new IllegalArgumentException("Can't pass null parameters");
		}
		this.source = source;
		this.target = target;
		this.card = card;
	}

	@Override
	public boolean doMove(Solitaire game) {
		if (!valid(game)) {
			return false;
		}

		target.add(card);
		game.updateScore(1);
		game.updateNumberCardsLeft(-1);

		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		source.add(target.get());
		game.updateScore(-1);
		game.updateNumberCardsLeft(-1);
		return true;
	}

	@Override
	public abstract boolean valid(Solitaire game);

}
