import java.util.ArrayList;
import java.util.Scanner;

import game.gamemode.GameMode;
import game.gamemode.BoomerangAustralia;
import communication.*;
import game.*;

class BoomerangGame {
    private GameMode gameMode;
    private GameEngine game;
    private Client client;
    private final Scanner scanner = new Scanner(System.in);

    public BoomerangGame(String[] params) throws Exception {
        if (params.length == 3) {
            int port = Integer.parseInt(params[0]);
            int numPlayers = Integer.valueOf(params[1]);
            int numBots = Integer.valueOf(params[2]);

            this.init(port, numPlayers, numBots);
        } else if (params.length == 2) {
            String ipAddress = params[0];
            int port = Integer.parseInt(params[1]);
            client(ipAddress, port);
        } else {
            System.out.println("Please provide the correct arguments.");
            System.out.println("Server syntax: java BoomerangGame numPlayers numBots");
            System.out.println("Client syntax: IP");
        }
    }

    public void init(int port, int numPlayers, int numBots) throws Exception {
        /*deck = Deck.loadCardsFromJSON("resources/australia/cards.json");

        for (Card card : deck) {
            if (card instanceof CardAustralia) {
                ((CardAustralia) card).printCard();
                System.out.println();  // Add an empty line between cards for better readability
            }
        }*/

        gameMode = new BoomerangAustralia();
        gameMode.initializeDeck();

        server(port, numPlayers, numBots);

        game = new GameEngine(gameMode);
        game.startGame();
    }

    public void client(String ipAddress, int port) throws Exception {
        client = new Client(ipAddress, port);
        String message = client.awaitMessageFromServer();
        System.out.println(message);
        client.sendMessage("Hello Server");
        awaitMessageFromServer();
    }

    public String promptClientForMessage() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    public void awaitMessageFromServer() {
        try {
            while (true) {
                String message = client.readMessageFromServer();
                if (message.startsWith("Round ")) {
                    System.out.println(message);
                    String inputMessage = promptClientForMessage();
                    client.sendMessage(inputMessage);
                } else if (message.equals("END")) {
                    // bimbam do nothing
                } else {
                    System.out.println(message);
                }
            }
        } catch (Exception e) {
            System.out.println("No message from server");
        }
    }

    public void server(int port, int numPlayers, int numBots) throws Exception {
        Server.getInstance().serverStart(port);
        Server.getInstance().listenToClients(numPlayers);
        Server.getInstance().broadcastMessage("Hello Client");
        ArrayList<String> messages = Server.getInstance().waitForClientMessages();
        for (String message : messages) {
            System.out.println(message);
        }
    }

    public static void main(String argv[]) {
        try {
            new BoomerangGame(argv);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}