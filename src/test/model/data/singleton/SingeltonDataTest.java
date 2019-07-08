package test.model.data.singleton;

import static org.junit.Assert.*;

import org.junit.Test;
import model.data.Data;

/**
 * 
 * @author Andreas Hager, andreashager19@gmail.com
 * @version Initial version
 */
public class SingeltonDataTest {
	private final Data sut = Data.getInstanceOfSingletonData(0);

	
	@Test(timeout = 1_000)
	public void getInstanceOfSingletonData() {
		//arrange
		final Data test = Data.getInstanceOfSingletonData(0);
		final boolean want = true;
		//act
		final boolean have = sut == test;
		//assert
		assertEquals(want, have);
	}
	
	@Test(timeout = 1_000)
	public void getPlayers() {
		//arrange
		final int want = 0;
		//act
		final int have = sut.getPlayerCount();
		//assert
		assertEquals(want, have);
	}
}
