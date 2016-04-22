package jadesmond.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import jadesmond.Alahambra;
import jadesmond.move.CardToAceFoundationMove;
import jadesmond.move.CardToFoundationMove;
import jadesmond.move.CardToKingFoundationMove;
import ks.common.model.Card;
import ks.common.model.Pile;
import ks.common.model.Stack;
import ks.common.view.CardView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

public class FoundationController extends MouseAdapter {

    protected Alahambra theGame;
    protected PileView foundationSource;
    private boolean isAceType;

    public FoundationController(Alahambra theGame, PileView foundationSource,
            boolean isAceType) {
        super();
        this.theGame = theGame;
        this.foundationSource = foundationSource;
        this.isAceType = isAceType;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        Container c = theGame.getContainer();

        /** Return if there is no card being dragged chosen. */
        Widget activeDraggingObject = c.getActiveDraggingObject();
        if (activeDraggingObject == Container.getNothingBeingDragged()) {
            c.releaseDraggingObject();
            return;
        }

        /** Recover the card from BuildablePile OR waste Pile */
        Widget fromWidget = c.getDragSource();
        if (fromWidget == null) {
            System.err.println(
                    "BuildablePileController::mouseReleased(): somehow no dragSource in container.");
            c.releaseDraggingObject();
            return;
        }

        handleReleaseLogic(fromWidget, activeDraggingObject, me);

        // release the dragging object, (container will reset dragSource)
        c.releaseDraggingObject();
        c.repaint();
    }

    private void handleReleaseLogic(Widget fromWidget,
            Widget activeDraggingObject, MouseEvent me) {
        if (!(activeDraggingObject instanceof CardView)) {
            System.err.println(
                    "Something other than a cardView was dragged onto waste pile");
            return;
        }
        CardView cardView = (CardView) activeDraggingObject;
        final Card card = (Card) cardView.getModelElement();

        final Stack stack = (Stack) fromWidget.getModelElement();
        Pile foundationPile = (Pile) foundationSource.getModelElement();

        CardToFoundationMove move;
        if (this.isAceType == true) {
            move = new CardToAceFoundationMove(stack, foundationPile, card);
        } else {
            move = new CardToKingFoundationMove(stack, foundationPile, card);
        }

        if (move.valid(theGame)) {
            move.doMove(theGame);
            theGame.pushMove(move);
        } else {
            stack.add(card);
        }
    }

}
