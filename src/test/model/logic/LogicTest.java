package test.model.logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.logic.CLogic;
import model.logic.Logic;

public class LogicTest {
	
	private Logic getSut() {
		return new CLogic();
	}
	
	
	@Test(timeout = 1_000)
	public void throwDiceBetweenOneAndSixTest() {
		//arrange
		final Logic sut = getSut();
		final int upperBound = 6;
		final int lowerBound = 1;
		final boolean want = true;
		final List<Integer> list = new ArrayList<>();
		boolean have = true;
		//act
		for(int counter = 0; counter < 1_000_000; counter++)
			list.add(sut.throwDice());
		for(int index = 0; index < list.size(); index++)
			if(list.get(index)< lowerBound || list.get(index)> upperBound)
				have = false;
		//assert
		assertEquals(want, have);
	}
	
	@Test(timeout = 10_000)
	public void throwDiceStatisticTest() {
		for(int i = 0; i < 50; i++ ) {
			//arrange
			final Logic sut = getSut();
			final double expected = 1./6;
			final double error = 0.002;
			final double upperBound = expected + error;
			final double lowerBound = expected - error;
			int countOne = 0;
			int countTwo = 0;
			int countThree = 0;
			int countFour = 0;
			int countFive = 0;
			int countSix = 0;
			final List<Integer> list = new ArrayList<>();
			final boolean want = true;
			//act
			for(int counter = 0; counter < 1_000_000; counter++)
				list.add(sut.throwDice());
			
			for(int index = 0; index < list.size(); index++) {
				if(list.get(index) == 1)
					countOne++;
				else if(list.get(index) == 2)
					countTwo++;
				else if(list.get(index) == 3)
					countThree++;
				else if(list.get(index) == 4)
					countFour++;
				else if(list.get(index) == 5)
					countFive++;
				else if(list.get(index) == 6)
					countSix++;
			}
			final int size = list.size();
			final double valueOne = ((double)countOne)/size;
			final double valueTwo = ((double)countTwo)/size;
			final double valueThree = ((double)countThree)/size;
			final double valueFour = ((double)countFour)/size;
			final double valueFive = ((double)countFive)/size;
			final double valueSix = ((double)countSix)/size;
			//assert
			final boolean haveOne = valueOne >= lowerBound  && valueOne <= upperBound;
			final boolean haveTwo = valueTwo >= lowerBound  && valueTwo <= upperBound;
			final boolean haveThree = valueThree >= lowerBound  && valueThree <= upperBound;
			final boolean haveFour = valueFour >= lowerBound  && valueFour <= upperBound;
			final boolean haveFive = valueFive >= lowerBound  && valueFive <= upperBound;
			final boolean haveSix = valueSix >= lowerBound  && valueSix <= upperBound;
			assertEquals(want, haveOne);
			assertEquals(want, haveTwo);
			assertEquals(want, haveThree);
			assertEquals(want, haveFour);
			assertEquals(want, haveFive);
			assertEquals(want, haveSix);
		}
	}

}
