package com.krytality.infiniterunner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

public class ObstacleManager
{
    private Obstacles obstacles = new Obstacles();

    private int spawnTime = 0;
    private int speedBonus = 0;

    private long lastSpeedBoostTime;

    private ArrayList<Rect> killers = new ArrayList<>();

    private Bitmap spike1;

    private Random rand = new Random();

    public ObstacleManager()
    {
        this.killers.add(obstacles.getObstacleType(0));
        this.lastSpeedBoostTime = System.currentTimeMillis();
        this.speedBonus = 0;

        BitmapFactory bf = new BitmapFactory();

        //this.spike1 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.spike1);
    }

    public ArrayList<Rect> getKillers()
    {
        return killers;
    }

    public void reset()
    {
        this.killers.clear();

        this.lastSpeedBoostTime = System.currentTimeMillis();
        this.speedBonus = 0;
    }

    public Rect getObstacle(int index)
    {
        return this.killers.get(index);
    }

    public void update()
    {
        this.spawnTime++;
        if(this.spawnTime == Constants.SPAWN_RATE)
        {
            this.spawnTime = 0;
            killers.add(obstacles.getObstacleType(rand.nextInt(3)));
            //killers.add(obstacles.getObstacleType(1));

        }

        for(Rect r : killers)
        {
            if(System.currentTimeMillis() - this.lastSpeedBoostTime > 1000)
            {
                this.lastSpeedBoostTime = System.currentTimeMillis();
                if(this.speedBonus < 50)
                {
                    this.speedBonus += 2;
                }
                else if(this.speedBonus < 100)
                {
                    this.speedBonus++;
                }
                else
                {
                    this.speedBonus += 2;
                }

                //this.speedBonus += Constants.SCREEN_WIDTH / 100;
                //System.out.println("Speed bonus: " + this.speedBonus);
            }
            r.left -= Constants.OBSTABLE_SPEED + this.speedBonus;
            r.right -= Constants.OBSTABLE_SPEED + this.speedBonus;
        }


    }

    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        for(Rect r:killers)
        {
            canvas.drawRect(r, paint);
            //canvas.drawBitmap(this.spike1, null, r, new Paint());
        }

    }
}
