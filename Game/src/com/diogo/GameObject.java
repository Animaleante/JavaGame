package com.diogo;

import java.awt.Color;

public class GameObject extends DisplayObject
{
    public float velX = 0;
    public float velY = 0;

    public GameObject()
    {
        super();
    }

    public GameObject(int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }

    public GameObject(int x, int y, int width, int height, Color color)
    {
        super(x, y, width, height, color);
    }

    @Override
    public void update(float time)
    {
        super.update(time);

        x += velX * time;
        y += velY * time;
    }
}
