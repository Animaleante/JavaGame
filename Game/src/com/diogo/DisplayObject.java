package com.diogo;

import java.awt.Color;
import java.awt.Graphics2D;

public class DisplayObject
{
    public float x = 0;
    public float y = 0;
    public int width = 0;
    public int height = 0;
    public Color color = Color.GRAY;

    public DisplayObject(){}

    public DisplayObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public DisplayObject(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void update(float time) {}

    public void paint(Graphics2D g) {
        g.setColor(this.color);
        g.fillRect(Math.round(x), Math.round(y), width, height);
    }
}
