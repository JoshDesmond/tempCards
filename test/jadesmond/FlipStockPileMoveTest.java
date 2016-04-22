package jadesmond;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import jadesmond.move.FlipStockPileMove;
import ks.common.model.Card;

public class FlipStockPileMoveTest {

    MoveTester tester = new MoveTester();

    @Test
    public void testIntegration() {
        Alahambra testGame = tester.getTestGame();

        Card topCard = testGame.stockPile.peek();
        FlipStockPileMove move = new FlipStockPileMove(testGame.stockPile,
                testGame.wastePile);
        assertTrue(move.valid(testGame));

        move.doMove(testGame);
        assertTrue(testGame.wastePile.count() == 1);

        assertEquals(testGame.wastePile.peek(), topCard);

        move.undo(testGame);
        assertTrue(testGame.wastePile.count() == 0);

        assertEquals(testGame.stockPile.peek(), topCard);
    }

    @Test
    public void testInvalidFlip() {
        Alahambra testGame = tester.getTestGame();
        testGame.stockPile.removeAll();
        FlipStockPileMove move = new FlipStockPileMove(testGame.stockPile,
                testGame.wastePile);

        assertTrue(!move.valid(testGame));
        assertTrue(!move.doMove(testGame));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalConstructor() {
        FlipStockPileMove move = new FlipStockPileMove(null, null);
    }

    @Test
    public void testFlipCard() {
        Alahambra game = tester.getTestGame();
        FlipStockPileMove move = new FlipStockPileMove(game.stockPile,
                game.wastePile);
        move.doMove(game);
        game.pushMove(move);

        assertTrue(game.wastePile.peek().isFaceUp());

        FlipStockPileMove move2 = new FlipStockPileMove(game.stockPile,
                game.wastePile);
        move2.doMove(game);
        game.pushMove(move2);

        assertTrue(game.wastePile.peek().isFaceUp());

    }

    @Test
    public void testUnflipCard() {
        Alahambra game = tester.getTestGame();
        FlipStockPileMove move = new FlipStockPileMove(game.stockPile,
                game.wastePile);
        move.doMove(game);
        game.pushMove(move);

        assertTrue(game.wastePile.peek().isFaceUp());

        game.undoMove();

        assertTrue(!game.stockPile.peek().isFaceUp());
    }

}
