package Model.Data;

public interface roData {
	
	static roData getInstanceOfROData() {
		return rwData.getInstanceOfRWData();
	}
	
	int getPlayerTurnID(); 
	
	int getBoardLength();
	
	int getTockenCountPP();//list.length
	
	int getPositionOfTocken(int tokenID);
	
	int getTockenIdAtPosition(int position);// search thru LSIST and check every entitits position
	
	boolean hasPlayerWon(int PlayerID);
	
	int getPlayerType(int PlayerID);
	
	int getPlayerCount();

}
