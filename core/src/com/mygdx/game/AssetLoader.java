package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.model.card.AllCards;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetLoader {
    public static final String BACKGROUND = "background.png";
    public static final String MUSIC = "track1.mp3";
    public static final String SKIN = "skins/neon/skin/default.json";

    private AssetManager assetManager;
    private Map<String, List<String>> factionLeadersMap;
    private List<String> factionList;
    private Map<String, String> factionExplanations = new HashMap<>();
    private Map<String, String> leaderExplanations = new HashMap<>();


    public AssetLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.factionLeadersMap = new HashMap<>();
        this.factionList = new ArrayList<>();
    }

    public void loadAll() {
        assetManager.load(BACKGROUND, Texture.class);
        assetManager.load(MUSIC, Music.class);
        assetManager.load(SKIN, Skin.class);

        // Load factions
        FileHandle factionDir = Gdx.files.internal("assets/images/factions/");
        for (FileHandle file : factionDir.list()) {
            String factionPath = "images/factions/" + file.name();
            if (!factionPath.endsWith(".txt")) {
                factionList.add(factionPath);
                assetManager.load(factionPath, Texture.class);
            } else {
                FileHandle explanationFile = Gdx.files.internal(factionPath);
                if (explanationFile.exists()) {
                    String explanationText = explanationFile.readString();
                    factionPath = factionPath.replace(".txt", ".png");
                    factionExplanations.put(factionPath, explanationText);
                }
            }
        }

        // Load cards
        FileHandle cardsDir = Gdx.files.internal("assets/images/cards/");
        for (FileHandle file : cardsDir.list()) {
            String cardPath = "images/cards/" + file.name();
            assetManager.load(cardPath, Texture.class);
        }


        // Load leaders
        FileHandle leadersDir = Gdx.files.internal("assets/images/leaders/");
        for (FileHandle factionFile : factionDir.list()) {
            String factionName = factionFile.nameWithoutExtension();
            FileHandle factionLeadersDir = leadersDir.child(factionName);
            List<String> leaders = new ArrayList<>();
            for (FileHandle leaderFile : factionLeadersDir.list()) {
                String leaderPath = "images/leaders/" + factionName + "/" + leaderFile.name();
                if (!leaderPath.endsWith(".txt")) {
                    leaders.add(leaderPath);
                    assetManager.load(leaderPath, Texture.class);
                } else {
                    FileHandle explanationFile = Gdx.files.internal(leaderPath);
                    if (explanationFile.exists()) {
                        String explanationText = explanationFile.readString();
                        leaderPath = leaderPath.replace(".txt", ".jpg");
                        leaderExplanations.put(leaderPath, explanationText);
                    }
                }
            }
            factionLeadersMap.put(factionName, leaders);
        }
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public List<String> getFactions() {
        return factionList;
    }

    public String getFactionExplanation(String factionPath) {
        return factionExplanations.getOrDefault(factionPath, "No explanation available.");
    }

    public String getLeaderExplanation(String leaderPath) {
        return leaderExplanations.getOrDefault(leaderPath, "No explanation available.");
    }

    public List<String> getLeaders(String faction) {
        return factionLeadersMap.get(faction);
    }

    public Texture getImageFromAllCard(AllCards allCard) {
        String path = "images/cards/" + allCard.name() + ".jpg";
        return assetManager.get(path, Texture.class);
    }
}
