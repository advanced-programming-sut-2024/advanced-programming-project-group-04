package com.mygdx.game.controller;

public class LoginController {
    public static ControllerResponse signInButtonClicked(String username, String password) {
        boolean isFail = true;
        String errorMessage = "";

        return new ControllerResponse(isFail, errorMessage);
    }
}
