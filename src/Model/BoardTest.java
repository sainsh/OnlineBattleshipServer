package Model;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    public void showShipsCoordinatesRandomlyGenerated(){
        Board board = new Board();
        System.out.println("\n\nBoard 1:");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
               // System.out.print( " " + (board.getBoard1()[i][j].getShip() != null ? 1 : 0) );
            }
            System.out.println();
        }
        System.out.println("\nBoard 2:");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
               // System.out.print(" " + (board.getBoard2()[i][j].getShip() != null ? 1 : 0) );
            }
            System.out.println();
        }
    }
}