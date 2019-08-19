package com.krytality.infiniterunner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player implements GameObject
{
    private Rect player;

    private int color;
    private int jumpTime = 0;
    private int whichRun = 0;

    private boolean jumping = false;

    private long lastFrameTime = System.currentTimeMillis();

    private Bitmap run1;
    private Bitmap run2;

    public Player(Rect player, int color)
    {
        this.player = player;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();

//        this.run1 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.run1);
//        this.run2 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.run2);
    }

    public void reset()
    {
//        this.player = new Rect(Constants.PLAYER_START_X,Constants.PLAYER_START_Y,
//                Constants.PLAYER_START_X + Constants.PLAYER_WIDTH,Constants.PLAYER_START_Y + Constants.PLAYER_HEIGHT);
        this.player.left = Constants.PLAYER_START_X;
        this.player.right = Constants.PLAYER_START_X + Constants.PLAYER_WIDTH;
        this.player.top = Constants.PLAYER_START_Y;
        this.player.bottom = Constants.PLAYER_START_Y + Constants.PLAYER_HEIGHT;
    }

    public Rect getPlayerRect()
    {
        return player;
    }

    public int getColor()
    {
        return color;
    }


    public boolean checkCollision(ObstacleManager obstables)
    {
        for(Rect ob : obstables.getKillers())
        {
            if(this.player.intersect(ob))
            {
                return true;
            }
        }

        return false;
    }

    private void jump()
    {
        if(this.jumpTime == Constants.JUMP_TIME)
        {
            jumping = false;
            this.jumpTime = 0;
        }
        this.player.bottom -= Constants.JUMP_SPEED;
        this.player.top = this.player.bottom - Constants.PLAYER_HEIGHT;
    }

    public void revieveEvent(MotionEvent event)
    {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                jumping = true;
                break;
        }
    }

    private void stayAboveFloor()
    {
        if(this.player.bottom > Constants.FLOOR_TOP)
        {
            this.player.bottom = Constants.FLOOR_TOP;
            this.player.top = this.player.bottom - Constants.PLAYER_HEIGHT;
        }
    }

    private void scaleRect(Rect r)
    {
        float whRatio = (float)(Constants.PLAYER_WIDTH)/Constants.PLAYER_HEIGHT;

        if(r.height() < r.width())
        {
            r.left = r.right - (int)(r.height() * whRatio);
        }
        else
        {
            r.top = r.bottom - (int)(r.width() * (1/whRatio));
        }
    }

    private void switchRun()
    {
        if(this.whichRun == 1)
        {
            this.whichRun = 0;
        }
        else
        {
            this.whichRun = 1;
        }

    }
    public void update()
    {
        if(jumping)
        {
            jump();
            this.jumpTime++;
        }
        else {

            this.player.top += Constants.JUMP_SPEED;
            this.player.bottom += Constants.JUMP_SPEED;

        }

        if(System.currentTimeMillis() - this.lastFrameTime > 100)
        {
            switchRun();
            this.lastFrameTime = System.currentTimeMillis();
        }
        stayAboveFloor();
    }

    public void draw(Canvas canvas)
    {
        //super(canvas);
        Paint paint = new Paint();
        paint.setColor(this.color);
        canvas.drawRect(player, paint);
//        if(this.whichRun == 0)
//        {
//            canvas.drawBitmap(this.run1, null, this.player, new Paint());
//            //this.whichRun++;
//        }
//        else
//        {
//            //this.whichRun = 0;
//            canvas.drawBitmap(this.run2, null, this.player, new Paint());
//
//        }
    }

}
