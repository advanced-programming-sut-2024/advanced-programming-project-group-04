package com.mygdx.game.controller;

public class SignUpController {
    public static ControllerResponse signUpButtonPressed(String username, String email, String password) {
        boolean isFail = true;
        String errorMessage = "";

        return new ControllerResponse(isFail, errorMessage);
    }
}
