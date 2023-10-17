package game.drafting;

import java.util.List;

import game.player.Player;

public interface Drafting {
    void draft(Player currentPlayer, List<Player> allPlayers);
}