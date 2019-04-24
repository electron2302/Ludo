package test.model.logic;

import model.data.RWData;
import model.data.exceptions.FalseIDException;
import model.data.exceptions.FalseTockenIDException;
import model.logic.CLogic;
import model.logic.Logic;
import model.logic.exceptions.FalseDiceValueException;
import model.logic.exceptions.IllegalMoveException;
import model.logic.exceptions.PlayerAlereadyWonException;

public class TestMain {

	public static void main(String... args) throws FalseIDException, FalseTockenIDException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException {
		final Logic logic = new CLogic();
		final RWData data = RWData.getInstanceOfRWData();
		//initialize
		logic.initialize(40, 4, 4, 1, 2, 3, 1);
		//test initialize
		System.out.println(data.getBoardLength() + " explected: " + 40);
		System.out.println(data.getPlayerCount() + " explected: " + 4);
		System.out.println(data.getPlayerTurnID() + " explected: " + -1);
		for(int index = 0; index < 4; index++)
			System.out.println(data.getPlayerType(index) + " explected: " + ((index)%3+1));
		for(int playerID = 0; playerID < 4; playerID++)
			for( int tokenID = 0; tokenID < 4; tokenID++)
				System.out.println(data.getPositionOfTocken(playerID, tokenID) + " explected: " + -1);
		System.out.println(data.getSpacesBetweenPlayerBases() + " explected: " + 10);
		System.out.println(data.getTockenCountPP() + " explected: " + 4);
		
		
		data.setPlayerTurn(0);
		
		logic.move(0, 2);
		//test nothing happend
		System.out.println(data.getPositionOfTocken(0, 0) + " explected: " + -1);
		
		logic.move(0, 6);
		System.out.println(data.getPositionOfTocken(1, 0) + " explected: " + 0);
		
		logic.move(0, 6);
		System.out.println(data.getPositionOfTocken(1, 0) + " explected: " + 6);
		
		logic.move(0, 1);
		System.out.println(data.getPositionOfTocken(1, 0) + " explected: " + 7);
		
		logic.move(0, 6);
		System.out.println(data.getPositionOfTocken(2, 0) + " explected: " + 0);
		
		logic.move(0, 1);
		System.out.println(data.getPositionOfTocken(2, 0) + " explected: " + 1);
		
		System.out.println(data.getPlayerTurnID() + " explected: " + 3);
		
	}
}
