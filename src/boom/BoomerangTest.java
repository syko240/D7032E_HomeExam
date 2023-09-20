package boom;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class BoomerangTest {

    // 1 %--------------------------------------------------------------------------------------------------
    @Test
    public void testValidPlayerCount() {
        // Test the lower limit
        try {
            String[] params = {"1", "1"};
            BoomerangAustralia game = new BoomerangAustralia(params);
        } catch (Exception e) {
            Assert.fail("Expected valid game initialization");
        }

        // Test the upper limit
        try {
            String[] params = {"3", "1"};
            BoomerangAustralia game = new BoomerangAustralia(params);
        } catch (Exception e) {
            Assert.fail("Expected valid game initialization");
        }
    }
    /*
    @Test
    public void testInvalidPlayerCount() {
        // Test below the lower limit
        try {
            String[] params = {"0", "1"};
            new BoomerangAustralia(params);
            Assert.fail("Expected an exception for invalid number of players and bots");
        } catch (Exception e) {
            // This is expected, so do nothing.
        }

        // Test above the upper limit
        try {
            String[] params = {"3", "2"};
            new BoomerangAustralia(params);
            Assert.fail("Expected an exception for invalid number of players and bots");
        } catch (Exception e) {
            // This is expected, so do nothing.
        }
    }

    // 2 %--------------------------------------------------------------------------------------------------
    @Test
    public void testDeckSize() {
        String[] params = {"1", "1"};
        try {
            BoomerangAustralia game = new BoomerangAustralia(params);
            Assert.assertEquals("Deck should have 28 cards", 28, game.arrayDeck.length);
        } catch (Exception e) {
            Assert.fail("Expected valid game initialization");
        }
    }

    @Test
    public void testDeckShuffled() {
        String[] params = {"2", "0"};
        try {
            BoomerangAustralia game = new BoomerangAustralia(params);
            boolean different = false;
            for (int i = 0; i < game.arrayDeck.length; i++) {
                if (!game.arrayDeck[i].equals(game.deck.get(i))) {
                    different = true;
                    break;
                }
            }
            Assert.assertTrue("Deck should be shuffled and differ from initial order", different);
        } catch (Exception e) {
            Assert.fail("Expected valid game initialization");
        }
    }

    // Without access to the Card class it becomes very difficult to make a JUnit test for Deck Uniqueness

    @Test
    public void testDeckCardsAreUnique() { // might not work
        String[] params = {"2", "0"};
        try {
            BoomerangAustralia game = new BoomerangAustralia(params);

            Set<String> uniqueCardStrings = new HashSet<>();

            for (Object card : game.deck) {
                uniqueCardStrings.add(card.toString());
            }
            
            Assert.assertEquals("All cards should be unique", game.deck.size(), uniqueCardStrings.size());
        } catch (Exception e) {
            Assert.fail("Expected valid game initialization");
        }
    }*/

    // 3 %--------------------------------------------------------------------------------------------------
}
