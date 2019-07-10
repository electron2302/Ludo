package test.model.data.token;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import model.data.exceptions.FalseTockenIDException;
import model.data.exceptions.FalseIDException;
import model.data.tocken.TockenID;

public class TockenIDTest {
	
	private TockenID getInstanceOfTockenID(int playerID, int tockenID) {
		return new TockenID(playerID, tockenID);
	}
	
	@Test(timeout = 1_000, expected = FalseIDException.class)
	public void playerIDSmalerZeroBombs() {
		// arrange  // act
		getInstanceOfTockenID(-1, 0);
	}

	@Test(timeout = 1_000, expected = FalseTockenIDException.class)
	public void tockenIDSmalerZeroBombs() {
		// arrange  // act
		getInstanceOfTockenID(0, -1);
	}
	
	@Test(timeout = 1_000)
	public void hashCodesEquals() {
		// arrange
		final List<TockenID> sut1 = new ArrayList<>();
		final List<TockenID> sut2 = new ArrayList<>();
		final boolean want = true;
		for(int playerID = 0; playerID < 1_000; playerID++) {
			for(int tockenID = 0; tockenID < 1_000; tockenID++) {
				sut1.add(getInstanceOfTockenID(playerID, tockenID));
				sut2.add(getInstanceOfTockenID(playerID, tockenID));
			}
		}
		//act 
		boolean have = true;
		for(int index = 0; index < sut1.size(); index++) {
			if(sut1.get(index).hashCode() != sut2.get(index).hashCode())
				have = false;
		}
		//assert
		assertEquals(want, have);
	}
	
	@Test(timeout = 1_000)
	public void equalsForEqualObjects() {
		//arrange
		final List<TockenID> sut1 = new ArrayList<>();
		final List<TockenID> sut2 = new ArrayList<>();
		final boolean want = true;
		for(int playerID = 0; playerID < 1_000; playerID++) {
			for(int tockenID = 0; tockenID < 1_000; tockenID++) {
				sut1.add(getInstanceOfTockenID(playerID, tockenID));
				sut2.add(getInstanceOfTockenID(playerID, tockenID));
			}
		}
		//act
		boolean have = true;
		for(int index = 0; index < sut1.size(); index++) {
			if(!sut1.get(index).equals(sut2.get(index)))
				have = false;
		}
		//assert
		assertEquals(want, have);
	}
	
	@Test(timeout = 1_000)
	public void sutNotEqualsNull() {
		//arrange
		final TockenID sut = getInstanceOfTockenID(0, 0);
		final boolean want = false;
		//act
		final boolean have = sut.equals(null);
		//assert
		assertEquals(want, have);
	}
	
	@Test(timeout = 1_000)
	public void notEqualsOtherRandObject() {
		//arrange
		final TockenID sut = getInstanceOfTockenID(0, 0);
		final boolean want = false;
		//act
		final boolean have = sut.equals(new Object());
		//assert
		assertEquals(want, have);
	}
	
	@Test(timeout = 10_000)
	public void notEqualsOtherTockenIDs() {
		//arrange
		final List<TockenID> sut1 = new ArrayList<>();
		final List<TockenID> sut2 = new ArrayList<>();
		for(int tockenID = 0; tockenID < 100; tockenID++) {
			for(int playerID = 0; playerID < 100; playerID++) {
				sut1.add(getInstanceOfTockenID(playerID, tockenID));
				sut2.add(getInstanceOfTockenID(playerID, tockenID));
			}
		}
		final boolean want = false;
		//act
		boolean have = false;
		for(int indexSut1 = 0; indexSut1 < sut1.size(); indexSut1++) {
			for(int indexSut2 = 0; indexSut2 < sut2.size(); indexSut2++) {
				if(indexSut1 != indexSut2 && sut1.get(indexSut1).equals(indexSut2))
					have = true;
			}
		}
		//assert
		assertEquals(want, have);		
	}
}
