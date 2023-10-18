package game.player;

import java.util.Random;

public class Bot extends Player {
    private static Random random = new Random();

    public Bot(int id) {
        super(id);
    }
    
    public String chooseCard() {
        if (!hand.isEmpty()) {
            return hand.get(random.nextInt(hand.size())).getLetter();
        }
        return null;
    }
}
