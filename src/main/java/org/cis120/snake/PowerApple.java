package org.cis120.snake;

import java.awt.*;

public class PowerApple extends Apple {

    public PowerApple(int px, int py) {
        super(px, py, Color.CYAN);
    }

    @Override
    public void effect(Snake snake) {
        snake.grow();
        snake.setAtePower(true);
    }
}
