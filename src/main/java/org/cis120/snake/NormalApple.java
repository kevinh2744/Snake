package org.cis120.snake;

import java.awt.*;

public class NormalApple extends Apple {

    public NormalApple(int px, int py) {
        super(px, py, Color.RED);
    }

    @Override
    public void effect(Snake snake) {
        snake.grow();
    }
}
