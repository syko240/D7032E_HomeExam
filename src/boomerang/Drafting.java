package boomerang;

import java.util.List;

interface DraftingStrategy {
    void draft(Player currentPlayer, List<Player> allPlayers);
}

class LeftToRight implements DraftingStrategy {
    @Override
    public void draft(Player currentPlayer, List<Player> allPlayers) {
        // Implement the Left to Right Drafting
        // For simplicity, let's say players just exchange a card with each other in a circle
        int currentIndex = allPlayers.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % allPlayers.size();

        Card cardToPass = currentPlayer.hand.get(0); 
        currentPlayer.hand.remove(0);
        allPlayers.get(nextIndex).hand.add(cardToPass);
    }

    public void draftCards(List<Player> players, int round) {
    // Logic for card drafting
    // This will vary depending on whether you choose LeftToRight or AlternateStrategy
    // This is a simplified version; you'll need to incorporate full logic
    for(Player player : players) {
        // This assumes the Player class has a draft method
        player.draft();
    }
}
}