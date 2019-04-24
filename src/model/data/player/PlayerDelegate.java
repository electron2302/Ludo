package model.data.player;

import model.data.exceptions.FalseIDException;
import model.data.exceptions.FalsePlayerTypeException;

/**
 * A Player Delegate
 * which represents the data memory for 
 * an Player of this Ludo game.
 *
 * @author Andreas Hager, andreashager19@gmail.com
 * @version Initial version
 */
abstract class PlayerDelegate implements Player {
	private final int playerID;
	private final int playerType;
	private boolean won = false;
	
	PlayerDelegate(int playerID, int playerType) throws FalseIDException, FalsePlayerTypeException {
		if(playerID < 0)
			throw new FalseIDException();
		if(playerType < 0)
			throw new FalsePlayerTypeException();
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
