package jadesmond;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ks.common.model.Card;

/**
 * This class is in charge of dealing out the cards to the correct spots at the
 * start of a game
 */
public class AlahambraGameStarter implements AlahambraDealer{

	Random random = new Random();
	private Alahambra game;

	public AlahambraGameStarter() {
	}

	@Override
	public void dealCards(Alahambra alahambra) {
		this.game = alahambra;
		// First lets get the kings and aces out to their foundations
		dealToFoundations();

		// Now lets put cards to the columns
		dealToColumns();

		dealToStock();
	}

	private void dealToStock() {
		while (!game.deck.empty()) {
			Card c = game.deck.get();
			c.setFaceUp(false);
			game.stockPile.add(c);
		}
	}

	private void dealToColumns() {
		for (int i = 0; i < game.columns.length; i++) {
			game.columns[i].add(game.deck.get());
			game.columns[i].add(game.deck.get());
			game.columns[i].add(game.deck.get());
			game.columns[i].add(game.deck.get());
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
			boolean hasFoundCard = false;
			while (!hasFoundCard) {
				if (game.deck.peek().equals(c)) {
					hasFoundCard = addToFoundation(game.deck.get(), i);
				}

				game.deck.shuffle(random.nextInt());
			}

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

	/**
	 * Adds the given card to the lowest index Ace Foundation pile.
	 * 
	 * @return true if that was the last ace needed for initializing the deck.
	 */
	private boolean addAceToFoundation(Card c) {
		assert c.getRank() == 1;

		for (int i = 0; i < 4; i++) {
			if (game.aceFoundations[i].peek() == null) {
				game.aceFoundations[i].add(c);

				if (i == 3) {
					return true;
				} else
					return false;
			}
		}

		throw new IllegalStateException(
				"Somehow exited a loop when not quite possible.");
	}

	/**
	 * Adds the given card to the lowest index Ace Foundation pile.
	 * 
	 * @return true if that was the last ace needed for initializing the deck.
	 */
	private boolean addKingToFoundation(Card c) {
		assert c.getRank() == 13;

		for (int i = 0; i < 4; i++) {
			if (game.kingFoundations[i].peek() == null) {
				game.kingFoundations[i].add(c);

				if (i == 3) {
					return true;
				} else
					return false;
			}
		}

		throw new IllegalStateException(
				"Somehow exited a loop when not quite possible.");
	}
}
