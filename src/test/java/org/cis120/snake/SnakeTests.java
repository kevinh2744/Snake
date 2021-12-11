package org.cis120.snake;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class SnakeTests {

    Snake snake = new Snake();

    @Test
    public void growTest() {
        snake.move(Direction.RIGHT);
        snake.grow();
        snake.move(Direction.RIGHT);
        snake.grow();
        snake.move(Direction.RIGHT);
        snake.grow();
        LinkedList<SnakePart> snakeList = snake.getSnakeList();
        assertEquals(4, snakeList.size());
    }

    @Test
    public void moveTest() {
        snake.move(Direction.RIGHT);
        snake.move(Direction.RIGHT);
        snake.move(Direction.DOWN);
        SnakePart head = snake.getHead();
        assertEquals(2, head.getPx());
        assertEquals(1, head.getPy());
    }

    @Test
    public void reverseTest() {
        snake.move(Direction.RIGHT);
        snake.grow();
        snake.move(Direction.RIGHT);
        snake.grow();
        snake.move(Direction.RIGHT);
        snake.grow();
        snake.move(Direction.DOWN);
        snake.grow();

        snake.reverse();
        SnakePart head = snake.getHead();
        SnakePart tail = snake.getTail();
        assertEquals(0, head.getPx());
        assertEquals(0, head.getPy());
        assertEquals(3, tail.getPx());
        assertEquals(1, tail.getPy());

        LinkedList<SnakePart> snakeList = snake.getSnakeList();
        assertEquals(Direction.LEFT, head.getDir());
        assertEquals(Direction.LEFT, snakeList.get(1).getDir());
        assertEquals(Direction.LEFT, snakeList.get(2).getDir());
        assertEquals(Direction.LEFT, snakeList.get(3).getDir());
        assertEquals(Direction.UP, tail.getDir());

    }

}
