package model.logic;
/**
 * 
 * @author A. Hager, ahager@hm.edu
 *
 */
public interface Logic {
	boolean move(int tockenID, int diceValue);
	boolean init();
	public static int throwDice() {
		return (int) ((Math.random()*5897)%6)+1;
	}
	
}
