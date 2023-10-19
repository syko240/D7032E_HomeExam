package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import game.drafting.Drafting;
import communication.Server;
import game.card.Card;
import game.gamemode.GameMode;
import game.player.Bot;
import game.player.Human;
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
            System.out.println("Round: " + (round+1));
            initializeRound();
            boolean throwCard = true;

            for (int draft = 0; draft < DRAFT_COUNT; draft++) {
                printRound(round, draft);
                if (draft == 0) {
                    Server.getInstance().broadcastMessage("\033[33mChose a Throw Card. (Enter the \'letter\' of the card)\033[0m");
                } else {
                    Server.getInstance().broadcastMessage("\033[33mChose a Card to keep. (Enter the \'letter\' of the card)\033[0m");
                    throwCard = false;
                }

                Server.getInstance().broadcastMessage("PROMPT");
                ArrayList<String> messages = Server.getInstance().waitForClientMessages();
                checkPlayerInput(messages, throwCard);

                drafting.draft(Server.getInstance().players);
            }

            printRoundSummery(round); // display all the chosen cards to the client after each round

            Server.getInstance().broadcastMessage("PROMPT");
            Server.getInstance().waitForClientMessages(); // wait for user input, then start next round
        }

        printGameSummery();

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

    private void checkPlayerInput(ArrayList<String> messages, boolean throwCard) {
        Pattern pattern = Pattern.compile("\\(ClientID: (\\d+)\\)(.*)");
        for (Player player : Server.getInstance().players) {
            if (player instanceof Bot) {
                // Simulate bot action
                String botChoice = ((Bot) player).chooseCard();
                processPlayerChoice(player, botChoice, throwCard, pattern);
            } else {
                // Process human player action
                String message = messages.get(player.getId() - 1);
                Map<Number, String> msg = splitMessage(pattern, message);
                Map.Entry<Number, String> entry = msg.entrySet().iterator().next();

                Number key = entry.getKey();
                String value = entry.getValue();
                processPlayerChoice(Server.getInstance().players.get(key.intValue()-1), value, throwCard, pattern);
            }
        }
    }

    private void processPlayerChoice(Player player, String choice, boolean throwCard, Pattern pattern) {
        int index = 0;
        boolean validInput = false;
        for (Card card : player.hand) {
            if (choice.equals(card.getLetter())) {
                if (throwCard) {
                    card.setThrowCard(true);
                }
                player.chosenCards.add(player.hand.remove(index));
                validInput = true;
                break;
            }
            index++;
        }
        if (!validInput && player instanceof Human) {
            Server.getInstance().clients.get(player.getId()-1).sendMessage("\033[31mChose a valid card in your hand by entering the correct \'letter\'\033[0m");
            Server.getInstance().clients.get(player.getId()-1).sendMessage("PROMPT");
            String clientMessage = Server.getInstance().readMessageFromClient(player.getId()-1);
            Map<Number, String> msg = splitMessage(pattern, clientMessage);
            Map.Entry<Number, String> entry = msg.entrySet().iterator().next();

            Number key = entry.getKey();
            String value = entry.getValue();
            processPlayerChoice(Server.getInstance().players.get(key.intValue()-1), value, throwCard, pattern);
        }
    }

    private Map<Number, String> splitMessage(Pattern pattern, String message) {
        Matcher matcher = pattern.matcher(message);
        Map<Number, String> msg = new HashMap<>();
        if (matcher.find()) {
            int clientID = Integer.parseInt(matcher.group(1));
            String clientMessage = matcher.group(2).trim();
            msg.put(clientID, clientMessage);
        }

        return msg;
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
        for (Player player : Server.getInstance().players) {
            Server.getInstance().broadcastMessage("\033[33mPlayer " + player.getId() + " scored:" + gameMode.scoreRound(player, player.chosenCards) + "\033[0m");
        }
    }

    private void printGameSummery() {
        Server.getInstance().broadcastMessage("\033[H\033[2J"); // clear terminal for clients
        Server.getInstance().broadcastMessage("\033[32m----- Game Summery -----\033[0m");
        
        // Sort players by score in descending order
        List<Player> sortedPlayers = Server.getInstance().players.stream()
            .sorted((player1, player2) -> Integer.compare(player2.getScore(), player1.getScore()))
            .collect(Collectors.toList());

        // Broadcast the sorted leaderboard
        int rank = 1;
        for (Player player : sortedPlayers) {
            Server.getInstance().broadcastMessage(rank + ". Player " + player.getId() + ": " + player.getScore() + " points");
            rank++;
        }
    }


    private void displayChosenCardsToClients() {
        for (Player player : Server.getInstance().players) {
            Server.getInstance().broadcastMessage("\033[34mPlayer " + player.getId() + "\'s chosen cards: \033[0m");
            for (Card card : player.chosenCards) {
                Server.getInstance().broadcastMessage("\033[34m" + card.getCardString(card.getThrowCard()) + "\033[0m");
            }
        }
    }

    
    private void displayHandToClients() {
        for (Player player : Server.getInstance().players) {
            if (player instanceof Human) {
                Server.getInstance().clients.get(player.getId() - 1).sendMessage("\033[32m(" + player.getId() +") Your hand: \033[0m");
                for (Card card : player.hand) {
                    Server.getInstance().clients.get(player.getId() - 1).sendMessage("\033[32m" + card.getCardString(false) + "\033[0m");
                }
            }
        }
    }

    private void displayChosenCardsToClient(int round) {
        round++;
        for (Player player : Server.getInstance().players) {
            if (player instanceof Human) {
                Server.getInstance().clients.get(player.getId() - 1).sendMessage("\033[33m(" + player.getId() + ") Cards after round: "+ round +" \033[0m");
                for (Card card : player.chosenCards) {
                    Server.getInstance().clients.get(player.getId() - 1).sendMessage("\033[34m" + card.getCardString(false) + "\033[0m");
                }
            }
        }
    }

}
