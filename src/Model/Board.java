package Model;

public class Board {
    private Cell[][] board1;
    private Cell[][] board2;


    public void setShipBoard1(Ship ship){}

    public void setShipBoard2(Ship ship){}

    public Cell shootBoard1(int x, int y){
        Cell shotCell = board1[x][y];
        shotCell.getCoordinate().setHit(true);
        shotCell.getStatus();
        if(shotCell.getShip() != null) {
            for (Coordinate cor : shotCell.getShip().getCoordinates()
            ) {
                if (shotCell.getCoordinate() == cor) {
                    cor.setHit(true);
                }
            }
        }
        return shotCell;
    }

    public Cell shootBoard2(int x, int y){
        Cell shotCell = board2[x][y];
        shotCell.getCoordinate().setHit(true);
        System.out.println("cellCoordinate = " + shotCell.getCoordinate().getX() + ", " + shotCell.getCoordinate().getY() + " status: " + shotCell.getCoordinate().isHit());
        shotCell.getStatus();
        System.out.println(x + ", " + y + " is shot at " + shotCell.getCoordinate().getX() + ", " + shotCell.getCoordinate().getY());
        if (!(shotCell.getShip() == null)) {
            for (Coordinate cor : shotCell.getShip().getCoordinates()
            ) {
                if (shotCell.getCoordinate().getX() == cor.getX() && shotCell.getCoordinate().getY() == cor.getY()) {
                    cor.setHit(true);
                }
            }
        }

        return shotCell;
    }

    public Cell[][] getBoard1() {
        return board1;
    }

    public Board() {
        Cell[][] cells = new Cell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                cells[i][j] = new Cell();
                //Sets the coordinate to the position in the array
                cells[i][j].setCoordinate(new Coordinate(i,j));

            }
        }
        Ship ship = new Ship();
        ship.setCoordinates();
        cells[0][1].setShip(ship);
        cells[0][0].setShip(ship);
        this.board1 = cells;
        this.board2 = cells;
    }

    public void setBoard1(Cell[][] board1) {
        this.board1 = board1;
    }

    public Cell[][] getBoard2() {
        return board2;
    }

    public void setBoard2(Cell[][] board2) {
        this.board2 = board2;
    }
}
