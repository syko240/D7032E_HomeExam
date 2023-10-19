package boomerang.game.displaymanager;

import boomerang.game.gamemode.GameMode;

public interface IDisplayManager {
    void printRound(int round, int draft);
    boolean fetchPropmtMessage(int draft);
    void printRoundSummary(int round, GameMode gameMode);
    void printGameSummary();
    void displayChosenCardsToClients();
    void displayHandToClients();
    void displayChosenCardsToClient(int round);
}
