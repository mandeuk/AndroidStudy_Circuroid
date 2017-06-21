package com.playgrammer.circuroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;

/**
 * Created by mande on 2017-06-21.
 */

public class Asteroid extends View {
    Bitmap Asteroid1= BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1);
    Bitmap Asteroid2= BitmapFactory.decodeResource(getResources(), R.drawable.asteroid2);
    Bitmap Asteroid3= BitmapFactory.decodeResource(getResources(), R.drawable.asteroid3);
    Bitmap resizeAsteroid1, resizeAsteroid2, resizeAsteroid3;
    Bitmap finalAsteroid1, finalAsteroid2, finalAsteroid3;
    boolean ismisresize;

    public float posX, posY;
    int state;
    float dirX, dirY;
    float asteroidSizeX, asteroidSizeY, rotaion;
    float width, height;
    double angle;
    boolean iscalculated;

    //long lastTime, curTime;//타이머용도

    public Asteroid(Context context, float _width, float _height) {
        super(context);
        width = _width;
        height = _height;
        posX = width/2;
        posY = height/2;
        dirX = 0;
        dirY = 0;
        asteroidSizeX = width * 0.08f;
        asteroidSizeY = width * 0.08f;
        state = (int)(Math.random()*3);
        rotaion = 0;

        ismisresize = false;
        iscalculated = false;
    }
    public void Draw(Canvas canvas)
    {

        if(ismisresize == false){
            switch(state)
            {
                case 0:
                    resizeAsteroid1 = Bitmap.createScaledBitmap(Asteroid1, (int)asteroidSizeX, (int)asteroidSizeY, true);
                    break;
                case 1:
                    resizeAsteroid2 = Bitmap.createScaledBitmap(Asteroid2, (int)asteroidSizeX, (int)asteroidSizeY, true);
                    break;
                case 2:
                    resizeAsteroid3 = Bitmap.createScaledBitmap(Asteroid3, (int)asteroidSizeX, (int)asteroidSizeY, true);
                    break;
            }
            ismisresize = true;
        }
        if(iscalculated == false){
            double cx, cy;

            int halfwidth = (int)(width/2);
            int halfheight = (int)(height/2);

            cx = (double)halfwidth - (Math.random()*width);
            cy = (double)halfheight - (Math.random()*height);
            if (cy == 0.0f)
                cy = 0.000001f;
            angle = Math.atan(-cx / cy);
            if (cy < 0) angle += 3.141592;

            angle = (angle * 57.295791)-90;
            int circleRad = (int)(width/2);

            dirX = (float)(Math.cos(angle*3.141592/180) * circleRad) + halfwidth;
            dirY = (float)(Math.sin(angle*3.141592/180) * circleRad) + halfheight;


            dirX = (dirX-(width/2))/600;
            dirY = (dirY-(height/2))/600;

            iscalculated = true;
        }
        posX += dirX;
        posY += dirY;


        Matrix matrix = new Matrix();
        matrix.postRotate(rotaion);
        rotaion++;

        switch(state)
        {
            case 0:
                finalAsteroid1 = Bitmap.createBitmap(resizeAsteroid1, 0, 0, (int)asteroidSizeX, (int)asteroidSizeY, matrix, true);
                break;
            case 1:
                finalAsteroid2 = Bitmap.createBitmap(resizeAsteroid2, 0, 0, (int)asteroidSizeX, (int)asteroidSizeY, matrix, true);
                break;
            case 2:
                finalAsteroid3 = Bitmap.createBitmap(resizeAsteroid3, 0, 0, (int)asteroidSizeX, (int)asteroidSizeY, matrix, true);
                break;
        }


        switch(state)
        {
            case 0:
                canvas.drawBitmap(finalAsteroid1, posX-(finalAsteroid1.getWidth()/2), posY-(finalAsteroid1.getHeight()/2), null);
                break;
            case 1:
                canvas.drawBitmap(finalAsteroid2, posX-(finalAsteroid2.getWidth()/2), posY-(finalAsteroid2.getHeight()/2), null);
                break;
            case 2:
                canvas.drawBitmap(finalAsteroid3, posX-(finalAsteroid3.getWidth()/2), posY-(finalAsteroid3.getHeight()/2), null);
                break;
        }
    }
}
