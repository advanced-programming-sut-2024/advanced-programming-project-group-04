package com.mygdx.game.controller;

import mygdx.game.Main;
import mygdx.game.controller.Client;
import mygdx.game.controller.ControllerResponse;
import mygdx.game.controller.MainMenuController;
import mygdx.game.controller.ServerCommand;
import mygdx.game.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class MainMenuControllerTest {
    private MainMenuController mainMenuController;
    private Main mockGame;
    private Player mockPlayer;
    private Client mockClient;

    @Before
    public void setUp() {
        mockGame = Mockito.mock(Main.class);
        mockPlayer = Mockito.mock(Player.class);
        mockClient = Mockito.mock(Client.class);
        Mockito.when(mockGame.getLoggedInPlayer()).thenReturn(mockPlayer);

        mainMenuController = new MainMenuController(mockGame);
        Mockito.when(mockGame.getClient()).thenReturn(mockClient);
    }

    @Test
    public void testLogout() {
        mainMenuController.logout();
        Mockito.verify(mockGame.getClient()).sendToServer(ServerCommand.LOGOUT_PLAYER, mockPlayer.getId());
        Mockito.verify(mockGame).setLoggedInPlayer(null);
    }

    @Test
    public void testStartNewGameFail() {
        Mockito.when(mockPlayer.canStartGame()).thenReturn(false);
        ControllerResponse response = mainMenuController.startNewGame();

        assertTrue(response.isFailed());
        assertEquals("Please complete your deck first", response.getError());
    }

    @Test
    public void testStartNewGameSuccess() {
        Mockito.when(mockPlayer.canStartGame()).thenReturn(true);
        ControllerResponse response = mainMenuController.startNewGame();

        assertFalse(response.isFailed());
        assertEquals("", response.getError());
    }
}
