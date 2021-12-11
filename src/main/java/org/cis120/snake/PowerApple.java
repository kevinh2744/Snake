package org.cis120.snake;

import java.awt.*;

public class PowerApple extends Apple {

    public PowerApple(int px, int py) {
        super(px, py);
    }

    @Override
    public void effect(Snake snake) {
        snake.grow();
        snake.changeAtePower();
    }

    @Override
    public int duration() {
        return -1;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillOval(this.getPx() * SnakePart.SIZE, this.getPy() * SnakePart.SIZE, this.getWidth(), this.getHeight());
    }
}
