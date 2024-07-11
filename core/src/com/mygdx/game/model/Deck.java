package mygdx.game.model;

import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;
import mygdx.game.model.leader.Leader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Deck implements Serializable {
    private Leader leader;
    private ArrayList<Card> cards = new ArrayList<>();
    private final int UNIT_MIN = 22, SPELL_MAX = 10;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public boolean isCardInDeck(Card card) {
        for (Card dummyCard : this.cards) {
            if (card.equals(dummyCard)) {
                return true;
            }
        }
        return false;
    }

    public void removeCard(Card card) {
        if (isCardInDeck(card)) {
            cards.remove(card);
        }
    }

    public Card removeCardFromAllCard(AllCards allCard) {
        Card targetCard = null;
        for (Card card : this.cards) {
            if (card.getAllCard().equals(allCard)) {
                targetCard = card;
                break;
            }
        }
        if (targetCard != null) removeCard(targetCard);
        return targetCard;
    }

    public boolean isNumberOfCardsValid() {
        return getNumberOfUnits() >= UNIT_MIN && getNumberOfSpecialCards() >= SPELL_MAX;
    }

    public int getNumberOfUnits() {
        int num = 0;
        for (Card card : this.cards) {
            if (card.isUnitCard()) num++;
        }
        return num;
    }

    public int getNumberOfSpecialCards() {
        int num = 0;
        for (Card card : this.cards) {
            if (!card.isUnitCard()) num++;
        }
        return num;
    }

    public int getNumberOfHeroCards() {
        int num = 0;
        for (Card card : this.cards) {
            if (card.isHero()) num++;
        }
        return num;
    }

    public Leader getLeader() {
        return this.leader;
    }

    public boolean isValid() {
        if (leader == null) return false;
        else return isNumberOfCardsValid();
    }

    public void shuffleIds() {
        Random random = new Random();
        for (Card card : this.cards) {
            int newId = random.nextInt();
            System.out.println("Generated id by deck: " + newId);
            card.setId(newId);
        }
    }
}
