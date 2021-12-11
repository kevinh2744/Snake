package org.cis120.snake;

/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * 
 * @version 2.1, Apr 2017
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Random;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private Snake snake; // the moving snake, keyboard control
    private Apple apple;
    private BadApple badApple;

    private int score;
    public static Direction currentDir;
    private Timer timer;

    private Grid grid;
    private int[][] gridArr;

    private boolean playing = false; // whether the game is running
    private JLabel status; // Current status text, i.e. "Running..."

    // Game constants
    public static final int COURT_WIDTH = 400;
    public static final int COURT_HEIGHT = 400;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 150;

    public GameCourt(JLabel status) {

        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single timestep.
        timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key
        // is pressed, by changing the square's velocity accordingly. (The tick
        // method below actually moves the square.)

        // Hitting the spacebar after the snake eats the power apple reverses
        // the snake's direction
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && currentDir != Direction.RIGHT) {
                    currentDir = Direction.LEFT;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentDir != Direction.LEFT) {
                    currentDir = Direction.RIGHT;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentDir != Direction.UP) {
                    currentDir = Direction.DOWN;
                } else if (e.getKeyCode() == KeyEvent.VK_UP && currentDir != Direction.DOWN) {
                    currentDir = Direction.UP;
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && snake.atePower()) {
                    SnakePart tail = snake.getTail();
                    Direction dir = tail.getDir();
                    snake.reverse();
                    if (dir == Direction.UP) {
                        currentDir = Direction.DOWN;
                    } else if (dir == Direction.DOWN) {
                        currentDir = Direction.UP;
                    } else if (dir == Direction.RIGHT) {
                        currentDir = Direction.LEFT;
                    } else if (dir == Direction.LEFT) {
                        currentDir = Direction.RIGHT;
                    }
                }
            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        snake = new Snake();
        currentDir = Direction.RIGHT;
        score = 0;

        playing = true;
        status.setText("Score: " + score);

        spawnApple();

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (playing) {
            // advance the snake in the current direction.
            snake.move(currentDir);

            // Checking for and dealing with collisions with apples
            if (snake.getHead().intersects(apple)) {
                apple.effect(snake);
                spawnApple();
                score += 10;
                status.setText("Score: " + score);
                status.repaint();
            }
            // check for the game end conditions
            checkLoss();

            // update the display
            repaint();
        }
    }

    public void checkLoss() {
        boolean lost = false;

        if (snake.ateBad()) {
            lost = true;
            playing = false;
            status.setText("You lose!");
        }

        if (snake.getHead().getPx() < 0) {
            lost = true;
            playing = false;
            status.setText("You lose!");
        } else if (snake.getHead().getPx() >= Grid.rowCount) {
            lost = true;
            playing = false;
            status.setText("You lose!");
        } else if (snake.getHead().getPy() < 0) {
            lost = true;
            playing = false;
            status.setText("You lose!");
        } else if (snake.getHead().getPy() >= Grid.colCount) {
            lost = true;
            playing = false;
            status.setText("You lose!");
        }

        for (int i = 1; i < snake.getSnakeList().size(); i++) {
            if (snake.getHead().intersects(snake.getSnakeList().get(i))) {
                lost = true;
                playing = false;
                status.setText("You lose!");
            }
        }
    }

    public void spawnApple() {
        int appleX;
        int appleY;

        grid = new Grid();
        gridArr = grid.getGrid();
        for (SnakePart snakePart : snake.getSnakeList()) {
            gridArr[snakePart.getPy()][snakePart.getPx()] = 1;
        }

        Random random = new Random();
        while (true) {
            int randomRow = random.nextInt(Grid.rowCount);
            int randomCol = random.nextInt(Grid.colCount);
            if (gridArr[randomRow][randomCol] == 0) {
                appleX = randomCol;
                appleY = randomRow;
                gridArr[randomRow][randomCol] = 1;
                break;
            }
        }
        if (score == 90) {
            apple = new PowerApple(appleX, appleY);
        } else {
            apple = new NormalApple(appleX, appleY);
        }
    }

    public void spawnBadApple() {
        int badAppleX;
        int badAppleY;

        grid = new Grid();
        gridArr = grid.getGrid();
        for (SnakePart snakePart : snake.getSnakeList()) {
            gridArr[snakePart.getPy()][snakePart.getPx()] = 1;
        }

        Random random = new Random();
        while (true) {
            int randomRow = random.nextInt(Grid.rowCount);
            int randomCol = random.nextInt(Grid.colCount);
            if (gridArr[randomRow][randomCol] == 0) {
                badAppleX = randomCol;
                badAppleY = randomRow;
                gridArr[randomRow][randomCol] = 1;
                break;
            }
        }
        badApple = new BadApple(badAppleX, badAppleY);
    }

    //Testing methods
    public void setDir(Direction dir) {
        currentDir = dir;
    }

    public boolean getPlaying() {
        return playing;
    }

    public void setPlaying(boolean bool) {
        playing = bool;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        apple.draw(g);
        snake.draw(g);
        Grid.draw(g);
        setBackground(Color.BLACK);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}