package mygdx.game.model.data;

import mygdx.game.model.message.Message;

import java.io.Serializable;

public class MessageData implements Serializable {
    private int senderId;
    private int receiverId;
    private String content;

    public MessageData(Message message) {
        this.senderId = message.getSender().getId();
        this.receiverId = message.getReceiver().getId();
        this.content = message.getContent();
    }

    public int getSenderId() { return this.senderId; }

    public int getReceiverId() { return this.receiverId; }

    public String getContent() { return this.content; }
}
