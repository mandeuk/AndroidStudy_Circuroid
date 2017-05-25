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
    float missileSizeX, missileSizeY;
    float width, height;

    public Missile(Context context, float _width, float _height, float posx, float posy, float dist) {
        super(context);
        width = _width;
        height = _height;
        posX = posx;
        posY = posy;
        dirX = (width / 2 - posx)/dist;
        dirY = (height / 2 - posy)/dist;

        missileSizeX = width * 0.02f;
        missileSizeY = height * 0.03f;
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    public void Draw(Canvas canvas)
    {
        posX += dirX;
        posY += dirY;

        Bitmap resizeMis = Bitmap.createScaledBitmap(basicmis, (int)missileSizeX, (int)missileSizeY, true);
        canvas.drawBitmap(resizeMis, posX, posY, null);
    }
}
