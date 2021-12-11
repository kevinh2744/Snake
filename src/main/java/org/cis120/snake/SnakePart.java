package org.cis120.snake;

/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * 
 * @version 2.1, Apr 2017
 */

import java.awt.*;

/**
 * A basic game object starting in the upper left corner of the game court. It
 * is displayed as a square of a specified color.
 */
public class SnakePart extends GameObj {
    public static final int SIZE = 25;
    private Direction dir;

    /**
     * Note that, because we don't need to do anything special when constructing
     * a Square, we simply use the superclass constructor called with the
     * correct parameters.
     */
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