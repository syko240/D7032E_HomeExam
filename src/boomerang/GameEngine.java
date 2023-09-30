package boomerang;

import java.util.List;

class GameEngine {
    private GameMode gameMode;
    private DraftingStrategy draftingStrategy;
    private List<Player> players;

    public GameEngine(GameMode gameMode, DraftingStrategy draftingStrategy, List<Player> players) {
        this.gameMode = gameMode;
        this.draftingStrategy = draftingStrategy;
        this.players = players;
    }

    public void startGame() {
        gameMode.initializeDeck();
        // Game rounds, handling drafts, and other game logic
        for (int i = 0; i < 4; i++) {
            draftRound();
            // Simplified scoring for brevity
            for (Player player : players) {
                System.out.println("Player's score after round " + (i+1) + ": " + gameMode.scoreRound(player));
            }
        }
    }

    public void draftRound() {
        for (Player player : players) {
            draftingStrategy.draft(player, players);
        }
    }
}
