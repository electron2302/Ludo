package test.model.data.data;

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
public class DataTest {
	

	private Data getInstanceOfSut(int gameNumber) {
		Data.getInstanceOfSingletonData(0).reset();
		return Data.getInstanceOfSingletonData(0);
	}
	
	@Test(timeout = 1_000)
	public void getAddPlayer() throws FalseIDException, FalsePlayerTypeException {
		//arrange
		final Data sut = getInstanceOfSut(0);
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

	
	@Test(timeout = 1_000)
	public void getInstanceOfSingletonData() {
		//arrange
		final Data sut = getInstanceOfSut(0);
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
		final Data sut = getInstanceOfSut(0);
		final int want = 0;
		//act
		final int have = sut.getPlayerCount();
		//assert
		assertEquals(want, have);
	}
	
	@Test(timeout = 1_000)
	public void hasPlayerWonFalse() throws FalseIDException, FalsePlayerTypeException {
		//arrange
		final Data sut = getInstanceOfSut(0);
		final boolean want = false;
		sut.addPlayer(1);
		//act
		final boolean have = sut.hasPlayerWon(0);
		//assert
		assertEquals(want, have);
		
		
	}
	
	@Test(timeout = 1_000)
	public void hasPlayerWonTrue() throws FalseIDException, FalsePlayerTypeException {
		//arrange
		final Data sut = getInstanceOfSut(0);
		final boolean want2 = true;
		sut.addPlayer(1);
		//act
		sut.setPlayerHasWon(0);
		final boolean have2 = sut.hasPlayerWon(0);
		//assert
		assertEquals(want2,have2);
	}
	
	@Test(timeout = 1_000)
	public void test() throws FalseIDException, FalsePlayerTypeException {
		//arrange
		final Data sut = getInstanceOfSut(0);
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
