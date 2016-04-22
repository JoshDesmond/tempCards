package jadesmond;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;

import ks.common.model.Card;
import ks.common.model.Column;

public class AlahambraGameStarterTest {

    MoveTester test = new MoveTester();

    @Test
    public void testFoundationPiles() {
        Alahambra game = test.getTestGame();

        Card[] cards = new Card[4];
        for (int i = 0; i < 4; i++) {
            cards[i] = game.aceFoundations[i].peek();
        }

        assertTrue(validateCards(cards, 1));

        for (int i = 0; i < 4; i++) {
            cards[i] = game.kingFoundations[i].peek();
        }

        assertTrue(validateCards(cards, 13));
    }

    /**
     * Validates the four foundation cards, checking to see that they're all the
     * given value and no suits are matching.
     */
    private boolean validateCards(Card[] cards, int value) {
        HashSet<Integer> suits = new HashSet<Integer>();
        for (Card c : cards) {
            if (c.getRank() != value) {
                return false;
            }

            suits.add(c.getSuit());
        }

        return (suits.size() == 4);
    }

    @Test
    public void testColumnCounts() {
        Alahambra game = test.getTestGame();

        for (Column c : game.columns) {
            assertTrue(c.count() == 4);
        }

    }
}
