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
    float dirX, dirY, originalX, originalY;
    float missileSizeX, missileSizeY;
    float width, height;
    double angle;

    long lastTime, curTime;//타이머용도

    float halfwidth, halfheight, circleRad;

    float temp, temp2, XCalibrate, YCalibrate;

    public Missile(Context context, float _width, float _height, float posx, float posy, float dist, double input_angle) {
        super(context);
        width = _width;
        height = _height;
        originalX = posx;
        originalY = posy;
        posX = posx;
        posY = posy;
        dirX = (width / 2 - posx)/dist;
        dirY = (height / 2 - posy)/dist;
        angle = input_angle-90;//플레이어의 각도를 넘겨받아 조정이 필요해서 -90해줌
        lastTime = 0;
        curTime = 0;
        missileSizeX = width * 0.02f;
        missileSizeY = height * 0.055f;

        halfwidth = width/2;
        halfheight = height/2;
        circleRad = dist;
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    public void Draw(Canvas canvas)
    {

        posX += dirX*2;
        posY += dirY*2;

        if(ismisresize == false){
            Matrix matrix = new Matrix();
            matrix.postRotate((float)angle);



            resizeMis = Bitmap.createScaledBitmap(basicmis, (int)missileSizeX, (int)missileSizeY, true);
            finalMis = Bitmap.createBitmap(resizeMis, 0, 0, (int)missileSizeX, (int)missileSizeY, matrix, true);
            ismisresize = true;
        }

        //int imgmoveX, imgmoveY;
        //imgmoveX = (int)(Math.cos((angle+180)*3.141592/180) * (missileSizeX/2));
        //imgmoveY = (int)(Math.sin((angle+180)*3.141592/180) * (missileSizeX/2));

        //float temp = (halfwidth + circleRad - posX);
        //float XCalibrate = ((temp/(circleRad*2))*missileSizeX);
        //float temp2 = (halfheight + circleRad - posY);
        //float YCalibrate = ((temp2/(circleRad*2))*missileSizeY);

        temp = (halfwidth + circleRad - originalX);
        XCalibrate = ((temp/(circleRad*2))*finalMis.getWidth());
        temp2 = (halfheight + circleRad - originalY);
        YCalibrate = ((temp2/(circleRad*2))*finalMis.getHeight());

        canvas.drawBitmap(finalMis, posX - XCalibrate, posY - YCalibrate, null);

        Bitmap check = BitmapFactory.decodeResource(getResources(), R.drawable.poscheck);
        canvas.drawBitmap(check, posX , posY, null);
    }
}
