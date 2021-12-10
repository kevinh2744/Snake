package org.cis120.snake;

import java.awt.*;

import javax.swing.JPanel;

public class Grid extends JPanel{
    public static final int rowCount = GameCourt.COURT_WIDTH / SnakePart.SIZE;
    public static final int colCount = GameCourt.COURT_HEIGHT / SnakePart.SIZE;
    public static Cell[][] grid;

    public Grid() {
        grid = new Cell[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                grid[i][j] = Cell.EMPTY;
            }
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public static void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < GameCourt.COURT_HEIGHT / SnakePart.SIZE; i++) {
            g.drawLine(i * SnakePart.SIZE, 0, i * SnakePart.SIZE, GameCourt.COURT_HEIGHT);
            g.drawLine(0, i * SnakePart.SIZE, GameCourt.COURT_WIDTH, i * SnakePart.SIZE);
        }
    }
}
