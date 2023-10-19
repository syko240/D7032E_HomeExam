package boomerang.communication;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private ConnectionManager connectionManager;
    private CommunicationManager communicationManager;

    public Client(String ipAddress, int port) {
        this.connectionManager = new ConnectionManager(ipAddress, port);
        this.communicationManager = new CommunicationManager(connectionManager);
    }

    public String awaitMessageFromServer() {
        return communicationManager.awaitMessage();
    }

    public void sendMessage(String message) {
        communicationManager.sendMessage(message);
    }
}

class ConnectionManager {
    private Socket socket;
    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    public ConnectionManager(String ipAddress, int port) {
        try {
            this.socket = new Socket(ipAddress, port);
            this.outToServer = new ObjectOutputStream(socket.getOutputStream());
            this.inFromServer = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error initializing ConnectionManager", e);
        }
    }

    public ObjectOutputStream getOutputStream() {
        return outToServer;
    }

    public ObjectInputStream getInputStream() {
        return inFromServer;
    }

    public void closeConnection() {
        try {
            socket.close();
            outToServer.close();
            inFromServer.close();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error closing connection", e);
        }
    }
}

class CommunicationManager {
    private ConnectionManager connectionManager;
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    public CommunicationManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public String awaitMessage() {
        try {
            while (true) {
                String message = readMessage();
                if (message == null || "END".equals(message) || "Connection terminated".equals(message)) {
                    connectionManager.closeConnection();
                    System.exit(0);  // terminate the client application
                }
                if (message.length() > 0) {
                    return message;
                }
            }
        } catch (Exception e) {
            connectionManager.closeConnection();
            LOGGER.log(Level.SEVERE, "Error awaiting message", e);
            System.exit(0);  // terminate the client application
            return "No message from server";
        }
    }

    public void sendMessage(String message) {
        try {
            connectionManager.getOutputStream().writeObject(message);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error sending message", e);
        }
    }

    private String readMessage() {
        try {
            return (String) connectionManager.getInputStream().readObject();
        } catch (Exception e) {
            connectionManager.closeConnection();
            LOGGER.log(Level.SEVERE, "Error reading message", e);
            System.exit(0);
            return "Connection terminated"; // won't execute
        }
    }
}