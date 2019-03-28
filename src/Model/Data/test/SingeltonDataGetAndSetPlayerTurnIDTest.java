package Model.Data.test;

import static org.junit.Assert.*;
import org.junit.Test;
import Model.Data.SingeltonData;

/**
 * 
 * @author Andreas Hager, andreashager19@gmail.com
 * @version Initial version
 */
public class SingeltonDataGetAndSetPlayerTurnIDTest {
	final SingeltonData sut = SingeltonData.getInstanceOfSingletonData();
	
	@Test(timeout = 1_000)
	public void test() {
		//arrange
		sut.addPlayer(2);
		sut.addPlayer(1);
		sut.addPlayer(3);
		sut.setPlayerHasWon(1);
		final boolean want = false;
		final boolean want2 = true;
		final boolean want3 = false;
		//act
		final boolean have = sut.hasPlayerWon(0);
		final boolean have2 = sut.hasPlayerWon(1);
		final boolean have3 = sut.hasPlayerWon(2);
		//assert
		assertEquals(want, have);
		assertEquals(want2, have2);
		assertEquals(want3, have3);
	}

}
