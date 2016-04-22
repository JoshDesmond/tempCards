package jadesmond;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import jadesmond.Alahambra;
import jadesmond.move.FlipStockPileMove;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.launcher.Main;

public class FlipStockPileMoveTest {

    @Test
    public void test() {
        Alahambra testGame = new Alahambra();
        GameWindow gw = Main.generateWindow(testGame, 5);

        Card topCard = testGame.stockPile.peek();
        FlipStockPileMove move = new FlipStockPileMove(testGame.stockPile,
                testGame.wastePile);
        assertTrue(move.valid(testGame));

        move.doMove(testGame);
        assertTrue(testGame.wastePile.count() == 1);

        move.undo(testGame);
        assertTrue(testGame.wastePile.count() == 0);
    }

    @Test
    public void testNormal() {
        fail();
    }

    @Test
    public void testInvalidFlip() {
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalConstructor() {
        FlipStockPileMove move = new FlipStockPileMove(null, null);
    }

}
