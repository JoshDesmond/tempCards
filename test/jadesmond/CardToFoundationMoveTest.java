package jadesmond;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import jadesmond.move.CardToAceFoundationMove;
import jadesmond.move.CardToKingFoundationMove;
import ks.common.model.Card;

public class CardToFoundationMoveTest {

    MoveTester test = new MoveTester();

    @Test
    public void testIntegeration() {
        Alahambra game = getGame(1, 13);

        Card tc = new Card(2, 1);

        CardToAceFoundationMove move = new CardToAceFoundationMove(
                game.columns[1], game.aceFoundations[0], tc);

        assertTrue(move.valid(game));
        assertTrue(move.doMove(game));
        game.pushMove(move);

        assertEquals(game.aceFoundations[0].peek(), tc);

        // Now lets test adding a 12 to the king foundation.
        CardToKingFoundationMove move2 = new CardToKingFoundationMove(
                game.columns[1], game.kingFoundations[0], new Card(12, 1));
        assertTrue(move2.valid(game));

        assertTrue(move2.doMove(game));

        assertEquals(game.kingFoundations[0].peek(), new Card(12, 1));
    }

    @Test
    public void testInvalidMoves() {
        Alahambra game = getGame(4, 5);
        Card four = new Card(4, 1);

        CardToAceFoundationMove move = new CardToAceFoundationMove(
                game.columns[0], game.aceFoundations[0], four);

        assertTrue(!move.valid(game));
        assertTrue(!move.doMove(game));

        CardToKingFoundationMove move2 = new CardToKingFoundationMove(
                game.columns[0], game.kingFoundations[0], four);

        assertTrue(move2.valid(game));
        assertTrue(move2.doMove(game));

        CardToKingFoundationMove move3 = new CardToKingFoundationMove(
                game.columns[0], game.kingFoundations[0], four);

        assertTrue(!move3.valid(game));

        CardToKingFoundationMove move4 = new CardToKingFoundationMove(
                game.columns[0], game.kingFoundations[0], new Card(3, 2));

        assertTrue(!move4.valid(game));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullMoveInput() {
        CardToAceFoundationMove move = new CardToAceFoundationMove(null, null,
                null);
    }

    private Alahambra getGame(int aceValue, int kingValue) {
        Alahambra game = test.getTestGame();
        game.aceFoundations[0].removeAll();
        game.aceFoundations[0].add(new Card(aceValue, 1));
        game.kingFoundations[0].removeAll();
        game.kingFoundations[0].add(new Card(kingValue, 1));
        return game;
    }

    @Test
    public void testScoreUpdate() {

        Alahambra game = getGame(1, 13);

        CardToAceFoundationMove moveAce = new CardToAceFoundationMove(
                game.columns[1], game.aceFoundations[0], new Card(2, 1));

        int oldScore = game.getScoreValue();
        moveAce.doMove(game);
        assertTrue(game.getScoreValue() == oldScore + 1);

        moveAce.undo(game);

        assertTrue(game.getScoreValue() == oldScore);
    }

}
