package com.mygdx.game.controller;

import mygdx.game.Main;
import mygdx.game.controller.Client;
import mygdx.game.controller.ControllerResponse;
import mygdx.game.controller.StartGameController;
import mygdx.game.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import mygdx.game.controller.commands.ServerCommand;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StartGameControllerTest {

    private Main mockGame;
    private Client mockClient;
    private Player mockPlayer;
    private StartGameController startGameController;

    @Before
    public void setUp() {
        mockGame = Mockito.mock(Main.class);
        mockClient = Mockito.mock(Client.class);
        mockPlayer = Mockito.mock(Player.class);
        when(mockGame.getClient()).thenReturn(mockClient);
        when(mockGame.getLoggedInPlayer()).thenReturn(mockPlayer);
        startGameController = new StartGameController(mockGame);
    }

    @Test
    public void testFindPlayerButtonClicked_Self() {
        when(mockPlayer.getUsername()).thenReturn("self");
        ControllerResponse response = startGameController.findPlayerButtonClicked("self");
        assertEquals("You can't start a game with yourself", response.getError());
        assertTrue(response.isFailed());
    }

    @Test
    public void testFindPlayerButtonClicked_PlayerNotFound() {
        when(mockPlayer.getUsername()).thenReturn("self");
        when(mockClient.sendToServer(ServerCommand.DOES_USERNAME_EXIST, "other")).thenReturn(false);
        ControllerResponse response = startGameController.findPlayerButtonClicked("other");
        assertEquals("Player not found", response.getError());
        assertTrue(response.isFailed());
    }

    @Test
    public void testFindPlayerButtonClicked_PlayerOffline() {
        when(mockPlayer.getUsername()).thenReturn("self");
        when(mockClient.sendToServer(ServerCommand.DOES_USERNAME_EXIST, "other")).thenReturn(true);
        when(mockClient.sendToServer(ServerCommand.IS_ONLINE, "other")).thenReturn(false);
        ControllerResponse response = startGameController.findPlayerButtonClicked("other");
        assertEquals("Player is offline", response.getError());
        assertTrue(response.isFailed());
    }

    @Test
    public void testFindPlayerButtonClicked_StartGameRequest() {
        when(mockPlayer.getUsername()).thenReturn("self");
        when(mockClient.sendToServer(ServerCommand.DOES_USERNAME_EXIST, "other")).thenReturn(true);
        when(mockClient.sendToServer(ServerCommand.IS_ONLINE, "other")).thenReturn(true);
        when(mockClient.sendToServer(ServerCommand.START_GAME_REQUEST, "other")).thenReturn(true);
        ControllerResponse response = startGameController.findPlayerButtonClicked("other");
        assertEquals(response.getError(), "");
        assertTrue(response.isFailed());
    }
}
