package mygdx.game.model;

import mygdx.game.model.ability.Spy;
import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;
import mygdx.game.model.card.Type;
import mygdx.game.model.faction.Monsters;
import mygdx.game.model.faction.Nilfgaard;
import mygdx.game.model.faction.NorthernRealms;
import mygdx.game.controller.GameServer;

import java.util.ArrayList;


public class GameManager {

    private final PlayerInGame player1, player2;
    private PlayerInGame currentPlayer;
    private final GameServer gameServer;
    private int turnNumber;

    private final ArrayList<Card> weatherCards = new ArrayList<>();

    public GameManager(Player player1, Player player2, GameServer gameServer) {
        this.player1 = new PlayerInGame(player1);
        this.player2 = new PlayerInGame(player2);
        this.gameServer = gameServer;
        this.currentPlayer = this.player1;
        this.turnNumber = 0;
    }

    public PlayerInGame getPlayer1() {
        return this.player1;
    }

    public PlayerInGame getPlayer2() {
        return this.player2;
    }

    public boolean canPlaceCard(Card card, Position position) {
        if (card.getAbility() instanceof Spy) return false;
        else return canPlaceTypeToPosition(card, position);
    }

    public boolean canPlaceCardEnemy(Card card, Position position) {
        if (!(card.getAbility() instanceof Spy)) return false;
        else return canPlaceTypeToPosition(card, position);
    }

    public boolean canPlaceTypeToPosition(Card card, Position position) {
        if (position.equals(Position.Melee)) {
            return canBeAddedToMelee(card);
        } else if (position.equals(Position.Range)) {
            return canBeAddedToRange(card);
        } else if (position.equals(Position.Siege)) {
            return canBeAddedToSiege(card);
        } else if (position.equals(Position.SpellMelee)) {
            return canBePlacedToSpellMelee(card);
        } else if (position.equals(Position.SpellRange)) {
            return canBePlacedToSpellRange(card);
        } else if (position.equals(Position.SpellSiege)) {
            return canBePlacedToSpellSiege(card);
        } else if (position.equals(Position.WeatherPlace)) {
            return canBePlacedToWeather(card);
        } else {
            return false;
        }
    }

    /// Functions for Placing the Cards with position
    public boolean placeCard(Card card, Position position) {
        System.out.println("GameManager placeCard 1: " + card.getName() + " " + position.toString());
        boolean flag;
        if (position.equals(Position.Melee)) {
            flag = addToMelee(card);
        } else if (position.equals(Position.Range)) {
            flag = addToRange(card);
        } else if (position.equals(Position.Siege)) {
            flag = addToSiege(card);
        } else if (position.equals(Position.SpellMelee)) {
            flag = placeToSpellMelee(card);
        } else if (position.equals(Position.SpellRange)) {
            flag = placeToSpellRange(card);
        } else if (position.equals(Position.SpellSiege)) {
            flag = placeToSpellSiege(card);
        } else if (position.equals(Position.WeatherPlace)) {
            flag = placeToWeather(card);
        } else {
            flag = false;
        }
        System.out.println("GameManager placeCard 2");

        if (!flag) {
            return false;
        }
        System.out.println("GameManager placeCard 3");
        if (!card.isBerserker() && !card.isCardsAbilityPassive() && !card.isTransformer()) {
            card.getAbility().run(this, card);
        }
        return true;
    }

    /// Functions for Placing the Cards without the need of position
    public boolean placeCard(Card card) {
        //Kartaii ke faghat ye ja mitoonan place beshan ro place kon
        Type theCardType = card.getType();
        boolean flag;
        if (!(card.getAbility() instanceof Spy)) {
            if (theCardType.equals(Type.Agile)) {
                flag = false;
            } else if (theCardType.equals(Type.CloseCombat)) {
                flag = placeCard(card, Position.Melee);
            } else if (theCardType.equals(Type.RangedCombat)) {
                flag = placeCard(card, Position.Range);
            } else if (theCardType.equals(Type.Siege)) {
                flag = placeCard(card, Position.Siege);
            } else if (theCardType.equals(Type.Spell)) {
                flag = false;
            } else if (theCardType.equals(Type.Weather)) {
                flag = placeCard(card, Position.WeatherPlace);
            } else {
                throw new RuntimeException();
            }
        } else {
            flag = false;
        }

        if (!flag) return false;

        if (!card.isBerserker() && !card.isCardsAbilityPassive() && !card.isTransformer()) {
            card.getAbility().run(this, card);
        }
        return true;
    }

    public boolean placeCardEnemy(Card card) {
        //Kartaii ke faghat ye ja mitoonan place beshan ro place kon faghat baraye spy
        Type theCardType = card.getType();

        PlayerInGame otherPlayer = getOtherPlayer();
        boolean flag;
        if ((card.getAbility() instanceof Spy)) {
            if (theCardType.equals(Type.Agile)) {
                flag = false;
            } else if (theCardType.equals(Type.CloseCombat)) {
                otherPlayer.addToMelee(card);
                gameServer.addCardToTableSection(card, Position.Melee, true);
                flag = true;
            } else if (theCardType.equals(Type.RangedCombat)) {
                otherPlayer.addToRange(card);
                gameServer.addCardToTableSection(card, Position.Range, true);
                flag = true;
            } else if (theCardType.equals(Type.Siege)) {
                otherPlayer.addToSiege(card);
                gameServer.addCardToTableSection(card, Position.Siege, true);
                flag = true;
            } else if (theCardType.equals(Type.Spell)) {
                flag = false;
            } else if (theCardType.equals(Type.Weather)) {
                flag = false;
            } else {
                throw new RuntimeException();
            }
        } else {
            flag = false;
        }

        if (!flag) return false;

        card.getAbility().run(this, card);
        return true;
    }

    public boolean addToMelee(Card card) {
        if (!canBeAddedToMelee(card)) {
            return false;
        }
        currentPlayer.addToMelee(card);
        gameServer.addCardToTableSection(card, Position.Melee, false);
        return true;
    }

    public boolean addToRange(Card card) {
        if (!canBeAddedToRange(card)) {
            return false;
        }
        currentPlayer.addToRange(card);
        gameServer.addCardToTableSection(card, Position.Range, false);
        return true;
    }

    public boolean placeCardEnemyButNotSpy(Card card) {
        // TODO @Arman: connect to server
//        Type theCardType = card.getType();
//
//        PlayerInGame otherPlayer = getOtherPlayer();
//        boolean flag;
//
//        if (theCardType.equals(Type.Agile)) {
//            otherPlayer.addToMelee(card);
//            gameController.addCardToTableSection(card, Position.Melee, true);
//            flag = true;
//        } else if (theCardType.equals(Type.CloseCombat)) {
//            otherPlayer.addToMelee(card);
//            gameController.addCardToTableSection(card, Position.Melee, true);
//            flag = true;
//        } else if (theCardType.equals(Type.RangedCombat)) {
//            otherPlayer.addToRange(card);
//            gameController.addCardToTableSection(card, Position.Range, true);
//            flag = true;
//        } else if (theCardType.equals(Type.Siege)) {
//            otherPlayer.addToSiege(card);
//            gameController.addCardToTableSection(card, Position.Siege, true);
//            flag = true;
//        } else if (theCardType.equals(Type.Spell)) {
//            flag = false;
//        } else if (theCardType.equals(Type.Weather)) {
//            flag = false;
//        } else {
//            throw new RuntimeException();
//        }
//        return flag;
        return false;
    }

    public boolean addToSiege(Card card) {
        if (!canBeAddedToSiege(card)) {
            return false;
        }
        currentPlayer.addToSiege(card);
        gameServer.addCardToTableSection(card, Position.Siege, false);
        return true;
    }

    public boolean placeToSpellMelee(Card card) {
        if (!canBePlacedToSpellMelee(card)) {
            return false;
        }
        currentPlayer.placeSpellMelee(card);
        gameServer.addCardToTableSection(card, Position.SpellMelee, false);
        return true;
    }

    public boolean placeToSpellRange(Card card) {
        if (!canBePlacedToSpellRange(card)) {
            return false;
        }
        currentPlayer.placeSpellRange(card);
        gameServer.addCardToTableSection(card, Position.SpellRange, false);
        return true;
    }

    public boolean placeToSpellSiege(Card card) {
        if (!canBePlacedToSpellSiege(card)) {
            return false;
        }
        currentPlayer.placeSpellSiege(card);
        gameServer.addCardToTableSection(card, Position.SpellSiege, false);
        return true;
    }

    public boolean placeToWeather(Card card) {
        if (!canBePlacedToWeather(card)) {
            return false;
        }
        weatherCards.add(card);
        gameServer.addCardToTableSection(card, Position.WeatherPlace, false);
        return true;
    }

    public Position findCardInGame(Card card) {
        PlayerInGame otherPlayer = getOtherPlayer();

        Position forCurrentPlayer = findCardInGameForCurrentPlayer(card);
        Position forOtherPlayer = findCardInGameForOtherPlayer(card);

        if (forCurrentPlayer != null && forOtherPlayer == null) {
            return forCurrentPlayer;
        } else if (forCurrentPlayer == null && forOtherPlayer != null) {
            return forOtherPlayer;
        } else {
            throw new RuntimeException("Can't find card in game");
        }
    }

    public boolean removeCard(Card card) {
        for (Card sampleCard : weatherCards) {
            if (card.equals(sampleCard)) {
                return removeFromWeather(card);
            }
        }

        PlayerInGame otherPlayer = getOtherPlayer();

        Position forCurrentPlayer = findCardInGameForCurrentPlayer(card);
        Position forOtherPlayer = findCardInGameForOtherPlayer(card);

        if (forCurrentPlayer != null && forOtherPlayer == null) {
            currentPlayer.removeCard(card, forCurrentPlayer);
            currentPlayer.addToGraveyard(card);
            gameServer.removeCardFromView(card);
            return true;
        } else if (forCurrentPlayer == null && forOtherPlayer != null) {
            otherPlayer.removeCard(card, forOtherPlayer);
            otherPlayer.addToGraveyard(card);
            gameServer.removeCardFromView(card);
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

    public boolean removeFromMelee(Card card) {
        if (!canBeAddedToMelee(card)) {
            return false;
        }
        currentPlayer.removeFromMelee(card);
        currentPlayer.addToGraveyard(card);
        gameServer.removeCardFromView(card);
        return true;
    }

    public boolean removeFromRange(Card card) {
        if (!canBeAddedToRange(card)) {
            return false;
        }
        currentPlayer.removeFromRange(card);
        currentPlayer.addToGraveyard(card);
        gameServer.removeCardFromView(card);
        return true;
    }

    public boolean removeFromSiege(Card card) {
        if (!canBeAddedToSiege(card)) {
            return false;
        }
        currentPlayer.removeFromSiege(card);
        currentPlayer.addToGraveyard(card);
        gameServer.removeCardFromView(card);
        return true;
    }

    public boolean removeSpellMelee(Card card) {
        if (!canBePlacedToSpellMelee(card)) {
            return false;
        }
        currentPlayer.removeSpellMelee(card);
        currentPlayer.addToGraveyard(card);
        gameServer.removeCardFromView(card);
        return true;
    }

    public boolean removeSpellRange(Card card) {
        if (!canBePlacedToSpellRange(card)) {
            return false;
        }
        currentPlayer.removeSpellRange(card);
        currentPlayer.addToGraveyard(card);
        gameServer.removeCardFromView(card);
        return true;
    }

    public boolean removeSpellSiege(Card card) {
        if (!canBePlacedToSpellSiege(card)) {
            return false;
        }
        currentPlayer.removeSpellSiege(card);
        currentPlayer.addToGraveyard(card);
        gameServer.removeCardFromView(card);
        return true;
    }

    public boolean removeFromWeather(Card card) {
        weatherCards.remove(card);
        gameServer.removeCardFromView(card);
        return true;
    }


    /// Functions for checking if we can place that Card
    public boolean canBeAddedToMelee(Card card) {
        if (card.getType().equals(Type.CloseCombat) || card.getType().equals(Type.Agile)) {
            return true;
        }
        return false;
    }

    public boolean canBeAddedToRange(Card card) {
        if (card.getType().equals(Type.RangedCombat) || card.getType().equals(Type.Agile)) {
            return true;
        }
        return false;
    }

    public boolean canBeAddedToSiege(Card card) {
        if (card.getType().equals(Type.Siege)) {
            return true;
        }
        return false;
    }

    public boolean canBePlacedToSpellMelee(Card card) {
        if (card.getType().equals(Type.Spell) && currentPlayer.getMeleeSpell() == null) {
            return true;
        }
        return false;
    }

    public boolean canBePlacedToSpellRange(Card card) {
        if (card.getType().equals(Type.Spell) && currentPlayer.getRangeSpell() == (null)) {
            return true;
        }
        return false;
    }

    public boolean canBePlacedToSpellSiege(Card card) {
        if (card.getType().equals(Type.Spell) && currentPlayer.getSiegeSpell() == null) {
            return true;
        }
        return false;
    }

    public boolean canBePlacedToWeather(Card card) {
        if (card.getType().equals(Type.Weather)) {
            return true;
        }
        return false;
    }

    public Position findCardInGameForCurrentPlayer(Card card) {
        for (Card sampleCard : weatherCards) {
            if (card.equals(sampleCard)) {
                return Position.WeatherPlace;
            }
        }
        return currentPlayer.findCardInGame(card);
    }

    public Position findCardInGameForOtherPlayer(Card card) {
        PlayerInGame otherPlayer = getOtherPlayer();

        for (Card sampleCard : weatherCards) {
            if (card.equals(sampleCard)) {
                return Position.WeatherPlace;
            }
        }
        return otherPlayer.findCardInGame(card);
    }


    public ArrayList<Card> getRowFromCard(Card card) {
        PlayerInGame otherPlayer = getOtherPlayer();
        ArrayList<Card> row = new ArrayList<>();
        if (currentPlayer.findCardInGame(card) == null) {
            return otherPlayer.getCardRowFromPosition(findCardInGameForOtherPlayer(card));
        }
        return currentPlayer.getCardRowFromPosition(findCardInGameForCurrentPlayer(card));

    }

    // Commander's horn related functions
    public void meleeCurrentHpTimesInt(int number) {
        currentPlayer.meleeCurrentHpTimesInt(number);
    }

    public void rangeCurrentHpTimesInt(int number) {
        currentPlayer.rangeCurrentHpTimesInt(number);
    }

    public void siegeCurrentHpTimesInt(int number) {
        currentPlayer.siegeCurrentHpTimesInt(number);
    }

    public void someCardsCurrentHpTimesInt(int number, ArrayList<Card> someCards) {
        currentPlayer.someCardsCurrentHpTimesInt(number, someCards);
    }

    // Muster related functions // TODO : what the hell is wrong with muster ability
    public ArrayList<Card> getCardsWithSameNameFromHand(Card card) {
        return currentPlayer.getCardsWithSameNameFromHand(card);
    }

    public ArrayList<Card> getCardsWithSameNameFromDeckInGame(Card card) {
        return currentPlayer.getCardsWithSameNameFromDeckInGame(card);
    }

    public ArrayList<Card> getCardsWithSameNameFromGraveyard(Card card) {
        return currentPlayer.getCardsWithSameNameFromGraveyard(card);
    }

    public ArrayList<Card> getCardsWithSameNameFromMelee(Card card) {
        return currentPlayer.getCardsWithSameNameFromMelee(card);
    }

    public ArrayList<Card> getCardsWithSameNameFromRange(Card card) {
        return currentPlayer.getCardsWithSameNameFromRange(card);
    }

    public ArrayList<Card> getCardsWithSameNameFromSiege(Card card) {
        return currentPlayer.getCardsWithSameNameFromSiege(card);
    }

    public int getDeckInGameCount() {
        return currentPlayer.getDeckInGameCount();
    }

    public int getGraveyardCount() {
        return currentPlayer.getGraveyardCount();
    }

    public int getHandCount() {
        return currentPlayer.getHandCount();
    }

    public ArrayList<Card> getWeatherCards() {
        return weatherCards;
    }

    public void endTurn() {
        System.out.println(currentPlayer.getMelee().size());
        runPassiveAbilities();
        calculateAllHPs();
        if (areBothPlayersPassed()) {
            // TODO end of the round
            PlayerInGame winner = null;
            // Adding Point to winner
            if (currentPlayer.getTotalHP() > getOtherPlayer().getTotalHP()) {
                getOtherPlayer().decreaseRemainingLives();
                winner = getCurrentPlayer();
            } else if (currentPlayer.getTotalHP() == getOtherPlayer().getTotalHP()) {
                if (!(getOtherPlayer().getPlayer().getSelectedFaction() instanceof Nilfgaard)) {
                    getOtherPlayer().decreaseRemainingLives();
                }
                if (!(currentPlayer.getPlayer().getSelectedFaction() instanceof Nilfgaard)) {
                    currentPlayer.decreaseRemainingLives();
                }
            } else {
                currentPlayer.decreaseRemainingLives();
                winner = getOtherPlayer();
            }


            if (currentPlayer.getRemainingLives() <= 0 && getOtherPlayer().getRemainingLives() <= 0) {

                System.out.println("TIED");
                return;
            } else if (currentPlayer.getRemainingLives() <= 0) {
                System.out.println(getOtherPlayer().getPlayer().getUsername() + "VICTORY");
                return;
            } else if (getOtherPlayer().getRemainingLives() <= 0) {
                System.out.println(currentPlayer.getPlayer().getUsername() + "VICTORY");
                return;
            }

            // deleting the Cards in Board
            ArrayList<Card> currentPlayerAllCards = new ArrayList<>();
            ArrayList<Card> otherPlayerAllCards = new ArrayList<>();
            ArrayList<Card> currentPlayerUnitCards = new ArrayList<>();
            ArrayList<Card> otherPlayerUnitCards = new ArrayList<>();
            currentPlayerAllCards.addAll(currentPlayer.getAllCards());
            otherPlayerAllCards.addAll(getOtherPlayer().getAllCards());

            for (Card sampleCard : currentPlayerAllCards) {
                if (sampleCard.isUnitCard()) {
                    currentPlayerUnitCards.add(sampleCard);
                }
                if (sampleCard.isTransformer()) {
                    if (sampleCard.getAllCard().equals(AllCards.Cow)) {
                        Card card = new Card(AllCards.BovineDefenseForce);
                        placeCard(card);
                    }
                    if (sampleCard.getAllCard().equals(AllCards.Kambi)) {
                        Card card = new Card(AllCards.Hemdall);
                        placeCard(card);
                    }
                }
                removeCard(sampleCard);
                gameServer.removeCardFromView(sampleCard);
            }
            for (Card sampleCard : otherPlayerAllCards) {
                if (sampleCard.isUnitCard()) {
                    otherPlayerUnitCards.add(sampleCard);
                }
                if (sampleCard.isTransformer()) {
                    if (sampleCard.getAllCard().equals(AllCards.Cow)) {
                        Card card = new Card(AllCards.BovineDefenseForce);
                        placeCardEnemyButNotSpy(card);
                    }
                    if (sampleCard.getAllCard().equals(AllCards.Kambi)) {
                        Card card = new Card(AllCards.Hemdall);
                        placeCardEnemyButNotSpy(card);
                    }
                }
                removeCard(sampleCard);
                gameServer.removeCardFromView(sampleCard);
            }
            for (int i = weatherCards.size() - 1; i >= 0; i--) {
                gameServer.removeCardFromView(weatherCards.get(i));
                removeCard(weatherCards.get(i));
            }
            if (currentPlayer.getPlayer().getSelectedFaction() instanceof Monsters) {
                int index = (int) (Math.random() * currentPlayerUnitCards.size());
                Card sampleCard = currentPlayerUnitCards.get(index);
                if (sampleCard.getType().equals(Type.Agile)) {
                    placeCard(sampleCard, Position.Melee);
                    currentPlayer.removeFromGraveyard(sampleCard);
                } else {
                    placeCard(sampleCard);
                    currentPlayer.removeFromGraveyard(sampleCard);
                }
            }
            if (getOtherPlayer().getPlayer().getSelectedFaction() instanceof Monsters) {
//                int index = (int) (Math.random() * otherPlayerUnitCards.size());
//                Card sampleCard = otherPlayerUnitCards.get(index);
//                if (sampleCard.getType().equals(Type.Agile)) {
//                    getOtherPlayer().addToMelee(sampleCard);
//                    gameController.addCardToTableSection(sampleCard, Position.Melee, true);
//                    getOtherPlayer().removeFromGraveyard(sampleCard);
//                } else {
//                    if (sampleCard.getType().equals(Type.Agile)) {
//                    } else if (sampleCard.getType().equals(Type.CloseCombat)) {
//                        getOtherPlayer().addToMelee(sampleCard);
//                        gameController.addCardToTableSection(sampleCard, Position.Melee, true);
//                        getOtherPlayer().removeFromGraveyard(sampleCard);
//                    } else if (sampleCard.getType().equals(Type.RangedCombat)) {
//                        getOtherPlayer().addToRange(sampleCard);
//                        gameController.addCardToTableSection(sampleCard, Position.Range, true);
//                        getOtherPlayer().removeFromGraveyard(sampleCard);
//                    } else if (sampleCard.getType().equals(Type.Siege)) {
//                        getOtherPlayer().addToSiege(sampleCard);
//                        gameController.addCardToTableSection(sampleCard, Position.Siege, true);
//                        getOtherPlayer().removeFromGraveyard(sampleCard);
//                    } else if (sampleCard.getType().equals(Type.Spell)) {
//                    } else if (sampleCard.getType().equals(Type.Weather)) {
//                    } else {
//                        throw new RuntimeException();
//                    }
//                }
            }

            // Northern Realms
            if (winner != null) {
                if (winner.getPlayer().getSelectedFaction() instanceof NorthernRealms) {
                    int index = (int) (Math.random() * winner.getDeckInGame().size());
                    Card sampleCard = winner.getDeckInGame().get(index);
                    winner.removeFromDeckInGame(sampleCard);
                    if (winner == currentPlayer) {
                        addToHand(sampleCard, true);
                    } else if (winner == getOtherPlayer()) {
                        addToHand(sampleCard, false);
                    } else {

                    }
                } else {

                }
            } else {
            }

            gameServer.updateScores();
            // Skeillige
            if (turnNumber == 1) {
                // TODO : Skellige
            }

            // reset isPassed for players
            currentPlayer.setIsPassed(false);
            getOtherPlayer().setIsPassed(false);

            gameServer.resetPassButtons();
            // delete the Cards
            // Transformer Cards

            // Who's Turn Is it?
            if (turnNumber % 2 == 0) {
                if (currentPlayer != player2) {
                    switchTurn();
                }
            } else {
                if (currentPlayer != player1) {
                    switchTurn();
                }
            }
            turnNumber++;
            return;
        }
        System.out.println("Arman:" + isOtherPlayerPassed());
        if (isOtherPlayerPassed()) {
            return;
        }
        switchTurn();

    }

    public boolean isOtherPlayerPassed() {
        return getOtherPlayer().isPassed();
    }

    public boolean areBothPlayersPassed() {
        return getOtherPlayer().isPassed() && currentPlayer.isPassed();
    }

    public ArrayList<Card> getAllCards() {
        ArrayList<Card> allOfTheCards = new ArrayList<>();
        allOfTheCards.addAll(currentPlayer.getAllCards());
        allOfTheCards.addAll(getOtherPlayer().getAllCards());
        allOfTheCards.addAll(weatherCards);
        return allOfTheCards;
    }

    public void runPassiveAbilities() {
        for (Card sampleCard : getAllCards()) {
            sampleCard.resetCard();
        }
        for (Card sampleCard : getAllCards()) {
            if (sampleCard.isCardsAbilityPassive()) {
                sampleCard.getAbility().run(this, sampleCard);
            }
        }
    }

    public void calculateAllHPs() {
        for (Card sampleCard : getAllCards()) {
            sampleCard.setCurrentHP(sampleCard.calculateCurrentHP());
        }
    }

    public ArrayList<Card> getCardRowFromPosition(Position position) {
        if (position.equals(Position.WeatherPlace)) {
            return getWeatherCards();
        } else {
            return currentPlayer.getCardRowFromPosition(position);
        }
    }

    public void switchTurn() {
        if (currentPlayer.equals(player1)) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
        gameServer.changeTurn();
    }


    // Functions Related To Scorch
    public ArrayList<Card> getMaximumPowerInRow(Position position) {
        PlayerInGame otherPlayer = getOtherPlayer();

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

        PlayerInGame otherPlayer = getOtherPlayer();

        int maxOfCurrentHP = 0;
        ArrayList<Card> enemyCards = otherPlayer.getAllCards();
        for (Card card : enemyCards) {
            if (card.getCurrentHP() > maxOfCurrentHP && !card.isHero()) {
                maxOfCurrentHP = card.getCurrentHP();
            }
        }


        ArrayList<Card> theStrongests = new ArrayList<>();

        for (Card card : enemyCards) {
            if (!card.isHero()) {
                if (card.getCurrentHP() == maxOfCurrentHP && card.getType() != Type.Spell) {
                    theStrongests.add(card);
                }
            }
        }

        return theStrongests;
    }

    public void drawRandomCardFromDeck(int occurrence) {
        for (int i = 0; i < occurrence; i++) {
            Card card = currentPlayer.drawRandomCardFromDeckInGame();
            gameServer.addCardToHand(card, currentPlayer);
        }
    }

    public PlayerInGame getCurrentPlayer() {
        return currentPlayer;
    }

    public PlayerInGame getOtherPlayer() {
        if (currentPlayer.equals(player1)) {
            return player2;
        } else {
            return player1;
        }
    }

    public void addToHand(Card card) {
        currentPlayer.addToHand(card);
        gameServer.addCardToHand(card, currentPlayer);
    }

    public void addToHand(Card card, boolean isCurrentPlayer) {
        if (isCurrentPlayer) {
            addToHand(card);
        } else {
            PlayerInGame otherPlayer = getOtherPlayer();
            otherPlayer.addToHand(card);
            gameServer.addCardToHand(card, otherPlayer);
        }
    }

    public void removeFromHand(Card card) {
        currentPlayer.removeFromHand(card);
        gameServer.removeCardFromView(card);
    }

    public void removeFromHand(Card card, boolean isCurrentPlayer) {
        if (isCurrentPlayer) {
            removeFromHand(card);
        } else {
            PlayerInGame otherPlayer = getOtherPlayer();
            otherPlayer.removeFromHand(card);
            gameServer.removeCardFromView(card);
        }
    }

//    public Card showSomeCardsAndSelectOne(ArrayList<Card> cards) {
//        // TODO: @Arman
//        return gameController.showSomeCardsAndSelectOne(cards);
//    }

}
