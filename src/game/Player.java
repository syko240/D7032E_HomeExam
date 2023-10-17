package game;

import java.util.ArrayList;
import java.util.List;

import game.card.Card;

public class Player {
    public List<Card> hand = new ArrayList<>();
    public static List<Card> draft = new ArrayList<>();
    public int playerID;

    public void draft() {
    }
}