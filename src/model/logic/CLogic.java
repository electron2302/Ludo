 package model.logic;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import model.data.RWData;
import model.data.exceptions.FalseIDException;
import model.data.exceptions.FalsePositionException;
import model.data.exceptions.FalseTockenIDException;
import model.logic.exceptions.FalseDiceValueException;
import model.logic.exceptions.PlayerAlereadyWonException;
import model.logic.exceptions.TriedToMooveToFarException;

/**
 * Implements the logic of the Ludo game.
 * 
 * @author A. Hager, ahger@hm.edu
 *
 */
public class CLogic implements Logic {

	private static final Logic logic = new CLogic();
	
	/**
	 * Connection to the data storage with read an write permission.
	 */
	private final RWData rwData = RWData.getInstanceOfRWData();
	
	private CLogic() {
		
	}
	
	public static Logic getInstanceOfLogic() {
		return logic;
	}
	
	/**Initialize the data storage.
	 * It sets the board length, the players and the tokens of each player. 
	 * @param boardLength the wanted board length.
	 * @param playerCount the wanted count of players.
	 * @param tockenCount the wanted count of tokens per player.
	 * @throws IllegalArgumentException if boardLength <= 0 || tokenCount <= 0
	 * 			|| playerCount <= 0 || types.length != playerCount || boardLenght%playerCount != 0
	 * 			|| one type < 0
	 */
	@Override
	public synchronized void initialize(int boardLength, int tockenCount, int playerCount, List<Integer> types) {
		if(!isInitialised()) {
			checkArguments(boardLength, tockenCount, playerCount, types);
			IntStream.iterate(0, counter -> counter+1)
				.limit(playerCount)
				.peek(counter -> rwData.addPlayer(types.get(counter)))
				.forEach(counter -> Stream.iterate(0, tockenCounter -> tockenCounter +1)
					.limit(tockenCount)
					.forEach(tockenCounter -> rwData.addTocken(counter, tockenCounter))
				);
			rwData.setBoardLength(boardLength);
			rwData.setPlayerTurn(0);
		}
	}

	private boolean isInitialised() {
		boolean isInitialized = true;
		if(rwData.getBoardLength() == -1)
			isInitialized = false;
		if(rwData.getPlayerCount() == 0)
			isInitialized = false;
		if(rwData.getPlayerTurnID() == -1)
			isInitialized = false;
		return isInitialized;
	}

	private void checkArguments(int boardLength, int tokenCount, int playerCount, List<Integer> types) {
		if(playerCount <= 0 || tokenCount <= 0 || boardLength <= 0 ||playerCount != types.size() || boardLength%playerCount != 0)
			throw new IllegalArgumentException();
		if(IntStream.iterate(0,  index -> index+1)
				.takeWhile(index -> index < types.size())
				.anyMatch(index -> types.get(index) <= 0 || types.get(index) > 3))
			throw new IllegalArgumentException();
	}
	
	/**
	 * Throws a dice.
	 * @return a random Integer between 1 and 6.
	 */
	@Override
	public Integer throwDice() {
		return (int) ((Math.random()*5897)%6)+1;
	}
	
	/**
	 * Moves and selected token by the input diceValue.
	 * @param tokenID the token which should be moved.
	 * @param diceValue the value from the dice between 1 and 6.
	 * @return boolean, true if the move was successful.
	 * @throws PlayerAlereadyWonException if the selected player has already won.
	 * @throws TriedToMooveToFarException if the position of the token + the diceValue exceeds the board length + the token count.
	 * @throws FalseDiceValueException  it the dice value is smaller than 0 or bigger than 6.
	 * @throws FalsePositionException 
	 * @throws FalseTockenIDException 
	 * @throws FalseIDException 
	 */
	@Override
	public synchronized boolean move(int tokenID, int diceValue) throws PlayerAlereadyWonException, FalseDiceValueException, TriedToMooveToFarException, FalseIDException, FalseTockenIDException, FalsePositionException  {
		if(diceValue < 1 || diceValue > 6)
			throw new FalseDiceValueException();
		final int playerTurn = rwData.getPlayerTurnID();
		boolean moved = true;
		if(checks(tokenID, diceValue, playerTurn)) {
			if(rwData.getPositionOfTocken(playerTurn, tokenID) == -1) {
				throwTocken((0 + rwData.getSpacesBetweenPlayerBases()*playerTurn)%rwData.getBoardLength());
				rwData.setPositionOfTocken(playerTurn, tokenID, 0);
			}else {
				final int relativeTargetPosition = rwData.getPositionOfTocken(playerTurn, tokenID) + diceValue;
				throwTocken((relativeTargetPosition + rwData.getSpacesBetweenPlayerBases()*playerTurn)%rwData.getBoardLength());
				rwData.setPositionOfTocken(playerTurn, tokenID, relativeTargetPosition);
			}
			if(diceValue != 6)
				rwData.setPlayerTurn(playerTurn + 1);
			if(updataPlayerHasWonStatus(playerTurn))
				rwData.setPlayerHasWon(playerTurn);
		}else {
			moved = false;
		}
		return moved;
	}

	/**
	 * Checks everything.
	 * Uses relative positions.
	 * @param tokenID
	 * @param diceValue
	 * @param playerTurn
	 * @throws FalseIDException
	 * @throws FalseTockenIDException
	 * @throws TriedToMooveToFarException
	 * @throws PlayerAlereadyWonException
	 */
	private boolean checks(int tokenID, int diceValue, int playerTurn) throws FalseIDException,
			FalseTockenIDException, TriedToMooveToFarException, PlayerAlereadyWonException {
		// boolean isAlowed = true;
		// check move distance
		if(rwData.getPositionOfTocken(playerTurn, tokenID) + diceValue >= rwData.getBoardLength() + rwData.getTockenCountPP())
			throw new TriedToMooveToFarException();
		// check if player has already won, throws uncatched  Exception.  
		if(rwData.hasPlayerWon(playerTurn))
			throw new PlayerAlereadyWonException();
		
		return IntStream.iterate(0, variableTockenID -> variableTockenID + 1)
					.limit(rwData.getTockenCountPP())
					.noneMatch(variableTockenID -> 
						(rwData.getPositionOfTocken(playerTurn, tokenID) == -1 && diceValue != 6 ||
							rwData.getPositionOfTocken(playerTurn, variableTockenID) == 
									rwData.getPositionOfTocken(playerTurn, tokenID) + diceValue && 
								variableTockenID != tokenID && 
								rwData.getPositionOfTocken(playerTurn, tokenID) != -1) ||
							(rwData.getPositionOfTocken(playerTurn, variableTockenID) == 0 && 
								rwData.getPositionOfTocken(playerTurn, tokenID) != 0) ||
							(diceValue == 6 && 
								rwData.getPositionOfTocken(playerTurn, tokenID) > 0 && 
								rwData.getPositionOfTocken(playerTurn, variableTockenID) == -1)
					);
	}
	
	

	/**
	 * Checks if the player has won.
	 * Uses relative positions.
	 * @param playerTurn
	 * @return true if the player has won.
	 * @throws FalseIDException
	 * @throws FalseTockenIDException
	 */
	private boolean updataPlayerHasWonStatus(int playerTurn) throws FalseIDException, FalseTockenIDException {
		return Stream.iterate(0, tockenID -> tockenID +1)
				.limit(rwData.getTockenCountPP())
				.noneMatch(tockenID -> rwData.getPositionOfTocken(playerTurn, tockenID) <rwData.getBoardLength());
	}

	/**
	 * Throws the token at the target position, if there is no token there will happen nothing.
	 * Uses absolute positions.
	 * @param targetPosition the position, where the token should be thrown.
	 * @throws FalsePositionException
	 * @throws FalseIDException
	 * @throws FalseTockenIDException
	 */
	private void throwTocken(int targetPosition) throws FalsePositionException, FalseIDException, FalseTockenIDException {
		Stream.iterate(0, playerIndex -> playerIndex+1)
			.limit(rwData.getPlayerCount())
			.forEach(playerIndex -> Stream.iterate(0, tockenIndex -> tockenIndex+1)
									.limit(rwData.getTockenCountPP())
									.filter(tockenIndex -> rwData.getPositionOfTocken(playerIndex, tockenIndex) < rwData.getBoardLength() && 
											(rwData.getPositionOfTocken(playerIndex, tockenIndex) + 
												rwData.getSpacesBetweenPlayerBases()*playerIndex)%rwData.getBoardLength() == targetPosition)
									.forEach(tockenIndex -> rwData.setPositionOfTocken(playerIndex, tockenIndex, -1) )
			);
	}
	
	
}
