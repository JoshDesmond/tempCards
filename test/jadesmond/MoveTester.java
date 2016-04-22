package jadesmond;

import java.util.Random;

import ks.client.gamefactory.GameWindow;
import ks.launcher.Main;

public class MoveTester {

    Random random = new Random();

    /**
     * Creates a new Alahambra with a window for testing.
     * 
     * @return
     */
    public Alahambra getTestGame() {
        Alahambra testGame = new Alahambra();
        int r = random.nextInt();
        GameWindow gw = Main.generateWindow(testGame, r);
        return testGame;
    }

}
