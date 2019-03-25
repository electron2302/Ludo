package Model.Data.Player;
import Model.Data.Player.PlayerDelgate;


public interface Player {
	
	void setPlayerHasWon();
	
	boolean hasPlayerWon();
	
	int getPlayerType();
	
	int getPlayerID();
	
	static Player make(int playerID, int playerType) {
		return new PlayerDelgate(playerID, playerType) {};
	}
}
