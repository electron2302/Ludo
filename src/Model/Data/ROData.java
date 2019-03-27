package Model.Data;


/**
 * A ROData Interface:
 *
 * @author Andreas Hager, andreashager19@gmail.com
 * @author Simon Arndt, technikon23@gmail.com
 * @version Initial version
 */
public interface ROData {
	
	static ROData getInstanceOfROData() {
		return RWData.getInstanceOfRWData();
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
