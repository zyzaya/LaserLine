package LaserLine;

import Draw.Draw;
import GameData.GameData;
import GameData.ScoreData;
import GameObject.MenuObject;
import Input.Input;

public class ScoreState extends LaserState {
    
    private final float FONT_PERC;
    private final String NAME_MENU;
    private final int NUM_SCORES;
    private double[] scores;
    private int viewHeight;
    
    public ScoreState(String name, String menuName, int numScores, float font_perc) {
        super(name, 3, 7);
        NAME_MENU = menuName;
        FONT_PERC = font_perc;
        NUM_SCORES = numScores;
        scores = new double[3];
        for (int i = 0; i < NUM_SCORES; i++) {
            scores[i] = 0;
        }
        setupObjects();
    }
    
    private void setupObjects(){
        for (int i = 0; i < NUM_SCORES; i++) {
            final int J = i;
            MenuObject label = new MenuObject("", 0, (i+1), 3, 1) {
                @Override
                public void draw(Draw graphics) {
                    graphics.setColor(0, 0, 0);
                    graphics.drawCentredText((J+1) + ": " + ((int) scores[J]), getBounds());
                }
            };
            addObject(label);
        }
        MenuObject btnBack = new MenuObject(NAME_MENU, 1, NUM_SCORES + 2, 1, 1) {
            @Override
            public void draw(Draw graphics) {
                graphics.setColor(0, 0, 0);
                graphics.drawCentredText("Back", getBounds());
            }
        };
        addObject(btnBack);
    }
    
    @Override
    public void draw(Draw graphics) {
        graphics.setColor(255, 255, 255);
        graphics.drawRect(getBounds());
        int fontSize = viewHeight*FONT_PERC > 0 ? (int) (viewHeight*FONT_PERC) : 1;
        graphics.setFont("Arial", fontSize);
        super.draw(graphics);
    }
    
    @Override
    public void update(Input input, GameData data) {
        super.update(input, data);
        viewHeight = data.getHeight();
        
        ScoreData scoreData = (ScoreData) data;
        for (int i = 0; i < NUM_SCORES; i++) {
            scores[i] = scoreData.getScore(i);
        }
    }
    
}
