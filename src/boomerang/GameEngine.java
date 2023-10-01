package boomerang;

import java.util.List;

class GameEngine {
    private GameMode gameMode;
    private DraftingStrategy draftingStrategy;
    private List<IPlayer> players;

    public GameEngine(GameMode gameMode, DraftingStrategy draftingStrategy, List<IPlayer> players) {
        this.gameMode = gameMode;
        this.draftingStrategy = draftingStrategy;
        this.players = players;
    }

    public void startGame() {

        // Game rounds, handling drafts, and other game logic
        for (int i = 0; i < 4; i++) {
            draftRound();
            // Simplified scoring for brevity
            for (IPlayer player : players) {
                System.out.println("Player's score after round " + (i+1) + ": " + gameMode.scoreRound(player));
            }
        }
    }

    public void draftRound() {
        for (IPlayer player : players) {
            draftingStrategy.draft(player, players);
        }
    }
}
