package jadesmond;

import jadesmond.move.CardToWastePileMove;

import java.awt.event.MouseEvent;

import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Pile;
import ks.common.model.Stack;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.PileView;
import ks.common.view.Widget;

public class WastePileController extends AbstractCardStackController {

	private PileView pileSource;

	public WastePileController(Alahambra theGame, Widget source) {
		super(theGame, source);

		this.pileSource = (PileView) source;
	}

	@Override
	protected CardView getCard(MouseEvent me) {
		return pileSource.getCardViewForTopCard(me);
	}

	@Override
	protected boolean isCardThere() {
		Pile pile = (Pile) source.getModelElement();
		return !pile.empty();
	}

	@Override
	protected void handleReleaseLogic(Widget fromWidget,
			Widget activeDraggingObject, MouseEvent me) {

		// Just cast these right away
		final Pile pile = (Pile) pileSource.getModelElement();
		if (!(activeDraggingObject instanceof CardView)) {
			System.err.println(
					"Something other than a cardView was dragged onto waste pile");
			return;
		}
		CardView cardView = (CardView) activeDraggingObject;
		final Card card = (Card) cardView.getModelElement();

		// If the fromWidget is a PileView, then we know source and destination
		// are both the waste pile.
		if (fromWidget instanceof PileView) {
			Stack s = (Stack) fromWidget.getModelElement();
			s.add((Card) activeDraggingObject.getModelElement());
		}

		// If the fromWidget is a ColumnView then make a move.
		else if (fromWidget instanceof ColumnView) {
			ColumnView columnView = (ColumnView) fromWidget;
			final Column column = (Column) columnView.getModelElement();

			CardToWastePileMove move = new CardToWastePileMove(pile, column,
					card);

			if (move.valid(theGame)) {
				move.doMove(theGame);
				theGame.pushMove(move);
			} else {
				column.add(card);
			}
		}

	}

}
