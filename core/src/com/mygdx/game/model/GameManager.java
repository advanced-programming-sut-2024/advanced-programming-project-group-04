package com.mygdx.game.model;


import java.util.ArrayList;

import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.card.Position;
import com.mygdx.game.model.card.specialcards.SpecialCard;
import com.mygdx.game.model.card.unitcards.UnitCard;

import static com.mygdx.game.model.card.Position.*;

public class GameManager {
    
    private PlayerInGame player1, player2, currentPlayer;
    // TODO arrayList of weatherCards

    public GameManager (Player player1, Player player2){
        this.player1 = new PlayerInGame(player1);
        this.player2 = new PlayerInGame(player2);
        this.currentPlayer = this.player1;
    }

    public String placeUnitCard (UnitCard unitCard , Position position){
        if (!canPlaceUnitCard(unitCard, position)){
            return "nashod place konam";
        }

        if (position == CloseCombat){
            currentPlayer.addToMelee(unitCard);
        } else if (position == Siege){
            currentPlayer.addToSiege(unitCard);
        } else if (position == RangedCombat){
            currentPlayer.addToRange(unitCard);
        } else {
            return "boosh miad";
        }
        
        return "ba movaghiat anjam shod";
    }
    public String placeSpecialCard (SpecialCard specialCard , Position position){
        if (!canPlaceSpecialCard(specialCard, position)){
            return "nashod place konam";
        }

        if (position == CloseCombat){
            currentPlayer.placeSpecialCardMelee(specialCard);
        } else if (position == Siege){
            currentPlayer.placeSpecialCardSiege(specialCard);
        } else if (position == RangedCombat){
            currentPlayer.placeSpecialCardRange(specialCard);
        } else {
            return "boosh miad";
        }

        return "ba movaghiat anjam shod";
    }
    public String placeWeatherCard () {
        return "OK";
    }

    public String removeUnitCard (UnitCard unitCard){
        return currentPlayer.removeUnitCard(unitCard);
    }
    public String removeSpecialCard (SpecialCard specialCard){
        return currentPlayer.removeSpecialCard(specialCard);
    }
    public String removeWeatherCard () {

        return "ba movafaghiat remove shod";
    }
    

    public int getRemainingCardsCount () {
        return currentPlayer.getRemainingCardsCount();
    }
    public int getGraveyardCount () {
        return currentPlayer.getGraveyardCount();
    }
    public int getHandCount () {
        return currentPlayer.getHandCount();
    } 
    public String endTurn () {
        // TODO

        switchTurn();
        return "OK";
    }

    public void switchTurn () {
        if(currentPlayer.equals(player1)){
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    public boolean canPlaceUnitCard (UnitCard unitCard , Position position){
        // TODO baad az neveshtan position card in kamel shavad
        return true;
    }

    public boolean canPlaceSpecialCard (SpecialCard specialCard , Position position){
        // TODO baad az neveshtan position card in kamel shavad
        return true;
    }

    // TODO canPlaceWeatherCard


}
