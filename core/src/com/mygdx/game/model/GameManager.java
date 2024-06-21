package com.mygdx.game.model;


import com.mygdx.game.model.card.*;

import static com.mygdx.game.model.card.Type.*;

public class GameManager {
    
    private PlayerInGame player1, player2, currentPlayer;
    // TODO arrayList of weatherCards

    public GameManager (Player player1, Player player2){
        this.player1 = new PlayerInGame(player1);
        this.player2 = new PlayerInGame(player2);
        this.currentPlayer = this.player1;
    }

    ///////// THE WTF PART
    /**
    public String placeCard (Card card , Position position) {
        if (!canPlaceCard(card , position)) {
            return "nashod place konam";
        }

        if (position.equals(Position.Front)){
            currentPlayer.addToMelee(card);
        } else if (position.equals(Position.Middle)){
            currentPlayer.addToRange(card);
        } else if (position.equals(Position.Back)){
            currentPlayer.addToSiege(card);
        } else if (position.equals(Position.SpecialFront)){
            currentPlayer.
        } else if (position.equals(Position.SpecialMiddle)){

        } else if (position.equals(Position.SpecialBack)){

        } else if (position.equals(Position.WeatherPlace)){

        }

        if (card.getPosition().equals(Type.CloseCombat)){

        } else if (card.getPosition().equals(Type.Agile)){

        } else if (card.getPosition().equals(Type.RangedCombat)){

        } else if (card.getPosition().equals(Type.Siege)){

        } else if (card.getPosition().equals(Type.Special)){

        } else if (card.getPosition().equals(Type.SpecialDuper)){

        } else {
            
        }
    }
    public String placeUnitCard (Card unitCard , Position position){
        if (!canPlaceUnitCard(unitCard, position)){
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
    /**/
    ///////// END OF WTF

    /// Functions for Placing the Cards
    public String placeCard (Card card, Position position) {
        if (position.equals(Position.Melee)) {
            return placeToMelee(card);
        } else if (position.equals(Position.Range)) {
            return placeToRange(card);
        } else if (position.equals(Position.Siege)) {
            return placeToSiege(card);
        } else if (position.equals(Position.SpellMelee)) {
            return placeToSpellMelee(card);
        } else if (position.equals(Position.SpellRange)) {
            return placeToSpellRange(card);
        } else if (position.equals(Position.SpellSiege)) {
            return placeToSpellSiege(card);
        } else if (position.equals(Position.WeatherPlace)) {
            return placeToWeather(card);
        } else {
            return "NANI?!";
        }
    }
    public String placeToMelee (Card card) {
        if (!canBePlacedToMelee(card)) {
            return "nemishe";
        }
        currentPlayer.addToMelee(card);
        return "ba movafaghiat remove shod";       
    }
    public String placeToRange (Card card) {
        if (!canBePlacedToRange(card)) {
            return "nemishe";
        }
        currentPlayer.addToRange(card);
        return "ba movafaghiat remove shod";
    }
    public String placeToSiege (Card card) {
        if (!canBePlacedToSiege(card)) {
            return "nemishe";
        }
        currentPlayer.addToSiege(card);
        return "ba movafaghiat remove shod";
    }
    public String placeToSpellMelee (Card card) {
        if (!canBePlacedToSpellMelee(card)) {
            return "nemishe";
        }
        currentPlayer.placeSpellMelee(card);
        return "ba movafaghiat remove shod";
    }
    public String placeToSpellRange (Card card) {
        if (!canBePlacedToSpellRange(card)) {
            return "nemishe";
        }
        currentPlayer.placeSpellRange(card);
        return "ba movafaghiat remove shod";
    }
    public String placeToSpellSiege (Card card) {
        if (!canBePlacedToSpellSiege(card)) {
            return "nemishe";
        }
        currentPlayer.placeSpellSiege(card);
        return "ba movafaghiat remove shod";
    }
    public String placeToWeather (Card card) {
        // TODO
        return "ba movafaghiat remove shod";
    }

    /// Functions for checking if we can place that Card
    public boolean canBePlacedToMelee (Card card) {
        if(card.getType().equals(Type.CloseCombat) || card.getType().equals(Type.Agile)){
            return true;
        }
        return false;
    }
    public boolean canBePlacedToRange (Card card) {
        if(card.getType().equals(Type.RangedCombat) || card.getType().equals(Type.Agile)){
            return true;
        }
        return false;
    }
    public boolean canBePlacedToSiege (Card card) {
        if(card.getType().equals(Type.Siege)){
            return true;
        }
        return false;
    }
    public boolean canBePlacedToSpellMelee (Card card) {
        if(card.getType().equals(Type.Spell) && currentPlayer.getMeleeSpell().equals(null)){
            return true;
        }
        return false;
    }
    public boolean canBePlacedToSpellRange (Card card) {
        if(card.getType().equals(Type.Spell) && currentPlayer.getRangeSpell().equals(null)){
            return true;
        }
        return true;
    }
    public boolean canBePlacedToSpellSiege (Card card) {
        if(card.getType().equals(Type.Spell) && currentPlayer.getSiegeSpell().equals(null)){
            return true;
        }
        return true;
    }
    public boolean canBePlacedToWeather (Card card) {
        // TODO
        return true;
    }

    // Comander's horn related functions
    public void meleeCurrentHpTimesInt (int number) {
        currentPlayer.meleeCurrentHpTimesInt(number);
    }
    public void rangeCurrentHpTimesInt (int number) {
        currentPlayer.rangeCurrentHpTimesInt(number);
    }
    public void siegeCurrentHpTimesInt (int number) {
        currentPlayer.siegeCurrentHpTimesInt(number);
    }






    public int getDeckInGameCount () {
        return currentPlayer.getDeckInGameCount();
    }
    public int getGraveyardCount () {
        return currentPlayer.getGraveyardCount();
    }
    public int getHandCount () {
        return currentPlayer.getHandCount();
    } 
    
    public boolean isTwoWeatherCardsSame () {
        // TODO
        return false;
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

}
