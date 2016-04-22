package jadesmond;

import jadesmond.controller.ColumnController;
import jadesmond.controller.FoundationController;
import jadesmond.controller.StockController;
import jadesmond.controller.WastePileController;

import java.util.LinkedList;
import java.util.List;

import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.Column;
import ks.common.model.MultiDeck;
import ks.common.model.MutableInteger;
import ks.common.model.Pile;
import ks.common.view.CardImages;
import ks.common.view.ColumnView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;
import ks.common.view.Widget;
import ks.launcher.Main;

public class Alahambra extends Solitaire {

	/**
	 * Starts at 2. If it's 0, the waste cannot be flipped back over onto the
	 * stock.
	 */
	MutableInteger numberOfStocksRemaining;

	MultiDeck deck;
	Pile wastePile;
	Pile stockPile;
	Pile[] aceFoundations;
	Pile[] kingFoundations;
	Column[] columns;

	PileView stockPileView;
	PileView wastePileView;
	PileView[] foundationViews;
	ColumnView[] columnViews;
	IntegerView scoreView;
	IntegerView numLeftView;
	IntegerView stocksLeftView;
	/**
	 * For use with batch operations on widgets, like registering the undo
	 * adapter.
	 */
	private List<Widget> views;

	private AlahambraDealer dealer;

	/** Optional constructor you can use to change the way the game starts */
	public Alahambra(AlahambraDealer dealer) {
		super();
		if (dealer == null) {
			throw new IllegalArgumentException(
					"null dealer given to constructor");
		}
		this.dealer = dealer;
	}

	public Alahambra() {
		super();
		this.dealer = new RandomDealer();
	}

	@Override
	public String getName() {
		return "Desmond-Alahambra";
	}

	@Override
	public boolean hasWon() {
		return getScore().getValue() == 96;
	}

	@Override
	public void initialize() {
		initializeModel(getSeed());
		initializeView();
		intializeControllers();

		dealer.dealCards(this);
	}

	private void intializeControllers() {
		for (Widget w : views) {
			w.setMouseAdapter(new SolitaireReleasedAdapter(this));
			w.setUndoAdapter(new SolitaireUndoAdapter(this));
			w.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
		}

		stockPileView.setMouseAdapter(new StockController(this, stockPile,
				wastePile));
		wastePileView.setMouseAdapter(new WastePileController(this,
				wastePileView));

		for (int i = 0; i < 8; i++) {
			columnViews[i].setMouseAdapter(new ColumnController(this,
					columnViews[i]));
		}

		for (int i = 0; i < 8; i++) {
			foundationViews[i].setMouseAdapter(new FoundationController(this,
					foundationViews[i], (i < 4)));
		}

		// Finally, cover the Container for any events not handled by a widget:
		getContainer().setMouseMotionAdapter(
				new SolitaireMouseMotionAdapter(this));
		getContainer().setMouseAdapter(new SolitaireReleasedAdapter(this));
		getContainer().setUndoAdapter(new SolitaireUndoAdapter(this));
	}

	private void initializeView() {
		views = new LinkedList<Widget>();
		CardImages ci = getCardImages();
		final int w = ci.getWidth();
		final int h = ci.getHeight();
		final int o = ci.getOverlap();

		stockPileView = new PileView(stockPile);
		stockPileView.setBounds(80 + 3 * w, 60 + 2 * h + 3 * o, w, h);
		container.addWidget(stockPileView);
		addToViewsList(stockPileView);

		wastePileView = new PileView(wastePile);
		wastePileView.setBounds(100 + 4 * w, 60 + 2 * h + 3 * o, w, h);
		container.addWidget(wastePileView);
		addToViewsList(wastePileView);

		initializeIntegerViews();

		foundationViews = new PileView[8];
		for (int i = 0; i < 4; i++) {
			foundationViews[i] = new PileView(aceFoundations[i]);
		}
		for (int i = 0; i < 4; i++) {
			foundationViews[i + 4] = new PileView(kingFoundations[i]);
		}

		for (int i = 0; i < 8; i++) {
			foundationViews[i].setBounds(20 + 20 * i + i * w, 20, w, h);
			container.addWidget(foundationViews[i]);
			addToViewsList(foundationViews[i]);

		}

		columnViews = new ColumnView[8];
		for (int i = 0; i < 8; i++) {
			columnViews[i] = new ColumnView(columns[i]);
			columnViews[i].setBounds(20 + 20 * i + i * w, 40 + h, w, h + 3 * o);
			container.addWidget(columnViews[i]);
			addToViewsList(columnViews[i]);
		}
	}

	/**
	 * Call this after the creation of every widget. This is needed for batch
	 * operations on widgets to work.
	 * 
	 * @param w
	 */
	private void addToViewsList(Widget w) {
		views.add(w);
	}

	private void initializeIntegerViews() {
		final int fontSize = 15;
		final int x = 20;
		final int yBase = 330;
		final int w = 100;
		final int h = 50;

		scoreView = new IntegerView(getScore());
		numLeftView = new IntegerView(getNumLeft());
		stocksLeftView = new IntegerView(getStocksLeft());
		scoreView.setFontSize(fontSize);
		numLeftView.setFontSize(fontSize);
		stocksLeftView.setFontSize(fontSize);

		scoreView.setBounds(x, yBase, w, h);
		numLeftView.setBounds(x, yBase + h, w, h);
		stocksLeftView.setBounds(x, yBase + 2 * h, w, h);

		container.addWidget(scoreView);
		container.addWidget(numLeftView);
		container.addWidget(stocksLeftView);
		addToViewsList(scoreView);
		addToViewsList(numLeftView);
		addToViewsList(stocksLeftView);

	}

	public MutableInteger getStocksLeft() {
		return numberOfStocksRemaining;
	}

	private void initializeModel(int seed) {
		numberOfStocksRemaining = new MutableInteger(2);
		deck = new MultiDeck("deck", 2);
		deck.create(seed);
		model.addElement(deck);

		wastePile = new Pile("waste");
		model.addElement(wastePile);

		stockPile = new Pile("stock");
		model.addElement(stockPile);

		aceFoundations = new Pile[4];
		kingFoundations = new Pile[4];
		columns = new Column[8];

		for (int i = 0; i < 4; i++) {
			initializeAceFoundation(i);
		}
		for (int i = 0; i < 4; i++) {
			initializeKingFoundation(i);
		}
		for (int i = 0; i < 8; i++) {
			initializeColumn(i);
		}

	}

	private void initializeColumn(int i) {
		columns[i] = new Column(Integer.toString(i));
		model.addElement(columns[i]);
	}

	private void initializeKingFoundation(int i) {
		kingFoundations[i] = new Pile();
		model.addElement(kingFoundations[i]);
	}

	private void initializeAceFoundation(int i) {
		aceFoundations[i] = new Pile();
		model.addElement(aceFoundations[i]);
	}

	/**
	 * Decreases the number of stocks remaining by one
	 * 
	 * @return True if there are zero stocks remaining after decreasing by one.
	 */
	public boolean updateStock(int delta) {
		this.numberOfStocksRemaining.increment(delta);
		int s = getStocksLeft().getValue();
		if (s < 0) {
			throw new IllegalStateException("Stocks shouldn't be below zero");
		}
		return (s == 0);

	}

	public static void main(String[] args) {
		Main.generateWindow(new Alahambra(), 5);
	}
}
