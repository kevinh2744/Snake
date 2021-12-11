package org.cis120.snake;

import org.junit.jupiter.api.*;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;


public class CollisionTests {

    Snake snake = new Snake();

    @Test
    public void SnakeWithAppleTest() {
        NormalApple normalApple = new NormalApple(3, 0);
        snake.move(Direction.RIGHT);
        snake.move(Direction.RIGHT);
        SnakePart head = snake.getHead();
        assertFalse(head.intersects(normalApple));

        snake.move(Direction.RIGHT);
        head = snake.getHead();
        assertTrue(head.intersects(normalApple));
    }

    @Test
    public void SnakeWithSnakeTest() {
        snake.move(Direction.RIGHT);
        snake.move(Direction.RIGHT);
        snake.grow();
        snake.move(Direction.RIGHT);
        snake.grow();
        snake.move(Direction.DOWN);
        snake.grow();
        snake.move(Direction.LEFT);
        snake.grow();
        SnakePart head = snake.getHead();
        LinkedList<SnakePart> snakeList = snake.getSnakeList();
        assertFalse(head.intersects(snakeList.get(4)));

        snake.move(Direction.UP);
        head = snake.getHead();
        assertTrue(head.intersects(snakeList.get(4)));
    }

    @Test
    public void SnakeWithTopWallTest() {
        GameCourt court = new GameCourt(new JLabel("Running..."));
        court.reset();
        court.setDir(Direction.UP);
        assertTrue(court.getPlaying());

        court.tick();
        assertFalse(court.getPlaying());
    }

    @Test
    public void SnakeWithLeftWallTest() {
        GameCourt court = new GameCourt(new JLabel("Running..."));
        court.reset();
        court.setDir(Direction.LEFT);
        assertTrue(court.getPlaying());

        court.tick();
        assertFalse(court.getPlaying());
    }

    @Test
    public void SnakeWithRightWallTest() {
        GameCourt court = new GameCourt(new JLabel("Running..."));
        court.reset();
        assertTrue(court.getPlaying());

        for (int i = 0; i < Grid.colCount; i++) {
            court.tick();
        }
        assertFalse(court.getPlaying());
    }

    @Test
    public void SnakeWithBottomWallTest() {
        GameCourt court = new GameCourt(new JLabel("Running..."));
        court.reset();
        court.setDir(Direction.DOWN);
        assertTrue(court.getPlaying());

        for (int i = 0; i < Grid.rowCount; i++) {
            court.tick();
        }
        assertFalse(court.getPlaying());
    }
}
