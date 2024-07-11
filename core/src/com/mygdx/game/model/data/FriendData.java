package mygdx.game.model.data;

import mygdx.game.model.Player;

import java.util.ArrayList;

public class FriendData {
    private ArrayList<PlayerFriendData> friends;

    public FriendData(ArrayList<Player> friends) {
        this.friends = new ArrayList<>();
        for (Player player : friends) {
            this.friends.add(new PlayerFriendData(player));
        }
    }

    public ArrayList<PlayerFriendData> getFriends() { return this.friends; }
}
