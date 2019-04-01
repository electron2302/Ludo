package model.data.tocken;

abstract public class TockenDelegate implements Tocken{

    private int position;

    public TockenDelegate() {
        position = -1;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
