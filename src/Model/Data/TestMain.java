package Model.Data;

public class TestMain {
	public static void main(String... ars) {
		
		roData test = roData.getInstanceOfROData();
		System.out.println(test.getPlayerTurnID() + ", expected = -1 " );
		System.out.println(test.getPlayerCount() + ", expected = 0 ");
		
		
		rwData test2 = rwData.getInstanceOfRWData();
		
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

		test2.addTocken(23);
		test2.addTocken(4);
		test2.addTocken(5);
		test2.addTocken(9);

		System.out.println(test.getPositionOfTocken(23) + ", expected = -1 ");
		System.out.println(test.getPositionOfTocken(4) + ", expected = -1 ");
		System.out.println(test.getPositionOfTocken(5) + ", expected = -1 ");
		System.out.println(test.getPositionOfTocken(9) + ", expected = -1 ");

		test2.setPositionOfTocken(23,1);
		test2.setPositionOfTocken(4,7869);
		test2.setPositionOfTocken(5,3);
		test2.setPositionOfTocken(9,5);

		System.out.println(test.getPositionOfTocken(23) + ", expected = 1 ");
		System.out.println(test.getPositionOfTocken(4) + ", expected = 7869 ");
		System.out.println(test.getPositionOfTocken(5) + ", expected = 3 ");
		System.out.println(test.getPositionOfTocken(9) + ", expected = 5 ");

	}
}
