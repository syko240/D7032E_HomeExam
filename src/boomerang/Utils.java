package boomerang;

import java.util.*;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

// Utility methods
class Utils {

    public static List<Card> loadCardsFromJSON(String filename) {
        List<Card> deck = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(filename));
            JSONArray cardList = (JSONArray) obj;

            for (Object o : cardList) {
                JSONObject cardObject = (JSONObject) o;

                String name = (String) cardObject.get("name");
                String site = (String) cardObject.get("site");
                String region = (String) cardObject.get("region");
                int number = Integer.parseInt(cardObject.get("number").toString());

                List<Symbol> symbols = new ArrayList<>();
                symbols.add(new Symbol("collection", (String) cardObject.get("collection")));
                symbols.add(new Symbol("animal", (String) cardObject.get("animal")));
                symbols.add(new Symbol("activity", (String) cardObject.get("activity")));

                deck.add(new Card(name, site, region, number, symbols));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deck;
    }
}