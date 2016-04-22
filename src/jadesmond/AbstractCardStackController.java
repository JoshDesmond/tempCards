package jadesmond;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ks.common.view.CardView;
import ks.common.view.Container;
import ks.common.view.Widget;

public abstract class AbstractCardStackController extends MouseAdapter {

    /** The game that we are partly controlling. */
    protected Alahambra theGame;

    protected Widget source;

    public AbstractCardStackController(Alahambra theGame, Widget column) {
        super();
        this.theGame = theGame;
        this.source = column;
    }

    public void mousePressed(MouseEvent me) {

        Container c = theGame.getContainer();

        if (!isCardThere()) {
            return;
        }

        CardView cardView = getCard(me);

        if (cardView == null) {
            return;
        }

        Widget w = c.getActiveDraggingObject();
        if (w != Container.getNothingBeingDragged()) {
            System.err.println(
                    "BuildablePileController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
            return;
        }

        // Tell container which object is being dragged, and where in that
        // widget
        // the user clicked.
        c.setActiveDraggingObject(cardView, me);

        // Tell container which BuildablePileView is the source for this drag
        // event.
        c.setDragSource(source);

        // we simply redraw our source pile to avoid flicker,
        // rather than refreshing all widgets...
        source.redraw();
    }

    /**
     * Coordinate reaction to the completion of a Drag Event.
     * <p>
     * A bit of a challenge to construct the appropriate move, because cards can
     * be dragged both from the WastePile (as a CardView widget) and the
     * BuildablePileView (as a ColumnView widget).
     * <p>
     * 
     * @param me
     *            java.awt.event.MouseEvent
     */
    public void mouseReleased(MouseEvent me) {
        Container c = theGame.getContainer();

        /** Return if there is no card being dragged chosen. */
        Widget activeDraggingObject = c.getActiveDraggingObject();
        if (activeDraggingObject == Container.getNothingBeingDragged()) {
            c.releaseDraggingObject();
            return;
        }

        /** Recover the from BuildablePile OR waste Pile */
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
        fromWidget.returnWidget(activeDraggingObject);
        c.repaint();
    }

    protected abstract CardView getCard(MouseEvent me);

    /** Return true if there is a card there */
    protected abstract boolean isCardThere();

    /**
     * Handles the release of a card once you already know the dragging object
     * and the source of the movement.
     * 
     * @param fromWidget
     * @param activeDraggingObject
     * @param me
     */
    protected abstract void handleReleaseLogic(Widget fromWidget,
            Widget activeDraggingObject, MouseEvent me);
}
