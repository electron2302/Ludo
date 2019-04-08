package model.logic.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import jdk.jfr.Unsigned;
import model.logic.Logic;

public class LogicTest {
	
	@Test(timeout = 1_000)
	public void throwDiceBetweenOneAndSixTest() {
		//arrange
		final int upperBound = 6;
		final int lowerBound = 1;
		final boolean want = true;
		final List<Integer> sut = new ArrayList<>();
		boolean have = true;
		//act
		for(int counter = 0; counter < 1_000_000; counter++)
			sut.add(Logic.throwDice());
		for(int index = 0; index < sut.size(); index++)
			if(sut.get(index)< lowerBound || sut.get(index)> upperBound)
				have = false;
		//assert
		assertEquals(want, have);
	}
	
	@Test(timeout = 10_000)
	public void throwDiceStatisticTest() {
		for(int i = 0; i < 50; i++ ) {
			//arrange
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
			final List<Integer> sut = new ArrayList<>();
			final boolean want = true;
			//act
			for(int counter = 0; counter < 1_000_000; counter++)
				sut.add(Logic.throwDice());
			
			for(int index = 0; index < sut.size(); index++) {
				if(sut.get(index) == 1)
					countOne++;
				else if(sut.get(index) == 2)
					countTwo++;
				else if(sut.get(index) == 3)
					countThree++;
				else if(sut.get(index) == 4)
					countFour++;
				else if(sut.get(index) == 5)
					countFive++;
				else if(sut.get(index) == 6)
					countSix++;
			}
			final int size = sut.size();
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
