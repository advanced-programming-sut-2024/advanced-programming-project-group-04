package com.mygdx.game.controller.commands;

import mygdx.game.controller.commands.GameClientCommand;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameClientCommandTest {

    @Test
    public void testEnumValues() {
        GameClientCommand[] commands = GameClientCommand.values();
        assertEquals(13, commands.length);

        assertEquals(GameClientCommand.SET_IS_MY_TURN, commands[0]);
        assertEquals(GameClientCommand.CHANGE_TURN, commands[1]);
        assertEquals(GameClientCommand.SET_FACTION, commands[2]);
        assertEquals(GameClientCommand.SET_DECK, commands[3]);
        assertEquals(GameClientCommand.SET_LEADERS, commands[4]);
        assertEquals(GameClientCommand.SET_HANDS, commands[5]);
        assertEquals(GameClientCommand.ADD_SOURCE, commands[6]);
        assertEquals(GameClientCommand.UPDATE_SCORES, commands[7]);
        assertEquals(GameClientCommand.RESET_PASS_BUTTONS, commands[8]);
        assertEquals(GameClientCommand.REMOVE_FROM_VIEW, commands[9]);
        assertEquals(GameClientCommand.ADD_CARD_TO_TABLE_SECTION, commands[10]);
        assertEquals(GameClientCommand.ADD_CARD_TO_HAND, commands[11]);
        assertEquals(GameClientCommand.EOF, commands[12]);
    }

    @Test
    public void testEnumValueOf() {
        assertEquals(GameClientCommand.SET_IS_MY_TURN, GameClientCommand.valueOf("SET_IS_MY_TURN"));
        assertEquals(GameClientCommand.CHANGE_TURN, GameClientCommand.valueOf("CHANGE_TURN"));
        assertEquals(GameClientCommand.SET_FACTION, GameClientCommand.valueOf("SET_FACTION"));
        assertEquals(GameClientCommand.SET_DECK, GameClientCommand.valueOf("SET_DECK"));
        assertEquals(GameClientCommand.SET_LEADERS, GameClientCommand.valueOf("SET_LEADERS"));
        assertEquals(GameClientCommand.SET_HANDS, GameClientCommand.valueOf("SET_HANDS"));
        assertEquals(GameClientCommand.ADD_SOURCE, GameClientCommand.valueOf("ADD_SOURCE"));
        assertEquals(GameClientCommand.UPDATE_SCORES, GameClientCommand.valueOf("UPDATE_SCORES"));
        assertEquals(GameClientCommand.RESET_PASS_BUTTONS, GameClientCommand.valueOf("RESET_PASS_BUTTONS"));
        assertEquals(GameClientCommand.REMOVE_FROM_VIEW, GameClientCommand.valueOf("REMOVE_FROM_VIEW"));
        assertEquals(GameClientCommand.ADD_CARD_TO_TABLE_SECTION, GameClientCommand.valueOf("ADD_CARD_TO_TABLE_SECTION"));
        assertEquals(GameClientCommand.ADD_CARD_TO_HAND, GameClientCommand.valueOf("ADD_CARD_TO_HAND"));
        assertEquals(GameClientCommand.EOF, GameClientCommand.valueOf("EOF"));
    }
}
