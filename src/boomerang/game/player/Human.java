package boomerang.game.player;

public class Human extends Player {

    public Human(int id) {
        super(id);
    }

    @Override
    public int getId() {
        return this.id;
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
