package jadesmond;

import static org.junit.Assert.*;
import jadesmond.move.CardToAceFoundationMove;
import jadesmond.move.CardToKingFoundationMove;
import jadesmond.move.FlipStockPileMove;
import ks.client.gamefactory.GameWindow;
import ks.launcher.Main;

import org.junit.Test;

/** This class is for testing the full execution of a game. */
public class AlahambraTest {
	MoveTester test = new MoveTester();
	private int score;

	@Test
	public void testExecuteFullGame() {
		Alahambra game = new Alahambra(new TestingDealer());
		GameWindow gw = Main.generateWindow(game, 0);
		score = 0;

		// First forty eight moves are via the waste pile.
		for (int i = 3; i >= 0; i--) {
			moveFromWasteToKingPile(game, i);
		}

		// next 32 moves are from the columns
		for (int i = 0; i < 4; i++) {
			// for each of the four suits

			// move the 2-5 column up to the Ace foundation
			moveFromColumnToAcePile(game, i);

			moveFromMediumColumnToAcePile(game, i);
		}

		// The last 16 moves are the 10-kings for the ace piles
		for (int i = 3; i >= 0; i--) {
			moveFromWasteToAce(game, i);
		}

		assertEquals(score, game.getScoreValue());
		assertTrue(game.hasWon());
	}

	private void moveFromWasteToAce(Alahambra game, int foundationNumber) {
		for (int z = 0; z < 4; z++) {
			FlipStockPileMove flip = new FlipStockPileMove(game.stockPile,
					game.wastePile);
			flip.doMove(game);
			CardToAceFoundationMove move = new CardToAceFoundationMove(
					game.wastePile, game.aceFoundations[foundationNumber],
					game.wastePile.get());
			move.doMove(game);
			score++;

		}
	}

	/** This moves the second row of cards up to the foundation */
	private void moveFromMediumColumnToAcePile(Alahambra game, int i) {
		for (int c = 1; c <= 4; c++) {
			CardToAceFoundationMove move = new CardToAceFoundationMove(
					game.columns[i + 4], game.aceFoundations[i],
					game.columns[i + 4].get());
			move.doMove(game);
			score++;
		}

	}

	private void moveFromColumnToAcePile(Alahambra game, int i) {
		for (int c = 1; c <= 4; c++) {
			CardToAceFoundationMove move = new CardToAceFoundationMove(
					game.columns[i], game.aceFoundations[i],
					game.columns[i].get());
			move.doMove(game);
			score++;

		}

	}

	private void moveFromWasteToKingPile(Alahambra game, int foundationNumber) {
		for (int i = 0; i < 12; i++) {
			FlipStockPileMove flip = new FlipStockPileMove(game.stockPile,
					game.wastePile);
			flip.doMove(game);
			CardToKingFoundationMove move = new CardToKingFoundationMove(
					game.wastePile, game.kingFoundations[foundationNumber],
					game.wastePile.get());
			move.doMove(game);
			score++;

		}
	}
}
