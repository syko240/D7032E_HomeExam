package boomerang.communication;

import java.io.*;
import java.net.*;

public class Client {
    //private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String ipAddress) throws IOException {
        socket = new Socket(ipAddress, SERVER_PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() throws IOException {
        return in.readLine();
    }

    public void handleServerMessages() {
        new Thread(() -> {
            try {
                String serverMessage;
                while ((serverMessage = receiveMessage()) != null) {
                    System.out.println(serverMessage);
                    // Handle other game logic based on server messages
                }
            } catch (IOException e) {
                System.out.println("Error reading from server: " + e.getMessage());
            }
        }).start();
    }

    public void sendNameToServer(String playerName) {
        sendMessage(playerName);
    }
}