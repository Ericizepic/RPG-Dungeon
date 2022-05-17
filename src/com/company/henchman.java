package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class henchman extends monster{
    private int henchXPos, henchYPos, xPos, yPos, dir, walkCounter = 0;
    private Image[][] henchmen = new Image[4][3];
    private char[][] map;
    static Random r = new Random();

    //constructor

    public henchman(int x, int y, int xP, int yP, int d, char[][] m) throws IOException {
        henchXPos = x;//position of minion
        henchYPos = y;
        xPos = xP;//position of player
        yPos = yP;
        map = m;//we need to know location of walls
        dir = d;//direction it is facing

        for (int i = 0; i < 4; i++) {//importing the images
            henchmen[i][0] = ImageIO.read(new File("src/henchman" + ((i + 1) * 10 + 1) + ".png"));
            henchmen[i][1] = ImageIO.read(new File("src/henchman" + ((i + 1) * 10 + 2) + ".png"));
            henchmen[i][2] = ImageIO.read(new File("src/henchman" + ((i + 1) * 10 + 3) + ".png"));
        }

        //timer needed later to schedule movements
        Timer t = new Timer();
        int INITIAL_DELAY = 200;
        int PERIOD_INTERVAL = 150;
        t.scheduleAtFixedRate(new henchman.ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);
    }
    @Override
    void move() {
        walkCounter++;
        //if (Math.abs(xPos - minionXPos) + Math.abs(yPos - minionYPos) < 5) {

        if ((walkCounter / 3) % 2 == 1) {
            int n = r.nextInt(4);
            if (n == 0 && map[henchYPos][henchXPos - 1] != 'W' && map[henchYPos][henchXPos - 2] != 'W') {
                dir = 1;
                henchXPos--;
            } else if (n == 1 && map[henchYPos][henchXPos + 1] != 'W' && map[henchYPos][henchXPos + 2] != 'W') {
                dir = 2;
                henchXPos++;
            } else if (n == 2 && map[henchYPos - 1][henchXPos] != 'W' && map[henchYPos - 2][henchXPos] != 'W') {
                dir = 3;
                henchYPos--;

            } else if (n == 3 && map[henchYPos + 1][henchXPos] != 'W' && map[henchYPos + 2][henchXPos] != 'W') {
                dir = 0;
                henchYPos++;
            }
        } else {
            if (dir == 1 && map[henchYPos][henchXPos - 1] != 'W' && map[henchYPos][henchXPos - 2] != 'W')
                henchXPos--;
            else if (dir == 2 && map[henchYPos][henchXPos + 1] != 'W' && map[henchYPos][henchXPos + 2] != 'W')
                henchXPos++;
            else if (dir == 3 && map[henchYPos - 1][henchXPos] != 'W' && map[henchYPos - 2][henchXPos] != 'W')
                henchYPos--;
            else if (dir == 0 && map[henchYPos + 1][henchXPos] != 'W' && map[henchYPos + 2][henchXPos] != 'W')
                henchYPos++;
        }
    }

    @Override
    void print(Graphics g) {
        //print the location of the henchman in relation to main character
        if (Math.abs(xPos - henchXPos) < 25 && Math.abs(yPos - henchYPos) < 20) {
            g.drawImage(henchmen[dir][walkCounter % 3], (henchXPos - xPos + 25) * 20 + 10, (henchYPos - yPos + 20) * 20 + 10, 55, 60, null);
        }
    }

    @Override
    void update(int x, int y) {//updates position of character
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
        //we check to see if the henchman hits the character
        Rectangle r1 = new Rectangle(25 * 20 + 15, 20 * 20 + 20, 35, 60);
        Rectangle r2 = new Rectangle((henchXPos - xPos + 25) * 20 + 15, (henchYPos - yPos + 20) * 20 + 15, 45, 50);
        return r1.intersects(r2);
    }
}
