package boomerang;

import java.util.ArrayList;
import java.util.List;

import boomerang.communication.Client;
import boomerang.communication.Server;
import boomerang.game.GameEngine;
import boomerang.game.Player;
import boomerang.game.drafting.BasicDraft;
import boomerang.game.drafting.Drafting;
import boomerang.game.gamemode.BoomerangAustralia;
import boomerang.game.gamemode.GameMode;

class BoomerangGame {

    private List<Player> players = new ArrayList<>();
    private GameMode gameMode;
    private Drafting draftingStrategy;

    private Server gameServer;
    private Client gameClient;

    public BoomerangGame(String[] params) throws Exception {
        if (params.length == 2) {
            int numPlayers = Integer.valueOf(params[0]);
            int numBots = Integer.valueOf(params[1]);

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
        draftingStrategy = new BasicDraft();
        gameMode = new BoomerangAustralia();
        gameMode.initializeDeck();
        
        server(numPlayers, numBots);

        GameEngine game = new GameEngine(gameMode, draftingStrategy, players);
        game.startGame();
    }

    public void client(String ipAddress) throws Exception {
        gameClient = new Client(ipAddress);
        gameClient.handleServerMessages();
        // more logic to handle game-specific messages from the server.
    }

    public void server(int numPlayers, int numBots) throws Exception {
        gameServer = new Server();
        gameServer.start(); // start the server and wait for clients to connect.

        while (!gameServer.getLobby().allPlayersConnected(numPlayers + numBots)) {
            Thread.sleep(1000); // Wait for 1 second before checking again
        }
        // once the desired number of players (including bots) have connected, start game.
    }

    public static void main(String argv[]) {
        try {
            new BoomerangGame(argv);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}