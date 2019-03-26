package Model.Data;

/**
 * A rwData Interface:
 *
 * @author Andreas Hager, andreashager19@gmail.com
 * @author Simon Arndt, technikon23@gmail.com
 * @version Initial version
 */
public interface rwData extends roData {
	
	// general stuff
	
	static rwData getInstanceOfRWData() {
		return SingeltonData.getInstanceOfSingletonData();
	}
	
	void setBoardLength(int length); // save in singleton
	//int getBoardLength();
	
	void setPlayerTurn(int player);// save in singleton
	//int getPalyerTurnID();
	
	/**
	 * how many individual tockens each player gets
	 * @param count amount of tokens per player
	 */
	void setTockenCoutPP(int count);//creates all Tockens and saves in LIST
	// int getTockenCountPP();//list.length
	
	// token stuff
	
	// int getPositionOfTocken(int tokenID);
	void setPositionOfTocken(int tokenID, int position);
	void addTocken(int tokenID);
	
	//Player stuff
	
	void setPlayerHasWon(int PlayerID);// sets to true since initialized with false
	// boolean hasPlayerWon(int PlayerID);
	
	/**
	 * 
	 * @param type (1-> Human, 2-> AI, 3-> network)
	 */
	void addPlayer(int type); // to a Player LIST of paler Objects
	// int getPlayerType(int PlayerID);
	// int getPlayerCount();

}
