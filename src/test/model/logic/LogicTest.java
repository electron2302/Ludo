package test.model.logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import model.data.RWData;
import model.data.exceptions.FalseIDException;
import model.data.exceptions.FalsePlayerTypeException;
import model.data.exceptions.FalsePositionException;
import model.data.exceptions.FalseTockenIDException;
import model.data.exceptions.NegativeBoardLengthException;
import model.logic.CLogic;
import model.logic.Logic;
import model.logic.exceptions.FalseDiceValueException;
import model.logic.exceptions.IllegalMoveException;
import model.logic.exceptions.PlayerAlereadyWonException;
import model.logic.exceptions.TriedToMooveToFarException;

public class LogicTest {
	
	private Logic getSut() {
		data.reset();
		return new CLogic();
	}
	
	private final RWData data = RWData.getInstanceOfRWData();
	
	
	//set up, start up
	@Test(timeout = 1_000)
	public void initializeTest() throws FalseIDException, FalseTockenIDException, FalsePlayerTypeException, NegativeBoardLengthException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		final int wantBoardLength = 40;
		final int wantPlayerCount = 4;
		final List<Integer> wantPlayerTypes = Arrays.asList(1, 2, 3, 1);
		final List<Integer> wantPositionOfTocken = Arrays.asList( -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
		final int wantSpacesBetweenPlayerAndBase = 10;
		final int wantTokenCountPP = 4;
		
		final List<Integer> havePlayerTypes = new ArrayList<>();
		final List<Integer> havePositionOfTocken = new ArrayList<>();
		//act
		final int haveBoardLength = data.getBoardLength();
		final int havePlayerCount = data.getPlayerCount();
		for(int index = 0; index < 4; index++) {
			havePlayerTypes.add(data.getPlayerType(index));
		}
		for(int player = 0; player < 4; player++) {
			for(int token = 0; token < 4; token++) {
				havePositionOfTocken.add(data.getPositionOfTocken(player, token));				
			}
		}
		final int haveSpacesBetweenPlayerAndBase = data.getSpacesBetweenPlayerBases();
		final int haveTokenCountPP = data.getTockenCountPP();
		//assert
		assertEquals(wantBoardLength, haveBoardLength);
		assertEquals(wantPlayerCount, havePlayerCount);
		for(int types = 0; types < 4; types++) {
			assertEquals(wantPlayerTypes.get(types), havePlayerTypes.get(types));
		}
		for(int index = 0; index < 16; index++) {
			assertEquals(wantPositionOfTocken.get(index), havePositionOfTocken.get(index));
		}
		assertEquals(wantSpacesBetweenPlayerAndBase, haveSpacesBetweenPlayerAndBase);
		assertEquals(wantTokenCountPP, haveTokenCountPP);
	}
	
	
	
	@Test(timeout = 1_000)
	public void initializeTwiceTest() throws FalseIDException, FalseTockenIDException, FalsePlayerTypeException, NegativeBoardLengthException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		sut.initialize(12345, 12345, 12345, Arrays.asList(12345));
		final int wantBoardLength = 40;
		final int wantPlayerCount = 4;
		final List<Integer> wantPlayerTypes = Arrays.asList(1, 2, 3, 1);
		final List<Integer> wantPositionOfTocken = Arrays.asList( -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
		final int wantSpacesBetweenPlayerAndBase = 10;
		final int wantTokenCountPP = 4;
		
		final List<Integer> havePlayerTypes = new ArrayList<>();
		final List<Integer> havePositionOfTocken = new ArrayList<>();
		
		//act
		final int haveBoardLength = data.getBoardLength();
		final int havePlayerCount = data.getPlayerCount();
		for(int index = 0; index < 4; index++) {
			havePlayerTypes.add(data.getPlayerType(index));
		}
		for(int player = 0; player < 4; player++) {
			for(int token = 0; token < 4; token++) {
				havePositionOfTocken.add(data.getPositionOfTocken(player, token));				
			}
		}
		final int haveSpacesBetweenPlayerAndBase = data.getSpacesBetweenPlayerBases();
		final int haveTokenCountPP = data.getTockenCountPP();
		
		//assert
		assertEquals(wantBoardLength, haveBoardLength);
		assertEquals(wantPlayerCount, havePlayerCount);
		for(int types = 0; types < 4; types++) {
			assertEquals(wantPlayerTypes.get(types), havePlayerTypes.get(types));
		}
		for(int index = 0; index < 16; index++) {
			assertEquals(wantPositionOfTocken.get(index), havePositionOfTocken.get(index));
		}
		assertEquals(wantSpacesBetweenPlayerAndBase, haveSpacesBetweenPlayerAndBase);
		assertEquals(wantTokenCountPP, haveTokenCountPP);
	}
	
	
	@Test(timeout = 1_000, expected = IllegalArgumentException.class)
	public void initialiseFalsePlayerTypeBombs() throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException {
		//arrange
		final Logic sut = getSut();
		//act
		sut.initialize(40, 4, 4, Arrays.asList(3, 3, 3, -1));
	}
	
	@Test(timeout = 1_000, expected = IllegalArgumentException.class)
	public void initialiseFalsePlayerType2Bombs() throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException {
		//arrange
		final Logic sut = getSut();
		//act
		sut.initialize(40, 4, 4, Arrays.asList(3, 3, 3, 6));
	}
	
	@Test(timeout = 1_000, expected = IllegalArgumentException.class)
	public void initialiseToLessPlayerTypeBombs() throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException {
		//arrange
		final Logic sut = getSut();
		//act
		sut.initialize(40, 4, 4, Arrays.asList(3, 3, 3));
	}
	
	@Test(timeout = 1_000, expected = IllegalArgumentException.class)
	public void initialiseToMuchPlayerTypeBombs() throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException {
		//arrange
		final Logic sut = getSut();
		//act
		sut.initialize(40, 4, 4, Arrays.asList(3, 3, 3, 3, 3));
	}
	
	@Test(timeout = 1_000, expected = IllegalArgumentException.class)
	public void initialiseBordLenght0Bombs() throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException {
		//arrange
		final Logic sut = getSut();
		//act
		sut.initialize(0, 4, 4, Arrays.asList(3, 3, 3, 3));
	}
	
	
	@Test(timeout = 1_000, expected = IllegalArgumentException.class)
	public void initialiseBordLenghtNotKTimesOfPlayerCountBombs() throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException {
		//arrange
		final Logic sut = getSut();
		//act
		sut.initialize(3, 4, 4, Arrays.asList(3, 3, 3, 3));
	}
	
	@Test(timeout = 1_000, expected = IllegalArgumentException.class)
	public void initialiseZeroTockenBombs() throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException {
		//arrange
		final Logic sut = getSut();
		//act
		sut.initialize(4, 0, 4, Arrays.asList(3, 3, 3, 3));
	}
	
	@Test(timeout = 1_000, expected = IllegalArgumentException.class)
	public void initialiseZeroPlayersBombs() throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException {
		//arrange
		final Logic sut = getSut();
		//act
		sut.initialize(4, 4, 0, new ArrayList<Integer>());
	}
	
	@Test(timeout = 1_000)
	public void moveOutOfBase() throws FalseIDException, FalseTockenIDException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, FalsePlayerTypeException, NegativeBoardLengthException, TriedToMooveToFarException, FalsePositionException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		final List<Integer> wantPositionOfTocken = Arrays.asList( 5, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);

		sut.move(0, 6);
		sut.move(0, 5);
		
		final List<Integer> havePositionOfTocken = new ArrayList<>();
		
		//act
		for(int player = 0; player < 4; player++) {
			for(int token = 0; token < 4; token++) {
				havePositionOfTocken.add(data.getPositionOfTocken(player, token));				
			}
		}
		
		//assert
		for(int index = 0; index < 16; index++) {
			assertEquals(wantPositionOfTocken.get(index), havePositionOfTocken.get(index));
		}
	}
	
	@Test(timeout = 1_000)
	public void moveOutOfBaseAndThrowTocken() throws FalseIDException, FalseTockenIDException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, FalsePlayerTypeException, NegativeBoardLengthException, TriedToMooveToFarException, FalsePositionException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		final List<Integer> wantPositionOfTocken = Arrays.asList( -1, -1, -1, -1, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);

		//first Player
		sut.move(0, 6);
		sut.move(0, 6);
		sut.move(0, 4);
		
		//second Player
		sut.move(0, 6);
		sut.move(0,3);
		
		final List<Integer> havePositionOfTocken = new ArrayList<>();
		
		//act
		for(int player = 0; player < 4; player++) {
			for(int token = 0; token < 4; token++) {
				havePositionOfTocken.add(data.getPositionOfTocken(player, token));				
			}
		}
		
		//assert
		for(int index = 0; index < 16; index++) {
			assertEquals(wantPositionOfTocken.get(index), havePositionOfTocken.get(index));
		}
	}
	
	@Test(timeout = 1_000)
	public void moveAtFieldAndThrowTocken() throws FalseIDException, FalseTockenIDException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, FalsePlayerTypeException, NegativeBoardLengthException, TriedToMooveToFarException, FalsePositionException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		final List<Integer> wantPositionOfTocken = Arrays.asList( 13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);

		//first Player
		sut.move(0, 6);
		sut.move(0, 6);
		sut.move(0, 5);
		// player 0 tocken 0 absolute Position 11
		
		//second Player
		sut.move(0, 6);
		sut.move(0,3);
		// player 1 tocken 0 absolute Position 13
		
		data.setPlayerTurn(0);
		
		//first player
		sut.move(0, 2);
		
		final List<Integer> havePositionOfTocken = new ArrayList<>();
		
		//act
		for(int player = 0; player < 4; player++) {
			for(int token = 0; token < 4; token++) {
				havePositionOfTocken.add(data.getPositionOfTocken(player, token));				
			}
		}
		
		//assert
		for(int index = 0; index < 16; index++) {
			assertEquals(wantPositionOfTocken.get(index), havePositionOfTocken.get(index));
		}
	}
	
	
	@Test(timeout = 1_000)
	public void moveAtFieldWithWrapArroundAndThrowTocken() throws FalseIDException, FalseTockenIDException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, FalsePlayerTypeException, NegativeBoardLengthException, TriedToMooveToFarException, FalsePositionException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		final List<Integer> wantPositionOfTocken = Arrays.asList( -1, -1, -1, -1, 32, 1, 2, 3, -1, -1, -1, -1, -1, -1, -1, -1);

		//first Player
		sut.move(0, 6);
		sut.move(0, 2);
		// player 0 tocken 0 absolute Position 2
		
		//second Player
		sut.move(1, 6);
		sut.move(1, 1);
		data.setPlayerTurn(1);
		sut.move(2, 6);
		sut.move(2, 2);
		data.setPlayerTurn(1);
		sut.move(3, 6);
		sut.move(3, 3);
		data.setPlayerTurn(1);
		for(int counter = 0; counter <= 5; counter++)
			sut.move(0, 6);
		sut.move(0, 2);
		// player 1 tocken 0 absolute Position 42 mod 40 = 2
		final List<Integer> havePositionOfTocken = new ArrayList<>();
		
		//act
		for(int player = 0; player < 4; player++)
			for(int token = 0; token < 4; token++)
				havePositionOfTocken.add(data.getPositionOfTocken(player, token));
		//assert
		for(int index = 0; index < 16; index++)
			assertEquals(wantPositionOfTocken.get(index), havePositionOfTocken.get(index));
	}
	
	@Test(timeout = 1_000)
	public void moveAndPlayerWins() 
			throws FalseIDException, FalseTockenIDException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, FalsePlayerTypeException, NegativeBoardLengthException, TriedToMooveToFarException, FalsePositionException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		final List<Integer> wantPositionOfTocken = Arrays.asList( 43, 42, 41, 40, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);

		//first Player
		data.setPlayerTurn(0);
		sut.move(0, 6);
		sut.move(0, 1);
		data.setPlayerTurn(0);
		sut.move(1, 6);
		sut.move(1, 2);
		data.setPlayerTurn(0);
		sut.move(2, 6);
		sut.move(2, 3);
		data.setPlayerTurn(0);
		sut.move(3, 6);
		sut.move(3, 4);
		data.setPlayerTurn(0);
		for(int counter = 0; counter < 6; counter++) {
			sut.move(3, 6);
			sut.move(2, 6);
			sut.move(1, 6);
			sut.move(0, 6);
		}
		sut.move(2, 2);
		data.setPlayerTurn(0);
		sut.move(1, 4);
		data.setPlayerTurn(0);
		sut.move(0, 6);
		data.setPlayerTurn(0);
		final List<Integer> havePositionOfTocken = new ArrayList<>();
		final boolean wantPlayerWonStatus = true;
		
		//act
		final boolean hasPlayerWon = data.hasPlayerWon(0);
		for(int player = 0; player < 4; player++)
			for(int token = 0; token < 4; token++)
				havePositionOfTocken.add(data.getPositionOfTocken(player, token));
		//assert
		assertEquals(wantPlayerWonStatus, hasPlayerWon);
		for(int index = 0; index < 16; index++) {
			assertEquals(wantPositionOfTocken.get(index), havePositionOfTocken.get(index));
		}
	}
	
	@Test(timeout = 1_000)
	public void moveAndTryToHitYourOwnTocken() throws FalseIDException, FalseTockenIDException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, FalsePlayerTypeException, NegativeBoardLengthException, TriedToMooveToFarException, FalsePositionException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		sut.move(0, 6);
		sut.move(0, 4);
		data.setPlayerTurn(0);
		sut.move(1, 6);
		sut.move(1, 3);
		final List<Integer> wantPositionOfTocken = Arrays.asList( 4, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
		
		final List<Integer> havePositionOfTocken = new ArrayList<>();
		final boolean wantMoved = false;
		
		//act
		data.setPlayerTurn(0);
		final boolean haveMoved = sut.move(1, 1);
		for(int player = 0; player < 4; player++) {
			for(int token = 0; token < 4; token++) {
				havePositionOfTocken.add(data.getPositionOfTocken(player, token));				
			}
		}
		//assert
		assertEquals(wantMoved, haveMoved);
		for(int index = 0; index < 16; index++) {
			assertEquals(wantPositionOfTocken.get(index), havePositionOfTocken.get(index));
		}
	}
	
	
	@Test(timeout = 1_000)
	public void moveTryToMoveTockenWhileOneIsAtPosition0() throws FalseIDException, FalseTockenIDException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, FalsePlayerTypeException, NegativeBoardLengthException, TriedToMooveToFarException, FalsePositionException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		sut.move(0, 6);
		sut.move(0, 4);
		data.setPlayerTurn(0);
		sut.move(1, 6);
		final List<Integer> wantPositionOfTocken = Arrays.asList( 4, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
		
		final List<Integer> havePositionOfTocken = new ArrayList<>();
		final boolean wantMoved = false;
		
		//act
		data.setPlayerTurn(0);
		final boolean haveMoved = sut.move(0, 1);
		for(int player = 0; player < 4; player++)
			for(int token = 0; token < 4; token++)
				havePositionOfTocken.add(data.getPositionOfTocken(player, token));
		//assert
		assertEquals(wantMoved, haveMoved);
		for(int index = 0; index < 16; index++) {
			assertEquals(wantPositionOfTocken.get(index), havePositionOfTocken.get(index));
		}
	}
	
	
	@Test(timeout = 1_000)
	public void moveFalseTockenWithDiceValue6AndTockenInBase() throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, TriedToMooveToFarException, FalsePositionException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		sut.move(0, 6);
		sut.move(0, 4);
		data.setPlayerTurn(0);
		final List<Integer> wantPositionOfTocken = Arrays.asList( 4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
			
		final List<Integer> havePositionOfTocken = new ArrayList<>();
		final boolean wantMoved = false;
		
		//act
		data.setPlayerTurn(0);
		final boolean haveMoved = sut.move(0, 6);
		for(int player = 0; player < 4; player++)
			for(int token = 0; token < 4; token++)
				havePositionOfTocken.add(data.getPositionOfTocken(player, token));
		//assert
		assertEquals(wantMoved, haveMoved);
		for(int index = 0; index < 16; index++)
			assertEquals(wantPositionOfTocken.get(index), havePositionOfTocken.get(index));
	}
	
	@Test(timeout = 1_000, expected = FalseDiceValueException.class)
	public void moveWithDiecValue0Bombs() throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, TriedToMooveToFarException, FalsePositionException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		//act
		sut.move(0, 0);
		
	}
	
	@Test(timeout = 1_000, expected = FalseDiceValueException.class)
	public void moveWithDiecValue7Bombs() throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, TriedToMooveToFarException, FalsePositionException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		//act
		sut.move(0, 7);
		
	}
	
	@Test(timeout = 1_000, expected = TriedToMooveToFarException.class)
	public void moveToFarBombs() throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, TriedToMooveToFarException, FalsePositionException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		sut.move(1, 6);
		sut.move(1, 2);
		data.setPlayerTurn(0);
		sut.move(2, 6);
		sut.move(2, 3);
		data.setPlayerTurn(0);
		sut.move(3, 6);
		sut.move(3, 4);
		data.setPlayerTurn(0);
		for(int counter = 0; counter <= 7; counter++)
			sut.move(0, 6);
		//assert
		sut.move(0, 5);
	}
	
	
	@Test(timeout = 1_000, expected = PlayerAlereadyWonException.class)
	public void moveTryToMoveIfPlayerHaseAlereadyWonBombs() throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, TriedToMooveToFarException, FalsePositionException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		//first Player
		data.setPlayerTurn(0);
		sut.move(0, 6);
		sut.move(0, 1);
		data.setPlayerTurn(0);
		sut.move(1, 6);
		sut.move(1, 2);
		data.setPlayerTurn(0);
		sut.move(2, 6);
		sut.move(2, 3);
		data.setPlayerTurn(0);
		sut.move(3, 6);
		sut.move(3, 4);
		data.setPlayerTurn(0);
		for(int counter = 0; counter < 6; counter++) {
			sut.move(3, 6);
			sut.move(2, 6);
			sut.move(1, 6);
			sut.move(0, 6);
		}
		sut.move(2, 2);
		data.setPlayerTurn(0);
		sut.move(1, 4);
		data.setPlayerTurn(0);
		sut.move(0, 6);
		data.setPlayerTurn(0);
		//act
		sut.move(3, 1);
	}
	
	@Test(timeout = 1_000)
	public void moveOutOfBaseWithFalseValue() throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, TriedToMooveToFarException, FalsePositionException {
		//arrange
		final Logic sut = getSut();
		sut.initialize(40, 4, 4, Arrays.asList(1, 1, 1, 1));
		final boolean want = false;
		//act
		final boolean have = sut.move(0, 3);
		//assert
		assertEquals(want, have);
	}
	
	
	
	@Test(timeout = 1_000)
	public void throwDiceBetweenOneAndSixTest() {
		//arrange
		final Logic sut = getSut();
		final int upperBound = 6;
		final int lowerBound = 1;
		final boolean want = true;
		final List<Integer> list = new LinkedList<>();
		boolean have = true;
		//act
		for(int counter = 0; counter < 1_000; counter++)
			list.add(sut.throwDice());
		for(int index = 0; index < list.size(); index++)
			if(list.get(index)< lowerBound || list.get(index)> upperBound)
				have = false;
		//assert
		assertEquals(want, have);
	}
	
	@Test(timeout = 1_000)
	public void throwDiceStatisticTest() {
		final Logic sut = getSut();
		final double expected = 1./6;
		final double error = 0.1;
		final double upperBound = expected + error;
		final double lowerBound = expected - error;
		//arrange
		for(int i = 0; i < 10; i++ ) {
			int countOne = 0;
			int countTwo = 0;
			int countThree = 0;
			int countFour = 0;
			int countFive = 0;
			int countSix = 0;
			final List<Integer> list = new LinkedList<>();
			final boolean want = true;
			//act
			for(int counter = 0; counter < 1_000; counter++)
				list.add(sut.throwDice());
			
			for(int index = 0; index < list.size(); index++) {
				if(list.get(index) == 1)
					countOne++;
				else if(list.get(index) == 2)
					countTwo++;
				else if(list.get(index) == 3)
					countThree++;
				else if(list.get(index) == 4)
					countFour++;
				else if(list.get(index) == 5)
					countFive++;
				else if(list.get(index) == 6)
					countSix++;
			}
			final int size = list.size();
			final double valueOne = ((double)countOne)/size;
			final double valueTwo = ((double)countTwo)/size;
			final double valueThree = ((double)countThree)/size;
			final double valueFour = ((double)countFour)/size;
			final double valueFive = ((double)countFive)/size;
			final double valueSix = ((double)countSix)/size;
			//assert
			final boolean haveOne = valueOne >= lowerBound  && valueOne <= upperBound;
			final boolean haveTwo = valueTwo >= lowerBound  && valueTwo <= upperBound;
			final boolean haveThree = valueThree >= lowerBound  && valueThree <= upperBound;
			final boolean haveFour = valueFour >= lowerBound  && valueFour <= upperBound;
			final boolean haveFive = valueFive >= lowerBound  && valueFive <= upperBound;
			final boolean haveSix = valueSix >= lowerBound  && valueSix <= upperBound;
			assertEquals(want, haveOne);
			assertEquals(want, haveTwo);
			assertEquals(want, haveThree);
			assertEquals(want, haveFour);
			assertEquals(want, haveFive);
			assertEquals(want, haveSix);
		}
		
	}

}
