package com.mygdx.game.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import mygdx.game.controller.Client;
import mygdx.game.controller.GameController;
import mygdx.game.controller.commands.GameServerCommand;
import mygdx.game.model.Player;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.card.Card;
import mygdx.game.view.CustomTable;
import mygdx.game.view.GameMenu;
import mygdx.game.view.TableSection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static mygdx.game.controller.commands.GameServerCommand.PLACE_CARD;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class GameControllerTest {

    private GameMenu mockGameMenu;
    private Client mockClient;
    private Player mockPlayer;

    private GameController gameController;

    @Before
    public void setUp() {
        gameController = new GameController(mockGameMenu, mockClient, mockPlayer);
        mockGameMenu = Mockito.mock(GameMenu.class);
        gameController.setGameMenu(mockGameMenu);
    }

    @Test
    public void testIsMyTurn() {
        assertFalse(gameController.isMyTurn());
        gameController.setIsMyTurn(true);
        assertTrue(gameController.isMyTurn());
    }

    @Test
    public void testUpdateScores() {
//        PlayerInGame self = mock(PlayerInGame.class);
//        PlayerInGame enemy = mock(PlayerInGame.class);
//        assertTrue(gameController.updateScores(self, enemy));
//        verify(mockGameMenu).updateScores(self, enemy);
    }

    @Test
    public void testAddCardToHand() {
        CustomTable mockTable = Mockito.mock(CustomTable.class);
        HashMap<TableSection, CustomTable> mockHashMap = Mockito.mock(HashMap.class);
        Mockito.when(mockGameMenu.getAllTables()).thenReturn(mockHashMap);
        Mockito.when(mockHashMap.get(TableSection.MY_HAND)).thenReturn(mockTable);
        Card card = mock(Card.class);
        assertTrue(gameController.addCardToHand(card, false));
        verify(mockGameMenu).getAllTables();
    }

    @Test
    public void testChangeTurn() {
        gameController.setIsMyTurn(true);
        assertTrue(gameController.changeTurn());
        assertFalse(gameController.isMyTurn());
    }

    @Test
    public void testRemoveCardFromView() {
        Card card = mock(Card.class);
        assertTrue(gameController.removeCardFromView(card));
    }
}
