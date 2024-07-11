package mygdx.game.model.data;

import mygdx.game.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class FriendData implements Serializable {
    private ArrayList<PlayerFriendData> friends;

    public FriendData(ArrayList<Player> friends) {
        this.friends = new ArrayList<>();
        if (friends != null) {
            for (Player player : friends) {
                this.friends.add(new PlayerFriendData(player));
            }
        }
    }

    public ArrayList<PlayerFriendData> getFriends() { return this.friends; }
}
