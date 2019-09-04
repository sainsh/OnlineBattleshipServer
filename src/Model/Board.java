package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private Cell[][] board1;
    private Cell[][] board2;
    private List<Ship> shipsBoard1 = new ArrayList<>();
    private List<Ship> shipsBoard2 = new ArrayList<>();

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

    public void shootBoard2(int x, int y){
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
                cells[i][j].setCoordinate(new Coordinate(i, j));

            }
        }
        this.board1 = cells;
        this.board2 = cells;
        setShips();
    }

    public void setShips(){
        Cell[][] board = new Cell[5][5];
        for (int i = 0; i < 2; i++) {
            if(i == 0){
                board = board1;
            }else{
                board = board2;
            }
        }
        for (int i = 0; i < 3; i++) {
            Ship ship = new Ship();
            if(i % 2 == 0){
                ship.setLength(2);
            }else{
                ship.setLength(3);
            }
            boolean shipNotPlaceable = true;
            int startX = 0;
            int startY = 0;
            boolean horizontal = false;
            while(shipNotPlaceable) {
                System.out.println("WhileLoop started");
                shipNotPlaceable = false;
                horizontal = (int) (Math.random() * 2) == 0;
                startX = horizontal ? (int) (Math.random() * (5 - ship.getLength())) : (int) (Math.random() * 5);
                startY = !horizontal ? (int) (Math.random() * (5 - ship.getLength())): (int) (Math.random() * 5);
                for (int j = 0; j < ship.getLength(); j++) {
                    if(horizontal){
                        if (board[startX + i][startY].getShip() != null)
                            System.out.println(board[startX + i][startY].getShip() != null);
                            shipNotPlaceable = true;
                    }else{
                        if (board[startX][startY + i].getShip() != null)
                            shipNotPlaceable = true;
                    }
                }
            }
            List<Coordinate> coordinates = new ArrayList<>();
            for (int j = 0; j < ship.getLength(); j++) {
                if (horizontal){
                    coordinates.add(new Coordinate(startX + j, startY));
                    board[startX + j][startY].setShip(ship);
                } else{
                    coordinates.add(new Coordinate(startX, startY + j));
                    board[startX][startY + j].setShip(ship);
                }
            }
            ship.setCoordinates(coordinates);
            System.out.print("\n" + ship.getCoordinates().get(0).getX() + "," + ship.getCoordinates().get(0).getY() + " " +
                    ship.getCoordinates().get(1).getX() + "," + ship.getCoordinates().get(1).getY() + " ");
            if (i == 1)
                System.out.print(ship.getCoordinates().get(2).getX() + "," + ship.getCoordinates().get(2).getY() + " ");

        }
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

    public List<Ship> getShipsBoard1() {
        return shipsBoard1;
    }

    public void setShipsBoard1(List<Ship> shipsBoard1) {
        this.shipsBoard1 = shipsBoard1;
        for (Ship s: shipsBoard1) {
            List<Coordinate> coordinates = s.getCoordinates();
            for (Coordinate c: coordinates) {
                int x = c.getX();
                int y = c.getY();
                System.out.println(x + "," + y + " Length" + board1.length);
                board1[x][y].setShip(s);
            }
        }
    }

    public List<Ship> getShipsBoard2() {
        return shipsBoard2;
    }

    public void setShipsBoard2(List<Ship> shipsBoard2) {
        this.shipsBoard2 = shipsBoard2;
        for (Ship s: shipsBoard2) {
            List<Coordinate> coordinates = s.getCoordinates();
            for (Coordinate c: coordinates) {
                int x = c.getX();
                int y = c.getY();
                board2[x][y].setShip(s);
            }
        }
    }
}
