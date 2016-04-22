package jadesmond;

import static org.junit.Assert.*;
import jadesmond.move.FlipStockPileMove;
import jadesmond.move.ResetStockPileMove;
import ks.common.model.Pile;

import org.junit.Test;

public class ResetStockPileMoveTest {

	MoveTester test = new MoveTester();

	@Test
	public void testNormalUse() {
		Alahambra game = test.getTestGame();

		flipThroughStock(game);

		assertTrue(game.stockPile.empty());
		ResetStockPileMove move = new ResetStockPileMove(game.stockPile,
				game.wastePile);

		checkState(game, 2, true);
		assertTrue(move.valid(game));
		assertTrue(move.doMove(game));
		checkState(game, 1, false);

		// TODO check the order of the cards.
	}

	@Test
	public void testUndo() {
		Alahambra game = test.getTestGame();
		flipThroughStock(game);

		ResetStockPileMove move = new ResetStockPileMove(game.stockPile,
				game.wastePile);
		move.doMove(game);
		checkState(game, 1, false);
		move.undo(game);
		checkState(game, 2, true);
	}

	@Test
	public void testInvalid() {
		Alahambra game = test.getTestGame();
		ResetStockPileMove move = new ResetStockPileMove(game.stockPile,
				game.wastePile);

		assertTrue(!move.valid(game));
		assertTrue(!move.doMove(game));
	}

	@Test
	public void testNoStocks() {
		Alahambra game = test.getTestGame();
		flipThroughStock(game);
		ResetStockPileMove move = new ResetStockPileMove(game.stockPile,
				game.wastePile);
		move.doMove(game);
		// Now one stock remains

		flipThroughStock(game);
		ResetStockPileMove move2 = new ResetStockPileMove(game.stockPile,
				game.wastePile);
		assertTrue(move2.doMove(game));
		// Now no stock remains

		flipThroughStock(game);
		ResetStockPileMove move3 = new ResetStockPileMove(game.stockPile,
				game.wastePile);

		assertTrue(!move3.valid(game));
		assertTrue(!move3.doMove(game));

	}

	@Test
	public void testCardsFlipped() {
		Alahambra game = test.getTestGame();
		flipThroughStock(game);

		ResetStockPileMove move = new ResetStockPileMove(game.stockPile,
				game.wastePile);
		move.doMove(game);
		// check face up state
		assertTrue(!game.stockPile.peek().isFaceUp());
		move.undo(game);
		// check face up state
		assertTrue(game.wastePile.peek().isFaceUp());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullConstructor() {
		@SuppressWarnings("unused")
		ResetStockPileMove m = new ResetStockPileMove(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBadConstructor() {
		Pile stock = test.getTestGame().stockPile;
		@SuppressWarnings("unused")
		ResetStockPileMove m = new ResetStockPileMove(stock, stock);
	}

	/**
	 * After calling this method, the stock will be emptied, and will have
	 * flipped over into the waste pile.
	 * 
	 * @param game
	 */
	private void flipThroughStock(Alahambra game) {
		while (!game.stockPile.empty()) {
			FlipStockPileMove flip = new FlipStockPileMove(game.stockPile,
					game.wastePile);
			flip.doMove(game);
		}
	}

	/**
	 * 
	 * @param game
	 *            Game to test state
	 * @param expectedStocks
	 *            number of stocks the game should have
	 * @param isStockEmpty
	 *            true if the stockPile should be empty.
	 */
	private void checkState(Alahambra game, int expectedStocks,
			boolean isStockEmpty) {
		assertTrue(game.getStocksLeft().getValue() == expectedStocks);
		if (isStockEmpty) {
			assertTrue(game.stockPile.empty());
		} else {
			assertTrue(!game.stockPile.empty());
		}
	}

}
