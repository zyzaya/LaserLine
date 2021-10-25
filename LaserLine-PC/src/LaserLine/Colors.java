package LaserLine;

import Draw.Draw;
import java.util.Random;

public class Colors {
    public static final int RED = 0;
    public static final int ORANGE = 1;
    public static final int YELLOW = 2;
    public static final int GREEN = 3;
    public static final int BLUE = 4;
    public static final int INDIGO = 5;
    public static final int VIOLET = 6;

    public static void SetColor(int color, Draw graphics) {
        switch (color) {
            case RED:
                graphics.setColor(255, 0, 0);
                break;
            case ORANGE:
                graphics.setColor(255, 165, 0);
                break;
            case YELLOW:
                graphics.setColor(255, 255, 0);
                break;
            case GREEN:
                graphics.setColor(0, 255, 0);
                break;
            case BLUE:
                graphics.setColor(0, 0, 255);
                break;
            case INDIGO:
                graphics.setColor(57, 100, 195);
                break;
            case VIOLET:
                graphics.setColor(160, 32, 240);
                break;
        }
    }
    
    public static int GetRandomColor() {
        Random random = new Random();
        int col = random.nextInt(7);
        return col;
    }
}
