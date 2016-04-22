package jadesmond;

import ks.client.gamefactory.GameWindow;
import ks.launcher.Main;

import org.junit.Test;

/** This class is for testing the full execution of a game. */
public class AlahambraTest {
	MoveTester test = new MoveTester();

	@Test
	public void testExecuteFullGame() {
		Alahambra game = new Alahambra();
		GameWindow gw = Main.generateWindow(game, 0);

	}
}
