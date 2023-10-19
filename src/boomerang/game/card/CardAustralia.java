package boomerang.game.card;

public class CardAustralia extends Card {
    private String collection;
    private String animal;
    private String activity;

    public CardAustralia(String name, String letter, String region, int number, String collection, String animal, String activity) {
        super(name, letter, region, number);
        this.collection = collection;
        this.animal = animal;
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public String getLetter() {
        return letter;
    }

    public String getRegion() {
        return region;
    }

    public int getNumber() {
        return number;
    }

    public String getCollection() {
        return collection;
    }

    public String getAnimal() {
        return animal;
    }

    public String getActivity() {
        return activity;
    }

    public boolean getThrowCard() {
        return throwCard;
    }

    public void setThrowCard(boolean throwCard) {
        this.throwCard = throwCard;
    }

    public boolean getCatchCard() {
        return catchCard;
    }

    public void setCatchCard(boolean catchCard) {
        this.catchCard = catchCard;
    }

    public String getCardString(boolean dontShowCard) {
        if (dontShowCard) {
            return "card(\"Throw Card\")";
        }
    
        StringBuilder cardString = new StringBuilder();
    
        if (this.getThrowCard()) {
            cardString.append("Throw Card(");
        } else if (this.getCatchCard()) {
            cardString.append("Catch Card(");
        } else {
            cardString.append("card(");
        }
        cardString.append("name: \"").append(getName()).append("\", ");
        cardString.append("letter: \"").append(getLetter()).append("\", ");
        cardString.append("region: \"").append(getRegion()).append("\", ");
        cardString.append("number: \"").append(getNumber()).append("\", ");
        cardString.append("collection: \"").append(getCollection()).append("\", ");
        cardString.append("animal: \"").append(getAnimal()).append("\", ");
        cardString.append("activity: \"").append(getActivity()).append("\")");
    
        return cardString.toString();
    }
}
