package com.krytality.infiniterunner;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Floor implements GameObject
{
    private Rect floor;
    private int color;

    public Floor (Rect floor, int color)
    {
        this.floor = floor;
        this.color = color;
    }

    public void update()
    {

    }

    public void draw (Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(this.color);

        canvas.drawRect(this.floor, paint);
    }

}
