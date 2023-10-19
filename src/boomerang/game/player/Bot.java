package boomerang.game.player;

import java.util.Random;

public class Bot extends Player {
    private static Random random = new Random();

    public Bot(int id) {
        super(id);
    }

    @Override
    public int getId() {
        return this.id;
    }
    
    public String chooseCard() {
        if (!hand.isEmpty()) {
            return hand.get(random.nextInt(hand.size())).getLetter();
        }
        return null;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public void addScore(int score) {
        this.score = this.score + score;
    }
}
