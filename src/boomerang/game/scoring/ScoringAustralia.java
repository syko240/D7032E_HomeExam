package boomerang.game.scoring;

import java.util.*;
import java.util.stream.Collectors;

import boomerang.game.card.Card;
import boomerang.game.card.CardAustralia;

public class ScoringAustralia implements IScoring {

    private Set<String> previouslyVisitedRegions = new HashSet<>();
    private Map<String, List<String>> regionMap;
    private Map<String, Integer> collectionValues;
    private Map<String, Integer> animalScores;

    public ScoringAustralia() {
        initializeRegionMap();
        initializeCollectionValues();
        initializeAnimalScores();
    }

    private void initializeRegionMap() {
        regionMap = new HashMap<>() {
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
    }

    private void initializeCollectionValues() {
        collectionValues = Map.of(
            "Leaves", 1,
            "Wildflowers", 2,
            "Shells", 3,
            "Souvenirs", 5
        );
    }

    private void initializeAnimalScores() {
        animalScores = Map.of(
            "Kangaroo", 3,
            "Emu", 4,
            "Wombat", 5,
            "Koala", 7,
            "Platypus", 9
        );
    }

    @Override
    public int calculateThrowCatchScore(Card throwCard, Card catchCard) {
        if (throwCard == null || catchCard == null) {
            return 0;
        }
        return Math.abs(throwCard.getNumber() - catchCard.getNumber());
    }

    @Override
    public int calculateRegionScore(List<Card> cards) {
        List<String> visitedSites = cards.stream().map(Card::getLetter).collect(Collectors.toList());
        int score = visitedSites.size();

        score += regionMap.entrySet().stream()
            .filter(entry -> visitedSites.containsAll(entry.getValue()) && !previouslyVisitedRegions.contains(entry.getKey()))
            .peek(entry -> previouslyVisitedRegions.add(entry.getKey()))
            .count() * 3;

        return score;
    }

    @Override
    public int calculateIconScore(List<Card> cards) {
        int totalScore = cards.stream()
            .filter(card -> card instanceof CardAustralia)
            .mapToInt(card -> collectionValues.getOrDefault(((CardAustralia) card).getCollection(), 0))
            .sum();

        return totalScore <= 7 ? totalScore * 2 : totalScore;
    }

    @Override
    public int calculatePairScore(List<Card> cards) {
        Map<String, Long> animalCounts = cards.stream()
            .map(card -> ((CardAustralia) card).getAnimal())
            .collect(Collectors.groupingBy(animal -> animal, Collectors.counting()));

        return animalCounts.entrySet().stream()
            .mapToInt(entry -> (int) (entry.getValue() / 2) * animalScores.getOrDefault(entry.getKey(), 0))
            .sum();
    }

    @Override
    public int calculateSpecialScore(List<Card> cards) {
        long maxActivityCount = cards.stream()
            .map(card -> ((CardAustralia) card).getActivity())
            .filter(Objects::nonNull)
            .collect(Collectors.groupingBy(activity -> activity, Collectors.counting()))
            .values().stream()
            .mapToLong(Long::longValue)
            .max().orElse(0);

        return switch ((int) maxActivityCount) {
            case 1 -> 0;
            case 2 -> 2;
            case 3 -> 4;
            case 4 -> 7;
            case 5 -> 10;
            case 6 -> 15;
            default -> 0;
        };
    }

    @Override
    public int calculateTotalScore(List<Card> cards) {
        return calculateThrowCatchScore(cards.get(0), cards.get(cards.size() - 1))
            + calculateRegionScore(cards)
            + calculateIconScore(cards)
            + calculatePairScore(cards)
            + calculateSpecialScore(cards);
    }
}
