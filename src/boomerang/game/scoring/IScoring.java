package boomerang.game.scoring;

import java.util.List;

import boomerang.game.card.Card;

public interface IScoring {
    int calculateThrowCatchScore(Card throwCard, Card catchCard);

    int calculateRegionScore(List<Card> cards);

    // Collections score in Boomerang Australia
    int calculateIconScore(List<Card> cards);

    // Animals score in Boomerang Australia
    int calculatePairScore(List<Card> cards);

    // Activities score in Boomerang Australia
    int calculateSpecialScore(List<Card> cards);

    int calculateTotalScore(List<Card> cards);
}
