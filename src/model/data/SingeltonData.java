package model.data;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.data.exceptions.FalseIDException;
import model.data.exceptions.FalsePlayerTypeException;
import model.data.exceptions.FalsePositionException;
import model.data.exceptions.FalseTockenIDException;
import model.data.exceptions.NegativeBoardLengthException;
import model.data.player.Player;
import model.data.tocken.Tocken;
import model.data.tocken.TockenID;

/**
 * A SingltonData:
 *
 * @author Andreas Hager, andreashager19@gmail.com
 * @author Simon Arndt, technikon23@gmail.com
 * @version Initial version
 */
public class SingeltonData implements RWData {
	
	/**
	 * For the singleton Pattern.
	 */
	private final static SingeltonData singeltonData = new SingeltonData();

	private final List<Player> players;
	private final Map<TockenID, Tocken> tockens;

	private int playerTurn;

	private int boardLength;
	private int spacesBetweenPlayerBases;
	
	/**
	 * For the singleton Pattern.
	 */
	private SingeltonData() {
		players = new ArrayList<>();
		tockens = new HashMap<>();
		playerTurn = -1;
		boardLength = -1;
		spacesBetweenPlayerBases = -1;
	}
	
	/**
	 * For the singleton Pattern.
	 * @return the Instance of an singleton.
	 */
	public static SingeltonData getInstanceOfSingletonData() {
		return singeltonData;
	}
	
	/**
	 * Standard getter.
	 * @return  List of players.
	 */
	private List<Player> getPlayers(){
		return players;
	}
	
	/**
	 * Adds an new player to the game.
	 * @param type (1-> Human, 2-> AI, 3-> network)
	 * @return ID of new Player
	 * @throws FalsePlayerTypeException it the type is < 1.
	 */
	public int addPlayer(int type) throws FalsePlayerTypeException {
		final int newID = getPlayers().size();	// the ID starts with 0.
		try {
			getPlayers().add(Player.make(newID, type));
		} catch (FalseIDException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return newID;
	}
	
	/**
	 * Setter.
	 * @param PlayerID the player wich has won.
	 * @throws FalseIDException it the playerID is < 0;
	 */
	public void setPlayerHasWon(int PlayerID) throws FalseIDException {
		if(PlayerID < 0) throw new FalseIDException();
		getPlayers().get(PlayerID).setPlayerHasWon();
	}
	
	/**
	 * Has the player with the ID playerID won.
	 * @param playerID the ID of the player.
	 * @return true if the player has won.
	 * @throws FalseIDException
	 */
	public boolean hasPlayerWon(int PlayerID) throws FalseIDException {
		if(PlayerID < 0) throw new FalseIDException();
		return getPlayers().get(PlayerID).hasPlayerWon();
	}
	
	/**
	 * Getter.
	 * @return total count of players.
	 */
	@Override
	public int getPlayerCount() {
		return getPlayers().size();
	}
	
	/**
	 * Getter for the type fo the player.
	 * @param PlayerID
	 * @return 1 for human, 2 for AI, 3 for Network.  
	 * @throws FalseIDException if the ID is < 0.
	 */
	@Override
	public int getPlayerType(int PlayerID) throws FalseIDException {
		if(PlayerID < 0) throw new FalseIDException(); 
		return getPlayers().get(PlayerID).getPlayerType();
	}
	
	/**
	 * Setter for the player turn.
	 * @param playerID player who is about to play.
	 */
	@Override
	public void setPlayerTurn(int playerID) {
		playerTurn = playerID;
	}
	
	/**
	 * Getter for the ID of the player who is about to play.
	 * @return the ID of the player.
	 */
	@Override
	public int getPlayerTurnID() {
		return playerTurn;
	}
	
	/**
	 * Setter for the board length.
	 * @param length sould be whole multiply of the playerCount.
	 * @throws NegativeBoardLengthException if the length is < 0
	 */
	@Override
	public void setBoardLength(int length) throws NegativeBoardLengthException {
		if(length < 0)
			throw new NegativeBoardLengthException();
		setSpacesBetweenPlayerBases(length / getPlayerCount());
		boardLength = length;
	}

	
	/**
	 * Getter for the space betwen two bases.
	 * @return the space betwen two bases.
	 */
	@Override
	public int getSpacesBetweenPlayerBases() {
		return spacesBetweenPlayerBases;
	}
	
	/**
	 * Getter for the board length.
	 * @return the BoardLength.
	 */
	@Override
	public int getBoardLength() {
		return boardLength;
	}

	/**
	 * Getter for the count of tokens, of each player.
	 * Every player has the same count of tokens.
	 * @return the count of tokens of each player.
	 */
	@Override
	public int getTockenCountPP() {
		return tockens.size()/getPlayerCount();
	}

	/**
	 * Adds an new token to an Player.
	 * @param playerID .
	 * @param tokenID .
	 * @throws FalseIDException if the playerID is < 0.
	 * @throws FalseTockenIDException if the tokenID is < 0.
	 */
	@Override
	public void addTocken(int playerID, int tockenID) throws FalseIDException, FalseTockenIDException{
		tockens.put(new TockenID(playerID, tockenID), Tocken.make());
	}

	/**
	 * Getter for the absolute position of the token from the player.
	 * @param playerID .
	 * @param tokenID .
	 * @return the absolute position of the tocken on the board.
	 * @throws FalseIDException if the ID is < 0.
	 * @throws FalseTockenIDException if the ID is < 0.
	 */
	@Override
	public int getPositionOfTocken(int playerID, int tockenID) throws FalseIDException, FalseTockenIDException {
		return tockens.get(new TockenID(playerID, tockenID)).getPosition();
	}

	/**
	 * Setter for the position of the specified token.
	 * @param playerID .
	 * @param tokenID .
	 * @param position new position.
	 * @throws FalseIDException if the playerID is < 0.
	 * @throws FalseTockenIDException if the TokenID is < 0.
	 * @throws FalsePositionException if the position is < -1.
	 */
	@Override
	public void setPositionOfTocken(int playerID, int tockenID, int position) throws FalsePositionException, FalseIDException, FalseTockenIDException {
		tockens.get(new TockenID(playerID, tockenID)).setPosition(position);
	}
	
	/**
	 * Sets all variable to it´s initial state.
	 * Method is only used for tests.
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void reset() {
		int size = players.size();
		for(int index = 0; index < size; index++)
			players.remove(0);
		size = tockens.size();
		for(int index = 0; index < size; index++)    // the Argument is the index because it is the primitive type
			tockens.remove(0);
		playerTurn = -1;
		boardLength = -1;
		spacesBetweenPlayerBases = -1;
		
	}

	private void setSpacesBetweenPlayerBases(int spacesBetweenPlayerBases) {
		this.spacesBetweenPlayerBases = spacesBetweenPlayerBases;
	}

}
