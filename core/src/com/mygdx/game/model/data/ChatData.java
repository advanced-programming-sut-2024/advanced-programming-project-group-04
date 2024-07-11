package mygdx.game.model.data;

import mygdx.game.model.message.Message;

import java.util.ArrayList;

public class ChatData {
    private ArrayList<MessageData> messageList;

    public ChatData(ArrayList<Message> messageList) {
        this.messageList = new ArrayList<>();
        for (Message message : messageList) {
            this.messageList.add(new MessageData(message));
        }
    }

    public ArrayList<MessageData> getMessageList() { return this.messageList; }
}
