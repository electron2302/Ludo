package model.data.test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.data.SingeltonData;

/**
 * 
 * @author Andreas Hager, andreashager19@gmail.com
 * @version Initial version
 */
public class SingletonDataHasPlayerWontTest {
	private final SingeltonData sut = SingeltonData.getInstanceOfSingletonData();

	@Test(timeout = 1_000)
	public void hasPlayerWon() {
		//arrange
		final boolean want = false;
		sut.addPlayer(1);
		//act
		final boolean have = sut.hasPlayerWon(0);
		//assert
		assertEquals(want, have);
		
		//arrange
		sut.setPlayerHasWon(0);
		final boolean want2 = true;
		//act
		final boolean have2 = sut.hasPlayerWon(0);
		//assert
		assertEquals(want2,have2);
	}

}
