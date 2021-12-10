package org.cis120.snake;

import java.awt.*;

public class BadApple extends GameObj implements Apple {
    public static final int SIZE = 20;

    public BadApple(int px, int py) {
        super(0, 0, px, py, SIZE, SIZE, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
    }

    @Override
    public void effect(Snake snake) {
        snake.ateBadApple();
    }

    @Override
    public boolean willVanish() {
        return true;
    }

    @Override
    public int duration() {
        return 50;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(this.getPx() * SIZE, this.getPy() * SIZE, this.getWidth(), this.getHeight());
    }
}
