package com.playgrammer.circuroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by DES_LSY on 2017-05-25.
 */

public class Missile extends View {
    public enum Missile_Type {MIS_BASIC, MIS_CHAINBOLT, MIS_LASER, MIS_GIUDED};
    public Missile_Type myType = Missile_Type.MIS_BASIC;
    Bitmap basicmis = BitmapFactory.decodeResource(getResources(), R.drawable.basicmissile);

    public float posX, posY;
    float dirX, dirY;

    public Missile(Context context, float posx, float posy, float dirx, float diry) {
        super(context);
        posX = posx;
        posY = posy;
        dirX = dirx;
        dirY = diry;
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    public void Draw(Canvas canvas)
    {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        posX += dirX;
        posY += dirY;

        Bitmap resizeMis = Bitmap.createScaledBitmap(basicmis, (int)(width * 0.02f), (int)(height * 0.02f), true);
        canvas.drawBitmap(resizeMis, posX, posY, null);
    }
}
