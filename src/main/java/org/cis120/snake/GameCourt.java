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
import java.util.LinkedList;
import java.util.ArrayList;
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
    private Apple apple; // apple can either be a normal or power apple
    private BadApple badApple; //spawns in random unoccupied squares

    private int score;
    private static Direction currentDir;
    private Timer timer;

    private Grid grid;
    private int[][] gridArr;

    private boolean playing = false; // whether the game is running
    private JLabel status; // Current status text, i.e. "Running..."

    // Game constants
    public static final int COURT_WIDTH = 400;
    public static final int COURT_HEIGHT = 400;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 125;

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
        // the snake's direction. Hitting 's' will save the current game.
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
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    try {
                        saveGame();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

        this.status = status;
    }

    /**
     * Loading a saved game state
     */
    public void load() {
        snake = new Snake();
        currentDir = Direction.RIGHT;
        score = 0;

        try {
            ArrayList<ArrayList<String>> snakeParts = getSavedSnakeParts();
            for (int i = 0; i < snakeParts.size(); i++) {
                ArrayList<String> snakePart = snakeParts.get(i);
                int partX = Integer.valueOf(snakePart.get(0));
                int partY = Integer.valueOf(snakePart.get(1));
                Direction partDir = stringToDir(snakePart.get(2));
                if (i == 0) {
                    snake.setHead(partX, partY, partDir);
                } else {
                    snake.addSnakePart(partX, partY, partDir);
                }
            }
            currentDir = snake.getHead().getDir();
            snake.setAtePower(getSavedPower());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            score = getSavedScore();
        } catch (IOException e) {
            score = 0;
        }

        playing = true;
        status.setText("Score: " + score);

        spawnApples();

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * Helper function for load
     * @param string
     * @return
     */
    public Direction stringToDir(String string) {
        if (string.equals("UP")) {
            return Direction.UP;
        } else if (string.equals("DOWN")) {
            return Direction.DOWN;
        } else if (string.equals("RIGHT")) {
            return Direction.RIGHT;
        } else if (string.equals("LEFT")) {
            return Direction.LEFT;
        }
        return Direction.RIGHT;
    }

    /**
     * resetting the game to the initial state
     */
    public void reset() {
        snake = new Snake();
        currentDir = Direction.RIGHT;
        score = 0;

        playing = true;
        status.setText("Score: " + score);

        spawnApples();

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
                spawnApples();
                score += 10;
                status.setText("Score: " + score);
                status.repaint();
            }

            if (badApple != null) {
                if (snake.getHead().intersects(badApple)) {
                    badApple.effect(snake);
                }
            }

            // check for the game end conditions
            checkLoss();

            // update the display
            repaint();
        }
    }

    /**
     * Checks losing conditions
     */
    public void checkLoss() {

        //Ate bad apple
        if (snake.ateBad()) {
            playing = false;
            status.setText("You lose!");
        }

        //Collisions with wall
        SnakePart head = snake.getHead();
        if (head.getPx() < 0
            || head.getPx() >= Grid.ROW_COUNT
            || head.getPy() < 0
            || head.getPy() >= Grid.COL_COUNT) {
            playing = false;
            status.setText("You lose!");
        }

        //Collisions with snake body
        for (int i = 1; i < snake.getSnakeList().size(); i++) {
            if (snake.getHead().intersects(snake.getSnakeList().get(i))) {
                playing = false;
                status.setText("You lose!");
            }
        }
    }

    /**
     * Spawns apples in unoccupied squares by initializing
     * a grid (2D integer array). Empty and occupied squares
     *  are represented by 0 and 1, respectively.
     */
    public void spawnApples() {
        //spawn either a NormalApple, or a PowerApple at 100 points
        int appleX;
        int appleY;

        grid = new Grid();
        gridArr = grid.getGrid();
        for (SnakePart snakePart : snake.getSnakeList()) {
            gridArr[snakePart.getPy()][snakePart.getPx()] = 1;
        }

        Random random = new Random();
        while (true) {
            int randomRow = random.nextInt(Grid.ROW_COUNT);
            int randomCol = random.nextInt(Grid.COL_COUNT);
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

        //spawn a BadApple, changes location every 50 points
        int badAppleX;
        int badAppleY;

        if (score % 50 == 0) {
            while (true) {
                int randomRow = random.nextInt(Grid.ROW_COUNT);
                int randomCol = random.nextInt(Grid.COL_COUNT);
                if (gridArr[randomRow][randomCol] == 0) {
                    badAppleX = randomCol;
                    badAppleY = randomRow;
                    gridArr[randomRow][randomCol] = 1;
                    break;
                }
            }
            badApple = new BadApple(badAppleX, badAppleY);
        }
    }

    /**
     * Saves the current game state to a text file. Called when
     * the key 's' is pressed. The score and whether the snake has
     * eaten the power apple are stored on the first two lines, and then
     * the snake parts are stored in the following lines in the following format:
     * x coordinate, y coordinate, direction
     * @throws IOException
     */
    public void saveGame() throws IOException {
        File gameFile = new File("game_save.txt");
        if (!gameFile.exists()) {
            try {
                gameFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileWriter write = new FileWriter("game_save.txt", false);
        BufferedWriter bw = new BufferedWriter(write);

        bw.write(String.valueOf(score));
        bw.newLine();

        bw.write(String.valueOf(snake.atePower()));
        bw.newLine();

        LinkedList<SnakePart> snakeList = snake.getSnakeList();
        for (SnakePart snakePart : snakeList) {
            bw.write(snakePart.getPx() + "," 
                    + snakePart.getPy() + "," 
                    + snakePart.getDir().toString());
            bw.newLine();
        }
        bw.close();
    }

    /**
     * Gets the saved snake parts as an array list of String array lists.
     * The outer array is a list of all the save snake parts.
     * The inner String array lists represent a specific snake part as an
     * x coordinate, y coordinate, and direction
     * @return
     * @throws IOException
     */
    public ArrayList<ArrayList<String>> getSavedSnakeParts() throws IOException {
        ArrayList<ArrayList<String>> snakeParts = new ArrayList<>();
        try {
            FileReader fr = new FileReader("game_save.txt");
            BufferedReader br = new BufferedReader(fr);
            String nextLine;
            br.readLine();
            br.readLine();
            while ((nextLine = br.readLine()) != null) {
                ArrayList<String> snakePartInfo = new ArrayList<>();
                for (String s : nextLine.split(",")) {
                    snakePartInfo.add(s);
                }
                snakeParts.add(snakePartInfo);
            }
            br.close();
            return snakeParts;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Gets the save score as an integer.
     * @return
     * @throws IOException
     */
    public int getSavedScore() throws IOException {
        try {
            FileReader fr = new FileReader("game_save.txt");
            BufferedReader br = new BufferedReader(fr);
            String nextLine = br.readLine();
            br.close();
            return Integer.valueOf(nextLine);
        } catch (IOException e) {
            return 0;
        }
    }

    /**
     * Gets whether the snake as eaten the PowerApple in the saved game.
     * @return
     * @throws IOException
     */
    public boolean getSavedPower() throws IOException {
        try {
            FileReader fr = new FileReader("game_save.txt");
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            String nextLine = br.readLine();
            br.close();
            return Boolean.parseBoolean(nextLine);
        } catch (IOException e) {
            return false;
        }
    }

    /**************************************************************************
     * TESTING METHODS
     **************************************************************************/
    public void setDir(Direction dir) {
        currentDir = dir;
    }

    public boolean getPlaying() {
        return playing;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        apple.draw(g);
        if (badApple != null) {
            badApple.draw(g);
        }
        snake.draw(g);
        Grid.draw(g);
        setBackground(Color.BLACK);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}