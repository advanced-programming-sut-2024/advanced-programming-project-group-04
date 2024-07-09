package com.mygdx.game.controller;


import com.badlogic.gdx.Input;
import mygdx.game.controller.cheat.CheatController;
import mygdx.game.view.GameMenu;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

public class CheatControllerTest {
    private CheatController cheatController;
    private GameMenu mockGameMenu;

    @Before
    public void setUp() {
        mockGameMenu = Mockito.mock(GameMenu.class);
        cheatController = new CheatController(mockGameMenu);
    }

    @Test
    public void testKeyDownTab() {
        boolean result = cheatController.keyDown(Input.Keys.TAB);
        assertTrue(result);
        verify(mockGameMenu).toggleCheatConsole();
    }

    @Test
    public void testKeyDownEscapeWithCheatConsoleVisible() {
        Mockito.when(mockGameMenu.isCheatConsoleVisible()).thenReturn(true);

        boolean result = cheatController.keyDown(Input.Keys.ESCAPE);
        assertTrue(result);
        verify(mockGameMenu).hideCheatConsole();
    }

    @Test
    public void testKeyDownEscapeWithCheatConsoleNotVisible() {
        Mockito.when(mockGameMenu.isCheatConsoleVisible()).thenReturn(false);

        boolean result = cheatController.keyDown(Input.Keys.ESCAPE);
        assertFalse(result);
    }

    @Test
    public void testKeyDownOtherKey() {
        boolean result = cheatController.keyDown(Input.Keys.A);
        assertFalse(result);
    }

    @Test
    public void testKeyUp() {
        boolean result = cheatController.keyUp(Input.Keys.A);
        assertFalse(result);
    }

    @Test
    public void testKeyTyped() {
        boolean result = cheatController.keyTyped('a');
        assertFalse(result);
    }

    @Test
    public void testTouchDown() {
        boolean result = cheatController.touchDown(0, 0, 0, 0);
        assertFalse(result);
    }

    @Test
    public void testTouchUp() {
        boolean result = cheatController.touchUp(0, 0, 0, 0);
        assertFalse(result);
    }

    @Test
    public void testTouchDragged() {
        boolean result = cheatController.touchDragged(0, 0, 0);
        assertFalse(result);
    }

    @Test
    public void testMouseMoved() {
        boolean result = cheatController.mouseMoved(0, 0);
        assertFalse(result);
    }

    @Test
    public void testScrolled() {
        boolean result = cheatController.scrolled(0, 0);
        assertFalse(result);
    }

    @Test
    public void testTouchCancelled() {
        boolean result = cheatController.touchCancelled(0, 0, 0, 0);
        assertFalse(result);
    }
}
