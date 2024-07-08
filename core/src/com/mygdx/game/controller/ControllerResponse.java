package mygdx.game.controller;

public class ControllerResponse {
    private boolean isFail;
    private String errorMessage;

    public ControllerResponse(boolean isFail, String errorMessage) {
        this.isFail = isFail;
        this.errorMessage = errorMessage;
    }

    public boolean isFailed() {
        return this.isFail;
    }

    public String getError() {
        return this.errorMessage;
    }
}
