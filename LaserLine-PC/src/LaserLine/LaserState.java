package LaserLine;

import GameData.GameData;
import GameState.MenuState;
import Input.Input;
import java.util.Random;

public class LaserState extends MenuState {
    
    private int timeToNextLaser;
    private final int MAX_TIME_TO_NEXT_LASER = 100;
    
    public LaserState(String name, int columns, int rows) {
        super(name, columns, rows);
        timeToNextLaser = 0;
    }

    @Override
    public void update(Input input, GameData data) {
        super.update(input, data);
        timeToNextLaser--;
        if (timeToNextLaser <= 0) {
            addLaser(data);
            timeToNextLaser = MAX_TIME_TO_NEXT_LASER;
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
        laser.setYSpeed(2);
        addObject(laser);
    }
}
