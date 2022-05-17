package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class minion extends monster {
    //private attributes
    private int minionXPos, minionYPos, xPos, yPos, dir, walkCounter = 0;
    private Image[][] minions = new Image[4][3];
    private char[][] map;
    static Random r = new Random();

    //constructor

    public minion(int x, int y, int xP, int yP, int d, char[][] m) throws IOException {
        minionXPos = x;//position of minion
        minionYPos = y;
        xPos = xP;//position of player
        yPos = yP;
        map = m;//we need to know location of walls
        dir = d;//direction it is facing

        for (int i = 0; i < 4; i++) {//importing the images
            minions[i][0] = ImageIO.read(new File("src/minion" + ((i + 1) * 10 + 1) + ".png"));
            minions[i][1] = ImageIO.read(new File("src/minion" + ((i + 1) * 10 + 2) + ".png"));
            minions[i][2] = ImageIO.read(new File("src/minion" + ((i + 1) * 10 + 3) + ".png"));
        }

        //creating a timer
        Timer t = new Timer();
        int INITIAL_DELAY = 200;
        int PERIOD_INTERVAL = 200;//speed of monster
        t.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);
    }

    @Override
    void move() {//minion moves randomly
        walkCounter++;
        //because the minion takes up more than a single space, we need to take extra precautions in determining where it can move
        if ((walkCounter / 3) % 2 == 1) {
            int n = r.nextInt(4);
            if (n == 0 && map[minionYPos][minionXPos - 1] == ' ' && map[minionYPos][minionXPos - 2] == ' ') {
                dir = 1;
                minionXPos--;
            } else if (n == 1 && map[minionYPos][minionXPos + 1] == ' ' && map[minionYPos][minionXPos + 2] == ' ') {
                dir = 2;
                minionXPos++;
            } else if (n == 2 && map[minionYPos - 1][minionXPos] == ' ' && map[minionYPos - 2][minionXPos] == ' ') {
                dir = 3;
                minionYPos--;

            } else if (n == 3 && map[minionYPos + 1][minionXPos] == ' ' && map[minionYPos + 2][minionXPos] == ' ') {
                dir = 0;
                minionYPos++;
            }
        } else {
            if (dir == 1 && map[minionYPos][minionXPos - 1] == ' ' && map[minionYPos][minionXPos - 2] == ' ')
                minionXPos--;
            else if (dir == 2 && map[minionYPos][minionXPos + 1] == ' ' && map[minionYPos][minionXPos + 2] == ' ')
                minionXPos++;
            else if (dir == 3 && map[minionYPos - 1][minionXPos] == ' ' && map[minionYPos - 2][minionXPos] == ' ')
                minionYPos--;
            else if (dir == 0 && map[minionYPos + 1][minionXPos] == ' ' && map[minionYPos + 2][minionXPos] == ' ')
                minionYPos++;
        }

    }


    public void print(Graphics g) {
        //print the location of the minion in relation to main character
        if (Math.abs(xPos - minionXPos) < 25 && Math.abs(yPos - minionYPos) < 20) {
            g.drawImage(minions[dir][walkCounter % 3], (minionXPos - xPos + 25) * 20 + 10, (minionYPos - yPos + 20) * 20 + 10, 55, 60, null);
        }
    }

    public void update(int x, int y) {//updates position of character
        xPos = x;
        yPos = y;
    }

    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
            move();
        }
    }

    public boolean collision() {
        //we check to see if the minion hits the character
        Rectangle r1 = new Rectangle(25 * 20 + 15, 20 * 20 + 20, 31, 60);
        Rectangle r2 = new Rectangle((minionXPos - xPos + 25) * 20 + 20, (minionYPos - yPos + 20) * 20 + 20, 35, 40);
        return r1.intersects(r2);
    }
}
