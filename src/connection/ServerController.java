package connection;

import javafx.scene.control.TextArea;

import java.util.Date;


public class ServerController {
    public TextArea serverLog;

        public void initialize(){
        serverLog.setEditable(false);
        serverLog.appendText(new Date() + ": Server started");
    }
}
