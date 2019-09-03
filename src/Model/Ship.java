package Model;

//This class needs to be changed
public class Ship {
    private Coordinate coordinate = new Coordinate(0,0);
    private Coordinate coordinate2 = new Coordinate(0,1);
    private Coordinate[] coordinates = new Coordinate[2];
    private boolean isSunken;

    public void setCoordinates() {
        coordinates[0] = coordinate;
        coordinates[1] = coordinate2;
        this.coordinates = coordinates;
    }


    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isSunken() {
        if (getCoordinates()[0].isHit() && getCoordinates()[1].isHit())
        {isSunken = true;}
        return isSunken;
    }

    public void setSunken(boolean sunken) {
        isSunken = sunken;
    }
}
