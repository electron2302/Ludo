package Model.Data;


/**
 * A roData Interface:
 *
 * @author Andreas Hager, andreashager19@gmail.com
 * @author Simon Arndt, technikon23@gmail.com
 * @version Initial version
 */
public interface roData {
	
	static roData getInstanceOfROData() {
		return rwData.getInstanceOfRWData();
	}
	
	int getPlayerTurnID(); 
	
	int getBoardLength();
	
	int getTockenCountPP();//list.length
	
	int getPositionOfTocken(int tokenID);
	
	//int getTockenIdAtPosition(int position);// search thru LSIST and check every entitits position /////////// we came to the conclusion that this belongs to logic
	
	boolean hasPlayerWon(int PlayerID);
	
	int getPlayerType(int PlayerID);
	
	int getPlayerCount();

}
