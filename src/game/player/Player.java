package game.player;

import java.util.ArrayList;
import java.util.List;

import game.card.Card;

public abstract class Player {
    public List<Card> hand = new ArrayList<>();
    public static List<Card> toDraft = new ArrayList<>();
    public int id;

    public Player(int id) {
        this.id = id;
    }
}