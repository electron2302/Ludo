package Model.Data;
import java.util.ArrayList;

import Model.Data.Player.Player;
import Model.Data.Tocken.Tocken;

import java.util.HashMap;
import java.util.Map;

/**
 * A SingltonData:
 *
 * @author Andreas Hager, andreashager19@gmail.com
 * @author Simon Arndt, technikon23@gmail.com
 * @version Initial version
 */
public class SingeltonData implements rwData {
	private final static SingeltonData singeltonData = new SingeltonData();

	private final ArrayList<Player> players = new ArrayList<>();

	private final Map<String,Tocken> tokens = new HashMap<>();

	private int playerTurn = -1;

	private int boardLength = -1;

	private int tockenCoutPP = -1;
	
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
	public int getPlayerType(int PlayerID) {
		return getPlayers().get(PlayerID).getPlayerType();
	}
	
	@Override
	public void setPlayerTurn(int player) {
		playerTurn = player;
		
	}
	
	@Override
	public int getPlayerTurnID() {
		return playerTurn;
	}
	

	@Override
	public void setBoardLength(int length) {
		boardLength = length;
	}

	@Override
	public int getBoardLength() {
		return boardLength;
	}

	@Override
	public void setTockenCoutPP(int count) {
		tockenCoutPP = count;
	}

	@Override
	public int getTockenCountPP() {
		return tockenCoutPP;
	}

	@Override
	public void addTocken(int tokenID){
		tokens.put(Integer.toString(tokenID),Tocken.make());
	}

	@Override
	public int getPositionOfTocken(int tokenID) {
		return tokens.get(Integer.toString(tokenID)).getPosition();
	}

	@Override
	public void setPositionOfTocken(int tokenID, int position) {
		tokens.get(Integer.toString(tokenID)).setPosition(position);
	}
}
