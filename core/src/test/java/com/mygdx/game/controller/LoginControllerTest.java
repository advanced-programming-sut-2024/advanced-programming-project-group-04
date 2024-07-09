package com.mygdx.game.controller;

import mygdx.game.Main;
import mygdx.game.controller.*;
import mygdx.game.model.Player;
import mygdx.game.view.LoginMenu;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
public class LoginControllerTest {
    private LoginController loginController;
    private Main mockGame;
    private Client mockClient;
    private Player mockPlayer;
    private LoginMenu mockLoginMenu;
    private EmailSender mockEmailSender;

    @Before
    public void setUp() {
        mockGame = Mockito.mock(Main.class);
        mockClient = Mockito.mock(Client.class);
        mockPlayer = Mockito.mock(Player.class);
        mockLoginMenu = Mockito.mock(LoginMenu.class);
        mockEmailSender = Mockito.mock(EmailSender.class);

        loginController = new LoginController(mockGame);
        loginController.setLoginMenu(mockLoginMenu);

        Mockito.when(mockGame.getClient()).thenReturn(mockClient);
    }

    @Test
    public void testEmptyUsername() {
        ControllerResponse response = loginController.signInButtonClicked("", "password");
        assertTrue(response.isFailed());
        assertEquals("Enter your username", response.getError());
    }

    @Test
    public void testEmptyPassword() {
        ControllerResponse response = loginController.signInButtonClicked("username", "");
        assertTrue(response.isFailed());
        assertEquals("Enter your password", response.getError());
    }

    @Test
    public void testNoSuchPlayerExists() {
        Mockito.when(mockClient.sendToServer(ServerCommand.DOES_USERNAME_EXIST, "username")).thenReturn(false);

        ControllerResponse response = loginController.signInButtonClicked("username", "password");
        assertTrue(response.isFailed());
        assertEquals("No such player exists", response.getError());
    }

    @Test
    public void testPlayerLoggedInAnotherDevice() {
        Mockito.when(mockClient.sendToServer(ServerCommand.DOES_USERNAME_EXIST, "username")).thenReturn(true);
        Mockito.when(mockClient.sendToServer(ServerCommand.HAS_ACTIVE_SESSION, "username")).thenReturn(true);

        ControllerResponse response = loginController.signInButtonClicked("username", "password");
        assertTrue(response.isFailed());
        assertEquals("Player logged in another device", response.getError());
    }

    @Test
    public void testWrongPassword() {
        Mockito.when(mockClient.sendToServer(ServerCommand.DOES_USERNAME_EXIST, "username")).thenReturn(true);
        Mockito.when(mockClient.sendToServer(ServerCommand.HAS_ACTIVE_SESSION, "username")).thenReturn(false);
        Mockito.when(mockClient.sendToServer(ServerCommand.VALIDATE_PASSWORD, "username", "password")).thenReturn(false);

        ControllerResponse response = loginController.signInButtonClicked("username", "password");
        assertTrue(response.isFailed());
        assertEquals("Wrong password", response.getError());
    }

    @Test
    public void testSuccessfulLoginWithout2FA() {
        Mockito.when(mockClient.sendToServer(ServerCommand.DOES_USERNAME_EXIST, "username")).thenReturn(true);
        Mockito.when(mockClient.sendToServer(ServerCommand.HAS_ACTIVE_SESSION, "username")).thenReturn(false);
        Mockito.when(mockClient.sendToServer(ServerCommand.VALIDATE_PASSWORD, "username", "password")).thenReturn(true);
        Mockito.when(mockClient.sendToServer(ServerCommand.FETCH_USER, "username")).thenReturn(mockPlayer);
        Mockito.when(mockPlayer.getTwoFAEnabled()).thenReturn(false);

        ControllerResponse response = loginController.signInButtonClicked("username", "password");
        assertFalse(response.isFailed());
        assertEquals(response.getError(), "");
    }

    @Test
    public void testSuccessfulLoginWith2FA() {
        Mockito.when(mockClient.sendToServer(ServerCommand.DOES_USERNAME_EXIST, "username")).thenReturn(true);
        Mockito.when(mockClient.sendToServer(ServerCommand.HAS_ACTIVE_SESSION, "username")).thenReturn(false);
        Mockito.when(mockClient.sendToServer(ServerCommand.VALIDATE_PASSWORD, "username", "password")).thenReturn(true);
        Mockito.when(mockClient.sendToServer(ServerCommand.FETCH_USER, "username")).thenReturn(mockPlayer);
        Mockito.when(mockPlayer.getTwoFAEnabled()).thenReturn(true);
        Mockito.doNothing().when(mockEmailSender).sendEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.when(mockPlayer.getEmail()).thenReturn("email@example.com");


        ControllerResponse response = loginController.signInButtonClicked("username", "password");
        assertTrue(response.isFailed());
        assertEquals(response.getError(), "");
    }



    @Test
    public void testVerifyButtonPressed() {
        String actualCode = "123456";
        String enteredCode = "123456";

        Mockito.when(mockPlayer.getId()).thenReturn(1);
        Mockito.when(mockPlayer.getUsername()).thenReturn("username");

        Mockito.when(mockClient.sendToServer(ServerCommand.FETCH_USER, "username")).thenReturn(mockPlayer);

        LoginController loginControllerSpy = Mockito.spy(loginController);

        loginControllerSpy.setUsername("username");

        ControllerResponse response = loginControllerSpy.verifyButtonPressed(actualCode, enteredCode);
        assertFalse(response.isFailed());
        assertEquals("", response.getError());

        Mockito.verify(mockClient).sendToServer(ServerCommand.LOGIN_PLAYER, 1);
    }

    @Test
    public void testVerifyButtonPressedFail() {
        String actualCode = "123456";
        String enteredCode = "654321";

        ControllerResponse response = loginController.verifyButtonPressed(actualCode, enteredCode);
        assertTrue(response.isFailed());
        assertEquals("Wrong verification code", response.getError());
    }
}