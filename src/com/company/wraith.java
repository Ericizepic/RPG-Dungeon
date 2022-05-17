package com.company;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;


public class wraith extends monster {
    //private attributes
    private int wraithXPos, wraithYPos, xPos, yPos, dir, walkCounter = 0;
    private Image[][] wraiths = new Image[4][3];
    private char[][] map;
    static Random r = new Random();

    public wraith(int x, int y, int xP, int yP, int d, char[][] m) throws IOException {
        wraithXPos = x;//position of minion
        wraithYPos = y;
        xPos = xP;//position of player
        yPos = yP;
        map = m;//we need to know location of walls
        dir = d;//direction it is facing

        for (int i = 0; i < 4; i++) {//importing the images
            wraiths[i][0] = ImageIO.read(new File("src/wraith" + ((i + 1) * 10 + 1) + ".png"));
            wraiths[i][1] = ImageIO.read(new File("src/wraith" + ((i + 1) * 10 + 2) + ".png"));
            wraiths[i][2] = ImageIO.read(new File("src/wraith" + ((i + 1) * 10 + 3) + ".png"));
        }

        Timer t = new Timer();
        int INITIAL_DELAY = 200;
        int PERIOD_INTERVAL = 300;//speed of monster
        t.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);
    }

    @Override
    void move() {//minion moves randomly
        walkCounter++;
        //if (Math.abs(xPos - minionXPos) + Math.abs(yPos - minionYPos) < 5) {

        if ((walkCounter / 3) % 2 == 1) {
            int n = r.nextInt(4);
            if (n == 0 && map[wraithYPos][wraithXPos - 1] == ' ' && map[wraithYPos][wraithXPos - 2] == ' ') {
                dir = 1;
                wraithXPos--;
            } else if (n == 1 && map[wraithYPos][wraithXPos + 1] == ' ' && map[wraithYPos][wraithXPos + 2] == ' '&& map[wraithYPos][wraithXPos + 3] == ' ') {
                dir = 2;
                wraithXPos++;
            } else if (n == 2 && map[wraithYPos - 1][wraithXPos] == ' ' && map[wraithYPos - 2][wraithXPos] == ' ') {
                dir = 3;
                wraithYPos--;

            } else if (n == 3 && map[wraithYPos + 1][wraithXPos] == ' ' && map[wraithYPos + 2][wraithXPos] == ' '&& map[wraithYPos + 3][wraithXPos] == ' ') {
                dir = 0;
                wraithYPos++;
            }
        } else {
            if (dir == 1 && map[wraithYPos][wraithXPos - 1] == ' ' && map[wraithYPos][wraithXPos - 2] == ' ')
                wraithXPos--;
            else if (dir == 2 && map[wraithYPos][wraithXPos + 1] == ' ' && map[wraithYPos][wraithXPos + 2] == ' '&& map[wraithYPos][wraithXPos + 3] == ' ')
                wraithXPos++;
            else if (dir == 3 && map[wraithYPos - 1][wraithXPos] == ' ' && map[wraithYPos - 2][wraithXPos] == ' ')
                wraithYPos--;
            else if (dir == 0 && map[wraithYPos + 1][wraithXPos] == ' ' && map[wraithYPos + 2][wraithXPos] == ' '&& map[wraithYPos + 3][wraithXPos] == ' ')
                wraithYPos++;
        }

    }


    public void print(Graphics g) {
        //print the location of the wraith in relation to main character
        if (Math.abs(xPos - wraithXPos) < 25 && Math.abs(yPos - wraithYPos) < 20) {
            g.drawImage(wraiths[dir][walkCounter % 3], (wraithXPos - xPos + 25) * 20 + 10, (wraithYPos - yPos + 20) * 20 + 10, 47, 80, null);
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
        //we check to see if the wraith hits the character
        Rectangle r1 = new Rectangle(25 * 20 + 15, 20 * 20 + 15, 35, 70);
        Rectangle r2 = new Rectangle((wraithXPos - xPos + 25) * 20 + 20, (wraithYPos - yPos + 20) * 20 + 20, 27, 60);
        return r1.intersects(r2);
    }
}

