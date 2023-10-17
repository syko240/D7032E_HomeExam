package game.gamemode;

import java.util.List;

import game.Deck;
import game.Player;
import game.card.Card;

public class BoomerangAustralia implements GameMode {
    private List<Card> deck;

    @Override
    public void initializeDeck() {
        this.deck = Deck.loadCardsFromJSON("../../resources/australia/cards.json");
    }

    @Override
    public int scoreRound(Player player) {
        return 0;
        /*int score = 0;
        for (Card card : Player.draft) {
            score += card.getNumber();
        }
        return score;*/
    }
}