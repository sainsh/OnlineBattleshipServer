package connection;

import Communication.MessageToClient;
import Model.Cell;
import Communication.MessageToServer;
import Model.Board;
import Model.Coordinate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Game {
    private Socket player1;
    private ObjectOutputStream outP1;
    private ObjectInputStream inP1;
    private Socket player2;
    private ObjectOutputStream outP2;
    private ObjectInputStream inP2;
    private Board board;
    private int sessionID;
    private NotifyServer notifier;

    public Game(Socket player1, Socket player2, NotifyServer notifier, int sessionID) {
        this.player1 = player1;
        this.player2 = player2;
        this.notifier = notifier;
        this.sessionID = sessionID;

        try {
            outP1 = new ObjectOutputStream(player1.getOutputStream());
            inP1 = new ObjectInputStream(player1.getInputStream());
            outP2 = new ObjectOutputStream(player2.getOutputStream());
            inP2 = new ObjectInputStream(player2.getInputStream());
            printToLog("I/O streams to clients established");
            listenToPlayer1();
            listenToPlayer2();
            startGame();
            printToLog("Game started");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        board = new Board();
        MessageToClient messageToPlayer1 = new MessageToClient();
        MessageToClient messageToPlayer2 = new MessageToClient();
        messageToPlayer1.setYourTurn(true);
        messageToPlayer1.setClientText("Your Turn");
        messageToPlayer1.setChangeClientText(true);
        messageToPlayer2.setClientText("Opponents turn");
        messageToPlayer1.setBoard(board.getBoard1AsInts());
        messageToPlayer1.setHasBoard(true);
        messageToPlayer2.setChangeClientText(true);
        messageToPlayer2.setBoard(board.getBoard2AsInts());
        messageToPlayer2.setHasBoard(true);
        try {
            outP1.writeObject(messageToPlayer1);
            outP1.flush();
            outP2.writeObject(messageToPlayer2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenToPlayer1() {
        Runnable runnable = () -> {
            while (true) {
                MessageToServer messageToServer = new MessageToServer();
                try {
                    messageToServer = (MessageToServer) inP1.readObject();
                    if (messageToServer.isShot()) {
                        int x = messageToServer.getX();
                        int y = messageToServer.getY();
                        shoot(x, y, 1);
                    }
                    if (messageToServer.isChatMessage()) {
                        writeMessage("Player1", messageToServer.getChatMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    public void listenToPlayer2() {
        Runnable runnable = () -> {
            while (true) {
                MessageToServer messageToServer = new MessageToServer();
                try {
                    messageToServer = (MessageToServer) inP2.readObject();
                    if (messageToServer.isShot()) {
                        int x = messageToServer.getX();
                        int y = messageToServer.getY();
                        shoot(x, y, 2);
                    }
                    if (messageToServer.isChatMessage()) {
                        writeMessage("Player2", messageToServer.getChatMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    public void shoot(int x, int y, int player) {
        if (player == 1) {
            board.shootBoard2(x, y);
        } else {
            board.shootBoard1(x, y);
        }
        Cell[][] enemyBoard = player == 1 ? board.getBoard2() : board.getBoard1();
        Cell cell = enemyBoard[x][y];
        cell.setStatus();
        printToLog("Shot registered from player" + player + " at (" + x + "," + y + ")" + (cell.getStatus() == 2 ? " - miss" : " - hit"));
        MessageToClient messageToClient = new MessageToClient();
        if (cell.getShip() != null) {
            if (cell.getShip().isSunken()) {
                List<Coordinate> coordinatesList = cell.getShip().getCoordinates();
                int[] coordinates = new int[coordinatesList.size() * 2];
                for (int i = 0; i < coordinates.length; i += 2) {
                    coordinates[i] = coordinatesList.get(i / 2).getX();
                    coordinates[i + 1] = coordinatesList.get(i / 2).getY();
                }
                messageToClient.setShipSunken(true);
                messageToClient.setCoordinate(coordinates);
                printToLog("Player" + player + " sank a ship");
            }
        }
        messageToClient.setChangeClientText(true);
        messageToClient.setShot(true);
        messageToClient.setX(cell.getCoordinate().getX());
        messageToClient.setY(cell.getCoordinate().getY());
        messageToClient.setStatus(cell.getStatus());
        messageToClient.setYourShot(player == 1);
        try {
            if (board.isGameOver(player == 1)) {
                messageToClient.setGameOver(true);
                messageToClient.setClientText(player == 1 ? "You Won!!!!" : "You Lost!!!!");
                outP1.writeObject(messageToClient);
                messageToClient.setClientText(player != 1 ? "You Won!!!!" : "You Lost!!!!");
                outP2.writeObject(messageToClient);
                printToLog("Player" + player + " won");
            } else {
                messageToClient.setClientText(player == 1 ? "Opponents turn" : "Your turn");
                messageToClient.setYourTurn(!(player == 1));
                outP1.writeObject(messageToClient);
                messageToClient.setClientText(player != 1 ? "Opponents turn" : "Your turn");
                messageToClient.setYourShot(!(player == 1));
                messageToClient.setYourTurn(player == 1);
                outP2.writeObject(messageToClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMessage(String sender, String message) {
        String string = sender + ": " + message;
        printToLog("Chat message - " + string);
        MessageToClient messageToClient = new MessageToClient();
        messageToClient.setMessage(string);
        messageToClient.setMessage(true);
        try {
            outP1.writeObject(messageToClient);
            outP2.writeObject(messageToClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printToLog(String message){
        notifier.printToServerLog("Game session #" + sessionID + ": " + message);
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
