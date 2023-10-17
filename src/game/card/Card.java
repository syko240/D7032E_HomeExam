package game.card;

public abstract class Card {
    protected String name;
    protected String letter;
    protected String region;
    protected int number;

    public Card(String name, String letter, String region, int number) {
        this.name = name;
        this.letter = letter;
        this.region = region;
        this.number = number;
    }

    public abstract String getName();

    public abstract String getletter();

    public abstract String getRegion();

    public abstract int getNumber();

    public abstract String getCardString();
}