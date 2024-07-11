package mygdx.game.model.data;

import mygdx.game.model.Player;

import java.io.Serializable;

public class PlayerFriendData implements Serializable {
    private int id;
    private String username;

    public PlayerFriendData(Player player) {
        this.id = player.getId();
        this.username = player.getUsername();
    }

    public String getUsername() { return this.username; }

    public int getId() { return this.id; }
}
