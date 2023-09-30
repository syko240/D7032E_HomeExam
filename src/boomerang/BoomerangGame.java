package boomerang;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

class BoomerangGame {

    private List<Player> players = new ArrayList<>();
    private GameMode gameMode;
    private DraftingStrategy draftingStrategy;
    private ServerSocket aSocket;

    public BoomerangGame(String[] params) throws Exception {
        if (params.length == 2) {
            gameMode = new BoomerangAustralia();
            int numPlayers = Integer.valueOf(params[0]);
            int numBots = Integer.valueOf(params[1]);
            draftingStrategy = (numPlayers + numBots == 2) ? new LeftToRight() : new LeftToRight(); // You can alternate this based on rounds or choice in the future

            this.initGame(numPlayers, numBots);
        } else if (params.length == 1) {
            client(params[0]);
        } else {
            System.out.println("Please provide the correct arguments.");
            System.out.println("Server syntax: java BoomerangGame numPlayers numBots");
            System.out.println("Client syntax: IP");
        }
    }

    public void initGame(int numPlayers, int numBots) throws Exception {
        gameMode.initializeDeck();
        server(numPlayers, numBots);
        // Initialize players and deal cards, implement the drafting, scoring, and other game logics from the original code here
    }

    public void client(String ipAddress) throws Exception {
        // Client logic stays mostly the same as the original code
    }

    public void server(int numPlayers, int numBots) throws Exception {
        // Server logic stays mostly the same as the original code
        // But ensure the cards dealt are from the 'gameMode' deck
        // And the drafting happens based on the 'draftingStrategy'
    }

    public static void main(String argv[]) {
        try {
            new BoomerangGame(argv);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}