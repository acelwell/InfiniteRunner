package com.krytality.infiniterunner;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class Obstacles implements GameObject
{
    private ArrayList<Rect> obstacleTypes = new ArrayList<>();


    public Obstacles()
    {
        obstacleTypes.add(new Rect(Constants.SCREEN_WIDTH+100,100,Constants.SCREEN_WIDTH+200,200));
        obstacleTypes.add(new Rect(Constants.SCREEN_WIDTH+200,200,Constants.SCREEN_WIDTH+300,300));
        obstacleTypes.add(new Rect(Constants.SCREEN_WIDTH+300,300,Constants.SCREEN_WIDTH+400,400));
    }

    public Rect getObstacleType(int index)
    {
        switch (index)
        {
            case 1: return new Rect(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT/10*6,Constants.SCREEN_WIDTH+Constants.SCREEN_WIDTH/20,Constants.FLOOR_TOP);
            case 2: return new Rect(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT/10*7,Constants.SCREEN_WIDTH+Constants.SCREEN_WIDTH/10,Constants.FLOOR_TOP);
        }
        return new Rect(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT/10*8,Constants.SCREEN_WIDTH+Constants.SCREEN_WIDTH/5,Constants.FLOOR_TOP);
    }

    @Override
    public void update()
    {

    }

    @Override
    public void draw(Canvas canvas)
    {

    }
}
