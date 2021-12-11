package org.cis120.snake;

import java.awt.*;

public class BadApple extends Apple {

    public BadApple(int px, int py) {
        super(px, py);
    }

    @Override
    public void effect(Snake snake) {
        snake.changeAteBad(true);
    }

    @Override
    public int duration() {
        return 50;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillOval(this.getPx() * SnakePart.SIZE, this.getPy() * SnakePart.SIZE,
            this.getWidth(), this.getHeight());
    }
}
