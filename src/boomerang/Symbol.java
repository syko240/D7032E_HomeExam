package boomerang;

public class Symbol {
    private String type;
    private String value;

    public Symbol(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}