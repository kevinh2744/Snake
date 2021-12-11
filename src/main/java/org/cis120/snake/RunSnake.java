package org.cis120.snake;
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * 
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunSnake implements Runnable {
    public void run() {

        JOptionPane.showMessageDialog(null,
                " Welcome to my poorly made Snake game. Your objective is to eat apples, \n"
                        + " which are represented by red dots. Each apple will increase the snake's \n"
                        + " length by 1 and are worth 10 points.  Beware, pink apples are bad for \n"
                        + " the snake's tummy and should be avoided all costs. You might encounter \n"
                        + " some special apples later :^). If you collide with the wall or with \n"
                        + " yourself, you will lose and give the snake a concussion. Try to survive \n"
                        + " as long as you can. \n\n"
                        + " Use your arrow keys to change the snake's direction. You cannot move in \n"
                        + " the opposite direction (e.g. can't move left if you're going right)",
                "Instructions", JOptionPane.INFORMATION_MESSAGE);

        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Snake");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }
}