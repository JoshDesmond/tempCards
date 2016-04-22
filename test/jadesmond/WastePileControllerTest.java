package jadesmond;

import static org.junit.Assert.*;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.launcher.Main;

import org.junit.Before;
import org.junit.Test;

public class WastePileControllerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testClick() {
		Alahambra game = new Alahambra(new TestingDealer());
		GameWindow gw = Main.generateWindow(game, 0);

		game.wastePile.add(new Card(5, 1));
		assertTrue(game.wastePile.count() == 1);

		MouseEvent me = StockPileControllerTest.createPressed(game,
				game.wastePileView, 5, 5);
		game.wastePileView.getMouseManager().handleMouseEvent(me);

		assertTrue(game.wastePile.count() == 0);
	}

	@Test
	public void testReleaseCard() {
		Alahambra game = new Alahambra(new TestingDealer());
		GameWindow gw = Main.generateWindow(game, 0);

		game.columns[0].add(new Card(5, 1));
		game.wastePile.add(new Card(4, 1));

		MouseEvent pickupCard = StockPileControllerTest.createPressed(game,
				game.columnViews[0], 5, 5 + 100);
		game.columnViews[0].getMouseManager().handleMouseEvent(pickupCard);

		assertTrue(!game.columns[0].peek().equals(new Card(5,1)));

		MouseEvent releaseCard = new MouseEvent(game.getContainer(),
				MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), 0,
				game.wastePileView.getX() + 5, game.wastePileView.getY() + 5,
				0, false);
		game.wastePileView.getMouseManager().handleMouseEvent(releaseCard);

		assertTrue(game.wastePile.peek().getRank() == 5);
	}

}
