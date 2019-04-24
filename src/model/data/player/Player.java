package model.data.player;
import model.data.exceptions.FalseIDException;
import model.data.exceptions.FalsePlayerTypeException;
import model.data.player.PlayerDelegate;

/**
 * A Player Inteface
 * 
 * @author Andreas Hager, andreashager19@gmail.com
 * @version Initial version
 */
public interface Player {
	
	void setPlayerHasWon();
	
	boolean hasPlayerWon();
	
	int getPlayerType();
	
	int getPlayerID();
	
	static Player make(int playerID, int playerType) throws FalseIDException, FalsePlayerTypeException {
		return new PlayerDelegate(playerID, playerType) {};
	}

	
}
