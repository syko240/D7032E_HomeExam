package game.scoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import game.card.Card;
import game.card.CardAustralia;

public class ScoringAustralia implements Scoring {
private static Set<String> previouslyVisitedRegions = new HashSet<>();

    @Override
    public int calculateThrowCatchScore(Card throwCard, Card catchCard) {
        if (throwCard == null || catchCard == null) {
            return 0;
        }

        int throwCardNumber = throwCard.getNumber();
        int catchCardNumber = catchCard.getNumber();

        return Math.abs(throwCardNumber - catchCardNumber);
    }

    @Override
    public int calculateRegionScore(List<Card> cards) {
        Map<String, List<String>> regionMap = new HashMap<>() {
            {
                put("Western Australia", Arrays.asList("A", "B", "C", "D"));
                put("Northern Territory", Arrays.asList("E", "F", "G", "H"));
                put("Queensland", Arrays.asList("I", "J", "K", "L"));
                put("South Australia", Arrays.asList("M", "N", "O", "P"));
                put("New South Wales", Arrays.asList("Q", "R", "S", "T"));
                put("Victoria", Arrays.asList("U", "V", "W", "X"));
                put("Tasmania", Arrays.asList("Y", "Z", "*", "-"));
            }
        };

        List<String> visitedSites = cards.stream().map(Card::getLetter).collect(Collectors.toList());

        int score = 0;
        List<String> completedRegions = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : regionMap.entrySet()) {
            if (visitedSites.containsAll(entry.getValue()) && !previouslyVisitedRegions.contains(entry.getKey())) {
                completedRegions.add(entry.getKey());
                score += 3;
            }
        }

        score += visitedSites.size();

        previouslyVisitedRegions.addAll(completedRegions);

        return score;
    }

    @Override
    public int calculateIconScore(List<Card> cards) {
        Map<String, Integer> collectionValues = new HashMap<>();
        collectionValues.put("Leaves", 1);
        collectionValues.put("Wildflowers", 2);
        collectionValues.put("Shells", 3);
        collectionValues.put("Souvenirs", 5);

        int totalScore = 0;
        for (Card card : cards) {
            if (card instanceof CardAustralia) {
                CardAustralia ausCard = (CardAustralia) card;
                String collectionItem = ausCard.getCollection();
                if (collectionValues.containsKey(collectionItem)) {
                    totalScore += collectionValues.get(collectionItem);
                }
            }
        }

        if (totalScore <= 7) {
            totalScore *= 2;
        }

        return totalScore;
    }

    @Override
    public int calculatePairScore(List<Card> cards) {
        Map<String, Integer> animalCounts = new HashMap<>();
        Map<String, Integer> animalScores = new HashMap<>();

        animalScores.put("Kangaroo", 3);
        animalScores.put("Emu", 4);
        animalScores.put("Wombat", 5);
        animalScores.put("Koala", 7);
        animalScores.put("Platypus", 9);

        for (Card card : cards) {
            String animal = ((CardAustralia) card).getAnimal();
            animalCounts.put(animal, animalCounts.getOrDefault(animal, 0) + 1);
        }

        int totalScore = 0;

        for (String animal : animalCounts.keySet()) {
            int pairs = animalCounts.get(animal) / 2;
            totalScore += pairs * animalScores.getOrDefault(animal, 0);
        }

        return totalScore;
    }

    @Override
    public int calculateSpecialScore(List<Card> cards) {
        Map<String, Integer> activityCount = new HashMap<>();

        for (Card card : cards) {
            String activity = ((CardAustralia) card).getActivity();
            if (activity != null) {
                activityCount.put(activity, activityCount.getOrDefault(activity, 0) + 1);
            }
        }

        int maxActivityCount = 0;
        for (Integer count : activityCount.values()) {
            maxActivityCount = Math.max(maxActivityCount, count);
        }

        switch (maxActivityCount) {
            case 1:
                return 0;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 7;
            case 5:
                return 10;
            case 6:
                return 15;
            default:
                return 0;
        }
    }

    @Override
    public int calculateTotalScore(List<Card> cards) {
        int throwAndCatchScore = calculateThrowCatchScore(cards.get(0),
                cards.get(cards.size() - 1));
        int touristSitesScore = calculateRegionScore(cards);
        int collectionsScore = calculateIconScore(cards);
        int animalsScore = calculatePairScore(cards);
        int activitiesScore = calculateSpecialScore(cards);

        return throwAndCatchScore + touristSitesScore + collectionsScore + animalsScore + activitiesScore;
    }
}