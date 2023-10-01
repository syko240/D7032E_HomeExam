package boomerang;

import java.util.ArrayList;
import java.util.List;

class BoomerangGame {

    private List<IPlayer> players = new ArrayList<>();
    private GameMode gameMode;
    private DraftingStrategy draftingStrategy;

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
        draftingStrategy = new LeftToRight();

        gameMode = new BoomerangAustralia();

        gameMode.initializeDeck();
        server(numPlayers, numBots);

        GameEngine game = new GameEngine(gameMode, draftingStrategy, players);
        game.startGame();
    }

    public void client(String ipAddress) throws Exception {

    }

    public void server(int numPlayers, int numBots) throws Exception {

    }

    public static void main(String argv[]) {
        try {
            new BoomerangGame(argv);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}