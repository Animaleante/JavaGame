package com.diogo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel
{
    private static final int HERO_DIMENSION = 20;
    private static final int HERO_SPEED = 100;
    private static final int HERO_JUMP = -500;
    private static final int GRAVITY = 1500;

    private ArrayList<DisplayObject> objs;

    private DisplayObject ground;
    private GameObject hero;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean jumpPressed = false;
    private boolean grounded = false;
    private boolean canDoubleJump = false;
    private boolean touchingWall = false;
    private boolean jumpedFromWall = false;

    public static void main(String[] args) throws InterruptedException
    {
        JFrame frame = new JFrame("Game");
        Game game = new Game();
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e)
            {
                game.onKeyRelease(e);
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                game.onKeyPress(e);
            }
        });
        frame.setSize(new Dimension(400,600));
        frame.setVisible(true);

        game.start();

        while(true) {
            game.updateLoop((float)1/60);
            Thread.sleep(16);
        }
    }

    protected void onKeyPress(KeyEvent e)
    {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                leftPressed = true;
                rightPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                leftPressed = false;
                rightPressed = true;
                break;
            case KeyEvent.VK_SPACE:
                jumpPressed = true;
                break;
        }
    }

    protected void onKeyRelease(KeyEvent e)
    {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            case KeyEvent.VK_SPACE:
                jumpPressed = false;
                break;
        }
    }

    public Game()
    {
        setBackground(Color.BLACK);

        objs = new ArrayList<DisplayObject>();
    }

    private void start()
    {
        int groundHeight = 30;
        ground = new DisplayObject(0, getHeight()-groundHeight, getWidth(), groundHeight, Color.WHITE);
        objs.add(ground);

        hero = new GameObject(10, getHeight()-groundHeight-HERO_DIMENSION, HERO_DIMENSION, HERO_DIMENSION, Color.GREEN);
        objs.add(hero);
    }

    private void updateLoop(float time)
    {
        if(leftPressed)
            hero.velX = -HERO_SPEED;
        else if(rightPressed)
            hero.velX = HERO_SPEED;
        else
            hero.velX = 0;

        if(jumpPressed && (grounded || canDoubleJump || touchingWall)) {
            jumpPressed = false;
            if(grounded)
                grounded = false;
            else if(!touchingWall)
                canDoubleJump = false;
            else {
                touchingWall = false;
                jumpedFromWall = true;
            }

            hero.velY = HERO_JUMP;
        }

        for(DisplayObject obj : objs) {
            obj.update(time);
        }

        hero.velY += GRAVITY * time;

        if(hero.y + HERO_DIMENSION > ground.y) {
            hero.y = ground.y-HERO_DIMENSION;
            hero.velY = 0;
            grounded = true;
            jumpedFromWall = false;
            //canDoubleJump = true;
        }

        if(hero.y < 0) {
            hero.y = 0;
            hero.velY = -hero.velY;
        }

        if(hero.x < 0)
            hero.x = 0;
        else if(hero.x > getWidth() - HERO_DIMENSION)
            hero.x = getWidth() - HERO_DIMENSION;

        if(jumpedFromWall) {
            if(hero.x > 0 && hero.x + HERO_DIMENSION < getWidth())
                jumpedFromWall = false;
        } else if(hero.x == 0 || hero.x + HERO_DIMENSION == getWidth())
            touchingWall = true;
        else touchingWall = false;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        for(DisplayObject obj : objs) {
            obj.paint(g2d);
        }
    }
}
