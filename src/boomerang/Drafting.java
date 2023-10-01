package boomerang;

import java.util.List;

interface DraftingStrategy {
    void draft(IPlayer currentPlayer, List<IPlayer> allPlayers);
}

class LeftToRight implements DraftingStrategy {
    @Override
    public void draft(IPlayer currentPlayer, List<IPlayer> allPlayers) {
        // Implement the Clockwise Drafting
        int currentIndex = allPlayers.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % allPlayers.size();

        Card cardToPass = currentPlayer.hand.get(0); 
        currentPlayer.hand.remove(0);
        allPlayers.get(nextIndex).hand.add(cardToPass);
    }

    public void draftCards(List<IPlayer> players, int round) {
    // Logic for card drafting
    // This will vary depending on whether you choose LeftToRight or AlternateStrategy
    // This is a simplified version; you'll need to incorporate full logic
    for(IPlayer player : players) {
        // This assumes the Player class has a draft method
        player.draft();
    }
}
}