package test.model.data.player;

import static org.junit.Assert.*;

import org.junit.Test;

import model.data.exceptions.FalseIDException;
import model.data.exceptions.FalsePlayerTypeException;
import model.data.player.Player;

public class PlayerTest {

	@Test(timeout = 1_000)
	public void getPlayerIDTest() throws FalseIDException, FalsePlayerTypeException {
		//arrange
		final Player sut = Player.make(1, 0);
		final int want = 1;
		//act
		final int have = sut.getPlayerID();
		//assert
		assertEquals(want, have);
	}

	@Test(timeout = 1_000)
	public void getPlayerTypeTest() throws FalseIDException, FalsePlayerTypeException {
		//arrange
		final Player sut = Player.make(0, 1);
		final int want = 1;
		//act
		final int have = sut.getPlayerType();
		//assert
		assertEquals(want, have);
	}
	
	
	@Test(timeout = 1_000)
	public void hasPlayerWonDefault() throws FalseIDException, FalsePlayerTypeException {
		//arrange
		final Player sut = Player.make(1, 0);
		final boolean want = false;
		//act
		final boolean have = sut.hasPlayerWon();
		//assert
		assertEquals(want, have);
	}
	
	@Test(timeout = 1_000)
	public void hasPlayerWon() throws FalseIDException, FalsePlayerTypeException {
		//arrange
		final Player sut = Player.make(1, 0);
		sut.setPlayerHasWon();
		final boolean want = true;
		//act
		final boolean have = sut.hasPlayerWon();
		//assert 
		assertEquals(want, have);
	}
	
	@Test(timeout = 1_000)
	public void hasPlayerWon2() throws FalseIDException, FalsePlayerTypeException {
		//arrange
		final Player sut = Player.make(1, 0);
		sut.setPlayerHasWon();
		sut.setPlayerHasWon();
		final boolean want = true;
		//act
		final boolean have = sut.hasPlayerWon();
		//assert
		assertEquals(want, have);
	}
	
}
