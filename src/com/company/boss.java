package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class boss extends monster {
    //private attributes
    private int bossXPos, bossYPos, xPos, yPos, dir, walkCounter = 0;
    private Image[][] boss1 = new Image[4][4];//store all positions of the monster
    private char[][] map;
    static Random r = new Random();//we need random for the random movements

    //constructor
    public boss(int x, int y, int xP, int yP, int d, char[][] m) throws IOException {
        bossXPos = x;//position of boss
        bossYPos = y;
        xPos = xP;//position of player
        yPos = yP;
        map = m;//we need to know location of walls
        dir = d;//direction it is facing

        for (int i = 0; i < 4; i++) {//importing the images
            boss1[i][0] = ImageIO.read(new File("src/boss" + ((i + 1) * 10 + 1) + ".png"));
            boss1[i][1] = ImageIO.read(new File("src/boss" + ((i + 1) * 10 + 2) + ".png"));
            boss1[i][2] = ImageIO.read(new File("src/boss" + ((i + 1) * 10 + 3) + ".png"));
            boss1[i][3] = ImageIO.read(new File("src/boss" + ((i + 1) * 10 + 4) + ".png"));
        }

        //creating a timer
        Timer t = new Timer();
        int INITIAL_DELAY = 200;
        int PERIOD_INTERVAL = 150;//this controls how fast the boss moves
        t.scheduleAtFixedRate(new boss.ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);
    }

    @Override
    void move() {
        walkCounter++;
        //idea is that we randomly move and we make sure that they are not moving into a wall
        if ((walkCounter / 3) % 2 == 1) {
            int n = r.nextInt(4);
            if (n == 0) {
                boolean move = true;
                for (int i = 0; i < 8; i++)
                    if (map[bossYPos][bossXPos - i] != ' ') {
                        move = false;
                        break;
                    }

                dir = 1;
                if (move)
                    bossXPos--;
            } else if (n == 1) {
                boolean move = true;
                for (int i = 0; i < 8; i++)
                    if (map[bossYPos][bossXPos + i] != ' ') {
                        move = false;
                        break;
                    }
                dir = 2;
                if (move)
                    bossXPos++;
            } else if (n == 2) {
                boolean move = true;
                for (int i = 0; i < 3; i++)
                    if (map[bossYPos - i][bossXPos] != ' ') {
                        move = false;
                        break;
                    }
                dir = 3;
                if (move)
                    bossYPos--;
            } else if (n == 3) {
                boolean move = true;
                for (int i = 0; i < 8; i++)
                    if (map[bossYPos + i][bossXPos] != ' ') {
                        move = false;
                        break;
                    }
                dir = 0;
                if (move)
                    bossYPos++;
            }
        } else {
            if (dir == 1) {
                boolean move = true;
                for (int i = 0; i < 8; i++)
                    if (map[bossYPos][bossXPos-i] != ' ') {
                        move = false;
                        break;
                    }

                if (move)
                    bossXPos--;
            } else if (dir == 2) {
                boolean move = true;
                for (int i = 0; i < 8; i++)
                    if (map[bossYPos][bossXPos + i] != ' ') {
                        move = false;
                        break;
                    }
                dir = 2;
                if (move)
                    bossXPos++;
            } else if (dir == 3) {
                boolean move = true;
                for (int i = 0; i < 8; i++)
                    if (map[bossYPos - i][bossXPos] != ' ') {
                        move = false;
                        break;
                    }

                dir = 3;
                if (move)
                    bossYPos--;
            } else if (dir == 0) {
                boolean move = true;
                for (int i = 0; i < 8; i++)
                    if (map[bossYPos + i][bossXPos] != ' ') {
                        move = false;
                        break;
                    }
                dir = 0;
                if (move)
                    bossYPos++;
            }
        }

        if (bossXPos>184)
            bossXPos = 184;

        if (bossYPos>70)
            bossYPos = 70;
    }

    @Override
    void print(Graphics g) {
        //print the location of the boss in relation to main character
        if (Math.abs(xPos - bossXPos) < 25 && Math.abs(yPos - bossYPos) < 20) {
            g.drawImage(boss1[dir][walkCounter % 4], (bossXPos - xPos + 25) * 20 + 10, (bossYPos - yPos + 20) * 20 + 10, 120, 170, null);
        }
    }

    @Override
    void update(int x, int y) {
        //update position of main character
        xPos = x;
        yPos = y;
    }


    private class ScheduleTask extends TimerTask {
        //we move
        @Override
        public void run() {
            move();
        }
    }

    public boolean collision() {
        //we check to see if the boss hits the character
        Rectangle r1 = new Rectangle(25 * 20 + 15, 20 * 20 + 15, 35, 70);
        Rectangle r2 = new Rectangle((bossXPos - xPos + 25) * 20 + 20, (bossYPos - yPos + 27) * 20 + 25, 100, 140);
        return r1.intersects(r2);
    }
}
