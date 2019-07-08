package model.data;

import model.data.exceptions.FalseIDException;
import model.data.exceptions.FalsePlayerTypeException;
import model.data.exceptions.FalsePositionException;
import model.data.exceptions.FalseTockenIDException;
import model.data.exceptions.NegativeBoardLengthException;

/**
 * Represents a view at the data storage with an read and write permission.
 *
 * @author Andreas Hager, andreashager19@gmail.com
 * @author Simon Arndt, technikon23@gmail.com
 * @version Initial version
 */
public interface RWData extends ROData {
	
	
	/**
	 * Getter for an instance of an view at the data storage.
	 * @return an instance of the view.
	 */
	static RWData getInstanceOfRWData(int gameNumber) {
		return Data.getInstanceOfSingletonData(gameNumber);
	}
	
	/**
	 * Setter for the board length.
	 * @param length sould be whole multiply of the playerCount.
	 * @throws NegativeBoardLengthException if the length is < 0
	 */
	void setBoardLength(int length) throws NegativeBoardLengthException;

	/**
	 * Setter for the player turn.
	 * @param playerID player who is about to play.
	 */
	void setPlayerTurn(int playerID);
	
	/**
	 * Setter for the position of the specified token.
	 * @param playerID .
	 * @param tokenID .
	 * @param position new position.
	 * @throws FalseIDException if the playerID is < 0.
	 * @throws FalseTockenIDException if the TokenID is < 0.
	 * @throws FalsePositionException if the position is < 0.
	 */
	void setPositionOfTocken(int playerID, int tokenID, int position) throws FalseIDException, FalseTockenIDException, FalsePositionException;
	
	/**
	 * Adds an new token to an Player.
	 * @param playerID .
	 * @param tokenID .
	 * @throws FalseIDException if the playerID is < 0.
	 * @throws FalseTockenIDException if the tokenID is < 0.
	 */
	void addTocken(int playerID, int tokenID) throws FalseIDException, FalseTockenIDException;
	
	/**
	 * Setter.
	 * @param PlayerID the player wich has won.
	 * @throws FalseIDException it the playerID is < 0;
	 */
	void setPlayerHasWon(int PlayerID) throws FalseIDException;

	/**
	 * Adds an new player to the game.
	 * @param type (1-> Human, 2-> AI, 3-> network)
	 * @return ID of new Player
	 * @throws FalsePlayerTypeException it the type is < 1.
	 */
	int addPlayer(int type) throws FalsePlayerTypeException;
	
	
	
	/**
	 * Sets all variable to it´s initial state.
	 * Method is only used for tests.
	 */
	void reset();
	

}
