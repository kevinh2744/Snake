package org.cis120.snake;

public interface Apple {

    public static final int SIZE = 20;


    public void effect(Snake snake);

    public boolean willVanish();

    public int duration();

}
