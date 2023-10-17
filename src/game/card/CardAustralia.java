package game.card;

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

    public String getletter() {
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

    public void printCard() {
        String border = "+----------------------------------+";
        String emptyLine = "|                                  |";
    
        System.out.println(border);
        System.out.println(emptyLine);
        System.out.println("|  Name: " + padRight(getName(), 26) + "|");
        System.out.println("|  Letter: " + padRight(getletter(), 24) + "|");
        System.out.println("|  Region: " + padRight(getRegion(), 24) + "|");
        System.out.println("|  Number: " + padRight(String.valueOf(getNumber()), 24) + "|");
        System.out.println("|  Collection: " + padRight(getCollection(), 20) + "|");
        System.out.println("|  Animal: " + padRight(getAnimal(), 24) + "|");
        System.out.println("|  Activity: " + padRight(getActivity(), 22) + "|");
        System.out.println(emptyLine);
        System.out.println(border);
    }
    
    // Helper method to pad a string with spaces on the right
    private String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }
}
