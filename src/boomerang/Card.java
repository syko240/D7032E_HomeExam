package boomerang;

import java.util.List;

public class Card {
    private String name;
    private String site;
    private String region;
    private int number;
    private List<Symbol> symbols;

    public Card(String name, String site, String region, int number, List<Symbol> symbols) {
        this.name = name;
        this.site = site;
        this.region = region;
        this.number = number;
        this.symbols = symbols;
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

    public List<Symbol> getSymbols() {
        return symbols;
    }
}