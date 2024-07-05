package com.mygdx.game.controller;

import com.mygdx.game.Main;
import com.mygdx.game.model.Player;
import com.mygdx.game.view.LoginMenu;

import java.util.Random;

public class LoginController {
    private final Main game;
    private String username;
    private String password;
    private EmailSender emailSender;
    private LoginMenu loginMenu;


    public LoginController(Main game) {
        this.game = game;
        emailSender = new EmailSender();
    }

    public ControllerResponse signInButtonClicked(String username, String password) {
        boolean isFail = true;
        String errorMessage = "";


        if (username.isEmpty()) errorMessage = "Enter your username";
        else if (password.isEmpty()) errorMessage = "Enter your password";
        else {
            Client client = game.getClient();
            if (client.sendToServer(ServerCommand.DOES_USERNAME_EXIST, username).equals(false)) errorMessage = "No such player exists";
            else if (client.sendToServer(ServerCommand.HAS_ACTIVE_SESSION, username)) errorMessage = "Player logged in another device";
            else if (client.sendToServer(ServerCommand.VALIDATE_PASSWORD, username, password).equals(false)) errorMessage = "Wrong password";
            else {

                this.username = username;
                this.password = password;
                Player player = client.sendToServer(ServerCommand.FETCH_USER, username);
                if (!player.getTwoFAEnabled()) {
                    client.sendToServer(ServerCommand.LOGIN_PLAYER, player.getId());
                    game.setLoggedInPlayer(player);
                    isFail = false;
                }
                else {
                    Random random = new Random();
                    int verificationCode = 100000 + random.nextInt(900000);
                    String subject = "Email Verification";
                    String body = "Your verification code is: " + verificationCode;
                    emailSender.sendEmail(player.getEmail(), subject, body);
                    loginMenu.verifyVerificationCode(Integer.toString(verificationCode));
                }
            }
        }

        return new ControllerResponse(isFail, errorMessage);
    }

    public boolean verifyCode(String actualCode, String enteredCode) {
        return actualCode.equals(enteredCode);
    }

    public ControllerResponse verifyButtonPressed(String actualCode, String enteredCode) {
        boolean isVerified = verifyCode(actualCode, enteredCode);
        boolean isFail;
        String errorMessage = "";
        if (isVerified) {
            Player player = game.getClient().sendToServer(ServerCommand.FETCH_USER, username);
            isFail = false;
            game.getClient().sendToServer(ServerCommand.LOGIN_PLAYER, player.getId());
            game.setLoggedInPlayer(player);
        } else {
            isFail = true;
            errorMessage = "Wrong verification code";
        }
        return new ControllerResponse(isFail, errorMessage);
    }

    public void setLoginMenu(LoginMenu loginMenu) {
        this.loginMenu = loginMenu;
    }
}
