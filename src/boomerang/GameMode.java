package boomerang;

import java.util.List;

abstract class GameMode {
    protected List<Card> deck;

    public abstract void initializeDeck();
    public abstract int scoreRound(IPlayer player);
}

class BoomerangAustralia extends GameMode {
    @Override
    public void initializeDeck() {
        this.deck = Deck.loadCardsFromJSON("../../resources/australia/cards.json");
    }

    @Override
    public int scoreRound(IPlayer player) {
        int score = 0;
        // Simplified scoring for brevity
        // Calculate score based on player's cards
        for (Card card : IPlayer.draft) {
            score += card.getNumber();
        }
        return score;
    }
}