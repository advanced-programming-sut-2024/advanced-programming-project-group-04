package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.model.Player;
import com.mygdx.game.view.SignUpMenu;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController {
    private EmailSender emailSender;
    private SignUpMenu signUpView;
    private String username;
    private String password;
    private String email;
    private String nickname;



<<<<<<< Updated upstream

    public SignUpController() {
        String gmailAccount = "gwent.2fa@gmail.com";
        String appPassword = "kcnq fryl ofis nhdv";
        emailSender = new EmailSender(gmailAccount, appPassword);
=======
    public SignUpController(Client client) {
        emailSender = new EmailSender();
        this.client = client;
>>>>>>> Stashed changes
    }
    public ControllerResponse signUpButtonPressed(String username, String password, String email, String nickname) {
        Random random = new Random();
        int verificationCode = 100000 + random.nextInt(900000);

        boolean isFail = true;
        String errorMessage = "";

        if (username.isEmpty()) errorMessage = "Username is necessary";
        else if (password.isEmpty()) errorMessage = "Password is necessary";
        else if (email.isEmpty()) errorMessage = "Please enter your email";
        else if (nickname.isEmpty()) errorMessage = "Please choose a nickname";
        else if (!isValidUsername(username)) errorMessage = "Invalid username";
        else if (Player.findPlayerByUsername(username) != null) errorMessage = "Username is taken";
        else if (!isValidPassword(password)) errorMessage = "Weak password";
        else if (!isValidEmail(email)) errorMessage = "Invalid email";
        else if (!isValidNickname(nickname)) errorMessage = "Invalid nickname";
        else {
<<<<<<< Updated upstream
            new Player(username, password, email, nickname);
            isFail = false;
            errorMessage = "Registered successfully";
=======
            this.username = username;
            this.nickname = nickname;
            this.password = password;
            this.email = email;
>>>>>>> Stashed changes
            String subject = "Email Verification";
            String body = "Your verification code is: " + verificationCode;
            emailSender.sendEmail(email, subject, body);

            signUpView.verifyVerificationCode(Integer.toString(verificationCode));

        }

        return new ControllerResponse(isFail, errorMessage);
    }

    private boolean isValidUsername(String username) {
        if (!username.matches("^[a-zA-Z0-9_]+$")) return false;
        else if (username.length() > 20) return false;
        return true;
    }

    private boolean isValidPassword(String password) {
        if (password.contains(" ")) return false;
        else if (password.length() < 6 || password.length() > 20) return false;
        Matcher matcher;
        matcher = Pattern.compile("[!@#$%]").matcher(password);
        if (!matcher.find()) return false;
        matcher = Pattern.compile("[0-9]").matcher(password);
        if (!matcher.find()) return false;
        matcher = Pattern.compile("[a-zA-Z]").matcher(password);
        if (!matcher.find()) return false;
        return true;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^.+@.+\\.com$");
    }

    private boolean isValidNickname(String nickname) {
        if (nickname.length() > 20) return false;
        if (nickname.contains(" ")) return false;
        return true;
    }

    public boolean verifyCode(String actualCode, String enteredCode) {
        return actualCode.equals(enteredCode);
    }

    public void setSignUpView(SignUpMenu signUpMenu) {
        this.signUpView = signUpMenu;
    }

    public ControllerResponse verifyButtonPressed(String actualCode, String enteredCode) {
        boolean isVerified = verifyCode(actualCode, enteredCode);
        boolean isFail;
        String errorMessage = "";
        if (isVerified) {
            client.sendToServer(ServerCommand.REGISTER_USER, username, password, email, nickname);
            isFail = false;
            errorMessage = "Registered Successfully";
        } else {
            isFail = true;
            errorMessage = "Wrong verification code";
        }
        return new ControllerResponse(isFail, errorMessage);
    }
}
