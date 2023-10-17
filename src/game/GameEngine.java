package game;

import java.util.List;

import game.drafting.Drafting;
import game.gamemode.GameMode;

public class GameEngine {
    private GameMode gameMode;
    private Drafting draftingStrategy;
    private List<Player> players;

    public GameEngine(GameMode gameMode, Drafting draftingStrategy, List<Player> players) {
        this.gameMode = gameMode;
        this.draftingStrategy = draftingStrategy;
        this.players = players;
    }

    public void startGame() {
        //game loop
        for (int i = 0; i < 4; i++) {
            draftRound();
            
        }
    }

    public void draftRound() {
        for (Player player : players) {
            draftingStrategy.draft(player, players);
        }
    }
}
