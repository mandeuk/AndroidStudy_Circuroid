package com.playgrammer.circuroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by DES_LSY on 2017-05-22.
 */

public class MainSceneView extends View {
    Bitmap mainView;
    Bitmap mainPlay;
    Bitmap mainItem;

    public MainSceneView(Context context) {
        super(context);
        mainView = BitmapFactory.decodeResource(getResources(), R.drawable.mainview2);
        mainPlay = BitmapFactory.decodeResource(getResources(), R.drawable.mainplaybtn);
        mainItem = BitmapFactory.decodeResource(getResources(), R.drawable.mainitembtn);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Bitmap ReMainImg = Bitmap.createScaledBitmap(mainView, width, height, true);
        int Playwidth = width/4;
        int Playheight = (int)(Playwidth * 0.875);
        Bitmap ReMainPlay = Bitmap.createScaledBitmap(mainPlay, Playwidth, Playheight, true);
        Bitmap ReMainItem = Bitmap.createScaledBitmap(mainItem, width / 8, width / 8, true);

        canvas.drawBitmap(ReMainImg, 0, 0, null);
        canvas.drawBitmap(ReMainPlay, (width-ReMainPlay.getWidth())/2, (height-ReMainPlay.getHeight())/2, null);
        canvas.drawBitmap(ReMainItem, (int)(width-(ReMainItem.getWidth() * 1.1)), (int)(height-(ReMainItem.getHeight() * 1.1)), null);
    }
}
