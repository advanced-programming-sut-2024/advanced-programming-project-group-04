package com.mygdx.game.controller;

import mygdx.game.controller.*;
import mygdx.game.model.Player;
import mygdx.game.view.SignUpMenu;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class SignUpControllerTest {
    private SignUpController signUpController;
    private Client mockClient;
    private Server mockServer;
    private SignUpMenu mockSignUpMenu;
    private EmailSender mockEmailSender;
    private Player mockPlayer;

    @Before
    public void setUp() {
        mockClient = Mockito.mock(Client.class);
        mockSignUpMenu = Mockito.mock(SignUpMenu.class);
        mockEmailSender = Mockito.mock(EmailSender.class);

        signUpController = new SignUpController(mockClient);
        signUpController.setSignUpView(mockSignUpMenu);
    }

    @Test
    public void testEmptyUsername() {
        ControllerResponse response = signUpController.signUpButtonPressed("", "password", "email@example.com", "");
        assertTrue(response.isFailed());
        assertEquals("Username is necessary", response.getError());
    }

    @Test
    public void testEmptyPassword() {
        ControllerResponse response = signUpController.signUpButtonPressed("username", "", "email@example.com", "nickname");
        assertTrue(response.isFailed());
        assertEquals("Password is necessary", response.getError());
    }

    @Test
    public void testEmptyEmail() {
        ControllerResponse response = signUpController.signUpButtonPressed("username", "password", "", "nickname");
        assertTrue(response.isFailed());
        assertEquals("Please enter your email", response.getError());
    }

    @Test
    public void testEmptyNickname() {
        ControllerResponse response = signUpController.signUpButtonPressed("username", "password", "email@example.com", "");
        assertTrue(response.isFailed());
        assertEquals("Please choose a nickname", response.getError());
    }

    @Test
    public void testInvalidUsername() {
        ControllerResponse response = signUpController.signUpButtonPressed("invalid username!", "password", "email@example.com", "nickname");
        assertTrue(response.isFailed());
        assertEquals("Invalid username", response.getError());
    }

    @Test
    public void testUsernameExists() {
        Mockito.when(mockClient.sendToServer(ServerCommand.DOES_USERNAME_EXIST, "username")).thenReturn(true);

        ControllerResponse response = signUpController.signUpButtonPressed("username", "password", "email@example.com", "nickname");
        assertTrue(response.isFailed());
        assertEquals("Username is taken", response.getError());
    }

    @Test
    public void testInvalidPassword() {
        Mockito.when(mockClient.sendToServer(ServerCommand.DOES_USERNAME_EXIST, "username")).thenReturn(false);
        ControllerResponse response = signUpController.signUpButtonPressed("username", "123", "email@example.com", "nickname");
        assertTrue(response.isFailed());
        assertEquals("Weak password", response.getError());
    }

    @Test
    public void testInvalidEmail() {
        Mockito.when(mockClient.sendToServer(ServerCommand.DOES_USERNAME_EXIST, "username")).thenReturn(false);
        ControllerResponse response = signUpController.signUpButtonPressed("username", "password123!", "invalid-email", "nickname");
        assertTrue(response.isFailed());
        assertEquals("Invalid email", response.getError());
    }

    @Test
    public void testInvalidNickname() {
        Mockito.when(mockClient.sendToServer(ServerCommand.DOES_USERNAME_EXIST, "username")).thenReturn(false);
        ControllerResponse response = signUpController.signUpButtonPressed("username", "password123!", "email@example.com", "salam aleyk");
        assertTrue(response.isFailed());
        assertEquals("Invalid nickname", response.getError());
    }

    @Test
    public void testSuccessfulSignUp() {
        Mockito.when(mockClient.sendToServer(ServerCommand.DOES_USERNAME_EXIST, "username")).thenReturn(false);
        Mockito.doNothing().when(mockEmailSender).sendEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

        SignUpController signUpControllerSpy = Mockito.spy(signUpController);

        ControllerResponse response = signUpControllerSpy.signUpButtonPressed("username", "Password123!", "email@example.com", "nickname");

        assertTrue(response.isFailed()); // Ww should fail but if we have no message that means we are waiting for verification code
        assertEquals("", response.getError());
    }


    @Test
    public void testVerifyButtonPressedPass() {
        String actualCode = "123456";
        String enteredCode = "123456";

        Mockito.when(mockClient.sendToServer(Mockito.eq(ServerCommand.FETCH_USER), Mockito.anyString())).thenReturn(mockPlayer);
        Mockito.when(mockClient.sendToServer(ServerCommand.REGISTER_USER, "username", "Password123!", "email@example.com", "nickname")).thenReturn(true);

        SignUpController signUpControllerSpy = Mockito.spy(signUpController);

        ControllerResponse response = signUpControllerSpy.verifyButtonPressed(actualCode, enteredCode);

        assertFalse(response.isFailed());
        assertEquals("Registered Successfully", response.getError());
    }

    @Test
    public void testVerifyButtonPressedFail() {
        String actualCode = "123456";
        String enteredCode = "654321";

        Mockito.when(mockClient.sendToServer(Mockito.eq(ServerCommand.FETCH_USER), Mockito.anyString())).thenReturn(mockPlayer);
        Mockito.when(mockClient.sendToServer(ServerCommand.REGISTER_USER, "username", "Password123!", "email@example.com", "nickname")).thenReturn(true);

        SignUpController signUpControllerSpy = Mockito.spy(signUpController);

        ControllerResponse response = signUpControllerSpy.verifyButtonPressed(actualCode, enteredCode);

        assertTrue(response.isFailed());
        assertEquals("Wrong verification code", response.getError());
    }

    @Test
    public void testVerifyCode() {
        String actualCode = "123456";
        String enteredCode = "654321";
        assertFalse(signUpController.verifyCode(actualCode, enteredCode));
    }

}
