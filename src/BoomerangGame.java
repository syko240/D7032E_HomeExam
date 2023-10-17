import java.util.ArrayList;
import java.util.List;

import communication.*;
import game.*;
import game.card.Card;

class BoomerangGame {
    private List<Card> deck;

    public BoomerangGame(String[] params) throws Exception {
        if (params.length == 1) {
            Server server = new Server(Integer.parseInt(params[0]));
            server.listenToClients(2);
            server.broadcastMessage("Hello");
            ArrayList<String> messages = server.waitForClientMessages();
            for (String message : messages) {
                System.out.println(message);
            }
            init();
        } else if (params.length == 2) {
            Client client = new Client(params[0], Integer.parseInt(params[1]));
            String message = client.awaitMessageFromServer();
            System.out.println(message);
            client.sendMessage("nword");
        } else {
            System.out.println("Usage: java Boomerang <port> or java Boomerang <ip> <port>");
        }
    }

    public void init() {
        deck = Deck.loadCardsFromJSON("resources/australia/cards.json");
        
    }

    public static void main(String argv[]) {
        try {
            new BoomerangGame(argv);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}