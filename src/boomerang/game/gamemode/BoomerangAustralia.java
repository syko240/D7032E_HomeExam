package boomerang.game.gamemode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boomerang.game.Deck;
import boomerang.game.card.Card;
import boomerang.game.player.Player;
import boomerang.game.scoring.IScoring;

public class BoomerangAustralia extends GameMode {
    private List<Card> deck;

    public BoomerangAustralia(IScoring scoring) {
        super(scoring);
    }

    @Override
    public void initializeDeck() {
        this.deck = Deck.loadCardsFromJSON("resources/australia/cards.json");
    }

    @Override
    public List<Card> cardHandOut() {
        List<Card> draftedCards = new ArrayList<>();
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