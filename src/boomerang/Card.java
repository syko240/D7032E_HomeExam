package boomerang;

public class Card {
    private String name;
    private String site;
    private String region;
    private int number;
    private String collection;
    private String animal;
    private String activity;

    public Card(String name, String site, String region, int number, String collection, String animal, String activity) {
        this.name = name;
        this.site = site;
        this.region = region;
        this.number = number;
        this.collection = collection;
        this.animal = animal;
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
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
}