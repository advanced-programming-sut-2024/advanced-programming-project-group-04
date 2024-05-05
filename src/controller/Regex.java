package controller;

public enum Regex {
    TEMPLATE("");
    final String regexPattern;

    Regex(String regexPattern) { this.regexPattern = regexPattern; }
}
