package boomerang.game.gamemode;

import java.util.List;

import boomerang.game.Card;
import boomerang.game.Deck;
import boomerang.game.Player;

public class BoomerangAustralia implements GameMode {
    private List<Card> deck;

    @Override
    public void initializeDeck() {
        this.deck = Deck.loadCardsFromJSON("../../resources/australia/cards.json");
    }

    @Override
    public int scoreRound(Player player) {
        int score = 0;
        for (Card card : Player.draft) {
            score += card.getNumber();
        }
        return score;
    }
}