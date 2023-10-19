package boomerang.communication;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;

    public Client(String ipAddress, int port) {
        try {
            this.socket = new Socket(ipAddress, port);
            this.outToServer = new ObjectOutputStream(socket.getOutputStream());
            this.inFromServer = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            // handle exception
        }
    }

    public String awaitMessageFromServer() {
        try {
            while (true) {
                String message = readMessageFromServer();
                if (message == null || "END".equals(message) || "Connection terminated".equals(message)) {
                    terminateConnection();
                    System.exit(0);  // Terminate the client application
                }
                if (message.length() > 0) {
                    return message;
                }
            }
        } catch (Exception e) {
            terminateConnection();  // Ensure termination if an exception occurs
            System.exit(0);  // Terminate the client application
            return "No message from server";
        }
    }

    
    public String readMessageFromServer() {
        try {
            return (String) inFromServer.readObject();
        } catch (Exception e) {
            String terminationMessage = terminateConnection();
            System.out.println(terminationMessage);
            System.exit(0); 
            return terminationMessage; // wont exec
        }
    }
    
    public String terminateConnection() {
        try {
            socket.close();
            outToServer.close();
            inFromServer.close();
        } catch (Exception e) {
            // Handle exception
        }

        return "Connection terminated";
    }

    public void sendMessage(String message) {
        try {
            outToServer.writeObject(message);
        } catch (Exception e) {
            // handle exception
        }
    }
}