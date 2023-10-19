package boomerang.game;

import java.util.ArrayList;

import boomerang.game.drafting.Drafting;
import boomerang.communication.Server;
import boomerang.game.displaymanager.IDisplayManager;
import boomerang.game.gamemode.GameMode;
import boomerang.game.inputhandler.IInputHandler;
import boomerang.game.player.Player;

public class GameEngine {
    private final int LOOP_COUNT = 4;
    private final int DRAFT_COUNT = 6;
    private GameMode gameMode;
    private Drafting drafting;
    private IDisplayManager displayManager;
    private IInputHandler inputHandler;
    private Server server = Server.getInstance();
    
    public GameEngine(GameMode gameMode, Drafting drafting, IDisplayManager displayManager, IInputHandler inputHandler) {
        this.gameMode = gameMode;
        this.drafting = drafting;
        this.displayManager = displayManager;
        this.inputHandler = inputHandler;
    }

    public void startGame() {
        // main game loop
        for (int round = 0; round < LOOP_COUNT; round++) {
            // new round
            System.out.println("Round: " + (round+1));
            initializeRound();
            boolean throwCard = true;

            for (int draft = 0; draft < DRAFT_COUNT; draft++) {
                displayManager.printRound(round, draft);
                throwCard = displayManager.fetchPropmtMessage(draft);

                server.broadcastMessage("PROMPT"); // prompt user
                ArrayList<String> messages = server.waitForClientMessages();
                inputHandler.checkPlayerInput(messages, throwCard);

                drafting.draft(server.players);
            }

            displayManager.printRoundSummary(round, gameMode); // display all the chosen cards to the client after each round

            server.broadcastMessage("PROMPT");
            server.waitForClientMessages(); // wait for user input, then start next round
        }

        displayManager.printGameSummary();

        server.broadcastMessage("END"); // end game
    }

    private void initializeRound() {
        // reset the deck
        gameMode.initializeDeck();

        // shuffle deck and draft cards to players
        for (Player player : server.players) {
            player.hand = gameMode.cardHandOut();
            player.chosenCards.clear();
        }
    }
}
