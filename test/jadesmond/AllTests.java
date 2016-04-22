package jadesmond;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AlahambraGameStarterTest.class, CardToFoundationMoveTest.class,
	CardToWastePileMoveTest.class, FlipStockPileMoveTest.class,
	ResetStockPileMoveTest.class, AlahambraTest.class })
public class AllTests {

}
