package com.mygdx.game.controller;

import mygdx.game.Main;
import mygdx.game.controller.Client;
import mygdx.game.controller.FactionAndLeaderController;
import mygdx.game.controller.commands.ServerCommand;
import mygdx.game.model.Deck;
import mygdx.game.model.Player;
import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;
import mygdx.game.model.faction.Faction;
import mygdx.game.model.leader.Leader;
import mygdx.game.view.FactionAndLeaderMenu;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FactionAndLeaderControllerTest {
    private FactionAndLeaderController factionAndLeaderController;
    private Main mockGame;
    private Client mockClient;
    private Player mockPlayer;
    private FactionAndLeaderMenu mockMenu;
    private Faction mockFaction;
    private Leader mockLeader;
    private Card mockCard;
    private AllCards mockAllCard;

    @Before
    public void setUp() {
        mockGame = Mockito.mock(Main.class);
        mockClient = Mockito.mock(Client.class);
        mockPlayer = Mockito.mock(Player.class);
        mockMenu = Mockito.mock(FactionAndLeaderMenu.class);
        mockFaction = Mockito.mock(Faction.class);
        mockLeader = Mockito.mock(Leader.class);
        mockCard = Mockito.mock(Card.class);

        Mockito.when(mockGame.getClient()).thenReturn(mockClient);
        Mockito.when(mockGame.getLoggedInPlayer()).thenReturn(mockPlayer);

        factionAndLeaderController = new FactionAndLeaderController(mockGame, mockMenu);
    }

    @Test
    public void testFactionButtonClicked() {
        Mockito.when(mockClient.sendToServer(ServerCommand.SELECT_FACTION, "Skellige")).thenReturn(mockFaction);
        Mockito.when(mockFaction.getName()).thenReturn("Skellige");

        factionAndLeaderController.factionButtonClicked("Skellige");

        Mockito.verify(mockMenu).updateCards();
        Mockito.verify(mockPlayer).createNewDeck();
        Mockito.verify(mockPlayer).setFaction(mockFaction);
        assertNull(factionAndLeaderController.getLeader());
        assertEquals(mockFaction, factionAndLeaderController.getFaction());
    }

    @Test
    public void testLeaderButtonClicked() {
        Mockito.when(mockClient.sendToServer(ServerCommand.SELECT_LEADER, "King Bran")).thenReturn(mockLeader);
        Mockito.when(mockLeader.getName()).thenReturn("King Bran");
        Mockito.when(mockPlayer.getDeck()).thenReturn(Mockito.mock(Deck.class));

        factionAndLeaderController.leaderButtonClicked("King Bran");

        Mockito.verify(mockPlayer.getDeck()).setLeader(mockLeader);
        assertEquals(mockLeader, factionAndLeaderController.getLeader());
    }


    @Test
    public void testNoFactionSelected() {
        factionAndLeaderController.setFaction(null);
        assertTrue(factionAndLeaderController.noFactionSelected());

        factionAndLeaderController.setFaction(mockFaction);
        assertFalse(factionAndLeaderController.noFactionSelected());
    }

    @Test
    public void testIsThisFactionSelected() {
        Mockito.when(mockFaction.getAssetName()).thenReturn("Skellige");

        factionAndLeaderController.setFaction(mockFaction);
        assertTrue(factionAndLeaderController.isThisFactionSelected("Skellige"));
        assertFalse(factionAndLeaderController.isThisFactionSelected("Nilfgaard"));
    }

    @Test
    public void testGetFactionAssetName() {
        Mockito.when(mockFaction.getAssetName()).thenReturn("Skellige");

        factionAndLeaderController.setFaction(mockFaction);
        assertEquals("Skellige", factionAndLeaderController.getFactionAssetName());

        factionAndLeaderController.setFaction(null);
        assertNull(factionAndLeaderController.getFactionAssetName());
    }

    @Test
    public void testIsThisLeaderSelected() {
        Mockito.when(mockLeader.getAssetName()).thenReturn("King Bran");

        factionAndLeaderController.setLeader(mockLeader);
        assertTrue(factionAndLeaderController.isThisLeaderSelected("King Bran"));
        assertFalse(factionAndLeaderController.isThisLeaderSelected("Eredin"));
    }

    @Test
    public void testSelectCard() {
        Mockito.when(mockClient.sendToServer(ServerCommand.SELECT_CARD, mockAllCard)).thenReturn(mockCard);
        Mockito.when(mockPlayer.getDeck()).thenReturn(Mockito.mock(Deck.class));

        factionAndLeaderController.selectCard(mockAllCard);

        Mockito.verify(mockPlayer.getDeck()).addCard(mockCard);
    }

    @Test
    public void testDeSelectCard() {
        Mockito.when(mockClient.sendToServer(ServerCommand.DE_SELECT_CARD, mockAllCard)).thenReturn(mockCard);
        Mockito.when(mockPlayer.getDeck()).thenReturn(Mockito.mock(Deck.class));

        factionAndLeaderController.deSelectCard(mockAllCard);

        Mockito.verify(mockPlayer.getDeck()).removeCard(mockCard);
    }

    @Test
    public void testGetHeroCount() {
        Deck mockDeck = Mockito.mock(Deck.class);
        Mockito.when(mockPlayer.getDeck()).thenReturn(mockDeck);
        Mockito.when(mockDeck.getNumberOfHeroCards()).thenReturn(3);

        assertEquals(3, factionAndLeaderController.getHeroCount());
    }

    @Test
    public void testGetSpellCount() {
        Deck mockDeck = Mockito.mock(Deck.class);
        Mockito.when(mockPlayer.getDeck()).thenReturn(mockDeck);
        Mockito.when(mockDeck.getNumberOfSpecialCards()).thenReturn(5);

        assertEquals(5, factionAndLeaderController.getSpellCount());
    }

    @Test
    public void testGetUnitCount() {
        Deck mockDeck = Mockito.mock(Deck.class);
        Mockito.when(mockPlayer.getDeck()).thenReturn(mockDeck);
        Mockito.when(mockDeck.getNumberOfUnits()).thenReturn(10);

        assertEquals(10, factionAndLeaderController.getUnitCount());
    }
}
