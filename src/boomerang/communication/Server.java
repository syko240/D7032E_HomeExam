package boomerang.communication;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();

    private Lobby lobby;

    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
        lobby = new Lobby();
    }

    public void start() throws IOException {
        System.out.println("Server started. Waiting for clients...");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket, this);
            clients.add(clientHandler);
            new Thread(clientHandler).start();
        }
    }

    public void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public Lobby getLobby() {
        return lobby;
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private Server server;
    private PrintWriter out;
    private String name;
    private BufferedReader in;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.name = "";
        this.server.getLobby().addClient(this);
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error getting input stream: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            // First, read the player's name
            name = in.readLine();

            // Notify the lobby about the new player
            server.getLobby().addClient(this);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            out = new PrintWriter(output, true);

            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                server.broadcastMessage(clientMessage);
            }

            socket.close();
            server.getLobby().removeClient(this);
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
