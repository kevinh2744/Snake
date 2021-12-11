package org.cis120.snake;

import java.awt.*;

import javax.swing.JPanel;

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

    public static void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < GameCourt.COURT_HEIGHT / SnakePart.SIZE; i++) {
            g.drawLine(i * SnakePart.SIZE, 0, i * SnakePart.SIZE, GameCourt.COURT_HEIGHT);
            g.drawLine(0, i * SnakePart.SIZE, GameCourt.COURT_WIDTH, i * SnakePart.SIZE);
        }
    }
}
