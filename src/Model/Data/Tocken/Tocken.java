package Model.Data.Tocken;

public interface Tocken {

    public int getPosition();

    public void setPosition(int position);

    static Tocken make(){
        return new TockenDelegate(){};
    }
}
