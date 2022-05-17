package com.company;


import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.imageio.ImageIO;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


// -------------------------------------- Map Class ------------------------------------------------------

class Map {

    //private attributes
    private char[][] map;
    private Image[][] character = new Image[4][4];
    private Image[] torches = new Image[4], lava = new Image[3];
    private Image torch, stone, currLava, vines, bones, death, gameOver;//bunch of pictures

    private int width, height, posx, posy, dir = 0, walkCounter = 0;
    static ArrayList<monster> list = new ArrayList<>();//list of all monster objects. Uses polymorphism
    static ArrayList<cannon> list2 = new ArrayList<>();//list of all cannons
    static ArrayList<spikes> list3 = new ArrayList<>();//list of all spikes
    private boolean isAlive = true;//status of your character
    private Clip clip;
    private int mode = 1; //god mode or easy mode

    public Map(int rows, int cols, int blockwidth, int blockheight) throws IOException {
        width = blockwidth;
        height = blockheight;
        map = new char[rows][cols]; // define 2-d array size
        posx = 67;
        posy = 45;

        //music player i found online
        try
        {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("src/backgroundmusic.wav")));
            clip.start();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }

        //initializing various blocks of wall

        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++) {
                if (row < 39 || row > 163) {
                    map[row][col] = 'W'; // wall
                } else if (col < 49 || col > 199) {
                    map[row][col] = 'W'; //  wall
                } else {
                    map[row][col] = ' '; // blank space
                }
            }

        for (int row = 40; row < 70; row++)
            for (int col = 50; col < 60; col++)
                map[row][col] = 'W';

        for (int row = 40; row < 70; row++)
            for (int col = 75; col < 85; col++)
                map[row][col] = 'W';

        for (int row = 0; row < 150; row++)
            for (int col = 85; col < 95; col++)
                map[row][col] = 'W';

        for (int row = 142; row < 150; row++)
            for (int col = 95; col < 180; col++)
                map[row][col] = 'W';


        for (int row = 100; row < 130; row++)
            for (int col = 115; col < 175; col++)
                map[row][col] = 'W';

        for (int row = 40; row < 55; row++)
            for (int col = 95; col < 127; col++)
                map[row][col] = 'W';

        for (int row = 70; row < 100; row++)
            for (int col = 115; col < 120; col++)
                map[row][col] = 'W';

        for (int row = 40; row < 85; row++)
            for (int col = 127; col < 135; col++)
                map[row][col] = 'W';

        for (int row = 80; row < 88; row++)
            for (int col = 135; col < 188; col++)
                map[row][col] = 'W';

        for (int row = 48; row < 115; row++)
            for (int col = 187; col < 190; col++)
                map[row][col] = 'W';

        for (int row = 40; row < 75; row++)
            for (int col = 147; col < 157; col++)
                map[row][col] = 'W';

        for (int row = 125; row < 130; row++)
            for (int col = 175; col < 210; col++)
                map[row][col] = 'W';


        //adding torches
        for (int row = 74; row < 140; row += 15) {
            map[row][53] = 'T';
            map[row][80] = 'T';
        }
        for (int col = 100; col < 180; col += 20)
            map[160][col] = 'T';

        for (int col = 120; col < 180; col += 20)
            map[138][col] = 'T';

        for (int row = 45; row < 75; row += 12) {
            map[row][135] = 'T';
            map[row][144] = 'T';
        }


        //adding lava
        for (int row = 70; row < 150; row++)
            for (int col = 82; col < 85; col++)
                map[row][col] = 'L';

        for (int row = 70; row < 164; row++)
            for (int col = 50; col < 53; col++)
                map[row][col] = 'L';

        for (int row = 155; row < 157; row++)
            for (int col = 90; col < 95; col++)
                map[row][col] = 'L';

        for (int row = 150; row < 157; row++)
            for (int col = 99; col < 102; col++)
                map[row][col] = 'L';

        for (int row = 155; row < 162; row++)
            for (int col = 107; col < 140; col++)
                map[row][col] = 'L';

        for (int row = 154; row < 155; row++)
            for (int col = 147; col < 160; col++)
                map[row][col] = 'L';

        for (int row = 159; row < 162; row++)
            for (int col = 147; col < 160; col++)
                map[row][col] = 'L';

        for (int row = 135; row < 139; row++) {
            for (int col = 120; col < 130; col++)
                map[row][col] = 'L';

            for (int col = 135; col < 145; col++)
                map[row][col] = 'L';

            for (int col = 150; col < 160; col++)
                map[row][col] = 'L';

            for (int col = 155; col < 165; col++)
                map[row][col] = 'L';

            for (int col = 170; col < 180; col++)
                map[row][col] = 'L';
        }

        for (int row = 59; row < 70; row++)
            for (int col = 106; col < 118; col++)
                map[row][col] = 'L';

        for (int row = 88; row < 97; row++)
            for (int col = 135; col < 146; col++)
                map[row][col] = 'L';

        for (int row = 93; row < 100; row++)
            for (int col = 150; col < 160; col++)
                map[row][col] = 'L';

        for (int row = 88; row < 97; row++)
            for (int col = 163; col < 174; col++)
                map[row][col] = 'L';

        for (int row = 48; row < 115; row++)
            for (int col = 190; col < 194; col++)
                map[row][col] = 'L';

        for (int row = 48; row < 115; row++)
            for (int col = 196; col < 200; col++)
                map[row][col] = 'L';


        //adding monsters
        minion m1 = new minion(189, 160, posx, posy, 0, map);
        minion m2 = new minion(65, 70, posx, posy, 0, map);
        minion m3 = new minion(173, 155, posx, posy, 0, map);
        minion m4 = new minion(65, 130, posx, posy, 0, map);
        minion m5 = new minion(65, 150, posx, posy, 0, map);
        minion m6 = new minion(189, 145, posx, posy, 0, map);
        minion m7 = new minion(175, 155, posx, posy, 0, map);
        henchman h1 = new henchman(110, 155, posx, posy, 0, map);
        henchman h2 = new henchman(130, 155, posx, posy, 0, map);
        henchman h3 = new henchman(160, 155, posx, posy, 0, map);
        henchman h4 = new henchman(105, 57, posx, posy, 0, map);
        henchman h5 = new henchman(110, 55, posx, posy, 0, map);
        henchman h6 = new henchman(115, 60, posx, posy, 0, map);
        wraith w1 = new wraith(188, 120, posx, posy, 0, map);
        wraith w2 = new wraith(193, 125, posx, posy, 0, map);
        wraith w3 = new wraith(198, 115, posx, posy, 0, map);

        boss b1 = new boss(175, 50, posx, posy, 0, map);

        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);
        list.add(m5);
        list.add(m6);
        list.add(m7);
        list.add(h1);
        list.add(h2);
        list.add(h3);
        list.add(h4);
        list.add(h5);
        list.add(h6);
        list.add(w1);
        list.add(w2);
        list.add(w3);

        list.add(b1);


        //adding cannons

        cannon c1 = new cannon(52, 80, posx, posy, map, 1, 80);//note that higher number is lower speed
        cannon c2 = new cannon(78, 87, posx, posy, map, 2, 80);
        cannon c3 = new cannon(52, 94, posx, posy, map, 1, 50);
        cannon c4 = new cannon(52, 101, posx, posy, map, 1, 50);
        cannon c5 = new cannon(78, 108, posx, posy, map, 2, 50);
        cannon c6 = new cannon(78, 115, posx, posy, map, 2, 50);
        cannon c7 = new cannon(78, 125, posx, posy, map, 2, 30);
        cannon c8 = new cannon(52, 133, posx, posy, map, 1, 30);
        cannon c9 = new cannon(52, 141, posx, posy, map, 1, 30);
        cannon c10 = new cannon(78, 149, posx, posy, map, 2, 30);
        cannon c11 = new cannon(95, 131, posx, posy, map, 1, 40);
        cannon c12 = new cannon(95, 138, posx, posy, map, 1, 40);
        cannon c13 = new cannon(120, 96, posx, posy, map, 1, 40);
        cannon c14 = new cannon(120, 87, posx, posy, map, 1, 40);
        list2.add(c1);
        list2.add(c2);
        list2.add(c3);
        list2.add(c4);
        list2.add(c5);
        list2.add(c6);
        list2.add(c7);
        list2.add(c8);
        list2.add(c9);
        list2.add(c10);
        list2.add(c11);
        list2.add(c12);
        list2.add(c13);
        list2.add(c14);

        //adding spikes
        spikes s1 = new spikes(95, 125, posx, posy);
        spikes s2 = new spikes(102, 118, posx, posy);
        spikes s3 = new spikes(107, 125, posx, posy);
        spikes s4 = new spikes(97, 86, posx, posy);
        spikes s5 = new spikes(98, 90, posx, posy);
        spikes s6 = new spikes(97, 103, posx, posy);
        spikes s7 = new spikes(109, 105, posx, posy);
        spikes s8 = new spikes(103, 95, posx, posy);
        spikes s9 = new spikes(96, 75, posx, posy);
        spikes s10 = new spikes(104, 78, posx, posy);
        list3.add(s1);
        list3.add(s2);
        list3.add(s3);
        list3.add(s4);
        list3.add(s5);
        list3.add(s6);
        list3.add(s7);
        list3.add(s8);
        list3.add(s9);
        list3.add(s10);

        loadImages();
    }


    private void loadImages() {//load in all our images
        try {
            for (int x = 0; x < 4; x++) {
                character[x][0] = ImageIO.read(new File("src/character" + ((x + 1) * 10 + 1) + ".png"));
                character[x][1] = ImageIO.read(new File("src/character" + ((x + 1) * 10 + 2) + ".png"));
                character[x][2] = ImageIO.read(new File("src/character" + ((x + 1) * 10 + 3) + ".png"));
                character[x][3] = ImageIO.read(new File("src/character" + ((x + 1) * 10 + 4) + ".png"));
            }
            for (int x = 0; x < 3; x++) {
                torches[x] = ImageIO.read(new File("src/fire" + (x + 1) + ".png"));
            }
            for (int x = 0; x < 3; x++) {
                lava[x] = ImageIO.read(new File("src/lava" + (x + 1) + ".png"));
            }
            torch = torches[0];
            currLava = lava[0];
            stone = ImageIO.read(new File("src/cobblestone.png"));
            vines = ImageIO.read(new File("src/vine.png"));
            bones = ImageIO.read(new File("src/bones.png"));
            death = ImageIO.read(new File("src/character51.png"));
            gameOver = ImageIO.read(new File("src/gameover.png"));

        } catch (IOException e) {
            System.out.println("File not found");
        }
    }


    public void print(Graphics g)     // displays the map on the screen
    {
        //corrects position
        if (posy < 40)
            posy = 40;

        if (posx < 50)
            posx = 50;

        if (posy > 160)
            posy = 160;

        if (posx > 200)
            posx = 200;


        for (int row = posy - 20; row < posy + 20; row++) // number of rows
        {
            for (int col = posx - 25; col < posx + 25; col++) // length of first row
            {
                if (row > 164 || row < 40 || col > 202 || col < 50) {
                    g.setColor(Color.black);
                    g.fillRect((col - (posx - 25)) * width + 10, (row - (posy - 20)) * height + 10, width, height); // draw black background
                } else if (map[row][col] == 'W') {
                    // wall
                    g.fillRect((col - (posx - 25)) * width + 10, (row - (posy - 20)) * height + 10, width, height); // draw black background
                } else {
                    g.drawImage(stone, (col - (posx - 25)) * width + 10, (row - (posy - 20)) * height + 10, 20, 20, null);
                }
            }
        }
        //we have to do everything else separately so as not to be covered up by cobblestone
        for (int row = posy - 20; row < posy + 20; row++) // number of rows
            for (int col = posx - 25; col < posx + 25; col++) { // length of first row
                if (row >= 40 && row < 80 && col >= 135 && col < 147 && ((col + 2 * row) % 17 == 4 || (col + 2 * row) % 17 == 7 || (col + 4 * row) % 17 == 15))
                    g.drawImage(vines, (col - (posx - 25)) * width + 10, (row - (posy - 20)) * height + 10, 30, 60, null);

                if (map[row][col] == 'T')
                    g.drawImage(torch, (col - (posx - 25)) * width + 10, (row - (posy - 20)) * height + 10, 40, 80, null);
                else if (map[row][col] == 'L')
                    g.drawImage(currLava, (col - (posx - 25)) * width + 10, (row - (posy - 20)) * height + 10, 20, 20, null);
                else if (row == 74 && col == 175)
                    g.drawImage(bones, (col - (posx - 25)) * width + 10, (row - (posy - 20)) * height + 10, 220, 100, null);

            }

        //we go through all of our objects and update the position of our main character. Then we print their new position
        for (monster temp : list) {
            temp.update(posx, posy);
            temp.print(g);
        }

        for (cannon temp : list2) {
            temp.update(posx, posy);
            temp.print(g);
        }

        for (spikes temp : list3) {
            temp.update(posx, posy);
            temp.print(g);
        }

        if (isAlive) {//check status of character
            walkCounter = walkCounter % 4;
            g.drawImage(character[dir][walkCounter], 25 * width + 10, 20 * height + 10, 45, 80, null); // draw character if he is alive
        } else {//death
            if (mode == 1) {
                g.drawImage(death, 25 * width + 10, 20 * height + 10, 80, 80, null);
                g.drawImage(gameOver, 25 * width - 170, 20 * height - 100, 400, 270, null);
                clip.stop();
            }else {
                g.drawImage(death, 25 * width + 10, 20 * height + 10, 80, 80, null);
                isAlive = true;
            }
        }
    }


    public void move(int dx, int dy, int d)  // moves character if possible (no obstruction)
    {
        if (map[posy + 2][posx + 1] == 'L')
            isAlive = false;
        if (isAlive) {
            walkCounter++;//determines which step animation he is on
            int tempY = posy + dy, tempX = posx + dx;
            if (dx == 1)
                tempX += 1;
            if (dy == 1)
                tempY += 3;

            if (map[tempY][tempX] != 'W') {
                posx += dx;
                posy += dy;
                dir = d;
            }
        }
        if (collision())
            isAlive = false;
    }


    //check if character has been hit by anything
    public boolean collision() {
        boolean hit = false;
        for (monster temp : list) {
            temp.update(posx, posy);
            if (temp.collision())
                hit = true;
        }

        for (cannon temp : list2) {
            temp.update(posx, posy);
            if (temp.collision())
                hit = true;
        }

        for (spikes temp : list3) {
            temp.update(posx, posy);
            if (temp.collision())
                hit = true;
        }
        return hit;
    }



    public void changeFire(int c) {//we take in which fire drawing we want to switch to
        torch = torches[c];
        currLava = lava[c];
    }

    public void checkCollision() {//if hit, we are no longer alive
        if (collision())
            isAlive = false;
    }

    public void changeMode(int n){
        mode = n;
    }

}


