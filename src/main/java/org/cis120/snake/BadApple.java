package org.cis120.snake;

import java.awt.*;

public class BadApple extends Apple {

    public BadApple(int px, int py) {
        super(px, py, Color.PINK);
    }

    @Override
    public void effect(Snake snake) {
        snake.setAteBad(true);
    }
}
