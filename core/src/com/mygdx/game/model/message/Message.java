package mygdx.game.model.message;

import mygdx.game.model.Player;

public class Message {
    private Player sender;
    private Player receiver;
    private String content;
    private long sendTime; // TIME PASSED FROM EPOCH

    public Message(Player sender, Player receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sendTime = System.currentTimeMillis();
    }

    public Player getSender() {
        return sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public long getSendTime() {
        return sendTime;
    }

}
