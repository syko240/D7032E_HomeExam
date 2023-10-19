package boomerang.communication;

import java.net.*;
import java.util.ArrayList;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import boomerang.communication.threadpool.IExecutableTask;
import boomerang.communication.threadpool.ThreadPool;
import boomerang.game.player.Bot;
import boomerang.game.player.Human;
import boomerang.game.player.Player;

public class Server {
    private ServerSocket aSocket;
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private PlayerManager playerManager = new PlayerManager();
    private static Server server_instance = null;
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

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
                LOGGER.log(Level.WARNING, "Error initializing ClientHandler", e);
            }
        }

        public void sendMessage(Object message) {
            try {
                outToClient.writeObject(message);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error sending message to client", e);
            }
        }

        public String readMessage() {
            try {
                return (String) inFromClient.readObject();
            } catch (SocketException se) {
                // client lost connection
                broadcastConnectionTerminated();
                LOGGER.log(Level.INFO, "Client lost connection", se);
                return null;
            } catch (Exception e) {
                // handle other reading exceptions
                LOGGER.log(Level.WARNING, "Error reading message from client", e);
                return null;
            }
        }

        public void terminateConnection() {
            try {
                socket.close();
                outToClient.close();
                inFromClient.close();
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error terminating client connection", e);
            }
        }
    }

    private Server() {
    }

    public void serverStart(int port) {
        try {
            this.aSocket = new ServerSocket(port);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error starting the server", e);
        }
    }

    public static synchronized Server getInstance() {
        if (server_instance == null)
            server_instance = new Server();

        return server_instance;
    }

    public void initiateBots(int amountOfBots) {
        playerManager.initiateBots(amountOfBots);
    }

    public boolean acceptClient() {
        try {
            Socket connection = aSocket.accept();
            ClientHandler handler = new ClientHandler(connection);
            clients.add(handler);
            playerManager.addHuman(handler.id);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error accepting client", e);
            return false;
        }
    }

    public void listenToClients(int amountOfPlayers) {
        while (clients.size() < amountOfPlayers) {
            if (acceptClient()) {
                System.out.println("Player " + getPlayers().get(getPlayers().size() - 1).getId() + " connected");
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

        // close the server socket if the END message is sent
        if ("END".equals(message) || "Connection terminated".equals(message)) {
            try {
                aSocket.close();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error closing server socket", e);
            }
            System.exit(0); // terminate the server application
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
        ThreadPool<String> pool = new ThreadPool<>(clients.size());
        for (int i = 0; i < clients.size(); i++) {
            int id = i;
            IExecutableTask<String> task = () -> readMessageFromClient(id);
            pool.submitTask(task);
        }
        return (ArrayList<String>) pool.executeTasks();
    }

    public ArrayList<Player> getPlayers() {
        return playerManager.getPlayers();
    }

    public ArrayList<ClientHandler> getClients() {
        return clients;
    }
}

class PlayerManager {
    private ArrayList<Player> players = new ArrayList<>();

    public void initiateBots(int amountOfBots) {
        int startId = players.size();
        for (int i = 1; i <= amountOfBots; i++) {
            Bot bot = new Bot(startId + i);
            players.add(bot);
        }
    }

    public void addHuman(int id) {
        Human newPlayer = new Human(id);
        players.add(newPlayer);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}