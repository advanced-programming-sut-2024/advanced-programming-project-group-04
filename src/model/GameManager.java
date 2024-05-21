package model;


import model.card.Type;
import model.card.specialcards.SpecialCard;
import model.card.unitcards.UnitCard;

import static model.card.Type.*;

public class GameManager {
    
    private PlayerInGame player1, player2, currentPlayer;
    // TODO arrayList of weatherCards

    public GameManager (Player player1, Player player2){
        this.player1 = new PlayerInGame(player1);
        this.player2 = new PlayerInGame(player2);
        this.currentPlayer = this.player1;
    }

    public String placeUnitCard (UnitCard unitCard , Type type){
        if (!canPlaceUnitCard(unitCard, type)){
            return "nashod place konam";
        }

        if (type == CloseCombat){
            currentPlayer.addToMelee(unitCard);
        } else if (type == Siege){
            currentPlayer.addToSiege(unitCard);
        } else if (type == RangedCombat){
            currentPlayer.addToRange(unitCard);
        } else {
            return "boosh miad";
        }
        
        return "ba movaghiat anjam shod";
    }
    public String placeSpecialCard (SpecialCard specialCard , Type type){
        if (!canPlaceSpecialCard(specialCard, type)){
            return "nashod place konam";
        }

        if (type == CloseCombat){
            currentPlayer.placeSpecialCardMelee(specialCard);
        } else if (type == Siege){
            currentPlayer.placeSpecialCardSiege(specialCard);
        } else if (type == RangedCombat){
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

    public boolean canPlaceUnitCard (UnitCard unitCard , Type type){
        // TODO baad az neveshtan position card in kamel shavad
        return true;
    }

    public boolean canPlaceSpecialCard (SpecialCard specialCard , Type type){
        // TODO baad az neveshtan position card in kamel shavad
        return true;
    }

    // TODO canPlaceWeatherCard


}
