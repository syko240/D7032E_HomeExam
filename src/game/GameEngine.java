package game;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import game.drafting.Drafting;
import communication.Server;
import game.card.Card;
import game.gamemode.GameMode;
import game.player.Player;

public class GameEngine {
    private final int LOOP_COUNT = 4;
    private final int DRAFT_COUNT = 6;
    GameMode gameMode;
    Drafting drafting;
    
    public GameEngine(GameMode gameMode, Drafting drafting) {
        this.gameMode = gameMode;
        this.drafting = drafting;
    }

    public void startGame() {

        for (int round = 0; round < LOOP_COUNT; round++) {
            // New round
            initializeRound();
            boolean throwCard = true;

            for (int draft = 0; draft < DRAFT_COUNT; draft++) {
                printRound(round, draft);
                if (draft == 0) {
                    Server.getInstance().broadcastMessage("\033[33mChose a Throw Card. (Enter the \'letter\' of the card)\033[0m");
                } else {
                    Server.getInstance().broadcastMessage("\033[33mChose a Card to keep. (Enter the \\'letter\\' of the card)\033[0m");
                    throwCard = false;
                }

                Server.getInstance().broadcastMessage("PROMPT");
                ArrayList<String> messages = Server.getInstance().waitForClientMessages();
                checkClientInput(messages, throwCard);

                drafting.draft(Server.getInstance().players);
            }
            //drafting.draft(Server.getInstance().players); // draft catch card
            printRoundSummery(round); // display all the chosen cards to the client after each round

            Server.getInstance().broadcastMessage("PROMPT");
            Server.getInstance().waitForClientMessages(); // wait for user input, then start next round
            
        }
        Server.getInstance().broadcastMessage("END");
    }

    private void initializeRound() {
        // reset the deck
        gameMode.initializeDeck();

        // shuffle deck and draft cards to players
        for (Player player : Server.getInstance().players) {
            player.hand = gameMode.draftCards();
            player.chosenCards.clear();
        }
    }

    private void checkClientInput(ArrayList<String> messages, boolean throwCard) {
        Pattern pattern = Pattern.compile("\\(ClientID: (\\d+)\\)(.*)");

        for (String message : messages) {
            Matcher matcher = pattern.matcher(message);
            boolean validInput = false;
            int clientID = 0;
            String clientMessage = "";

            if (matcher.find()) {  // if the pattern is found in the message
                clientID = Integer.parseInt(matcher.group(1));  // extract clientID
                clientMessage = matcher.group(2).trim();     // extract the actual message
            }

            while (!validInput) {
                int index = 0;
                for (Card card : Server.getInstance().players.get(clientID-1).hand) {
                    if (clientMessage.equals(card.getletter())) {
                        if (throwCard) {
                            card.setThrowCard(true);
                        }
                        Server.getInstance().players.get(clientID-1).chosenCards.add(Server.getInstance().players.get(clientID-1).hand.remove(index));
                        validInput = true;
                        System.out.println(validInput);
                        break;
                    }
                    index++;
                }

                if (!validInput) {
                    Server.getInstance().clients.get(clientID-1).sendMessage("\033[31mChose a valid card in your hand by entering the correct \'letter\'\033[0m");
                    Server.getInstance().clients.get(clientID-1).sendMessage("PROMPT");
                    clientMessage = Server.getInstance().readMessageFromClient(clientID-1);
                }
            }

            System.out.println(message);
        }
    }

    private void printRound(int round, int draft) {
        Server.getInstance().broadcastMessage("\033[H\033[2J"); // clear terminal for clients
        Server.getInstance().broadcastMessage("\033[33mRound " + (round + 1) + ", Draft " + (draft + 1) + "\033[0m");
        displayChosenCardsToClients();
        displayHandToClients();
    }

    private void printRoundSummery(int round) {
        Server.getInstance().broadcastMessage("\033[H\033[2J"); // clear terminal for clients
        Server.getInstance().broadcastMessage("\033[32m----- Round Summery -----\033[0m");
        displayChosenCardsToClient(round);

        Server.getInstance().broadcastMessage("\n\033[32m----- Score Summery -----\033[0m");
    }


    private void displayChosenCardsToClients() {
        for (Player player : Server.getInstance().players) {
            Server.getInstance().broadcastMessage("\033[34mPlayer " + player.id + "\'s chosen cards: \033[0m");
            for (Card card : player.chosenCards) {
                Server.getInstance().broadcastMessage("\033[34m" + card.getCardString(card.getThrowCard()) + "\033[0m");
            }
        }
    }

    
    private void displayHandToClients() {
        for (Player player : Server.getInstance().players) {
            Server.getInstance().clients.get(player.id - 1).sendMessage("\033[32m(" + player.id +") Your hand: \033[0m");
            for (Card card : player.hand) {
                Server.getInstance().clients.get(player.id - 1).sendMessage("\033[32m" + card.getCardString(false) + "\033[0m");
            }
        }
    }

    private void displayChosenCardsToClient(int round) {
        round++;
        for (Player player : Server.getInstance().players) {
            Server.getInstance().clients.get(player.id - 1).sendMessage("\033[33m(" + player.id + ") Cards after round: "+ round +" \033[0m");
            for (Card card : player.chosenCards) {
                Server.getInstance().clients.get(player.id - 1).sendMessage("\033[34m" + card.getCardString(false) + "\033[0m");
            }
        }
    }

}
