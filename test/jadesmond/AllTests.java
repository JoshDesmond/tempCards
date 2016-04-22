package jadesmond;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AlahambraGameStarterTest.class, AlahambraTest.class,
	CardToFoundationMoveTest.class, CardToWastePileMoveTest.class,
	FlipStockPileMoveTest.class, FoundationControllerTest.class,
	ResetStockPileMoveTest.class, StockPileControllerTest.class,
	WastePileControllerTest.class })
public class AllTests {

}
