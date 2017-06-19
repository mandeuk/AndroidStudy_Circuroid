package com.playgrammer.circuroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;

/**
 * Created by DES_LSY on 2017-05-25.
 */

public class Missile extends View {
    public enum Missile_Type {MIS_BASIC, MIS_CHAINBOLT, MIS_LASER, MIS_GIUDED};
    public Missile_Type myType = Missile_Type.MIS_BASIC;
    Bitmap basicmis = BitmapFactory.decodeResource(getResources(), R.drawable.basicmissile);
    Bitmap resizeMis, finalMis;
    boolean ismisresize = false;

    public float posX, posY;
    float dirX, dirY;
    float missileSizeX, missileSizeY;
    float width, height;
    double angle;

    long lastTime, curTime;//타이머용도

    public Missile(Context context, float _width, float _height, float posx, float posy, float dist, double input_angle) {
        super(context);
        width = _width;
        height = _height;
        posX = posx;
        posY = posy;
        dirX = (width / 2 - posx)/dist;
        dirY = (height / 2 - posy)/dist;
        angle = input_angle-90;//플레이어의 각도를 넘겨받아 조정이 필요해서 -90해줌
        lastTime = 0;
        curTime = 0;
        missileSizeX = width * 0.02f;
        missileSizeY = height * 0.03f;
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    public void Draw(Canvas canvas)
    {
        curTime = System.currentTimeMillis() / 1000;
        if(lastTime == 0)
        posX += dirX;
        posY += dirY;

        if(ismisresize == false){
            Matrix matrix = new Matrix();
            matrix.postRotate((float)angle);

            resizeMis = Bitmap.createScaledBitmap(basicmis, (int)missileSizeX, (int)missileSizeY, true);
            finalMis = Bitmap.createBitmap(resizeMis, 0, 0, (int)missileSizeX, (int)missileSizeY, matrix, true);
            ismisresize = true;
        }
        lastTime = curTime;
        canvas.drawBitmap(finalMis, posX, posY, null);
    }
}
