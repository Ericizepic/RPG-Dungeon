package com.company;

import java.awt.*;
import java.io.*; // allows file access
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.*;  // Needed for ActionListener
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


class Main {
    public static void main(String[] args) {
        Game window = new Game();
        window.setVisible(true);
    }
}

class Game extends JFrame implements KeyListener, ActionListener{
    static Map map; // create map, block size
    static JComboBox<String> box;

    static {
        try {
            map = new Map(201, 251, 20, 20);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int fireC = 1;

    //======================================================== constructor
    public Game() {

        // 1... Enable key listener for movement
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        MyMouseListener listener = new MyMouseListener();//adds a mouse listener
        addMouseListener(listener);
        addMouseMotionListener(listener);


        String [] s = {"Normal Mode", "God Mode"};//lets you pick between being not able to die and playing normally
        box = new JComboBox<>(s);
        box.setFocusable(false);
        box.addActionListener(this);


        // 2... Create graphics panel

        JPanel content = new JPanel ();
        JPanel north = new JPanel ();
        DrawArea board = new DrawArea(1200, 900); // Area for map to be displayed

        north.add(box);
        content.add(north, "North");
        content.add(board, "South");
        board.setFocusable(true);

        setContentPane (content);


        //3...We set up timer1 which changes up the torches and check collision
        Timer timer = new Timer();
        int INITIAL_DELAY = 100;
        int PERIOD_INTERVAL = 100;
        timer.scheduleAtFixedRate(new ScheduleTask1(), INITIAL_DELAY, PERIOD_INTERVAL);
        timer.scheduleAtFixedRate(new ScheduleTask2(), 0, 10);

        // 5... Set this window's attributes.
        pack();
        setResizable(false);
        setTitle("ERIC GAMES: RPG DUNGEON ");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);           // Center window.

        board.addKeyListener(this);
    }


    public void keyTyped(KeyEvent e) {
        // nothing
    }


    public void keyReleased(KeyEvent e) {
        // nothing
    }


    public void keyPressed(KeyEvent e)  // handle cursor keys and enter
    {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                map.move(0, -1, 3);
                break;
            case KeyEvent.VK_DOWN:
                map.move(0, 1, 0);
                break;
            case KeyEvent.VK_LEFT:
                map.move(-1, 0, 1);
                break;
            case KeyEvent.VK_RIGHT:
                map.move(1, 0, 2);
                break;
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = (String) Objects.requireNonNull(box.getSelectedItem());
        if (s.equals("Normal Mode"))
            map.changeMode(1);
        else
            map.changeMode(2);
        this.setFocusable(true);
    }

    static class MyMouseListener extends MouseAdapter {
        public void mouseReleased(MouseEvent e) {
            String s = (String) Objects.requireNonNull(box.getSelectedItem());
            if (s.equals("Normal Mode"))
                map.changeMode(1);
            else
                map.changeMode(2);
        }
    }

    static class DrawArea extends JPanel {
        public DrawArea(int width, int height) {
            this.setPreferredSize(new Dimension(width, height)); // size
        }

        public void paintComponent(Graphics g) {
            map.print(g);
        }
    }


    //when ever timer runs, we change the fire and repaint
    class ScheduleTask1 extends TimerTask {
        @Override
        public void run() {
            fireC++;
            map.changeFire(fireC%3);
            repaint();
        }
    }

    //we check if character is still alive
    class ScheduleTask2 extends TimerTask {
        @Override
        public void run() {
            map.checkCollision();
            repaint();
        }
    }
}




