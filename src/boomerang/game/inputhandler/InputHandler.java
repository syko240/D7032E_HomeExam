package boomerang.game.inputhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import boomerang.communication.Server;
import boomerang.game.card.Card;
import boomerang.game.player.Bot;
import boomerang.game.player.Human;
import boomerang.game.player.Player;

public class InputHandler implements IInputHandler {
    private Server server = Server.getInstance();
    private Pattern pattern = Pattern.compile("\\(ClientID: (\\d+)\\)(.*)");
    private static final String COLOR_RED = "\033[31m";
    private static final String RESET_COLOR = "\033[0m";

    @Override
    public void checkPlayerInput(ArrayList<String> messages, boolean throwCard) {
        for (Player player : server.getPlayers()) {
            if (player instanceof Bot) {
                // Simulate bot action
                String botChoice = ((Bot) player).chooseCard();
                processPlayerChoice(player, botChoice, throwCard);
            } else {
                // Process human player action
                String message = messages.get(player.getId() - 1);
                Map<Number, String> msg = splitMessage(message);
                Map.Entry<Number, String> entry = msg.entrySet().iterator().next();

                Number key = entry.getKey();
                String value = entry.getValue();
                processPlayerChoice(server.getPlayers().get(key.intValue()-1), value, throwCard);
            }
        }
    }

    private void processPlayerChoice(Player player, String choice, boolean throwCard) {
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
            server.getClients().get(player.getId()-1).sendMessage(COLOR_RED + "Chose a valid card in your hand by entering the correct \'letter\'" + RESET_COLOR);
            server.getClients().get(player.getId()-1).sendMessage("PROMPT");
            String clientMessage = server.readMessageFromClient(player.getId()-1);
            Map<Number, String> msg = splitMessage(clientMessage);
            Map.Entry<Number, String> entry = msg.entrySet().iterator().next();

            Number key = entry.getKey();
            String value = entry.getValue();
            processPlayerChoice(server.getPlayers().get(key.intValue()-1), value, throwCard);
        }
    }

    private Map<Number, String> splitMessage(String message) {
        Matcher matcher = pattern.matcher(message);
        Map<Number, String> msg = new HashMap<>();
        if (matcher.find()) {
            int clientID = Integer.parseInt(matcher.group(1));
            String clientMessage = matcher.group(2).trim();
            msg.put(clientID, clientMessage);
        }

        return msg;
    }
}
