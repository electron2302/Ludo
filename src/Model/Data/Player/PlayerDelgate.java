package Model.Data.Player;

public abstract class PlayerDelgate implements Player {
	
	private int playerID;
	private int playerType;
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
