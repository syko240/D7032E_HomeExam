package game.drafting;

import java.util.ArrayList;
import java.util.List;

import game.card.Card;
import game.player.Player;

// Clockwise Drafting
public class BasicDraft implements Drafting {
    @Override
    public void draft(List<Player> players) {
        List<Card> tempLastHand = new ArrayList<>(players.get(players.size() - 1).hand);

        for (int i = players.size() - 1; i > 0; i--) {
            players.get(i).hand = players.get(i - 1).hand;
        }
        players.get(0).hand = tempLastHand;
    }
}
