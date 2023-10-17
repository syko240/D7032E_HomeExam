package game.gamemode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import game.Deck;
import game.card.Card;
import game.player.Player;

public class BoomerangAustralia implements GameMode {
    private List<Card> deck;

    @Override
    public void initializeDeck() {
        this.deck = Deck.loadCardsFromJSON("resources/australia/cards.json");
    }

    public List<Card> draftCards() {
        List<Card> draftedCards = new LinkedList<>();
        Collections.shuffle(deck);
        for (int i = 0; i < 7; i++) {
            draftedCards.add(deck.remove(0));
        }
        return draftedCards;
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