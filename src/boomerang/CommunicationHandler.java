package boomerang;

import java.util.ArrayList;
import java.util.Scanner;

import boomerang.communication.Client;
import boomerang.communication.Server;

public class CommunicationHandler {
    private Client client;
    private Server server = Server.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    public void startServer(int port, int numPlayers, int numBots) throws Exception {
        server.serverStart(port);
        server.listenToClients(numPlayers); // connect clients
        server.initiateBots(numBots); // add bots
        server.broadcastMessage("Hello Client");
        ArrayList<String> messages = server.waitForClientMessages(); // wait for all clients to connect
        for (String message : messages) {
            System.out.println(message);
        }
    }

    public void startClient(String ipAddress, int port) throws Exception {
        client = new Client(ipAddress, port);
        String message = client.awaitMessageFromServer();
        System.out.println(message);
        client.sendMessage("Hello Server"); // notify server that client is ready
        awaitMessageFromServer();
    }

    private String promptClientForMessage() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    private void awaitMessageFromServer() {
        try {
            while (true) {
                String message = client.awaitMessageFromServer();
                if (message.equals("PROMPT")) { // check if message received = PROMPT, if so, then prompt the user for input
                    String inputMessage = promptClientForMessage();
                    client.sendMessage(inputMessage);
                } else if (message.equals("END")) {
                    // do nothing bimbam
                } else {
                    System.out.println(message);
                }
            }
        } catch (Exception e) {
            System.out.println("No message from server");
        }
    }
}