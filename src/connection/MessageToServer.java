package connection;

import Model.Cell;

public class MessageToServer {

    boolean isChatMessage = false;
    boolean isShot = false;
    String chatMessage;
    Cell shot;
    String sender;

    public MessageToServer() {

    }

    public boolean isChatMessage() {
        return isChatMessage;
    }

    public void setChatMessage(boolean chatMessage) {
        isChatMessage = chatMessage;
    }

    public boolean isShot() {
        return isShot;
    }

    public void setShot(boolean shot) {
        isShot = shot;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public Cell getShot() {
        return shot;
    }

    public void setShot(Cell shot) {
        this.shot = shot;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
