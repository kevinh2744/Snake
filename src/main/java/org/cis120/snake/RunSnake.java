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
                " Welcome to my poorly made Snake game. Your objective is  \n"
                        + " to eat apples, which are represented by red dots. Each  \n"
                        + " apple will increase the snake's length by 1 and \n"
                        + " are worth 10 points. Beware, pink apples are bad for the  \n"
                        + " snake's tummy and should be avoided all costs. You might \n"
                        + " encounter a special blue apple later, which will give \n"
                        + " you the power to reverse directions when you hit your spacebar :0 \n\n"
                        + " If you collide with the wall or with your own body, \n"
                        + " you will lose and give the snake a concussion (not cool). \n"
                        + " Try to survive as long as you can. \n\n"
                        + " Use your arrow keys to change the snake's direction. \n"
                        + " You cannot move in the opposite direction (e.g. can't move \n"
                        + " left if you're going right). You can reverse directions \n"
                        + " by hitting the spacebar after you've eaten the special apple. \n"
                        + " Save your game by pressing your 's' key, and load a saved game \n"
                        + " by pressing the 'Load' button. Have fun!",
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
        final JButton load = new JButton("Load");
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.load();
            }
        });

        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });

        control_panel.add(load);
        control_panel.add(reset);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.load();
    }
}