package sample;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {

    ArrayList<Socket> clients;
    private int clientNo = 0;


    @Override
    public void run() {
        clients = new ArrayList<>();

        new Thread(() -> {

            try {
                ServerSocket serverSocket = new ServerSocket(1337);


                while (true) {
                    Socket socket = serverSocket.accept();
                    clientNo++;

                    Platform.runLater(() -> {

                        clients.add(socket);

                    });

                    new Thread(new HandleAClient(socket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }).start();

    }

    private class HandleAClient implements Runnable {
        private Socket socket;
        String output = "";

        public HandleAClient(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                while(true){

                    output = in.readUTF();

                    for (Socket socket: clients) {
                        new DataOutputStream(socket.getOutputStream()).writeUTF(output);
                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
