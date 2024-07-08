package com.mygdx.game.controller;

import com.mygdx.game.Main;
import com.mygdx.game.controller.commands.ServerCommand;
import com.mygdx.game.model.Deck;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.faction.Faction;
import com.mygdx.game.model.leader.Leader;
import com.mygdx.game.view.FactionAndLeaderMenu;

import java.util.ArrayList;

public class FactionAndLeaderController {
    private final Main game;
    private final FactionAndLeaderMenu menu;
    private Faction faction;
    private Leader leader;

    public FactionAndLeaderController(Main game, FactionAndLeaderMenu menu) {
        this.game = game;
        this.menu = menu;
    }

    public void loadData() {
        faction = game.getLoggedInPlayer().getSelectedFaction();
        Deck deck = game.getLoggedInPlayer().getDeck();
        if (faction != null) {
            if (deck != null) {
                leader = deck.getLeader();
                menu.setCards(deck.getCards());
            } else {
                menu.setCards(new ArrayList<>());
            }
        }
    }

    public void factionButtonClicked(String factionName) {
        Faction faction = game.getClient().sendToServer(ServerCommand.SELECT_FACTION, factionName);
        this.faction = faction;
        menu.updateCards();
        game.getLoggedInPlayer().createNewDeck();
        this.leader = null;
        game.getLoggedInPlayer().setFaction(faction);

        System.out.println("Selected Faction: " + faction.getName());
    }

    public void leaderButtonClicked(String leaderName) {
        Leader leader = game.getClient().sendToServer(ServerCommand.SELECT_LEADER, leaderName);
        this.leader = leader;
        game.getLoggedInPlayer().getDeck().setLeader(leader);

        System.out.println("Selected Leader: " + leader.getName());
    }

    public ArrayList<AllCards> getFactionCardsRepeated() {
        ArrayList<AllCards> cards = null;
        ArrayList<AllCards> factionCards = new ArrayList<>();
        try {
            cards = Faction.getCardsFromFaction(faction);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (AllCards allCard : cards) {
            for (int i = 0; i < allCard.getNumber(); i++) {
                factionCards.add(allCard);
            }
        }
        cards = Faction.getNeutralCards();
        for (AllCards allCard : cards) {
            for (int i = 0; i < allCard.getNumber(); i++) {
                factionCards.add(allCard);
            }
        }

        return factionCards;
    }

    public boolean noFactionSelected() {
        return faction == null;
    }

    public boolean isThisFactionSelected(String factionName) {
        if (faction == null) return false;
        else return faction.getAssetName().equals(factionName);
    }

    public String getFactionAssetName() {
        if (faction == null) return null;
        else return faction.getAssetName();
    }

    public boolean isThisLeaderSelected(String leaderName) {
        if (leader == null) return false;
        else return leader.getAssetName().equals(leaderName);
    }

    public void selectCard(AllCards allCard) {
        Card card = game.getClient().sendToServer(ServerCommand.SELECT_CARD, allCard);
        game.getLoggedInPlayer().getDeck().addCard(card);
    }

    public void deSelectCard(AllCards allCard) {
        Card card = game.getClient().sendToServer(ServerCommand.DE_SELECT_CARD, allCard);
        game.getLoggedInPlayer().getDeck().removeCard(card);
    }

    public int getHeroCount() {
        return game.getLoggedInPlayer().getDeck().getNumberOfHeroCards();
    }

    public int getSpellCount() {
        return game.getLoggedInPlayer().getDeck().getNumberOfSpecialCards();
    }

    public int getUnitCount() {
        return game.getLoggedInPlayer().getDeck().getNumberOfUnits();
    }
}
