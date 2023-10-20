package boomerang;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import boomerang.game.Deck;
import boomerang.game.card.Card;
import boomerang.game.card.CardAustralia;
import boomerang.game.gamemode.BoomerangAustralia;
import boomerang.game.player.Human;
import boomerang.game.player.Player;
import boomerang.game.scoring.IScoring;
import boomerang.game.scoring.ScoringAustralia;

public class BoomerangTest {

    // -------------------------------------------------------------------
    // ************************** Requirement 1 **************************
    // -------------------------------------------------------------------
    @Before
    public void setup1() {
        CommunicationHandler.TEST_MODE1 = true;
    }
    
    @Test
    public void test4Players() {
        try {
            new CommunicationHandler().startServer(8080, 2, 2);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test2Players() {
        try {
            new CommunicationHandler().startServer(8081, 2, 0);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test1Players() {
        try {
            new CommunicationHandler().startServer(8082, 1, 0);
            Assert.fail();
        } catch (Exception e) {
            // This is expected, so do nothing.
        }
    }

    @Test
    public void test5Players() {
        try {
            new CommunicationHandler().startServer(8083, 3, 2);
            Assert.fail();
        } catch (Exception e) {
            // This is expected, so do nothing.
        }
    }

    @After
    public void cleanup1() {
        CommunicationHandler.TEST_MODE1 = false;
    }

    // -------------------------------------------------------------------
    // ************************** Requirement 2 **************************
    // -------------------------------------------------------------------

    @Test
    public void testDeckSize() {
        try {
            List<Card> deck  = new Deck().loadCardsFromJSON("resources/australia/cards.json");
            Assert.assertEquals(28, deck.size());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    // -------------------------------------------------------------------
    // ************************** Requirement 3 **************************
    // -------------------------------------------------------------------
    
    @Test
    public void testShuffleDeck() {
        try {
            BoomerangAustralia gameMode = new BoomerangAustralia(new ScoringAustralia());
            gameMode.initializeDeck();
            List<Card> testDeck = new ArrayList<>(gameMode.getDeck());
            gameMode.shuffleDeck();
            Assert.assertNotSame(testDeck, gameMode.getDeck());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    // -------------------------------------------------------------------
    // ************************** Requirement 4 **************************
    // -------------------------------------------------------------------

    @Test
    public void testDeal7Cards() {
        try {
            List<Player> players = new ArrayList<>();
            players.add(new Human(1));
            players.add(new Human(2));
            players.add(new Human(3));

            BoomerangAustralia gameMode = new BoomerangAustralia(new ScoringAustralia());
            gameMode.initializeDeck();

            for (Player player : players) {
                player.hand = gameMode.cardHandOut();
            }

            for (Player player : players) {
                Assert.assertEquals(7, player.hand.size());
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    // -------------------------------------------------------------------
    // ************************** Requirement 5 **************************
    // -------------------------------------------------------------------

    @Test
    public void testThrowAndCatchCard() {
        try {
            
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    // -------------------------------------------------------------------
    // ************************** Requirement 10 *************************
    // -------------------------------------------------------------------

    @Test
    public void testScoring() {
        try {
            IScoring scoring = new ScoringAustralia();

            BoomerangAustralia gameMode = new BoomerangAustralia(scoring);
            gameMode.initializeDeck();

            List<Card> cards = new ArrayList<>();
            cards.add(new CardAustralia("Uluru", "E", "Northern Territory", 4, "", "Emus", "Indigenous Culture"));
            cards.add(new CardAustralia("Daintree Rainforest", "K", "Queensland", 6, "Souvenirs", "", "Bushwalking"));
            cards.add(new CardAustralia("Blue Mountains", "Q", "New South Whales", 5, "", "Wombats", "Indigenous Culture"));
            cards.add(new CardAustralia("Kangaroo Island", "O", "South Australia", 3, "", "Kangaroos", "Bushwalking"));
            cards.add(new CardAustralia("The MCG", "V", "Victoria", 2, "Leaves", "", "Indigenous Culture"));
            cards.add(new CardAustralia("Mount Wellington", "Z", "Tasmania", 7, "", "Koalas", "Sightseeing"));
            cards.add(new CardAustralia("Bondi Beach", "S", "New South Whales", 5, "", "Wombats", "Swimming"));

            cards.get(0).setThrowCard(true);
            cards.get(6).setCatchCard(true);
            Card throwCard = cards.get(0);
            Card catchCard = cards.get(6);

            // test a: Throw Card and Catch Card diff
            Assert.assertEquals(Math.abs(throwCard.getNumber() - catchCard.getNumber()), scoring.calculateThrowCatchScore(throwCard, catchCard));

            // test b: Region Scoring
            Assert.assertEquals(7, scoring.calculateRegionScore(cards));

            // test c: Collections Score
            Assert.assertEquals(12, scoring.calculateIconScore(cards));

            // test d: Animals Score
            // Pair scores: (1)Emus (0 points) + (2)Wombats (5 points) + (1)Kangaroos (0 points) + (1)Koalas (0 points)
            // Total animal score = 0 + 5 + 0 + 0 = 5
            Assert.assertEquals(0, scoring.calculatePairScore(cards));

            // test e: Activities Score
            Assert.assertEquals(4, scoring.calculateSpecialScore(cards));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
