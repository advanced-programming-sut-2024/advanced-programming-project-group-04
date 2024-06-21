package com.mygdx.game.controller;

import com.mygdx.game.model.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController {
    public static ControllerResponse signUpButtonPressed(String username, String password, String email, String nickname) {
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
            new Player(username, password, email, nickname);
            isFail = false;
            errorMessage = "Registered successfully";
        }

        return new ControllerResponse(isFail, errorMessage);
    }

    private static boolean isValidUsername(String username) {
        if (!username.matches("^[a-zA-Z0-9_]+$")) return false;
        else if (username.length() > 20) return false;
        return true;
    }

    private static boolean isValidPassword(String password) {
        if (password.contains(" ")) return false;
        else if (password.length() < 6 || password.length() > 20) return false;
        Matcher matcher;
        matcher = Pattern.compile("[!@#$%]").matcher(password);
        if (!matcher.find()) return false;
        matcher = Pattern.compile("[0-9]").matcher(password);
        if (!matcher.find()) return false;
        matcher = Pattern.compile("[A-Z]").matcher(password);
        if (!matcher.find()) return false;
        return true;
    }

    private static boolean isValidEmail(String email) {
        return email.matches("^.+@.+\\.com$");
    }

    private static boolean isValidNickname(String nickname) {
        if (nickname.length() > 20) return false;
        if (nickname.contains(" ")) return false;
        return true;
    }
}
