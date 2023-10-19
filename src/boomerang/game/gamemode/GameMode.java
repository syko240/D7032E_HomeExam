package boomerang.game.gamemode;

import java.util.List;

import boomerang.game.card.Card;
import boomerang.game.player.Player;
import boomerang.game.scoring.IScoring;

public abstract class GameMode {
    IScoring scoring;

    public GameMode(IScoring scoring) {
        this.scoring = scoring;
    }

    public abstract void initializeDeck();
    public abstract List<Card> draftCards();
    public abstract int scoreRound(Player player, List<Card> cards);
}