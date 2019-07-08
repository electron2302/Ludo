package test.model.logic;

import java.io.IOException;
import java.util.Arrays;

import model.data.RWData;
import model.data.exceptions.FalseIDException;
import model.data.exceptions.FalsePlayerTypeException;
import model.data.exceptions.FalsePositionException;
import model.data.exceptions.FalseTockenIDException;
import model.data.exceptions.NegativeBoardLengthException;
import model.logic.CLogic;
import model.logic.Logic;
import model.logic.exceptions.FalseDiceValueException;
import model.logic.exceptions.IllegalMoveException;
import model.logic.exceptions.PlayerAlereadyWonException;
import model.logic.exceptions.TriedToMooveToFarException;

public class TestMain {

	public static void main(String... args) throws FalseIDException, FalseTockenIDException, PlayerAlereadyWonException, FalseDiceValueException, IllegalMoveException, IOException, FalsePlayerTypeException, NegativeBoardLengthException, TriedToMooveToFarException, FalsePositionException {
		final Logic logic = CLogic.getInstanceOfLogic();
		final RWData data = RWData.getInstanceOfRWData();
		
		logic.initialize(40, 4, 4, Arrays.asList(1, 2, 3, 1));
		
		//test initialize
		System.out.println(data.getBoardLength() + " explected: " + 40);
		System.out.println(data.getPlayerCount() + " explected: " + 4);
		System.out.println(data.getPlayerTurnID() + " explected: " + 0);
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
		System.out.println(data.getPositionOfTocken(0, 0) + " explected: " + 0);
		
		logic.move(0, 6);
		System.out.println(data.getPositionOfTocken(0, 0) + " explected: " + 6);
		
		logic.move(0, 1);
		System.out.println(data.getPositionOfTocken(0, 0) + " explected: " + 7);
		
		logic.move(0, 6);
		System.out.println(data.getPositionOfTocken(1, 0) + " explected: " + 0);
		
		logic.move(0, 1);
		System.out.println(data.getPositionOfTocken(1, 0) + " explected: " + 1);
		
		System.out.println(data.getPlayerTurnID() + " explected: " + 2);

	}
}
