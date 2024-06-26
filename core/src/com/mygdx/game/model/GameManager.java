package com.mygdx.game.model;


import com.mygdx.game.model.ability.Spy;
import com.mygdx.game.model.card.*;

import static com.mygdx.game.model.card.Type.Weather;

import java.util.ArrayList;

public class GameManager {
    
    private PlayerInGame player1, player2, currentPlayer;

    private ArrayList<Card> weatherCards = new ArrayList<>();

    public GameManager (Player player1, Player player2){
        this.player1 = new PlayerInGame(player1);
        this.player2 = new PlayerInGame(player2);
        this.currentPlayer = this.player1;
    }

    /// Functions for Placing the Cards with position
    public boolean placeCard (Card card, Position position) {
        if (position.equals(Position.Melee)) {
            return addToMelee(card);
        } else if (position.equals(Position.Range)) {
            return addToRange(card);
        } else if (position.equals(Position.Siege)) {
            return addToSiege(card);
        } else if (position.equals(Position.SpellMelee)) {
            return placeToSpellMelee(card);
        } else if (position.equals(Position.SpellRange)) {
            return placeToSpellRange(card);
        } else if (position.equals(Position.SpellSiege)) {
            return placeToSpellSiege(card);
        } else if (position.equals(Position.WeatherPlace)) {
            return placeToWeather(card);
        } else {
            return false;
        }
    }

    /// Functions for Placing the Cards without the need of position
    public boolean placeCard(Card card) {
        //Kartaii ke faghat ye ja mitoonan place beshan ro place kon
        Type theCardType = card.getType();
        if (!(card.getAbility() instanceof Spy)){
            if (theCardType.equals(Type.Agile)) {
                return false;
            } else if (theCardType.equals(Type.CloseCombat)){
                return placeCard(card, Position.Melee);
            } else if (theCardType.equals(Type.RangedCombat)){
                return placeCard(card , Position.Range);
            } else if (theCardType.equals(Type.Siege)) {
                return placeCard(card , Position.Siege);
            } else if (theCardType.equals(Type.Spell)) {
                return false;
            } else if (theCardType.equals(Type.Weather)) {
                return placeCard(card , Position.WeatherPlace);
            } else {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }
    } 

    public boolean placeCardEnemy(Card card) {
        //Kartaii ke faghat ye ja mitoonan place beshan ro place kon faghat baraye spy
        Type theCardType = card.getType();

        PlayerInGame otherPlayer;
        if (currentPlayer.equals(player1)) {
            otherPlayer = player2;
        } else {
            otherPlayer = player1;
        }

        if ((card.getAbility() instanceof Spy)){
            if (theCardType.equals(Type.Agile)) {
                return false;
            } else if (theCardType.equals(Type.CloseCombat)){
                otherPlayer.addToMelee(card);
                return true;
            } else if (theCardType.equals(Type.RangedCombat)){
                otherPlayer.addToRange(card);
                return true;
            } else if (theCardType.equals(Type.Siege)) {
                otherPlayer.addToSiege(card);
                return true;
            } else if (theCardType.equals(Type.Spell)) {
                return false;
            } else if (theCardType.equals(Type.Weather)) {
                return false;
            } else {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }
    }

    public boolean addToMelee (Card card) {
        if (!canBeAddedToMelee(card)) {
            return false;
        }
        currentPlayer.addToMelee(card);
        return true;
    }
    public boolean addToRange (Card card) {
        if (!canBeAddedToRange(card)) {
            return false;
        }
        currentPlayer.addToRange(card);
        return true;
    }
    public boolean addToSiege (Card card) {
        if (!canBeAddedToSiege(card)) {
            return false;
        }
        currentPlayer.addToSiege(card);
        return true;
    }
    public boolean placeToSpellMelee (Card card) {
        if (!canBePlacedToSpellMelee(card)) {
            return false;
        }
        currentPlayer.placeSpellMelee(card);
        return true;
    }
    public boolean placeToSpellRange (Card card) {
        if (!canBePlacedToSpellRange(card)) {
            return false;
        }
        currentPlayer.placeSpellRange(card);
        return true;
    }
    public boolean placeToSpellSiege (Card card) {
        if (!canBePlacedToSpellSiege(card)) {
            return false;
        }
        currentPlayer.placeSpellSiege(card);
        return true;
    }
    public boolean placeToWeather (Card card) {
        if (!canBePlacedToWeather(card)) {
            return false;
        }
        weatherCards.add(card);
        return true;
    }
    

    public boolean removeCard (Card card) {
        for (Card sampleCard : weatherCards) {
            if (card.equals(sampleCard)) {
                return removeFromWeather(card);
            }
        }

        PlayerInGame otherPlayer;
        if (currentPlayer.equals(player1)) {
            otherPlayer = player2;
        } else {
            otherPlayer = player1;
        }

        Position forCurrentPlayer = findCardInGameForCurrentPlayer(card);
        Position forOtherPlayer = findCardInGameForOtherPlayer(card);

        if (forCurrentPlayer != null && forOtherPlayer == null) {
            currentPlayer.removeCard(card, forCurrentPlayer);
            return true;
        } else if (forCurrentPlayer == null && forOtherPlayer != null) {
            otherPlayer.removeCard(card, forOtherPlayer);
            return true;
        } else {
            return false;
        }
    }
    /* *
    public boolean removeCard (Card card, Position position) {
        if (position.equals(Position.WeatherPlace)) {
            return removeFromWeather(card);
        }
        currentPlayer.removeCard(card, position);
        return true;
    }
    /* */

    public boolean removeFromMelee (Card card) {
        if (!canBeAddedToMelee(card)) {
            return false;
        }
        currentPlayer.removeFromMelee(card);
        return true;
    }
    public boolean removeFromRange (Card card) {
        if (!canBeAddedToRange(card)) {
            return false;
        }
        currentPlayer.removeFromRange(card);
        return true;
    }
    public boolean removeFromSiege (Card card) {
        if (!canBeAddedToSiege(card)) {
            return false;
        }
        currentPlayer.removeFromSiege(card);
        return true;
    }
    public boolean removeSpellMelee (Card card) {
        if (!canBePlacedToSpellMelee(card)) {
            return false;
        }
        currentPlayer.removeSpellMelee(card);
        return true;
    }
    public boolean removeSpellRange (Card card) {
        if (!canBePlacedToSpellRange(card)) {
            return false;
        }
        currentPlayer.removeSpellRange(card);
        return true;
    }
    public boolean removeSpellSiege (Card card) {
        if (!canBePlacedToSpellSiege(card)) {
            return false;
        }
        currentPlayer.removeSpellSiege(card);
        return true;
    }

    public boolean removeFromWeather (Card card) {
        // TODO
        return false;
    }


    /// Functions for checking if we can place that Card
    public boolean canBeAddedToMelee (Card card) {
        if(card.getType().equals(Type.CloseCombat) || card.getType().equals(Type.Agile)){
            return true;
        }
        return false;
    }
    public boolean canBeAddedToRange (Card card) {
        if(card.getType().equals(Type.RangedCombat) || card.getType().equals(Type.Agile)){
            return true;
        }
        return false;
    }
    public boolean canBeAddedToSiege (Card card) {
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
        if (card.getType().equals(Type.Weather)) {
            return true;
        }
        return false;
    }

    public Position findCardInGameForCurrentPlayer(Card card){
        for (Card sampleCard : weatherCards) {
            if (card.equals(sampleCard)) {
                return Position.WeatherPlace;
            }
        }
        return currentPlayer.findCardInGame(card);
    }
    public Position findCardInGameForOtherPlayer(Card card){
        PlayerInGame otherPlayer;
        if (currentPlayer.equals(player1)) {
            otherPlayer = player2;
        } else {
            otherPlayer = player1;
        }

        for (Card sampleCard : weatherCards) {
            if (card.equals(sampleCard)) {
                return Position.WeatherPlace;
            }
        }
        return otherPlayer.findCardInGame(card);    
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
    public void someCardsCurrentHpTimesInt (int number , ArrayList<Card> someCards) {
        currentPlayer.someCardsCurrentHpTimesInt(number, someCards);
    }

    // Muster related functions // TODO : what the hell is wrong with muster ability
    public ArrayList<Card> getCardsWithSameNameFromHand (Card card) {
        return currentPlayer.getCardsWithSameNameFromHand(card);
    }
    public ArrayList<Card> getCardsWithSameNameFromDeckInGame (Card card) {
        return currentPlayer.getCardsWithSameNameFromDeckInGame(card);
    }
    public ArrayList<Card> getCardsWithSameNameFromGraveyard (Card card) {
        return currentPlayer.getCardsWithSameNameFromGraveyard(card);
    }
    public ArrayList<Card> getCardsWithSameNameFromMelee (Card card) {
        return currentPlayer.getCardsWithSameNameFromMelee(card);
    }
    public ArrayList<Card> getCardsWithSameNameFromRange (Card card) {
        return currentPlayer.getCardsWithSameNameFromRange(card);
    }
    public ArrayList<Card> getCardsWithSameNameFromSiege (Card card) {
        return currentPlayer.getCardsWithSameNameFromSiege(card);
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
    public ArrayList<Card> getWeatherCards () {
        return weatherCards;
    }

    public boolean endTurn () {
        // TODO

        switchTurn();
        return true;
    }

    public ArrayList<Card> getCardRowFromPosition(Position position) {
        if (position.equals(Position.WeatherPlace)) {
            return getWeatherCards();
        } else {
            return currentPlayer.getCardRowFromPosition(position);
        }
    }

    public void switchTurn () {
        if(currentPlayer.equals(player1)){
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }


    // Functions Related To Scorch
    public ArrayList<Card> getMaximumPowerInRow(Position position) {
        // BAYAD MAJMOO GHODRAT CARD HAYE GHEIR HERO TOYE OON RADIF OTHER PLAYER HESAB BEHSE
        // AGE IN ADDAD KAMTAR AZ 10 bood return null
        // AGE BISHTAR MOSAVI 10 BOOD TAMAM CARD HA BA BISHTARIN GHODRAT BE YE ARRAYLIST EZAFE VA RETURN SHAN
        PlayerInGame otherPlayer;
        if (currentPlayer.equals(player1)) {
            otherPlayer = player2;
        } else {
            otherPlayer = player1;
        }
        
        int sumOfNoneHeroPowers = 0;
        int maxOfCurrentHP = 0;
        ArrayList<Card> cardInRow = otherPlayer.getCardRowFromPosition(position);
        for (Card card : cardInRow) {
            if (!card.isHero()) {
                sumOfNoneHeroPowers += card.getCurrentHP();
                if (card.getCurrentHP() > maxOfCurrentHP) {
                    maxOfCurrentHP = card.getCurrentHP();
                }
            }
        }
        if (sumOfNoneHeroPowers < 10) {
            return null;
        }
        
        ArrayList<Card> theStrongests = new ArrayList<>();
        for (Card card : cardInRow) {
            if (!card.isHero()) {
                if (card.getCurrentHP() == maxOfCurrentHP) {
                    theStrongests.add(card);
                }
            }
        }
        return theStrongests;
    }

    public ArrayList<Card> getMaximumPowerInField() {
        //TODO @Arvin
        // TOYE IN MAJMOO GHORAT MOHEM NIST VALI HERO HA HAMCHENAN REMOVE NEMITOONAN BESHAN
        // AGE BISHTAR MOSAVI 10 BOOD TAMAM CARD HA BA BISHTARIN GHODRAT BE YE ARRAYLIST EZAFE VA RETURN SHAN

        // ARVIN IS WORKING HERE
        /* *
        PlayerInGame otherPlayer;
        if (currentPlayer.equals(player1)) {
            otherPlayer = player2;
        } else {
            otherPlayer = player1;
        }

        int maxOfCurrentHP = 0;
        ArrayList<Card> cardInRow = otherPlayer.getCardRowFromPosition(position);
        for (Card card : cardInRow) {
            if (!card.isHero()) {
                sumOfNoneHeroPowers += card.getCurrentHP();
                if (card.getCurrentHP() > maxOfCurrentHP) {
                    maxOfCurrentHP = card.getCurrentHP();
                }
            }
        }
        
        
        ArrayList<Card> theStrongests = new ArrayList<>();
        for (Card card : cardInRow) {
            if (!card.isHero()) {
                if (card.getCurrentHP() == maxOfCurrentHP) {
                    theStrongests.add(card);
                }
            }
        }
        return theStrongests;
        /* */

        return null;
    }

    public void drawRandomCardFromDeck() {
        currentPlayer.drawRandomCardFromDeckInGame();
    }

}
