package mygdx.game.model.data;

import mygdx.game.model.Deck;
import mygdx.game.model.Player;
import mygdx.game.model.faction.Faction;

import java.io.Serializable;

public class PlayerProfileData implements Serializable {
    private Faction faction;
    private Deck deck;
    private String username;
    private String nickname;

    public PlayerProfileData(Player player) {
        this.faction = player.getSelectedFaction();
        this.deck = player.getDeck();
        this.username = player.getUsername();
        this.nickname = player.getNickname();
    }

    public Faction getSelectedFaction() { return this.faction; }

    public Deck getDeck() { return this.deck; }

    public String getUsername() { return this.username; }

    public String getNickname() { return this.nickname; }
}
