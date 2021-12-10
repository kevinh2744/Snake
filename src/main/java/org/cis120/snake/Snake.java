package org.cis120.snake;

import java.awt.*;
import java.util.LinkedList;

public class Snake {
    private LinkedList<SnakePart> snakeList;
    private boolean shouldDie;


    public Snake() {
        snakeList = new LinkedList<SnakePart>();
        snakeList.add(new SnakePart(0, 0, Direction.RIGHT));
        shouldDie = false;
    }

    public void grow() {
        Direction lastDir = snakeList.getLast().getDir();
        int x = snakeList.getLast().getPx();
        int y = snakeList.getLast().getPy();
        if (lastDir == Direction.UP) {
            y++;
        } else if (lastDir == Direction.DOWN) {
            y--;
        } else if (lastDir == Direction.RIGHT) {
            x--;
        } else if (lastDir == Direction.LEFT) {
            x++;
        }
        snakeList.addLast(new SnakePart(x, y, lastDir));
    }

    public void move(Direction dir) {
        int x = snakeList.getFirst().getPx();
        int y = snakeList.getFirst().getPy();
        if (dir == Direction.UP) {
            y--;
        } else if (dir == Direction.DOWN) {
            y++;
        } else if (dir == Direction.RIGHT) {
            x++;
        } else if (dir == Direction.LEFT) {
            x--;
        }
        snakeList.addFirst(new SnakePart(x, y, dir));
        snakeList.removeLast();
    }

    public SnakePart getHead() {
        return snakeList.getFirst();
    }

    public LinkedList<SnakePart> getSnakeList() {
        return snakeList;
    }

    public boolean isDead() {
        return shouldDie;
    }

    public void ateBadApple() {
        shouldDie = true;
    }

    public void draw(Graphics g) {
        for (SnakePart snakePart : snakeList) {
            snakePart.draw(g);
        }
    }

}
