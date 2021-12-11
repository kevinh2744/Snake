package org.cis120.snake;

import java.awt.*;
import java.util.LinkedList;
import java.util.Collections;

public class Snake {
    private LinkedList<SnakePart> snakeList;
    private boolean ateBad;
    private boolean atePower;


    public Snake() {
        snakeList = new LinkedList<SnakePart>();
        snakeList.add(new SnakePart(0, 0, Direction.RIGHT));
        ateBad = false;
        atePower = false;
    }

    public SnakePart getHead() {
        return snakeList.getFirst();
    }

    public SnakePart getTail() {
        return snakeList.getLast();
    }

    public LinkedList<SnakePart> getSnakeList() {
        LinkedList<SnakePart> ans = snakeList;
        return ans;
    }

    public boolean ateBad() {
        return ateBad;
    }

    public void changeAteBad() {
        ateBad = true;
    }

    public boolean atePower() {
        return atePower;
    }

    public void changeAtePower() {
        atePower = true;
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

    public void reverse() {
        for (SnakePart snakePart : snakeList) {
            Direction dir = snakePart.getDir();
            if (dir == Direction.UP) {
                snakePart.setDir(Direction.DOWN);
            } else if (dir == Direction.DOWN) {
                snakePart.setDir(Direction.UP);
            } else if (dir == Direction.RIGHT) {
                snakePart.setDir(Direction.LEFT);
            } else if (dir == Direction.LEFT) {
                snakePart.setDir(Direction.RIGHT);
            }
        }
        Collections.reverse(snakeList);
    }

    public void draw(Graphics g) {
        for (SnakePart snakePart : snakeList) {
            snakePart.draw(g);
        }
    }

}
