package com.mygdx.game.model;

import mygdx.game.model.Deck;
import mygdx.game.model.GameResult;
import mygdx.game.model.Player;
import mygdx.game.model.Rank;
import mygdx.game.model.faction.Faction;
import mygdx.game.model.faction.Skellige;
import mygdx.game.model.message.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;
    private Player friend;
    private Deck deck;

    @Before
    public void setUp() {
        player = new Player("testuser", "testuser@example.com", "TestUser");
        friend = new Player("frienduser", "frienduser@example.com", "FriendUser");
        deck = new Deck();
    }

    @Test
    public void testSetUsername() {
        String newUsername = "newuser";
        player.setUsername(newUsername);
        assertEquals(newUsername, player.getUsername());
    }

    @Test
    public void testSetEmail() {
        String newEmail = "newuser@example.com";
        player.setEmail(newEmail);
        assertEquals(newEmail, player.getEmail());
    }

    @Test
    public void testSetNickname() {
        String newNickname = "NewUser";
        player.setNickname(newNickname);
        assertEquals(newNickname, player.getNickname());
    }

    @Test
    public void testSetAndGetPasswordQuestion() {
        String question = "What is your pet's name?";
        player.setForgetPasswordQuestion(question);
        assertEquals(question, player.getForgetPasswordQuestion());
    }

    @Test
    public void testSetAndGetAnswerToQuestion() {
        String answer = "Fluffy";
        player.setAnswerToQuestion(answer);
        assertTrue(player.validateAnswerToQuestion(answer));
        assertFalse(player.validateAnswerToQuestion("WrongAnswer"));
    }

    @Test
    public void testMaxScore() {
        player.setMaxScore(1000);
        assertEquals(1000, player.getMaxScore());
    }

    @Test
    public void testGameCount() {
        player.setGameCount(50);
        assertEquals(50, player.getGameCount());
    }

    @Test
    public void testWinCount() {
        player.setWinCount(30);
        assertEquals(30, player.getWinCount());
    }

    @Test
    public void testDrawCount() {
        player.setDrawCount(10);
        assertEquals(10, player.getDrawCount());
    }

    @Test
    public void testLossCount() {
        player.setLossCount(10);
        assertEquals(10, player.getLossCount());
    }

    @Test
    public void testMessageSending() {
        player.sendMessage(friend, "Hello, Friend!");
        assertTrue(player.getSentMessages().containsKey(friend));
        assertTrue(friend.getReceivedMessages().containsKey(player));
        assertEquals(1, player.getSentMessages().get(friend).size());
        assertEquals(1, friend.getReceivedMessages().get(player).size());
    }

    @Test
    public void testFriendRequests() {
        assertTrue(player.sendFriendRequest(friend));
        assertFalse(player.sendFriendRequest(friend)); // Already sent
        assertTrue(friend.getIncomingFriendRequests().contains(player));
        assertTrue(player.getOutgoingFriendRequests().contains(friend));

        assertTrue(friend.acceptFriendRequest(player));
        assertTrue(friend.getFriends().contains(player));
        assertTrue(player.getFriends().contains(friend));

        Player anotherFriend = new Player("anotheruser", "anotheruser@example.com", "AnotherUser");
        player.sendFriendRequest(anotherFriend);
        assertTrue(anotherFriend.rejectFriendRequest(player));
        assertFalse(anotherFriend.getIncomingFriendRequests().contains(player));
        assertFalse(player.getOutgoingFriendRequests().contains(anotherFriend));
    }

    @Test
    public void testTwoFA() {
        assertFalse(player.getTwoFAEnabled());
        player.toggleTwoFA();
        assertTrue(player.getTwoFAEnabled());
        player.toggleTwoFA();
        assertFalse(player.getTwoFAEnabled());
    }

    @Test
    public void testLPManagement() {
        player.addLP(100);
        assertEquals(100, player.getLP());

        player.addLP(-50);
        assertEquals(50, player.getLP());

        player.addLP(-100);
        assertEquals(0, player.getLP()); // LP should not go below 0
    }

    @Test
    public void testChatWithPlayer() {
        player.sendMessage(friend, "Hello, Friend!");
        player.sendMessage(friend, "How are you?");
        ArrayList<Message> chat = player.getChatWithPlayer(friend);
        assertEquals(2, chat.size());
    }
}
