package jadesmond.move;

import jadesmond.Alahambra;
import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;

public class ResetStockPileMove extends Move {

	Pile stockPile;
	Pile wastePile;

	public ResetStockPileMove(Pile stockPile, Pile wastePile) {
		super();
		if (stockPile == null || wastePile == null) {
			throw new IllegalArgumentException(
					"Illegal null input to constructor");
		} else if (stockPile.equals(wastePile)) {
			throw new IllegalArgumentException(
					"Can't have duplicate piles in a reset stock move");
		}
		this.stockPile = stockPile;
		this.wastePile = wastePile;
	}

	@Override
	// Game must be an Alahambra
	public boolean doMove(Solitaire game) {
		if (!valid(game)) {
			return false;
		}

		while (!wastePile.empty()) {
			Card c = wastePile.get();
			c.setFaceUp(false);
			stockPile.add(c);
		}

		((Alahambra) game).updateStock(-1);

		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		while (!stockPile.empty()) {
			Card c = stockPile.get();
			c.setFaceUp(true);
			wastePile.add(c);
		}

		((Alahambra) game).updateStock(1);

		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		if (!stockPile.empty()) {
			return false;
		}
		return (((Alahambra) game).getStocksLeft().getValue() > 0);

	}
}
