package com.krytality.infiniterunner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class GameHub extends SurfaceView implements SurfaceHolder.Callback
{

    private MainThread mt = new MainThread(getHolder(), this);

    private Player player;
    private Floor floor;
    private Obstacles obstacles;
    private ObstacleManager obstacleManager = new ObstacleManager();

    private Rect bgRect = new Rect(0,0,Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

    private boolean playing = true;

    private long killTime;

    private int score = 0;

    private Bitmap background;

    private ArrayList<GameObject> objects = new ArrayList<>();

    private Button button;

    private Context context;



    public GameHub(Context context)
    {
        super(context);

        this.context = context;

        getHolder().addCallback(this);

        this.player = new Player(new Rect(Constants.PLAYER_START_X,Constants.PLAYER_START_Y,
                Constants.PLAYER_START_X + Constants.PLAYER_WIDTH,Constants.PLAYER_START_Y + Constants.PLAYER_HEIGHT), Color.CYAN);
        this.floor = new Floor(new Rect(0, Constants.dm.heightPixels - Constants.dm.heightPixels / 10, Constants.dm.widthPixels, Constants.dm.heightPixels),
                Color.GRAY);

        this.obstacles = new Obstacles();

        objects.add(this.floor);
        objects.add(this.player);

        this.killTime = System.currentTimeMillis();

        BitmapFactory bf = new BitmapFactory();

        this.background = bf.decodeResource(context.getResources(), R.drawable.background);

        scaleRect(this.bgRect);

        button = new Button(context);


        setFocusable(true);
    }

    public boolean isPlaying()
    {
        return playing;
    }


    public void gameOver()
    {
        if(this.playing)
        {
            this.killTime = System.currentTimeMillis();
        }
        this.playing = false;


        this.context.startActivity(new Intent(context, DeadAd.class));
        mt.switchRunning();

        //new DeadAd();
//        if(Constants.AD.isLoaded())
//        {
//            Constants.AD.show();
//        }

        //reset


    }

    public void update()
    {
        if(!this.playing)
        {

        }
        else if(this.playing)
        {
            for (GameObject go : objects) {
                go.update();
            }
            this.obstacleManager.update();
            if (this.player.checkCollision(this.obstacleManager))
            {
                gameOver();
            }

//            if(this.player.getPlayerRect().left >= this.obstacleManager.getObstacle(this.score).right)
//            {
//                this.score++;
//            }
            this.score++;

        }

    }

    private void scaleRect(Rect r)
    {
        float whRatio = (float)(Constants.SCREEN_WIDTH)/Constants.SCREEN_HEIGHT;

        if(r.height() < r.width())
        {
            r.left = r.right - (int)(r.height() * whRatio);
        }
        else
        {
            r.top = r.bottom - (int)(r.width() * (1/whRatio));
        }
    }

    public void draw(Canvas canvas)
    {

        try {
            super.draw(canvas);


            if (this.playing) {
                super.draw(canvas);
                Paint paint = new Paint();

                //canvas.drawColor(Color.MAGENTA);
                canvas.drawBitmap(this.background, null, this.bgRect, new Paint());

                paint.setTextSize(69);
                paint.setColor(Color.WHITE);
                canvas.drawText("Score: " + score, 50, 50 + paint.descent() - paint.ascent(), paint);


                this.player.draw(canvas);
                this.floor.draw(canvas);
                this.obstacleManager.draw(canvas);
            } else if (!this.playing) {
                Paint paint = new Paint();

                //canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(this.background, null, this.bgRect, new Paint());

                this.player.draw(canvas);
                this.floor.draw(canvas);
                this.obstacleManager.draw(canvas);

                paint.setTextSize(69);
                paint.setColor(Color.WHITE);
                canvas.drawText("Score: " + score, 50, 50 + paint.descent() - paint.ascent(), paint);
                drawText(canvas, new Paint(), "Tap to retry");
            }
        }
        catch (Exception e) {}

    }

    public void drawText(Canvas canvas, Paint paint, String text)
    {
        Rect r = new Rect();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.WHITE);
        paint.setTextSize(69f);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(playing)
        {
            this.player.revieveEvent(event);
        }
        else if(!playing)
        {
            if(event.getAction() == MotionEvent.ACTION_DOWN && this.killTime < System.currentTimeMillis() - 500)
            {

                this.score = 0;
                this.player.reset();
                this.obstacleManager.reset();

                //setContentView(new DeadAd());

                this.playing = true;
            }
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        mt.setRunning(true);
        mt.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        System.out.println("surface was destroyed");
    }
}
