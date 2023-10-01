package boomerang;

import java.util.ArrayList;
import java.util.List;

interface IPlayer {
    List<Card> hand = new ArrayList<>();
    List<Card> draft = new ArrayList<>();

    public void draft();
}

class Player implements IPlayer {
    public int playerID;

    @Override
    public void draft() {
    }
}

class Bot implements IPlayer {

    @Override
    public void draft() {
    }
    
}