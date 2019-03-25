package Model.Data;
import java.util.ArrayList;

import Model.Data.Player.Player;

public class SingeltonData implements rwData {
	private final static SingeltonData singeltonData = new SingeltonData();
	
	private final ArrayList<Player> players = new ArrayList<>();
	
	private SingeltonData() {
	}
	
	public static SingeltonData getInstanceOfSingletonData() {
		return singeltonData;
	}
	
	
	//player Stuff
	
	
	/**
	 * Standard getter
	 * @return
	 */
	private ArrayList<Player> getPlayers(){
		return players;
	}
	
	/**
	 * adds a new Player to the list with the current size as an ID. 
	 * The size starts with zero! 
	 * @param type it is given by the Controller(1-> Human, 2-> AI, 3-> network)
	 */
	public void addPlayer(int type) {
		getPlayers().add(Player.make(getPlayers().size(), type)); 
	}
	
	/**
	 * 
	 * @param PlayerID it equals to the position in the ArrayList(Player)
	 */
	public void setPlayerHasWon(int PlayerID) {
		getPlayers().get(PlayerID).setPlayerHasWon();
	}
	
	/**
	 * 
	 * @param PlayerID it equals to the position in the ArrayList(Player)
	 * @return true if the player has won
	 */
	public boolean hasPlayerWon(int PlayerID) {
		return getPlayers().get(PlayerID).hasPlayerWon();
	}
	
	
	/**
	 * 
	 * @return the total count of Players
	 */
	public int getPlayerCount() {
		return getPlayers().size();
	}
	
	
	
	

	@Override
	public void setBoardLength(int length) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBoardLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPlayerTurn(int PlayerID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPalyerTurnID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTockenCoutPP(int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTockenCountPP() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPositionOfTocken(int tokenID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPositionOfTocken(int tokenID, int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTockenIdAtPosition(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPlayerType(int PlayerID) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
