package test.model.data;

import model.data.ROData;
import model.data.RWData;
import model.data.exceptions.FalseIDException;
import model.data.exceptions.FalsePlayerTypeException;
import model.data.exceptions.FalsePositionException;
import model.data.exceptions.FalseTockenIDException;

public class TestMain {
	public static void main(String... args) throws FalseIDException, FalsePlayerTypeException, FalsePositionException, FalseTockenIDException {
		
		ROData test = ROData.getInstanceOfROData(0);
		
		
		System.out.println(test.getPlayerTurnID() + ", expected = -1 " );
		System.out.println(test.getPlayerCount() + ", expected = 0 ");
		
		
		//((RWData) test).addPlayer(1); // ist das ein Problem?!?
		
		RWData test2 = RWData.getInstanceOfRWData(0);
		System.out.println(test2.getPlayerCount() + ", expected = 0 ");
		test2.addPlayer(1);
		test2.addPlayer(3);
		System.out.println(test2.getPlayerType(0) + ", expected = 1 ");
		System.out.println(test2.getPlayerType(1) + ", expected = 3 ");
		System.out.println(test2.getPlayerCount() + ", expected = 2 ");
		System.out.println(test2.getPlayerTurnID() + ", expected = -1 ");
		test2.setPlayerTurn(0);
		System.out.println(test2.getPlayerTurnID() + ", expected = 0 ");
		System.out.println(test2.hasPlayerWon(1) + ", expected = false ");
		test2.setPlayerHasWon(1);
		System.out.println(test2.hasPlayerWon(1) + ", expected = true ");
		
		//and at position of the view
		System.out.println(test.getPlayerType(0) + ", expected = 1 ");
		System.out.println(test.getPlayerType(1) + ", expected = 3 ");
		System.out.println(test.getPlayerCount() + ", expected = 2 ");
		System.out.println(test.getPlayerTurnID() + ", expected = 0 ");
		System.out.println(test.hasPlayerWon(0) + ", expected = false ");
		System.out.println(test.hasPlayerWon(1) + ", expected = true ");

		test2.addTocken(1, 23);
		test2.addTocken(1, 4);
		test2.addTocken(2, 5);
		test2.addTocken(2, 9);

		System.out.println(test.getPositionOfTocken(1, 23) + ", expected = -1 ");
		System.out.println(test.getPositionOfTocken(1, 4) + ", expected = -1 ");
		System.out.println(test.getPositionOfTocken(2, 5) + ", expected = -1 ");
		System.out.println(test.getPositionOfTocken(2, 9) + ", expected = -1 ");

		test2.setPositionOfTocken(1, 23,1);
		test2.setPositionOfTocken(1, 4,7869);
		test2.setPositionOfTocken(2, 5,3);
		test2.setPositionOfTocken(2, 9,5);

		System.out.println(test.getPositionOfTocken(1, 23) + ", expected = 1 ");
		System.out.println(test.getPositionOfTocken(1, 4) + ", expected = 7869 ");
		System.out.println(test.getPositionOfTocken(2, 5) + ", expected = 3 ");
		System.out.println(test.getPositionOfTocken(2, 9) + ", expected = 5 ");

	}
}
