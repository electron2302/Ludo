package model.data.tocken;

import model.data.exceptions.FalsePositionException;

abstract public class TockenDelegate implements Tocken{

    private int position;

    public TockenDelegate() {
        position = -1;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) throws FalsePositionException {
    	if(position < 0)
    		throw new FalsePositionException();
        this.position = position;
    }

}
