package model.data.tocken;

import model.data.exceptions.FalsePositionException;

public interface Tocken {

    public int getPosition();

    public void setPosition(int position) throws FalsePositionException;

    static Tocken make(){
        return new TockenDelegate(){};
    }
}
