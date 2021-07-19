package LaserLine;

import Collision.Square;
import GameData.GameData;
import GameObject.CollisionObject;
import GameObject.PhysicsObject;
import Input.Input;
import Draw.Draw;
import Particles.Particle;
import Particles.ParticleEmitter;

public class Player extends PhysicsObject {
    
    private ParticleEmitter emitter;
    private boolean lose;
    private int viewWidth, viewHeight;
    
    public Player(int viewWidth, int viewHeight) {
        super();
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        lose = false;
        float size = viewWidth / 15f;
        float x = (viewWidth / 2f) - (size / 2f);
        float y = viewHeight - (size * 2);
        Square bounds = new Square(x, y, size, size);
        setBounds(bounds);
        setupEmitter();
    }
    
    private void setupEmitter() {
        Particle[] choices = new Particle[7];
        for (int i = 0; i < choices.length; i++) {
            final int J = i;
            choices[i] = new Particle((int) getBounds().getWidth(), (int) getBounds().getHeight()){
                int color = J;
                
                @Override
                public void draw(Draw graphics) {
                    Colors.SetColor(color, graphics);
                    graphics.drawRect(getBounds());
                }
            };
        }
        
        emitter = new ParticleEmitter(choices);
        emitter.setMaxParticles(8);
        emitter.setParticlesPerTick(1);
        emitter.setParticleXSpeed(1, 2);
        emitter.setParticleYSpeed(1, 2);
        emitter.setParticleSize(getBounds().getWidth() * 0.1f, getBounds().getWidth() * 0.9f);
        emitter.setParticleLifeSpan(5, 25);
    }
    
    @Override
    public void draw(Draw draw) {
        emitter.draw(draw);
        draw.setColor(100, 100, 100);
        draw.drawRect(getBounds(), true);
    }
    
    @Override
    public void update(Input input, GameData data) {
        viewWidth = data.getWidth();
        viewHeight = data.getHeight();
        Square bounds = getBounds();
        if (input.isPressed(Game.Game.MOUSE1)) {
            double newX = input.getMouseX() - (bounds.getWidth() / 2);
            double newY = input.getMouseY() - (bounds.getHeight() / 2);
            bounds.setX((float) newX);
            bounds.setY((float) newY);
            setBounds(bounds);
            emitter.setIsGenerating(true);
        } else {
            emitter.setIsGenerating(false);
        }
        emitter.setX(bounds.getX());
        emitter.setY(bounds.getY());
        emitter.update(input, data);
    }
    
    @Override
    public void onCollide(CollisionObject other) {
        if (other instanceof Laser) {
            lose = true;
        }
    }
    
    public boolean shouldLose() {
        return lose;
    }
    
    public void reset() {
        lose = false;
        float size = viewWidth / 15f;
        float x = (viewWidth / 2f) - (size / 2f);
        float y = viewHeight - (size * 2);
        Square bounds = new Square(x, y, size, size);
        setBounds(bounds);
    }
}
