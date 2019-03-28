package Model.Data.player;
import Model.Data.player.PlayerDelegate;

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
	
	static Player make(int playerID, int playerType) {
		return new PlayerDelegate(playerID, playerType) {};
	}

	
}
