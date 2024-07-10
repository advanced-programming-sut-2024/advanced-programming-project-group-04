package com.mygdx.game.controller.commands;

import mygdx.game.controller.commands.ClientCommand;
import mygdx.game.controller.commands.GameServerCommand;
import mygdx.game.controller.commands.GeneralCommand;
import org.junit.Test;
import static org.junit.Assert.*;

public class EnumTest {

    @Test
    public void testClientCommandEnumValues() {
        ClientCommand[] commands = ClientCommand.values();
        assertEquals(1, commands.length);
        assertEquals(ClientCommand.START_GAME, commands[0]);
    }

    @Test
    public void testClientCommandEnumValueOf() {
        assertEquals(ClientCommand.START_GAME, ClientCommand.valueOf("START_GAME"));
    }

    @Test
    public void testGameServerCommandEnumValues() {
        GameServerCommand[] commands = GameServerCommand.values();
        assertEquals(16, commands.length);

        assertEquals(GameServerCommand.IS_MY_TURN, commands[0]);
        assertEquals(GameServerCommand.PASS_TURN, commands[1]);
        assertEquals(GameServerCommand.END_TURN, commands[2]);
        assertEquals(GameServerCommand.CAN_PLACE_CARD, commands[3]);
        assertEquals(GameServerCommand.CAN_PLACE_CARD_ENEMY, commands[4]);
        assertEquals(GameServerCommand.PLACE_CARD, commands[5]);
        assertEquals(GameServerCommand.PLACE_CARD_ENEMY, commands[6]);
        assertEquals(GameServerCommand.REMOVE_FROM_HAND, commands[7]);
        assertEquals(GameServerCommand.REMOVE_CARD, commands[8]);
        assertEquals(GameServerCommand.ADD_TO_HAND, commands[9]);
        assertEquals(GameServerCommand.ADD_A_LIFE_TO_ME, commands[10]);
        assertEquals(GameServerCommand.ADD_A_LIFE_TO_ENEMY, commands[11]);
        assertEquals(GameServerCommand.REMOVE_LIFE_FROM_ME, commands[12]);
        assertEquals(GameServerCommand.REMOVE_LIFE_FROM_ENEMY, commands[13]);
        assertEquals(GameServerCommand.DONE, commands[14]);
        assertEquals(GameServerCommand.EOF, commands[15]);
    }

    @Test
    public void testGameServerCommandEnumValueOf() {
        assertEquals(GameServerCommand.IS_MY_TURN, GameServerCommand.valueOf("IS_MY_TURN"));
        assertEquals(GameServerCommand.PASS_TURN, GameServerCommand.valueOf("PASS_TURN"));
        assertEquals(GameServerCommand.END_TURN, GameServerCommand.valueOf("END_TURN"));
        assertEquals(GameServerCommand.CAN_PLACE_CARD, GameServerCommand.valueOf("CAN_PLACE_CARD"));
        assertEquals(GameServerCommand.CAN_PLACE_CARD_ENEMY, GameServerCommand.valueOf("CAN_PLACE_CARD_ENEMY"));
        assertEquals(GameServerCommand.PLACE_CARD, GameServerCommand.valueOf("PLACE_CARD"));
        assertEquals(GameServerCommand.PLACE_CARD_ENEMY, GameServerCommand.valueOf("PLACE_CARD_ENEMY"));
        assertEquals(GameServerCommand.REMOVE_FROM_HAND, GameServerCommand.valueOf("REMOVE_FROM_HAND"));
        assertEquals(GameServerCommand.REMOVE_CARD, GameServerCommand.valueOf("REMOVE_CARD"));
        assertEquals(GameServerCommand.ADD_TO_HAND, GameServerCommand.valueOf("ADD_TO_HAND"));
        assertEquals(GameServerCommand.ADD_A_LIFE_TO_ME, GameServerCommand.valueOf("ADD_A_LIFE_TO_ME"));
        assertEquals(GameServerCommand.ADD_A_LIFE_TO_ENEMY, GameServerCommand.valueOf("ADD_A_LIFE_TO_ENEMY"));
        assertEquals(GameServerCommand.REMOVE_LIFE_FROM_ME, GameServerCommand.valueOf("REMOVE_LIFE_FROM_ME"));
        assertEquals(GameServerCommand.REMOVE_LIFE_FROM_ENEMY, GameServerCommand.valueOf("REMOVE_LIFE_FROM_ENEMY"));
        assertEquals(GameServerCommand.DONE, GameServerCommand.valueOf("DONE"));
        assertEquals(GameServerCommand.EOF, GameServerCommand.valueOf("EOF"));
    }

    @Test
    public void testGeneralCommandEnumValues() {
        GeneralCommand[] commands = GeneralCommand.values();
        assertEquals(1, commands.length);
        assertEquals(GeneralCommand.CLEAR, commands[0]);
    }

    @Test
    public void testGeneralCommandEnumValueOf() {
        assertEquals(GeneralCommand.CLEAR, GeneralCommand.valueOf("CLEAR"));
    }
}
