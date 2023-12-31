package boomerang.game;

import java.util.*;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import boomerang.game.card.Card;
import boomerang.game.card.CardAustralia;

public class Deck {
    public static List<Card> loadCardsFromJSON(String filename) {
        List<Card> deck = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(filename));
            JSONArray cardList = (JSONArray) obj;

            for (Object o : cardList) {
                JSONObject cardObject = (JSONObject) o;

                String name = (String) cardObject.get("name");
                String letter = (String) cardObject.get("letter");
                String region = (String) cardObject.get("region");
                int number = Integer.parseInt(cardObject.get("number").toString());
                String collection = (String) cardObject.get("collection");
                String animal = (String) cardObject.get("animal");
                String activity = (String) cardObject.get("activity");

                deck.add(new CardAustralia(name, letter, region, number, collection, animal, activity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deck;
    }
}