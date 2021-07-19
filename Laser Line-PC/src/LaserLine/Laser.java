package LaserLine;

import GameObject.CollisionObject;
import GameObject.PhysicsObject;
import Draw.Draw;

public class Laser extends PhysicsObject {
    
    private int color;
    
    public Laser() {
        super();
        color = Colors.GetRandomColor();
    }
    
    @Override
    public void draw(Draw draw) {
        super.draw(draw);
        Colors.SetColor(color, draw);
        draw.drawRect(getBounds(), true);
    }
    
    @Override
    public void onCollide(CollisionObject other) { }
}
