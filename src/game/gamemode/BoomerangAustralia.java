package game.gamemode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import game.Deck;
import game.card.Card;
import game.player.Player;
import game.scoring.Scoring;

public class BoomerangAustralia extends GameMode {
    private List<Card> deck;

    public BoomerangAustralia(Scoring scoring) {
        super(scoring);
    }

    @Override
    public void initializeDeck() {
        this.deck = Deck.loadCardsFromJSON("resources/australia/cards.json");
    }

    @Override
    public List<Card> draftCards() {
        List<Card> draftedCards = new LinkedList<>();
        Collections.shuffle(deck);
        for (int i = 0; i < 7; i++) {
            draftedCards.add(deck.remove(0));
        }
        return draftedCards;
    }

    @Override
    public int scoreRound(Player player, List<Card> cards) {
        int score = this.scoring.calculateTotalScore(cards);
        player.addScore(score);
        return score;
    }
}