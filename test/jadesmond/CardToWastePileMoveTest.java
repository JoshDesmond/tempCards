package jadesmond;

import static org.junit.Assert.*;
import jadesmond.move.CardToWastePileMove;
import ks.common.model.Card;
import ks.common.model.Pile;

import org.junit.Before;
import org.junit.Test;

public class CardToWastePileMoveTest {

	Alahambra game;

	private static final Card ac = new Card(1, 1);
	private static final Card kc = new Card(13, 1);
	private static final Card twoc = new Card(2, 1);
	private static final Card twod = new Card(2, 2);
	private static final Card kd = new Card(13, 2);
	private static final Card threec = new Card(3, 1);

	@Before
	public void setUp() {
		this.game = new MoveTester().getTestGame();
	}

	@Test
	public void testIntegration() {
		game.wastePile.add(twoc);
		CardToWastePileMove move = new CardToWastePileMove(game.wastePile,
				game.columns[0], ac);
		assertTrue(move.valid(game));
		assertTrue(move.doMove(game));

		assertEquals(game.wastePile.peek(), ac);
		assertEquals(((Pile) game.wastePileView.getModelElement()).peek(), ac);
		assertTrue(move.undo(game));

		assertEquals(game.columns[0].peek(), ac);
	}

	@Test
	public void testIllegalMoves() {
		game.wastePile.add(twod);
		CardToWastePileMove move = new CardToWastePileMove(game.wastePile,
				game.columns[1], threec);

		assertTrue(!move.valid(game));
		assertTrue(!move.doMove(game));

		assertTrue(game.wastePile.peek().equals(twod));
	}

	@Test
	public void testWrapping() {
		game.wastePile.add(ac);
		CardToWastePileMove move = new CardToWastePileMove(game.wastePile,
				game.columns[0], kc);
		assertTrue(move.valid(game));

		game.wastePile.add(kc);
		CardToWastePileMove move2 = new CardToWastePileMove(game.wastePile,
				game.columns[0], ac);
		assertTrue(move2.valid(game));
	}

	@Test
	public void testIllegalWrapping() {
		game.wastePile.add(ac);
		CardToWastePileMove move = new CardToWastePileMove(game.wastePile,
				game.columns[0], kd);
		assertTrue(!move.valid(game));

		game.wastePile.add(kd);
		CardToWastePileMove move2 = new CardToWastePileMove(game.wastePile,
				game.columns[0], ac);
		assertTrue(!move2.valid(game));
	}

	@Test
	public void testEmptyWastePile() {
		CardToWastePileMove move = new CardToWastePileMove(game.wastePile,
				game.columns[3], new Card(3, 3));

		assertTrue(!move.valid(game));
		assertTrue(!move.doMove(game));
	}

}
