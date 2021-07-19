package LaserLine;

import GameData.GameData;
import GameObject.MenuObject;
import GameState.ContainerState;
import GameState.MenuState;
import GameState.PhysicsState;
import Input.Input;
import Draw.Draw;
import GameData.ScoreData;
import GameObject.CollisionObject;
import java.util.LinkedList;
import java.util.Random;

public class PlayState extends ContainerState {
    
    private final String NAME_PLAY = "PLAY_PLAY_AREA";
    private final String NAME_LOSE = "PLAY_LOSE";
    private final String NAME_MAIN;
    
    private MenuState loseState;
    private PhysicsState playArea;
    
    private int viewWidth, viewHeight;
    
    private final int DELTA_SCORE = 1;
    private final double DELTA_LASER_SPEED;
    
    private final double MAX_LASER_SPEED;
    private final double MIN_LASER_SPEED;
    
    private double laserSpeed;
    
    
    private int score;
//    private int finalScore;
    
    private MenuObject lblScore;
    private Player player;
    private LinkedList<Laser> lasers;
    
    public PlayState(String name, String mainStateName, int viewWidth, int viewHeight) {
        super(name, 5, 5);
        NAME_MAIN = mainStateName;
        DELTA_LASER_SPEED = 0.0005;
        MAX_LASER_SPEED = 7;
        MIN_LASER_SPEED = 2;
        lasers = new LinkedList<>();
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        setupObjects();
        setupStates();
        reset();
    }
    
    private void setupObjects() {
        player = new Player(viewWidth, viewHeight) {
            @Override
            public void update(Input input, GameData data) {
                if (!player.shouldLose())
                    super.update(input, data);
            }
        };
        lblScore = new MenuObject("", 0, 0, 5, 1) {
            @Override
            public void draw(Draw graphics) {
                graphics.setColor(0, 0, 0);
                graphics.drawCentredText("" + ((int) score), getBounds());
            }
        };
    }
    
    private void setupStates() {
        setupPlayArea();
        setupLoseState();
    }
    
    private void setupPlayArea() {
        playArea = new PhysicsState(NAME_PLAY);
        playArea.setLocation(0, 0, 5, 5);
        playArea.addObject(player);
        addObject(playArea);
        addObject(lblScore);
    }
    
    private void setupLoseState() {
        loseState = new MenuState(NAME_LOSE, 3, 5);
        loseState.setLocation(1, 1, 3, 3);
        MenuObject btnAgain = new MenuObject(NAME_PLAY, 1, 1, 1, 1){
            @Override
            public void draw(Draw draw) {
                draw.setColor(0, 0, 0);
                draw.drawCentredText("Again", getBounds());
            }
        };
        MenuObject btnBack = new MenuObject(NAME_MAIN, 1, 3, 1, 1){
            @Override
            public void draw(Draw draw) {
                draw.setColor(0, 0, 0);
                draw.drawCentredText("Back", getBounds());
            }
        };
        loseState.addObject(btnAgain);
        loseState.addObject(btnBack);
        addObject(loseState);
    }
    
    private void reset() {
        player.reset();
        loseState.setNextState("");
        setNextState("");
        for (Laser laser : lasers) {
            playArea.removeObject((CollisionObject) laser);
        }
        lasers.clear();
        score = 0;
        laserSpeed = MIN_LASER_SPEED;
    }
    //
    // end stetup
    //
    @Override
    public void draw(Draw draw) {
        super.draw(draw);
        draw.setColor(255, 255, 255);
        draw.drawRect(getBounds(), true);
        playArea.draw(draw);
        if (player.shouldLose()) {
            loseState.draw(draw);
        } else {
            lblScore.draw(draw);
        }
    }
    
    @Override
    public void update(Input input, GameData gameData) {
        super.update(input, gameData);
        if (!player.shouldLose()) {
            updatePlaying(input, gameData);
        }
        if (player.shouldLose()) {
            loseState.update(input, gameData);
            ScoreData scoreData = (ScoreData) gameData;
            scoreData.updateScores();
            scoreData.resetScore();
            
            String nextState = loseState.getNextState();
            if (nextState.equals(NAME_PLAY)) {
                reset();
            } else if (nextState.equals(NAME_MAIN)) {
                reset();
                this.setNextState(NAME_MAIN);
            }
        }
    }
    
    private void updatePlaying(Input input, GameData data) {
        ScoreData scoreData = (ScoreData) data;
        scoreData.incrementScore(DELTA_SCORE);
        score = (int) scoreData.getCurrentScore();
        laserSpeed += DELTA_LASER_SPEED;
        if (laserSpeed > MAX_LASER_SPEED) laserSpeed = MAX_LASER_SPEED;
        playArea.update(input, data);
        if ((lasers.size() == 0) || (lasers.getLast().getBounds().getY() >= 0)) {
            addLaser(data);
        }
    }
    
    private void addLaser(GameData data) {
        Random rand = new Random();
        float laserWidth = data.getWidth() / 20f;
        int max = (int) (data.getWidth() / laserWidth);
        float laserX = rand.nextInt(max) * laserWidth;
        float minLaserHeight = data.getHeight() / 20f;
        float maxLaserHeight = minLaserHeight * 10f;
        float laserHeight = rand.nextInt((int) (maxLaserHeight - minLaserHeight)) + minLaserHeight;
        Laser laser = new Laser();
        laser.setBounds(laserX, -laserHeight, laserWidth, laserHeight);
        laser.setYSpeed((float) laserSpeed);
        lasers.add(laser);
        playArea.addObject(laser);
    }

}
