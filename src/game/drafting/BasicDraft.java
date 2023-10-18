package game.drafting;

import java.util.ArrayList;
import java.util.List;

import game.card.Card;
import game.player.Player;

// Clockwise Drafting
public class BasicDraft implements Drafting {
    @Override
    public void draft(List<Player> players) {
        if (players.get(0).hand.size() == 1) { // pass 'Catch' card to the previous player (counter clockwise)
            Card tempFirstHand = players.get(0).hand.get(0);
            tempFirstHand.setCatchCard(true);
        
            // Starting from the first player, give each player the 'Catch' card of the next player
            for (int i = 0; i < players.size() - 1; i++) {
                Card nextPlayerCard = players.get(i + 1).hand.get(0);
                nextPlayerCard.setCatchCard(true);  // Mark the card as a 'Catch' card
                players.get(i).chosenCards.add(nextPlayerCard);  // Add next player's 'Catch' card to the current player's chosenCards
            }
        
            // Give the last player the first player's 'Catch' card
            players.get(players.size() - 1).chosenCards.add(tempFirstHand);
        } else {
            List<Card> tempLastHand = new ArrayList<>(players.get(players.size() - 1).hand);
    
            for (int i = players.size() - 1; i > 0; i--) {
                players.get(i).hand = players.get(i - 1).hand;
            }
            players.get(0).hand = tempLastHand;
        }
    }
}
