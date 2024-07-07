package com.mygdx.game.model;

public enum Rank {
    Challenger(400, "images/rank/challenger.png"),
    Diamond(300, "images/rank/diamond.png"),
    Gold(200, "images/rank/gold.png"),
    Silver(100, "images/rank/silver.png"),
    Bronze(0, "images/rank/bronze.png"),
    ;

    final int minimumLP;
    final String imageURL;

    Rank(int minimumLP, String imageURL) {
        this.minimumLP = minimumLP;
        this.imageURL = imageURL;
    }

    public int getMinimumLP() {
        return minimumLP;
    }

    public String getImageURL() {
        return imageURL;
    }


}
