package model;


import model.card.Card;
import model.card.specialcards.SpecialCard;
import model.card.unitcards.UnitCard;
import model.position.CloseCombat;
import model.position.Position;
import model.position.RangedCombat;
import model.position.Siege;

public class GameManager {
    
    private PlayerInGame player1, player2, currentPlayer;
    private Card weatherStatus;

    public GameManager (Player player1, Player player2){
        this.player1 = new PlayerInGame(player1);
        this.player2 = new PlayerInGame(player2);
        this.currentPlayer = this.player1;
    }

    public String placeUnitCard (UnitCard unitCard , Position position){
        if (!canPlaceUnitCard(unitCard, position)){
            return "nashod place konam";
        }

        if (position instanceof CloseCombat){
            currentPlayer.addToMelee(unitCard);
        } else if (position instanceof Siege){
            currentPlayer.addToSiege(unitCard);
        } else if (position instanceof RangedCombat){
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

        if (position instanceof CloseCombat){
            currentPlayer.placeSpecialCardMelee(specialCard);
        } else if (position instanceof Siege){
            currentPlayer.placeSpecialCardSiege(specialCard);
        } else if (position instanceof RangedCombat){
            currentPlayer.placeSpecialCardRange(specialCard);
        } else {
            return "boosh miad";
        }

        return "ba movaghiat anjam shod";
    }

    public boolean canPlaceUnitCard (UnitCard unitCard , Position position){
        // TODO baad az neveshtan position card in kamel shavad
        return true;
    }

    public boolean canPlaceSpecialCard (SpecialCard specialCard , Position position){
        // TODO baad az neveshtan position card in kamel shavad
        return true;
    }
}
