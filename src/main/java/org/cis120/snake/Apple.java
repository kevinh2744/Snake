package org.cis120.snake;

import java.awt.*;

public abstract class Apple extends GameObj {

    public Apple(int px, int py) {
        super(0, 0, px, py, SnakePart.SIZE, SnakePart.SIZE,
            GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
    }

    public abstract void effect(Snake snake);

    public abstract int duration();

    public abstract void draw(Graphics g);

}
