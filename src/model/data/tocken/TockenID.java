package model.data.tocken;

import model.data.exceptions.FalseIDException;
import model.data.exceptions.FalseTockenIDException;

/**
 * 
 * @author A. Hager, ahager@hm.edu
 *
 */
public class TockenID {

	private final int payerID;
	private final int tockenID;
	
	public TockenID(int playerID, int tockenID)
			throws FalseIDException, FalseTockenIDException {
		if(playerID < 0)
			throw new FalseIDException();
		if(tockenID < 0)
			throw new FalseTockenIDException();
		this.payerID = playerID;
		this.tockenID = tockenID;		
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + payerID;
		result = prime * result + tockenID;
		return result;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that)
			return true;
		if (that == null)
			return false;
		if (getClass() != that.getClass())
			return false;
		TockenID other = (TockenID) that;
		if (payerID != other.payerID)
			return false;
		if (tockenID != other.tockenID)
			return false;
		return true; 
	}


	@Override
	public String toString() {
		return "playerID : " + payerID + " tockenID : " + tockenID;
	}
}
