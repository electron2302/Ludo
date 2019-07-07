package model.logic;

import java.util.List;

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
 * 
 * @author A. Hager, ahager@hm.edu
 *
 */
public interface Logic {

	/**Initialize the data storage.
	 * It sets the board length, the players and the tokens of each player. 
	 * @param boardLength the wanted board length.
	 * @param playerCount the wanted count of players.
	 * @param tockenCount the wanted count of tokens per player.
	 * @throws IllegalArgumentException if boardLength <= 0 || tokenCount <= 0
	 * 			|| playerCount <= 0 || types.length != playerCount
	 */
	void initialize(int boardLength, int tokenCount, int playerCount, List<Integer> types) throws FalsePlayerTypeException, FalseIDException, FalseTockenIDException, NegativeBoardLengthException;

	/**
	 * Throws a dice.
	 * @return a random Integer between 1 and 6.
	 */
	Integer throwDice();

	/**
	 * Moves and selected token by the input diceValue.
	 * @param tokenID the token which should be moved.
	 * @param diceValue the value from the dice between 1 and 6.
	 * @return boolean, true if the move was successful.
	 * @throws PlayerAlereadyWonException if the selected player has already won.
	 * @throws TriedToMooveToFarException if the position of the token + the diceValue exceeds the board length + the token count.
	 * @throws FalseDiceValueException  it the dice value is smaller than 0 or bigger than 6.
	 * @throws IllegalMoveException if you tried to throw your own token.
	 * @throws FalsePositionException 
	 * @throws FalseTockenIDException 
	 * @throws FalseIDException 
	 */
	boolean move(int tokenID, int diceValue) throws PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, TriedToMooveToFarException, FalseIDException, FalseTockenIDException, FalsePositionException;
	
	
}
