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

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    public static Direction currentDir;
    public static Grid grid;
    private Cell[][] gridArr;

    // the state of the game logic
    private Snake snake; // the Black Square, keyboard control
    private Circle snitch; // the Golden Snitch, bounces

    private boolean playing = false; // whether the game is running
    private JLabel status; // Current status text, i.e. "Running..."

    // Game constants
    public static final int COURT_WIDTH = 400;
    public static final int COURT_HEIGHT = 400;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 100;

    public GameCourt(JLabel status) {
        grid = new Grid();
        Cell[][] gridArr = grid.getGrid();

        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
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
        snitch = new Circle(COURT_WIDTH, COURT_HEIGHT, Color.RED);

        playing = true;
        status.setText("Running...");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (playing) {
            // advance the square and snitch in their current direction.
            snake.move(currentDir);

            // check for the game end conditions
            if (snake.getHead().intersects(snitch)) {
                snake.grow();
            }
            checkLoss();

            // update the display
            repaint();
        }
    }

    public void checkLoss() {
        boolean lost = false;

        if (snake.isDead()) {
            lost = true;
            playing = false;
            status.setText("You lose!");
        }

        if (snake.getHead().getPx() < 0) {
            lost = true;
            playing = false;
            status.setText("You lose!");
        } else if (snake.getHead().getPx() > Grid.rowCount) {
            lost = true;
            playing = false;
            status.setText("You lose!");
        } else if (snake.getHead().getPy() < 0) {
            lost = true;
            playing = false;
            status.setText("You lose!");
        } else if (snake.getHead().getPy() > Grid.colCount) {
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

    public void spawnNormalApple() {
        for (SnakePart snakePart : snake.getSnakeList()) {
            gridArr[snakePart.getPy()][snakePart.getPx()] = Cell.SNAKE_PART;
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.draw(g);
        snitch.draw(g);
        Grid.draw(g);
        setBackground(Color.BLACK);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}