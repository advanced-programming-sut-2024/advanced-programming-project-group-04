package mygdx.game.model.data;

import mygdx.game.model.Player;

public class PlayerFriendData {
    private int id;
    private String username;

    public PlayerFriendData(Player player) {
        this.id = player.getId();
        this.username = player.getUsername();
    }

    public String getUsername() { return this.username; }

    public int getId() { return this.id; }
}
