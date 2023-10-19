package game.player;

import java.util.ArrayList;
import java.util.List;

import game.card.Card;

public abstract class Player {
    public List<Card> hand = new ArrayList<>();
    public List<Card> chosenCards = new ArrayList<>();
    protected int id;
    protected int score;

    public Player(int id) {
        this.id = id;
    }

    public abstract int getId();

    public abstract int getScore();

    public abstract void addScore(int score);
}