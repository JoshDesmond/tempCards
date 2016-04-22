package jadesmond;

import java.awt.event.MouseEvent;

import ks.common.model.Pile;
import ks.common.view.CardView;
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
        // TODO Auto-generated method stub

    }

}
