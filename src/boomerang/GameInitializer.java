package boomerang;

import boomerang.game.GameEngine;
import boomerang.game.displaymanager.DisplayManager;
import boomerang.game.displaymanager.IDisplayManager;
import boomerang.game.drafting.BasicDraft;
import boomerang.game.drafting.Drafting;
import boomerang.game.gamemode.BoomerangAustralia;
import boomerang.game.gamemode.GameMode;
import boomerang.game.inputhandler.IInputHandler;
import boomerang.game.inputhandler.InputHandler;
import boomerang.game.scoring.IScoring;
import boomerang.game.scoring.ScoringAustralia;

public class GameInitializer {
    private GameMode gameMode;
    private IScoring scoring;
    private Drafting drafting;
    private GameEngine game;
    private IDisplayManager displayManager;
    private IInputHandler inputHandler;

    public GameInitializer() {
        // initialize game components
        this.scoring = new ScoringAustralia();
        this.gameMode = new BoomerangAustralia(scoring);
        this.drafting = new BasicDraft();
        this.displayManager = new DisplayManager();
        this.inputHandler = new InputHandler();
        this.game = new GameEngine(gameMode, drafting, displayManager, inputHandler);
    }

    public void startGame() {
        game.startGame();
    }
}
