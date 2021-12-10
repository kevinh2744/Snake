package org.cis120.snake;

import java.awt.*;

public class NormalApple extends GameObj implements Apple {

    public static final int SIZE = 20;

    public NormalApple(int px, int py) {
        super(0, 0, px, py, SIZE, SIZE, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
    }

    @Override
    public void effect(Snake snake) {
        snake.grow();
    }

    @Override
    public boolean willVanish() {
        return false;
    }

    @Override
    public int duration() {
        return -1;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(this.getPx() * SIZE, this.getPy() * SIZE, this.getWidth(), this.getHeight());
    }
}
