package boomerang.communication;

import java.net.*;
import java.util.ArrayList;

import boomerang.game.player.Bot;
import boomerang.game.player.Human;
import boomerang.game.player.Player;

import java.io.*;

public class Server {
    public ServerSocket aSocket;
    public ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    public ArrayList<Player> players = new ArrayList<Player>();
    private static Server server_instance = null;

    public class ClientHandler {
        private int id;
        private Socket socket;
        private ObjectOutputStream outToClient;
        private ObjectInputStream inFromClient;
        public static int nextId = 1;

        public ClientHandler(Socket socket) {
            try {
                this.socket = socket;
                this.outToClient = new ObjectOutputStream(socket.getOutputStream());
                this.inFromClient = new ObjectInputStream(socket.getInputStream());
                this.id = nextId;
                nextId++;
            } catch (Exception e) {
                // handle exception
            }
        }

        public void sendMessage(Object message) {
            try {
                outToClient.writeObject(message);
            } catch (Exception e) {
                // handle exception
            }
        }

        public String readMessage() {
            try {
                return (String) inFromClient.readObject();
            } catch (SocketException se) {
                // Client lost connection
                broadcastConnectionTerminated();
                return null;
            } catch (Exception e) {
                // Handle other exceptions
                return null;
            }
        }

        public void terminateConnection() {
            try {
                socket.close();
                outToClient.close();
                inFromClient.close();
            } catch (Exception e) {
                // Handle exception
            }
        }
    }

    public Server() {
    }

    public void serverStart(int port) {
        try {
            this.aSocket = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized Server getInstance() {
        if (server_instance == null)
            server_instance = new Server();

        return server_instance;
    }

    public void initiateBots(int amountOfBots) {
        int startId = players.size();
        for (int i = 1; i <= amountOfBots; i++) {
            Bot bot = new Bot(startId + i); 
            players.add(bot);
        }
    }

    public boolean acceptClient() {
        try {
            Socket connection = aSocket.accept();
            ClientHandler handler = new ClientHandler(connection);
            clients.add(handler);

            Human newPlayer = new Human(handler.id);
            players.add(newPlayer);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void listenToClients(int amountOfPlayers) {
        while (clients.size() < amountOfPlayers) {
            if (acceptClient()) {
                System.out.println("Player " + players.get(players.size() - 1).getId() + " connected");
            }
        }
    }

    public void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
            if ("END".equals(message) || "Connection terminated".equals(message)) {
                client.terminateConnection();
            }
        }

        // Close the server socket if the END message is sent
        if ("END".equals(message) || "Connection terminated".equals(message)) {
            try {
                aSocket.close();
            } catch (IOException e) {
                // Handle exception
            }
            System.exit(0); // Terminate the server application
        }
    }

    public void broadcastConnectionTerminated() {
        broadcastMessage("Connection terminated");
    }

    public String readMessageFromClient(int id) {
        int clientID = clients.get(id).id;
        return "(ClientID: "+ clientID +")" + clients.get(id).readMessage();
    }

    // wait for messages from all clients
    public ArrayList<String> waitForClientMessages() {
        ThreadPool<String> pool = new ThreadPool<String>(clients.size());
        for (int i = 0; i < clients.size(); i++) {
            int id = i;
            pool.submit_task(() -> readMessageFromClient(id));
        }
        return pool.run_tasks();
    }

    // wait for message from client
    public String waitForClientMessage(int id) {
        ThreadPool<String> pool = new ThreadPool<String>(1);
        pool.submit_task(() -> readMessageFromClient(id));
        return pool.run_tasks().get(0);
    }

    public Player getPlayerById(int id) {
        return players.get(id);
    }

    public int getNumberOfPlayers() {
        return players.size();
    }
}