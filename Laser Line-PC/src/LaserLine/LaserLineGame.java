package LaserLine;

import Draw.Draw;
import Game.Game;
import GameData.ScoreData;
import GameObject.MenuObject;
import Save.SaveData;
import Save.SaveItem;

public class LaserLineGame {
    private Game game;
//    private LaserData laserData;
    private ScoreData gameData;

    private final String SAVE_PATH = "laser_data";
    
    private final int NUM_SCORES = 3;
    private final int VIEW_WIDTH = 330;
    private final int VIEW_HEIGHT = 560;
    
    private final String NAME_MENU = "STATE_MENU";
    private final String NAME_HELP = "STATE_OPTIONS";
    private final String NAME_SCORE = "STATE_SCORES";
    private final String NAME_PLAY = "STATE_PLAY";
    
    private final float FONT_PERC = 0.05f;
    
    private LaserState mainMenu;
    private ScoreState scoreState;
    
    private PlayState statePlay;
    
    public LaserLineGame() {
        
        gameData = new ScoreData(3);
        loadScores();
        
        game = new Game(gameData, VIEW_WIDTH, VIEW_HEIGHT){
            @Override
            public void finish() {
                onClose();
            }
        };
        setupStates();
        
        game.addGameState(mainMenu);
//        game.addGameState(helpState);
        game.addGameState(scoreState);
        game.addGameState(statePlay);
        
        game.setState(NAME_MENU);
        game.start();
    }
    
    private void loadScores() {
        SaveData save = new SaveData(SAVE_PATH);
        save.reload();
        SaveItem gd = save.getItem("LaserData");
        if (gd != null)
            gameData.load(gd);
    }
    
    private void setupStates() {
        setupMenu();
        setupScore();
        setupPlay();
    }
    
    private void setupMenu() {
        mainMenu = new LaserState(NAME_MENU, 3, 10) {
            @Override
            public void draw(Draw draw) {
                draw.setColor(255, 255, 255);
                draw.drawRect(getBounds(), true);
                draw.setFont("Arial", (int) (VIEW_HEIGHT*FONT_PERC));
                super.draw(draw);
            }
        };
        MenuObject btnStart = new MenuObject(NAME_PLAY, 1, 2, 1, 1){
            @Override
            public void draw(Draw draw) {
                draw.setColor(0, 0, 0);
                draw.drawCentredText("Start", getBounds());
            }
        };
        MenuObject btnScore = new MenuObject(NAME_SCORE, 1, 4, 1, 1)  {
            @Override
            public void draw(Draw draw) {
                draw.setColor(0, 0, 0);
                draw.drawCentredText("Scores", getBounds());
            }
        };
        mainMenu.addObject(btnStart);
        mainMenu.addObject(btnScore);
    }
    
    private void setupScore() {
        scoreState = new ScoreState(NAME_SCORE, NAME_MENU, NUM_SCORES, FONT_PERC);
    }
    
    private void setupPlay() {
        statePlay = new PlayState(NAME_PLAY, NAME_MENU, VIEW_WIDTH, VIEW_WIDTH){
            @Override
            public void draw(Draw graphics) {
                graphics.setColor(0, 0, 0);
                graphics.setFont("Arial", (int) (VIEW_HEIGHT*FONT_PERC));
                super.draw(graphics);
            }
        };
    }
    
    private void onClose() {
        SaveData save = new SaveData(SAVE_PATH);
        SaveItem scores = gameData.getSaveData();
        scores.setName("LaserData");
        save.addItem(scores);
        save.editItem("LaserData", scores);
        save.saveAll();
    }
}
