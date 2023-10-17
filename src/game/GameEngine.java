package game;

import java.util.ArrayList;

import communication.Server;

public class GameEngine {
    private final int LOOP_COUNT = 4;
    
    public GameEngine() {
    }

    public void startGame() {
        Server.getInstance().broadcastMessage("START");
        for (int i = 0; i < LOOP_COUNT; i++) {
            // Notify clients that a new round has started
            Server.getInstance().broadcastMessage("Round " + (i + 1));
    
            ArrayList<String> messages = Server.getInstance().waitForClientMessages();
            for (String message : messages) {
                System.out.println(message);
            }
    
            //Server.getInstance().broadcastMessage("Received messages for round " + (i + 1));
        }
        Server.getInstance().broadcastMessage("END");
    }
}
