package jadesmond;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ks.common.model.Card;

/**
 * This is a version of a dealer which puts all the cards in a very rigirous
 * order.
 */
public class TestingDealer implements AlahambraDealer {

	private Alahambra game;

	public TestingDealer() {
	}

	@Override
	public void dealCards(Alahambra alahambra) {
		this.game = alahambra;
		dealToFoundations();
		dealToColumns();
		dealToStock();
	}

	private void dealToStock() {
		// There are 64 cards in the stock.

		Queue<Card> stockCards = new LinkedList<Card>();
		for (int i = 1; i <= 4; i++) {
			// now for every of four suits
			// you'll need 10 - 13
			for (int r = 13; r >= 10; r--) {
				stockCards.add(new Card(r, i));
			}
		}

		for (int i = 1; i <= 4; i++) {
			// For every of the four suits
			// you'll need 12 through 1
			for (int r = 1; r <= 12; r++) {
				stockCards.add(new Card(r, i));
			}
		}

		while (!stockCards.isEmpty()) {
			Card c = stockCards.poll();
			c.setFaceUp(false);
			game.stockPile.add(c);
		}
	}

	/**
	 * Columns look like [2,3,4,5],[6,7,8,9],[2,3,4,5]... etc
	 */
	private void dealToColumns() {
		Queue<Card> columnCards = new LinkedList<Card>();
		for (int i = 1; i <= 4; i++) {
			// For every of the four suits
			for (int r = 5; r >= 2; r--) {
				// create 4 cards from 2 - 5
				columnCards.add(new Card(r, i));
			}
		}

		for (int i = 1; i <= 4; i++) {
			// For every of the four suits
			for (int r = 9; r >= 6; r--) {
				// create 4 cards from 6 - 9
				columnCards.add(new Card(r, i));
			}
		}

		// now add the cards sequentially up the columns
		for (int i = 0; i < game.columns.length; i++) {
			for (int z = 0; z < 4; z++) {
				game.columns[i].add(columnCards.poll());
			}
		}

	}

	/**
	 * Returns the list of foundation cards in the order of the eight
	 * foundations
	 */
	private List<Card> getListOfFoundationCards() {
		List<Card> cards = new LinkedList<Card>();
		cards.add(new Card(1, 1));
		cards.add(new Card(1, 2));
		cards.add(new Card(1, 3));
		cards.add(new Card(1, 4));
		cards.add(new Card(13, 1));
		cards.add(new Card(13, 2));
		cards.add(new Card(13, 3));
		cards.add(new Card(13, 4));
		return cards;
	}

	private void dealToFoundations() {
		int i = 0;
		for (Card c : getListOfFoundationCards()) {
			addToFoundation(c, i);
			i++;
		}
	}

	/**
	 * Adds the given card to the foundation i
	 * 
	 * @param card
	 * @param i
	 * @return
	 */
	private boolean addToFoundation(Card card, int i) {
		if (i < 4) {
			game.aceFoundations[i].add(card);
			return true;
		}

		// else
		game.kingFoundations[i - 4].add(card);
		return true;
	}

}
