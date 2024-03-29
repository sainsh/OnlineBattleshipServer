package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConToClients implements Runnable {
    private int port = 8000;
    private ServerSocket serverSocket;
    private NotifyServer notifier;


    public ConToClients(NotifyServer notifier) {
        this.notifier = notifier;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            int sessionID = 1;

            while(true){
                //Establishing connection to player1
                Socket player1 = new Socket();
                player1 = serverSocket.accept();
                notifier.printToServerLog("Player1 connected to server");

                //Establishing connection tro player2
                Socket player2 = new Socket();
                player2 = serverSocket.accept();
                notifier.printToServerLog("Player2 connected to server");

                //Starting game with two players
                Game game = new Game(player1, player2, notifier, sessionID);
                sessionID++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
