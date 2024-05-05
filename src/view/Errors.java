package view;

public enum Errors {
    INVALID_COMMAND("");
    final String errorMessage;

    Errors(String errorMessage) { this.errorMessage = errorMessage; }
}
