package org.cis120.snake;

import java.awt.*;

import javax.swing.JPanel;

/**
 * Establishes the grid system in the form of a 2D array
 * that the game uses to keep track of which square
 * are occupied and which are empty (useful for spawning apples).
 */
public class Grid extends JPanel {
    public static final int ROW_COUNT = GameCourt.COURT_WIDTH / SnakePart.SIZE;
    public static final int COL_COUNT = GameCourt.COURT_HEIGHT / SnakePart.SIZE;
    private static int[][] grid;

    public Grid() {
        grid = new int[ROW_COUNT][COL_COUNT];
    }

    public int[][] getGrid() {
        int[][] ans = grid;
        return ans;
    }

    /**
     * drawing the actual grid using lines
     * @param g
     */
    public static void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < GameCourt.COURT_HEIGHT / SnakePart.SIZE; i++) {
            g.drawLine(i * SnakePart.SIZE, 0, i * SnakePart.SIZE, GameCourt.COURT_HEIGHT);
            g.drawLine(0, i * SnakePart.SIZE, GameCourt.COURT_WIDTH, i * SnakePart.SIZE);
        }
    }
}
