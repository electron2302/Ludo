
package model.data.test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.data.SingeltonData;

/**
 * 
 * @author Andreas Hager, andreashager19@gmail.com
 * @version Initial version
 */
public class SingeltonDataADDPlayerTest {
	private final SingeltonData sut = SingeltonData.getInstanceOfSingletonData();
	
	@Test(timeout = 1_000)
	public void getAddPlayer() {
		//arrange
		final int want = 1;
		final int want2 = 2;
		final int want3 = 3;
		final int want4 = 4;
		final int want5 = 5;
		//act
		sut.addPlayer(15);
		final int have = sut.addPlayer(1);
		final int have2 = sut.addPlayer(1);
		final int have3 = sut.addPlayer(1);
		final int have4 = sut.addPlayer(1);
		final int have5 = sut.addPlayer(1);
		//assert
		assertEquals(want, have);
		assertEquals(want2, have2);
		assertEquals(want3, have3);
		assertEquals(want4, have4);
		assertEquals(want5, have5);
	}

}
