package jadesmond;

import static org.junit.Assert.*;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.view.Widget;
import ks.launcher.Main;

import org.junit.Test;

public class FoundationControllerTest {

	@Test
	public void testPlaceAce() {
		Alahambra game = new Alahambra(new TestingDealer());
		GameWindow gw = Main.generateWindow(game, 0);

		Card card = new Card(2, 1);
		pickUpCard(game, card);

		MouseEvent releaseOnAce = getMouseReleaseOnAce(game);
		game.foundationViews[0].getMouseManager()
		.handleMouseEvent(releaseOnAce);

		assertTrue(game.aceFoundations[0].peek().equals(card));
	}

	@Test
	public void testPlaceKing() {
		Alahambra game = new Alahambra(new TestingDealer());
		GameWindow gw = Main.generateWindow(game, 0);

		Card card = new Card(12, 1);
		pickUpCard(game, card);

		MouseEvent releaseOnKing = getMouseReleaseOnKing(game);
		game.foundationViews[4].getMouseManager().handleMouseEvent(
				releaseOnKing);

		assertTrue(game.kingFoundations[0].peek().equals(card));

	}

	private void pickUpCard(Alahambra game, Card card) {
		game.columns[0].add(card);
		MouseEvent pickupCard = StockPileControllerTest.createPressed(game,
				game.columnViews[0], 5, 5 + 100);
		game.columnViews[0].getMouseManager().handleMouseEvent(pickupCard);
	}

	private MouseEvent getMouseReleaseOnAce(Alahambra game) {
		return createReleased(game, game.foundationViews[0], 5, 5);
	}

	private MouseEvent getMouseReleaseOnKing(Alahambra game) {
		return createReleased(game, game.foundationViews[4], 5, 5);
	}

	/** (dx,dy) are offsets into the widget space. Feel Free to Use as Is. */
	public static MouseEvent createReleased(Solitaire game, Widget view,
			int dx, int dy) {
		MouseEvent me = new MouseEvent(game.getContainer(),
				MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), 0,
				view.getX() + dx, view.getY() + dy, 0, false);
		return me;
	}
}
