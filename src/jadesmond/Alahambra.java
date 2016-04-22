package jadesmond;

import java.util.LinkedList;
import java.util.List;

import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.Card;
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
    int numberOfStocksRemaining;

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

    @Override
    public String getName() {
        return "Desmond-Alahambra";
    }

    @Override
    public boolean hasWon() {
        return false;
    }

    @Override
    public void initialize() {
        initializeModel(getSeed());
        initializeView();
        intializeControllers();
    }

    private void intializeControllers() {
        for (Widget w : views) {
            w.setUndoAdapter(new SolitaireUndoAdapter(this));
            w.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
        }

        stockPileView.setMouseAdapter(
                new StockController(this, stockPile, wastePile));
        wastePileView
                .setMouseAdapter(new WastePileController(this, stockPileView));

        for (int i = 0; i < 8; i++) {
            columnViews[i].setMouseAdapter(
                    new ColumnController(this, columnViews[i]));
        }

        // Finally, cover the Container for any events not handled by a widget:
        getContainer()
                .setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
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

    private MutableInteger getStocksLeft() {
        // TODO
        return new MutableInteger(2);
    }

    private void initializeModel(int seed) {
        deck = new MultiDeck("deck", 2);
        deck.create(500);
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

        while (!deck.empty()) {
            stockPile.add(deck.get());
        }
    }

    private void initializeColumn(int i) {
        columns[i] = new Column(Integer.toString(i));
        columns[i].add(deck.get());
        columns[i].add(deck.get());
        columns[i].add(deck.get());
        columns[i].add(deck.get());
    }

    private void initializeKingFoundation(int i) {
        kingFoundations[i] = new Pile();
        kingFoundations[i].add(deck.get());
    }

    private void initializeAceFoundation(int i) {
        aceFoundations[i] = new Pile();
        Card c = deck.get();
        aceFoundations[i].add(c);
    }

    /**
     * Decreases the number of stocks remaining by one
     * 
     * @return True if there are zero stocks remaining after decreasing by one.
     */
    boolean decreaseStock() {
        this.numberOfStocksRemaining--;
        if (numberOfStocksRemaining == 0) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Main.generateWindow(new Alahambra(), 5);
    }
}
