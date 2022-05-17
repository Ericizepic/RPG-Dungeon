package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


// -------------------------------------- Cannon Class ------------------------------------------------------

public class cannon {
    //private attributes
    private int cannonXPos;
    private int cannonYPos;
    private int xPos;
    private int yPos;
    private int dir;
    private int fbX;
    private int fbY;
    private int oGx;
    private Image cannonI, fireball;
    private char[][] map;


    //constructor
    public cannon(int x, int y, int xP, int yP, char[][] m, int d, int speed) throws IOException {
        cannonXPos = x;//position of cannon
        cannonYPos = y;
        if (d == 1) {
            oGx = cannonXPos +  2;
        } else {
            oGx = cannonXPos -  2;
        }
        int oGy = cannonYPos + 1;
        xPos = xP;//position of player
        yPos = yP;
        dir = d;
        map = m;//we need to know location of walls
        fbX = oGx;
        fbY = oGy;

        //loading images
        if (d == 1)
            cannonI = ImageIO.read(new File("src/cannon2.png"));
        else
            cannonI = ImageIO.read(new File("src/cannon.png"));

        Image[] fb = new Image[6];
        for (int i = 0; i < 6; i++)
            fb[i] = ImageIO.read(new File("src/fireball" + (i + 1) + ".png"));

        if (d == 1)
            fireball = fb[0];
        else
            fireball = fb[3];

        Timer t = new Timer();
        int INITIAL_DELAY = 500;
        t.scheduleAtFixedRate(new cannon.ScheduleTask(), INITIAL_DELAY, speed);
    }

    public void print(Graphics g) {
        //print location of the cannon along with the fireball
        if (Math.abs(xPos - cannonXPos) < 24 && Math.abs(yPos - cannonYPos) < 19) {
            g.drawImage(cannonI, (cannonXPos - xPos + 25) * 20 + 10, (cannonYPos - yPos + 20) * 20 + 10, 100, 80, null);}
        if (Math.abs(xPos - fbX) < 25 && Math.abs(yPos - fbY) < 20) {
            g.drawImage(fireball, (fbX - xPos + 25) * 20 + 10, (fbY - yPos + 20) * 20 + 12, 60, 40, null);}
    }


    public void update(int x, int y) {
        //updates position of character
        xPos = x;
        yPos = y;
    }

    private void move() {
        //moves the fireball
        if (dir == 1)
            fbX++;
        else if (dir == 2)
            fbX--;

        if (map[fbY][fbX] == 'W')
            fbX = oGx;

    }

    public boolean collision() {
        //we check to see if the fireball hits the character
        Rectangle r1 = new Rectangle(25 * 20 + 15, 20 * 20 + 20, 35, 60);
        Rectangle r2 = new Rectangle((fbX - xPos + 25) * 20 + 16, (fbY - yPos + 20) * 20 + 20, 40, 20);
        return r1.intersects(r2);
    }

    private class ScheduleTask extends TimerTask {
        //move the fireball
        @Override
        public void run() {
            move();
        }
    }
}
