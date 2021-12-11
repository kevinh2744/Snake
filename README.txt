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

  2. Inheritance and subtyping: All the different types of apples share certain
     properties like being able to spawn on an unoccupied square and being able to be eaten by the snake.
     Each type of fruit can inherit the common properties from a parent fruit class while having their own unique effects.

  3.

  4.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think using points instead of coordinates (px and py) also could have made the code slightly cleaner.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  None used.