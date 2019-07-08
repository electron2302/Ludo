package model.data;

import model.data.exceptions.FalseIDException;
import model.data.exceptions.FalseTockenIDException;

/**
 * The Interface represents an view at the data.
 * The view has a read only permission.
 *
 * @author Andreas Hager, ahager@hm.edu
 * @author Simon Arndt, technikon23@gmail.com
 * @version Initial version
 */
public interface ROData {
	
	/**
	 * Getter for a read only view at the data.
	 * @return an instance of the RO methods of the data storage.
	 */
	static ROData getInstanceOfROData(int gameNumber) {
		return RWData.getInstanceOfRWData(gameNumber);
	}
	
	/**
	 * Getter for the ID of the player who is about to play.
	 * @return the ID of the player.
	 */
	int getPlayerTurnID(); 
	
	/**
	 * Getter for the board length.
	 * @return the BoardLength.
	 */
	int getBoardLength();

	/**
	 * Getter for the space betwen two bases.
	 * @return the space betwen two bases.
	 */
	int getSpacesBetweenPlayerBases();
	
	/**
	 * Getter for the count of tokens, of each player.
	 * Every player has the same count of tokens.
	 * @return the count of tokens of each player.
	 */
	int getTockenCountPP();

	/**
	 * Getter for the relative position of the token from the player.
	 * Every player start by his own base with zero.
	 * @param playerID .
	 * @param tokenID .
	 * @return the absolute position of the tocken on the board.
	 * @throws FalseIDException if the ID is < 0.
	 * @throws FalseTockenIDException if the ID is < 0.
	 */
	int getPositionOfTocken(int playerID, int tokenID) throws FalseIDException, FalseTockenIDException;
	
	/**
	 * Has the player with the ID playerID won.
	 * @param playerID the ID of the player.
	 * @return true if the player has won.
	 * @throws FalseIDException
	 */
	boolean hasPlayerWon(int playerID) throws FalseIDException;
	
	/**
	 * Getter for the type fo the player.
	 * @param PlayerID
	 * @return 1 for human, 2 for AI, 3 for Network.  
	 * @throws FalseIDException if the ID is < 0.
	 */
	int getPlayerType(int PlayerID) throws FalseIDException;
	
	/**
	 * Getter.
	 * @return total count of players.
	 */
	int getPlayerCount();
}
