package org.cis120.snake;

/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * 
 * @version 2.1, Apr 2017
 */

import java.awt.*;

/**
 * These snake parts will comprise the body of the snake
 * through a LinkedList of SnakeParts.
 * Each part is a green square GameObj that has
 * a position in the grid and an associated direction.
 */
public class SnakePart extends GameObj {
    public static final int SIZE = 25;
    private Direction dir;

    public SnakePart(int px, int py, Direction dir) {
        super(0, 0, px, py, SIZE, SIZE,
                GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
        this.dir = dir;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction newDir) {
        dir = newDir;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(this.getPx() * SIZE, this.getPy() * SIZE, this.getWidth(), this.getHeight());
    }
}