package test.model.data.singleton;

import static org.junit.Assert.*;
import org.junit.Test;
import model.data.Data;
import model.data.exceptions.FalseIDException;
import model.data.exceptions.FalsePlayerTypeException;

/**
 * 
 * @author Andreas Hager, andreashager19@gmail.com
 * @version Initial version
 */
public class SingeltonDataGetAndSetPlayerTurnIDTest {
	final Data sut = Data.getInstanceOfSingletonData(0);
	
	@Test(timeout = 1_000)
	public void test() throws FalseIDException, FalsePlayerTypeException {
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
