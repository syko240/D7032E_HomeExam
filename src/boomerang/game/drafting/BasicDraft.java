package boomerang.game.drafting;

import java.util.List;

import boomerang.game.Card;
import boomerang.game.Player;

// Clockwise Drafting
public class BasicDraft implements Drafting {
    @Override
    public void draft(Player currentPlayer, List<Player> allPlayers) {
        int currentIndex = allPlayers.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % allPlayers.size();

        Card cardToPass = currentPlayer.hand.get(0); 
        currentPlayer.hand.remove(0);
        allPlayers.get(nextIndex).hand.add(cardToPass);
    }
}
