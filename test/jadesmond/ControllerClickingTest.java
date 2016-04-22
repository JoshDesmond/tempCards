package jadesmond;

import static org.junit.Assert.assertTrue;

import java.awt.event.MouseEvent;

import org.junit.Test;

import ks.client.gamefactory.GameWindow;
import ks.common.games.Solitaire;
import ks.common.view.Widget;
import ks.launcher.Main;

public class ControllerClickingTest {

    /** (dx,dy) are offsets into the widget space. Feel Free to Use as Is. */
    public MouseEvent createPressed(Solitaire game, Widget view, int dx,
            int dy) {
        MouseEvent me = new MouseEvent(game.getContainer(),
                MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0,
                view.getX() + dx, view.getY() + dy, 0, false);
        return me;
    }

    @Test
    public void testStockPileClick() {
        Alahambra game = new Alahambra(new TestingDealer());
        GameWindow gw = Main.generateWindow(game, 0);

        MouseEvent me = createPressed(game, game.stockPileView, 5, 5);
        game.stockPileView.getMouseManager().handleMouseEvent(me);

        assertTrue(game.wastePile.count() == 1);
    }

    @Test
    public void testStockPileClickReset() {
        Alahambra game = new Alahambra(new TestingDealer());
        GameWindow gw = Main.generateWindow(game, 0);

        game.wastePile.add(game.stockPile.get());
        game.stockPile.removeAll();

        MouseEvent me = createPressed(game, game.stockPileView, 5, 5);
        game.stockPileView.getMouseManager().handleMouseEvent(me);

        assertTrue(game.getStocksLeft().getValue() == 1);
    }

}
