package Model.Data.player;
/**
 * A Player Delegate
 * which represents the data memory for 
 * an Player of this Ludo game.
 *
 * @author Andreas Hager, andreashager19@gmail.com
 * @version Initial version
 */
abstract class PlayerDelgate implements Player {
	private final int playerID;
	private final int playerType;
	private boolean won = false;
	
	PlayerDelgate(int playerID, int playerType) {
		this.playerID = playerID;
		this.playerType = playerType;
	}
	
	@Override
	public int getPlayerID() {
		return playerID;
	}
	
	@Override
	public int getPlayerType() {
		return playerType;
	}
	
	@Override
	public boolean hasPlayerWon() {
		return won;
	}
	
	@Override
	public void setPlayerHasWon() {
		won = true;
	}
	
}
