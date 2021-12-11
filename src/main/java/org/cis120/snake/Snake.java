package org.cis120.snake;

import java.awt.*;
import java.util.LinkedList;
import java.util.Collections;

/**
 * Our snake will be a LinkedList of SnakeParts.
 * It also has booleans ateBad and atePower to indicate
 * whether the snake has eaten one of the special apples.
 * Class contains methods for moving and growing the snake,
 * reversing the directions and indices of its parts, and
 * setters and getters.
 */
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

    /**************************************************************************
     * SETTERS AND GETTERS
     **************************************************************************/
    public void setHead(int px, int py, Direction dir) {
        snakeList.removeFirst();
        snakeList.addFirst(new SnakePart(px, py, dir));
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

    public void setAteBad(boolean bool) {
        ateBad = bool;
    }

    public boolean atePower() {
        return atePower;
    }

    public void setAtePower(boolean bool) {
        atePower = bool;
    }

    public void addSnakePart(int px, int py, Direction dir) {
        snakeList.add(new SnakePart(px, py, dir));
    }

    /**************************************************************************
     * MAIN METHODS
     **************************************************************************/

    /**
     * When the snake grows when it eats an apple,
     * a new SnakePart is added to the tail with coordinates
     * and direction that depends on the direction of the
     * previous tail.
     */
    public void grow() {
        SnakePart tail = getTail();
        Direction tailDir = tail.getDir();
        int x = tail.getPx();
        int y = tail.getPy();
        if (tailDir == Direction.UP) {
            y++;
        } else if (tailDir == Direction.DOWN) {
            y--;
        } else if (tailDir== Direction.RIGHT) {
            x--;
        } else if (tailDir == Direction.LEFT) {
            x++;
        }
        snakeList.addLast(new SnakePart(x, y, tailDir));
    }

    /**
     * Moving the snake involves adding a new head
     * with coordinates and direction that depends
     * on the current the snake is moving, and then
     * removing the tail.
     * @param dir
     */
    public void move(Direction dir) {
        SnakePart head = getHead();
        int x = head.getPx();
        int y = head.getPy();
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

    /**
     * Reversing the directions and indices of the snakeParts.
     * Used for when the PowerApple is eaten, which allows the
     * user to reverse the snake's direction.
     */
    public void reverse() {
        //Reverse directions of all snake parts
        for (SnakePart snakePart : snakeList) {
            Direction dir = snakePart.getDir();
            snakePart.setDir(oppositeDir(dir));
        }

        //Reverse their indices
        Collections.reverse(snakeList);

        //Adjust directions at turns in the snake
        for (int i = 0; i < snakeList.size() - 1; i++) {
            SnakePart cur = snakeList.get(i);
            Direction curDir = cur.getDir();
            SnakePart next = snakeList.get(i + 1);
            Direction nextDir = next.getDir();

            if (curDir != nextDir) {
                cur.setDir(nextDir);
            }
        }
    }

    /**
     * Helper function for reverse()
     */
    public Direction oppositeDir(Direction dir) {
        if (dir == Direction.UP) {
            return Direction.DOWN;
        } else if (dir == Direction.DOWN) {
            return Direction.UP;
        } else if (dir == Direction.RIGHT) {
            return Direction.LEFT;
        } else {
            return Direction.RIGHT;
        }
    }

    public void draw(Graphics g) {
        for (SnakePart snakePart : snakeList) {
            snakePart.draw(g);
        }
    }

}
