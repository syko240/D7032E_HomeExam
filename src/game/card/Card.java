package game.card;

public abstract class Card {
    protected String name;
    protected String letter;
    protected String region;
    protected int number;
    protected boolean throwCard;
    protected boolean catchCard;

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

    public abstract boolean getThrowCard();

    public abstract void setThrowCard(boolean throwCard);

    public abstract boolean getCatchCard();

    public abstract void setCatchCard(boolean catchCard);

    public abstract String getCardString(boolean dontShowCard);
}