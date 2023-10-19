package game.gamemode;

import java.util.List;

import game.card.Card;
import game.player.Player;
import game.scoring.Scoring;

public abstract class GameMode {
    Scoring scoring;

    public GameMode(Scoring scoring) {
        this.scoring = scoring;
    }

    public abstract void initializeDeck();
    public abstract List<Card> draftCards();
    public abstract int scoreRound(Player player, List<Card> cards);
}