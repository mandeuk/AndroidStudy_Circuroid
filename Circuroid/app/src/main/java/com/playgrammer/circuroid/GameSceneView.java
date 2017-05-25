package com.playgrammer.circuroid;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by DES_LSY on 2017-05-22.
 */

public class GameSceneView extends View {
    Bitmap background, Circle, Player;
    float playerX = 0;
    float playerY = 0;
    static float width, height;
    static float circleRad, playerSize;
    boolean gamecount = false;
    long lastTime, curTime;
    ArrayList<Missile> missile_list = new ArrayList<Missile>();

    public GameSceneView(Context context) {
        super(context);
        lastTime = System.currentTimeMillis();
        background = BitmapFactory.decodeResource(getResources(), R.drawable.mainview2);
        Circle = BitmapFactory.decodeResource(getResources(), R.drawable.playcircle);
        Player = BitmapFactory.decodeResource(getResources(), R.drawable.basicplayer);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = canvas.getWidth();
        height = canvas.getHeight();
        circleRad = (int)(width * 0.9f) / 2;
        playerSize = width / 10;

        if(gamecount == false)
        {
            gamecount = true;
            playerX = (width / 2);
            playerY = (height / 2) + circleRad;
        }

        Bitmap resizeCircle = Bitmap.createScaledBitmap(Circle, (int)circleRad * 2, (int)circleRad * 2, true);
        Bitmap resizeBackground = Bitmap.createScaledBitmap(background, (int)width, (int)height, true);
        Bitmap resizePlayer = Bitmap.createScaledBitmap(Player, (int)playerSize, (int)playerSize, true);

        canvas.drawBitmap(resizeBackground, 0, 0, null);
        canvas.drawBitmap(resizeCircle, (width / 2) - circleRad, (height / 2) - circleRad, null);
        canvas.drawBitmap(resizePlayer, playerX - playerSize / 2, playerY - playerSize / 2, null);

        curTime = System.currentTimeMillis();
        if(curTime - lastTime > 500)
        {
            missile_list.add(new Missile(getContext(), width, height, playerX, playerY, circleRad));
            lastTime = curTime;
        }
        for(Missile mis : missile_list)
        {
            mis.Draw(canvas);
            if(mis.posX < (width / 2 + 10) && mis.posX > (width / 2 - 10) && mis.posY < (height / 2 + 10) && mis.posY > (height / 2 - 10))
            {
                missile_list.remove(mis);
                break;
            }
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float MouseX = event.getX();
        float MouseY = event.getY();

        if(event.ACTION_MOVE == event.getAction())
        {
            playerY = MouseY - height / 2;
            if(MouseY >= height / 2 + circleRad)
                playerY = circleRad;
            if(MouseY <= height / 2 - circleRad)
                playerY = -circleRad;

            if(MouseX >= width / 2)
                playerX = (float)Math.sqrt(circleRad * circleRad - playerY * playerY);
            else
                playerX = -(float)Math.sqrt(circleRad * circleRad - playerY * playerY);

            playerX += width / 2;
            playerY += height / 2;
        }
        return true;
    }
}
