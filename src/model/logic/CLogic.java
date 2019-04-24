package model.logic;

import model.data.RWData;
import model.data.exceptions.FalseIDException;
import model.data.exceptions.FalsePlayerTypeException;
import model.data.exceptions.FalsePositionException;
import model.data.exceptions.FalseTockenIDException;
import model.data.exceptions.NegativeBoardLengthException;
import model.logic.exceptions.FalseDiceValueException;
import model.logic.exceptions.IllegalMoveException;
import model.logic.exceptions.PlayerAlereadyWonException;
import model.logic.exceptions.TriedToMooveToFarException;

/**
 * Implements the logic of the Ludo game.
 * 
 * @author A. Hager, ahger@hm.edu
 *
 */
public class CLogic implements Logic {

	/**
	 * Connection to the data storage with read an write permission.
	 */
	private final RWData rwData = RWData.getInstanceOfRWData();
	
	/**Initialize the data storage.
	 * It sets the board length, the players and the tokens of each player. 
	 * @param boardLength the wanted board length.
	 * @param playerCount the wanted count of players.
	 * @param tockenCount the wanted count of tokens per player.
	 */
	@Override
	public void initialize(int boardLength, int tokenCount, int playerCount, int... types) {
		checkArguments(boardLength, tokenCount, playerCount, types);
		try {
			for(int counter = 0; counter < playerCount; counter++) {
				rwData.addPlayer(types[counter]);
				for(int tokenCounter = 0; tokenCounter < tokenCount; tokenCounter++) {
					rwData.addTocken(counter, tokenCounter);
				}
			}
			rwData.setBoardLength(boardLength);
		} catch (FalseIDException | FalseTockenIDException | FalsePlayerTypeException | NegativeBoardLengthException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	private void checkArguments(int boardLength, int tokenCount, int playerCount, int... types) {
		if(playerCount <= 0 || tokenCount <= 0 || playerCount != types.length || boardLength%playerCount != 0)
			throw new IllegalArgumentException();
		for(int index = 0; index < types.length; index++)
			if(types[index] <= 0 || types[index] > 3)
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
	 * @throws IllegalMoveException if you tried to throw your own token.
	 */
	@Override
	public boolean move(int tokenID, int diceValue) throws PlayerAlereadyWonException, FalseDiceValueException  {
		if(diceValue < 1 || diceValue > 6)
			throw new FalseDiceValueException();
		final int playerTurn = rwData.getPlayerTurnID();
		boolean moved = true;
		try {
			if(checks(tokenID, diceValue, playerTurn)) {
				if(rwData.getPositionOfTocken(playerTurn, tokenID) == -1) {
					throwTocken(0 + rwData.getSpacesBetweenPlayerBases()*playerTurn);
					rwData.setPositionOfTocken(playerTurn, tokenID, 0);
				}else {
					final int relativeTargetPosition = rwData.getPositionOfTocken(playerTurn, tokenID) + diceValue;
					throwTocken(relativeTargetPosition + rwData.getSpacesBetweenPlayerBases()*playerTurn);
					rwData.setPositionOfTocken(playerTurn, tokenID, relativeTargetPosition);
				}
				if(diceValue != 6)
					rwData.setPlayerTurn(playerTurn + 1);
				if(updataPlayerHasWonStatus(playerTurn))
					rwData.setPlayerHasWon(playerTurn);
			}else {
				rwData.setPlayerTurn(playerTurn + 1);
				moved = false;
			}
		}catch( FalseIDException | 
				FalsePositionException | 
				FalseTockenIDException |
				TriedToMooveToFarException |
				IllegalMoveException e) {
			e.printStackTrace();
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
	 * @throws IllegalMoveException
	 */
	private boolean checks(int tokenID, int diceValue, int playerTurn) throws FalseIDException,
			FalseTockenIDException, TriedToMooveToFarException, PlayerAlereadyWonException, IllegalMoveException {
		boolean isAlowed = true;
		//check move distance
		if(rwData.getPositionOfTocken(playerTurn, tokenID) + diceValue >= rwData.getBoardLength() + rwData.getTockenCountPP())
			throw new TriedToMooveToFarException();
		//check if player has already won, throws uncatched  Exception.  
		if(rwData.hasPlayerWon(playerTurn))
			throw new PlayerAlereadyWonException();
		//check if you try to hit your own token.
		for(int tokenIDOfPlayer = 0; tokenIDOfPlayer < rwData.getTockenCountPP(); tokenIDOfPlayer++) {
			final int positionOfNotMovedToken = rwData.getPositionOfTocken(playerTurn, tokenIDOfPlayer);
			final int positionOfToken = rwData.getPositionOfTocken(playerTurn, tokenID);
			if(positionOfNotMovedToken == positionOfToken && tokenIDOfPlayer != tokenID && positionOfToken != -1)
				throw new IllegalMoveException();
			if(positionOfNotMovedToken == 0 && positionOfToken != 0)
				throw new IllegalMoveException();
		}
		if(rwData.getPositionOfTocken(playerTurn, tokenID) == -1 && diceValue != 6)
			isAlowed = false;
		return isAlowed;
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
		boolean hasPlayerWon = true;
		for(int tockenID = 0; tockenID < rwData.getTockenCountPP(); tockenID++)
			if(rwData.getPositionOfTocken(playerTurn, tockenID) <rwData.getBoardLength())
				hasPlayerWon = false;
		return hasPlayerWon;
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
		for(int playerIndex = 0; playerIndex < rwData.getPlayerCount(); playerIndex++)
			for(int tockenIndex = 0; tockenIndex < rwData.getTockenCountPP(); tockenIndex++)
				if(rwData.getPositionOfTocken(playerIndex, tockenIndex) + rwData.getSpacesBetweenPlayerBases()* playerIndex == targetPosition)
					rwData.setPositionOfTocken(playerIndex, tockenIndex, -1);
	}
	
}
