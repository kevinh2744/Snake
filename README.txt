=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: kevinhu
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D arrays: I used an integer 2D array to model the grid of the game board. Specific squares can be
     retrieved through a row and column number. If the square is occupied by a SnakePart or Apple, it's value
     in the array is a 1. Empty squares will have a value of 0. This ensures that Apples will not spawn in
     places in the grid that are already occupied.

  2. Inheritance and subtyping: this game has several types of apples that can be eaten by the snake.
     An abstract parent class allows for each type of apple to inherit properties that are common across all
     apples, while also allowing for each type of apple to have a unique effect on the snake.

  3. I/O: the state of the game is saved and loaded using I/O. The state of the game can be represented by
     the coordinates and directions of the SnakeParts, the score, and whether the snake has eaten the power apple.
     are written to a file. These aspects of the game are written whenever the user presses the "s" key, and
     are read when the user clicks the "Load" button.

  4. JUnit testable components: several components of the game can be JUnit tested like the move(), grow(),
     and reverse() methods for the snake. Collisions between the snake and the wall, itself, or an apple can also
     be tested independently without involving the GUI. All of these aspects of the game are vital, so JUnit testing
     has been very helpful in building this project.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  Apple: the abstract parent class that each apple extends.

  BadApple: an apple that kills the snake and ends the game if eaten.

  Direction: enum for the directions of the snake.

  Game: top level main class.

  GameCourt: contains all the game logic; allows the snake to move when an arrow key is pressed, updates the score
  and snake whenever apples are eaten, checks losing conditions, spawns food at squares that aren't occupied, and
  saves the game state when 's' is pressed.

  GameObj: abstract class that the apples and snake parts extend. Contains each object's coordinates and the
  intersects() method that is used to check for collisions.

  Grid: establishes the grid system of the board as a 2D array with rows and columns such that each object
  occupies a specific square.

  NormalApple: an apple that increases the snake's length by 1 and increases the score by 10.

  PowerApple: an apple that gives the snake the ability to reverse directions. Also increases
  the snake's length by 1 and increases the score by 10.

  RunSnake: contains all the GUI components and allows the game to run.

  Snake: a LinkedList of snake parts. Contains the methods of move(), grow(), and reverse() for the snake.

  SnakeObj: each individual part of the snake is represented as a snake part, which extends GameObj



- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  I/O was very difficult to implement, since I wasn't sure how to actually save the entire game state. Also, relating
  the grid system to the snake's movement and growth was very difficult.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I believe there is good separation of functionality with the different classes. Certain aspects are encapsulated
  to ensure that retrieving them doesn't affect its structure. I think using points instead of coordinates (px and py)
  could have made the code slightly cleaner.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  None used.