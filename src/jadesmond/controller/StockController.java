package jadesmond.controller;

import jadesmond.Alahambra;
import jadesmond.move.FlipStockPileMove;
import jadesmond.move.ResetStockPileMove;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Move;
import ks.common.model.Pile;

public class StockController extends SolitaireReleasedAdapter {

	protected Alahambra theGame;
	protected Pile stockPile;
	protected Pile wastePile;

	public StockController(Alahambra alahambra, Pile stockPile,
			Pile wastePile) {
		super(alahambra);
		this.theGame = alahambra;
		this.stockPile = stockPile;
		this.wastePile = wastePile;
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent me) {
		// Attempting a DealFourCardMove
		Move m = new FlipStockPileMove(stockPile, wastePile);
		if (m.doMove(theGame)) {
			theGame.pushMove(m);
			theGame.refreshWidgets();
		}

		else if (stockPile.empty()) {
			Move reset = new ResetStockPileMove(stockPile, wastePile);
			if (reset.doMove(theGame)) {
				theGame.pushMove(reset);
				theGame.refreshWidgets();
			}
		}
	}

}
