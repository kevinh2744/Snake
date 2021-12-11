package org.cis120.snake;

import java.awt.*;

/**
 * Parent apple class that other apples will inherit.
 * Components that will be shared among apples are
 * a similar constructor, a unique effect on the snake,
 * and a draw method.
 */
public abstract class Apple extends GameObj {

    private Color color;

    public Apple(int px, int py, Color color) {
        super(0, 0, px, py, SnakePart.SIZE, SnakePart.SIZE,
            GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
        this.color = color;
    }

    public abstract void effect(Snake snake);

    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.getPx() * SnakePart.SIZE, this.getPy() * SnakePart.SIZE,
                this.getWidth(), this.getHeight());
    }

}
