package game;

import java.util.ArrayList;

import game.drafting.Drafting;
import communication.Server;
import game.card.Card;
import game.gamemode.GameMode;
import game.player.Player;

public class GameEngine {
    private final int LOOP_COUNT = 4;
    private final int DRAFT_COUNT = 7;
    GameMode gameMode;
    Drafting drafting;
    
    public GameEngine(GameMode gameMode, Drafting drafting) {
        this.gameMode = gameMode;
        this.drafting = drafting;
    }

    public void startGame() {

        // draft cards to players
        for (Player player : Server.getInstance().players) {
            player.hand = gameMode.draftCards();
        }

        for (int round = 0; round < LOOP_COUNT; round++) {
            // New round
            for (int draft = 0; draft < DRAFT_COUNT; draft++) {
                printRound(round, draft);
                if (draft == 0) {
                    Server.getInstance().broadcastMessage("Chose a Throw Card.");
                } else {
                    Server.getInstance().broadcastMessage("Chose a Card to keep.");
                }

                Server.getInstance().broadcastMessage("PROMPT");
                ArrayList<String> messages = Server.getInstance().waitForClientMessages();
                for (String message : messages) {
                    System.out.println(message);
                }

                drafting.draft(Server.getInstance().players);
            }
    
        }
        Server.getInstance().broadcastMessage("END");
    }

    private void printRound(int round, int draft) {
        Server.getInstance().broadcastMessage("\033[H\033[2J"); // clear terminal for clients
        Server.getInstance().broadcastMessage("Round " + (round + 1) + ", Draft " + (draft + 1));
        displayChosenCardsToClients();
        displayHandToClients();
    }


    private void displayChosenCardsToClients() {
        for (Player player : Server.getInstance().players) {
            Server.getInstance().broadcastMessage("Player " + player.id + "\'s chosen cards: ");
            for (Card card : player.chosenCards) {
                Server.getInstance().broadcastMessage(card.getCardString());
            }
        }
    }

    private void displayHandToClients() {
        for (Player player : Server.getInstance().players) {
            Server.getInstance().clients.get(player.id - 1).sendMessage("(" + player.id +") Your hand: ");
            for (Card card : player.hand) {
                Server.getInstance().clients.get(player.id - 1).sendMessage(card.getCardString());
            }
        }
    }
}
