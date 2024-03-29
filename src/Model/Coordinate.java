package Model;

import java.io.Serializable;

public class Coordinate implements Serializable {

    private int x;
    private int y;
    private boolean isHit = false;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    //x: int
    //y: int
    //isHit: Boolean
}
