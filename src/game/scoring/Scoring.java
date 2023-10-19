package game.scoring;

import java.util.List;

import game.card.Card;

public interface Scoring {
    int calculateThrowCatchScore(Card throwCard, Card catchCard);

    // calculateTouristSitesScore
    int calculateRegionScore(List<Card> cards);

    // calculateCollectionsScore
    int calculateIconScore(List<Card> cards);

    // calculateAnimalsScore
    int calculatePairScore(List<Card> cards);

    // calculateActivitiesScore
    int calculateSpecialScore(List<Card> cards);

    int calculateTotalScore(List<Card> cards);
}
