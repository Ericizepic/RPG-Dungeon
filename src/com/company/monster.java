package com.company;

import java.awt.*;

// -------------------------------------- Abstract Monster Class ------------------------------------------------------

public abstract class monster {//abstract class for all the mobs to extend
    abstract void move();
    abstract void print(Graphics g);
    abstract void update(int x, int y);
    abstract boolean collision();
}
