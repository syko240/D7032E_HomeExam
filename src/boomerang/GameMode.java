package boomerang;

import java.util.List;

abstract class GameMode {
    protected List<Card> deck;

    public abstract void initializeDeck();
    public abstract int scoreRound(Player player);
}

class BoomerangAustralia extends GameMode {
    @Override
    public void initializeDeck() {
        this.deck = Utils.loadCardsFromJSON("cards.json");
    }

    @Override
    public int scoreRound(Player player) {
        int score = 0;
        // Simplified scoring for brevity
        // Calculate score based on player's cards
        for (Card card : player.draft) {
            score += card.getNumber();
        }
        return score;
    }
}