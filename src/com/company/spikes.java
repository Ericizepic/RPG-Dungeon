package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class spikes {
    //private attributes
    private int spikeXPos, spikeYPos, xPos, yPos, count;
    private Image[] spike = new Image[10];
    private boolean activated;
    private Image current;

    //constructor
    public spikes(int x, int y, int xP, int yP) throws IOException {
        spikeXPos = x;//position of spikes
        spikeYPos = y;
        xPos = xP;//position of player
        yPos = yP;

        //initializing images

        for (int i = 0; i < 5; i++)
            spike[i] = ImageIO.read(new File("src/spike" + (i + 1) + ".png"));
        for (int i = 5; i < 10; i++)
            spike[i] = spike[9 - i];
        current = spike[0];
        count = 0;

        //we need timer
        Timer t = new Timer();
        int INITIAL_DELAY = 500;
        t.scheduleAtFixedRate(new spikes.ScheduleTask(), INITIAL_DELAY, 500);
    }

    //shows spikes moving up and down
    void animate() {
        count++;
        current = spike[count % 10];
        activated = count%10 == 0 || count%10 == 2 ||count%10 == 3 | count%10 == 7||count%10 == 8||count%10 == 9;
    }

    //prints position of spikes in relation to character along with the frame it is in
    void print(Graphics g) {
        if (Math.abs(xPos - spikeXPos) < 24 && Math.abs(yPos - spikeYPos) < 19) {
            g.drawImage(current, (spikeXPos - xPos + 25) * 20 + 10, (spikeYPos - yPos + 20) * 20 + 10, 100, 80, null);
        }
    }

    //updates position the charcter is in
    void update(int x, int y) {
        xPos = x;
        yPos = y;
    }

    //we "animate" the spikes
    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
            animate();
        }
    }

    //check to see if the character's box and spike's box intersect. if they do while the spike is activated, we die!
    public boolean collision() {
        Rectangle r1 = new Rectangle(25 * 20 + 15, 20 * 20 + 20, 35, 60);
        Rectangle r2 = new Rectangle((spikeXPos - xPos + 25) * 20 + 3, (spikeYPos - yPos + 20) * 20 + 40, 90, 40);
        return r1.intersects(r2)&&activated;
    }
}
