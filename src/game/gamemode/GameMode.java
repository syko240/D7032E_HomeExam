package game.gamemode;

import java.util.List;

import game.card.Card;
import game.player.Player;

public interface GameMode {
    public void initializeDeck();
    public List<Card> draftCards();
    public int scoreRound(Player player);
}