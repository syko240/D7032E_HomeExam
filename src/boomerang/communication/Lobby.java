package boomerang.communication;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private List<ClientHandler> clients;
    private final int MAX_PLAYERS = 4;

    public Lobby() {
        clients = new ArrayList<>();
    }

    public synchronized void addClient(ClientHandler client) {
        if (clients.size() < MAX_PLAYERS) {
            clients.add(client);
            broadcastMessage(client.getName() + " has joined the lobby. (" + clients.size() + "/" + MAX_PLAYERS + ")");
            if (clients.size() == MAX_PLAYERS) {
                startGame();
            }
        } else {
            client.sendMessage("Lobby is full. Please wait.");
        }
    }

    public synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
        broadcastMessage(client.getName() + " has left the lobby.");
    }

    private void startGame() {
        broadcastMessage("All players are here. Starting the game!");
        // Initialize game logic here
    }

    private void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public synchronized boolean allPlayersConnected(int totalPlayers) {
        return clients.size() == totalPlayers;
    }
}
