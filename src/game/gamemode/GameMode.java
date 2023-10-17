package game.gamemode;

import game.Player;

public interface GameMode {
    public abstract void initializeDeck();
    public abstract int scoreRound(Player player);
}