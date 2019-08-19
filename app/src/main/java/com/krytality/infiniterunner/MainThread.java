package com.krytality.infiniterunner;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    public static final int max_fps = 30;

    private double averageFps;
    private SurfaceHolder surfaceHolder;
    private GameHub gamePanel;
    private boolean running;
    public static Canvas canvas;

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, GameHub gamePanel)
    {
        super();

        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void switchRunning()
    {
        running = !running;
    }

    @Override
    public void run()
    {
        long startTime;
        long timeMillies = 1000/max_fps;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/max_fps;

        while(running)
        {
            startTime = System.nanoTime();
            canvas = null;

            try
            {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if(canvas != null)
                {
                    try
                    {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            timeMillies = (System.nanoTime() - startTime/1000000);
            waitTime = targetTime - timeMillies;

            try
            {
                if(waitTime > 0)
                {
                    this.sleep(waitTime);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == max_fps)
            {
                averageFps = 1000/((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                //System.out.println("fps:" + averageFps);
            }
        }
    }
}
