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

    public MainSceneView(Context context) {
        super(context);
        mainView = BitmapFactory.decodeResource(getResources(), R.drawable.mainview);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Bitmap resizeImg = Bitmap.createScaledBitmap(mainView, width, height, true);

        canvas.drawBitmap(resizeImg, 0, 0, null);
    }
}
