package connection;

import javafx.scene.control.TextArea;

import java.util.Date;


public class ServerController implements NotifyServer {
    public TextArea serverLog;

    public void initialize(){
        serverLog.setEditable(false);
        serverLog.appendText(new Date() + ": Server started\n");
        ConToClients con = new ConToClients(this);
        new Thread(con).start();
    }

    @Override
    public void printToServerLog(String msg) {
        serverLog.appendText(msg + "\n");
    }
}
