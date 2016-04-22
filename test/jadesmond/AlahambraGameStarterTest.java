package jadesmond;

import static org.junit.Assert.*;
import jadesmond.move.FlipStockPileMove;

import java.util.HashSet;

import ks.common.model.Card;
import ks.common.model.Column;

import org.junit.Before;
import org.junit.Test;

public class AlahambraGameStarterTest {

	Alahambra game;

	@Before
	public void setUp() {
		this.game = new MoveTester().getTestGame();
	}

	@Test
	public void testFoundationPiles() {

		Card[] cards = new Card[4];
		for (int i = 0; i < 4; i++) {
			cards[i] = game.aceFoundations[i].peek();
		}

		assertTrue(validateCards(cards, 1));

		for (int i = 0; i < 4; i++) {
			cards[i] = game.kingFoundations[i].peek();
		}

		assertTrue(validateCards(cards, 13));
	}

	/**
	 * Validates the four foundation cards, checking to see that they're all the
	 * given value and no suits are matching.
	 */
	private boolean validateCards(Card[] cards, int value) {
		HashSet<Integer> suits = new HashSet<Integer>();
		for (Card c : cards) {
			if (c.getRank() != value) {
				return false;
			}
			suits.add(c.getSuit());
		}
		return (suits.size() == 4);
	}

	@Test
	public void testColumnCounts() {
		for (Column c : game.columns) {
			assertTrue(c.count() == 4);
		}
	}

	@Test
	public void testStockFlippedOver() {
		assertTrue(!game.stockPile.peek().isFaceUp());
		FlipStockPileMove move = new FlipStockPileMove(game.stockPile,
				game.wastePile);

		assertTrue(move.doMove(game));

		assertTrue(!game.stockPile.peek().isFaceUp());

	}
}
