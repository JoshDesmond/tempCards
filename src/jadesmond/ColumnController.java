package jadesmond;

import java.awt.event.MouseEvent;

import ks.common.model.Column;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Widget;

public class ColumnController extends AbstractCardStackController {

    private ColumnView columnSource;

    public ColumnController(Alahambra theGame, Widget widget) {
        super(theGame, widget);
        this.columnSource = (ColumnView) widget;
    }

    @Override
    protected CardView getCard(MouseEvent me) {
        return columnSource.getCardViewForTopCard(me);
    }

    @Override
    protected boolean isCardThere() {
        /** Return if there is no card to be chosen. */
        Column col = (Column) source.getModelElement();
        return (col.count() > 0);
    }

    @Override
    protected void handleReleaseLogic(Widget fromWidget,
            Widget activeDraggingObject, MouseEvent me) {
        // Do Nothing?

    }

}