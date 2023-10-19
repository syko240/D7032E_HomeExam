package boomerang.game.displaymanager;

import java.util.List;
import java.util.stream.Collectors;

import boomerang.communication.Server;
import boomerang.game.card.Card;
import boomerang.game.gamemode.GameMode;
import boomerang.game.player.Human;
import boomerang.game.player.Player;

public class DisplayManager implements IDisplayManager {
private Server server = Server.getInstance();

    private static final String COLOR_YELLOW = "\033[33m";
    private static final String COLOR_GREEN = "\033[32m";
    private static final String COLOR_BLUE = "\033[34m";
    private static final String RESET_COLOR = "\033[0m";

    @Override
    public void printRound(int round, int draft) {
        server.broadcastMessage("\033[H\033[2J"); // clear terminal for clients
        server.broadcastMessage(COLOR_YELLOW + "Round " + (round + 1) + ", Draft " + (draft + 1) + RESET_COLOR);
        displayChosenCardsToClients();
        displayHandToClients();
    }

    @Override
    public boolean fetchPropmtMessage(int draft) {
        if (draft == 0) {
            server.broadcastMessage(COLOR_YELLOW + "Chose a Throw Card. (Enter the \'letter\' of the card)" + RESET_COLOR);
            return true;
        }
        server.broadcastMessage(COLOR_YELLOW + "Chose a Card to keep. (Enter the \'letter\' of the card)" + RESET_COLOR);
        return false;
        
    }

    @Override
    public void printRoundSummary(int round, GameMode gameMode) {
        server.broadcastMessage("\033[H\033[2J"); // clear terminal for clients
        server.broadcastMessage(COLOR_GREEN + "----- Round Summary -----" + RESET_COLOR);
        displayChosenCardsToClient(round);

        server.broadcastMessage("\n" + COLOR_GREEN + "----- Score Summary -----" + RESET_COLOR);
        for (Player player : server.getPlayers()) {
            server.broadcastMessage(COLOR_YELLOW + "Player " + player.getId() + " scored:" + gameMode.scoreRound(player, player.chosenCards) + RESET_COLOR);
        }
    }

    @Override
    public void printGameSummary() {
        server.broadcastMessage("\033[H\033[2J"); // clear terminal for clients
        server.broadcastMessage(COLOR_GREEN + "----- Game Summary -----" + RESET_COLOR);
        
        // sort players by score in descending order
        List<Player> sortedPlayers = server.getPlayers().stream()
            .sorted((player1, player2) -> Integer.compare(player2.getScore(), player1.getScore()))
            .collect(Collectors.toList());

        // broadcast the sorted leaderboard
        int rank = 1;
        for (Player player : sortedPlayers) {
            server.broadcastMessage(rank + ". Player " + player.getId() + ": " + player.getScore() + " points");
            rank++;
        }
    }

    @Override
    public void displayChosenCardsToClients() {
        for (Player player : server.getPlayers()) {
            server.broadcastMessage(COLOR_BLUE + "Player " + player.getId() + "\'s chosen cards: " + RESET_COLOR);
            for (Card card : player.chosenCards) {
                server.broadcastMessage(COLOR_BLUE + card.getCardString(card.getThrowCard()) + RESET_COLOR);
            }
        }
    }

    @Override
    public void displayHandToClients() {
        for (Player player : server.getPlayers()) {
            if (player instanceof Human) {
                server.getClients().get(player.getId() - 1).sendMessage(COLOR_GREEN + "(" + player.getId() +") Your hand: " + RESET_COLOR);
                for (Card card : player.hand) {
                    server.getClients().get(player.getId() - 1).sendMessage(COLOR_GREEN + card.getCardString(false) + RESET_COLOR);
                }
            }
        }
    }

    @Override
    public void displayChosenCardsToClient(int round) {
        round++;
        for (Player player : server.getPlayers()) {
            if (player instanceof Human) {
                server.getClients().get(player.getId() - 1).sendMessage(COLOR_YELLOW + "(" + player.getId() + ") Cards after round: "+ round + " " + RESET_COLOR);
                for (Card card : player.chosenCards) {
                    server.getClients().get(player.getId() - 1).sendMessage(COLOR_BLUE + card.getCardString(false) + RESET_COLOR);
                }
            }
        }
    }
}
