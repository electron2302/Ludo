package test.model.data.token;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.data.exceptions.FalsePositionException;
import model.data.tocken.Tocken;
import model.data.tocken.TockenDelegate;

/**
 * 
 * @author Andreas Hager
 *
 */
public class TockenDelegateTest {
	
	private Tocken getInstanceOfTocken() {
		return new TockenDelegate() {};
	}
	
	@Test(timeout = 1_000)
	public void defaultPosition() {
		//arrange
		final Tocken sut = getInstanceOfTocken();
		final int want = -1;
		//act
		final int have = sut.getPosition();
		//assert
		assertEquals(want, have);
	}

	@Test(timeout = 1_000)
	public void setPosition() {
		//arrange
		final Tocken sut = getInstanceOfTocken();
		final int want = 42;
		//act
		sut.setPosition(42);
		final int have = sut.getPosition();
		//assert
		assertEquals(want, have);
	}

	@Test(timeout = 1_000, expected = FalsePositionException.class)
	public void setPositionSmalerMinusOne() {
		//arrange
		final Tocken sut = getInstanceOfTocken();
		//act
		sut.setPosition(-2);
	}
}
