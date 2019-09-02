package connection;

import connection.MessageToServer;
import Model.Board;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Game{
    private Socket player1;
    private ObjectOutputStream outP1;
    private ObjectInputStream inP1;
    private Socket player2;
    private ObjectOutputStream outP2;
    private ObjectInputStream inP2;
    private Board board;

    public Game(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;

        try {
            outP1 = new ObjectOutputStream(player1.getOutputStream());
            inP1 = new ObjectInputStream(player1.getInputStream());
            outP2 = new ObjectOutputStream(player2.getOutputStream());
            inP2 = new ObjectInputStream(player2.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenToPlayer1(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true){
                    MessageToServer messageToServer = new MessageToServer();
                    try {
                        messageToServer = (MessageToServer) inP1.readObject();
                        if(messageToServer.isShot()){
                            int x = messageToServer.getShot().getCoordinate().getX();
                            int y = messageToServer.getShot().getCoordinate().getY();
                            shoot(x, y, 2);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public void listenToPlayer2(){

    }

    public void shoot(int x, int y, int player){
        
    }


    public Socket getPlayer1() {
        return player1;
    }

    public void setPlayer1(Socket player1) {
        this.player1 = player1;
    }

    public ObjectOutputStream getOutP1() {
        return outP1;
    }

    public void setOutP1(ObjectOutputStream outP1) {
        this.outP1 = outP1;
    }

    public ObjectInputStream getInP1() {
        return inP1;
    }

    public void setInP1(ObjectInputStream inP1) {
        this.inP1 = inP1;
    }

    public Socket getPlayer2() {
        return player2;
    }

    public void setPlayer2(Socket player2) {
        this.player2 = player2;
    }

    public ObjectOutputStream getOutP2() {
        return outP2;
    }

    public void setOutP2(ObjectOutputStream outP2) {
        this.outP2 = outP2;
    }

    public ObjectInputStream getInP2() {
        return inP2;
    }

    public void setInP2(ObjectInputStream inP2) {
        this.inP2 = inP2;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
