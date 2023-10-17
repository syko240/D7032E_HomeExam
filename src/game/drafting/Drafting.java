package game.drafting;

import java.util.List;

import game.Player;

public interface Drafting {
    void draft(Player currentPlayer, List<Player> allPlayers);
}