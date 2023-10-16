package boomerang.game.drafting;

import java.util.List;

import boomerang.game.Player;

public interface Drafting {
    void draft(Player currentPlayer, List<Player> allPlayers);
}