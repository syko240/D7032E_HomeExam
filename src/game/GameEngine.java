package game;

import java.util.ArrayList;

import communication.Server;
import communication.Server.ClientHandler;
import game.card.Card;
import game.gamemode.GameMode;
import game.player.Player;

public class GameEngine {
    private final int LOOP_COUNT = 4;
    GameMode gameMode;
    
    public GameEngine(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void startGame() {

        // draft cards to players
        for (Player player : Server.getInstance().players) {
            player.hand = gameMode.draftCards();
        }

        for (Player player : Server.getInstance().players) {
            Server.getInstance().clients.get(player.id - 1).sendMessage("Player " + player.id + "'s hand: ");
            for (Card card : player.hand) {
                Server.getInstance().clients.get(player.id - 1).sendMessage(card.getCardString());
            }
        }

        for (int i = 0; i < LOOP_COUNT; i++) {
            // New round
            Server.getInstance().broadcastMessage("Round " + (i + 1));
    
            ArrayList<String> messages = Server.getInstance().waitForClientMessages();
            for (String message : messages) {
                System.out.println(message);
            }
        }
        Server.getInstance().broadcastMessage("END");
    }
}
