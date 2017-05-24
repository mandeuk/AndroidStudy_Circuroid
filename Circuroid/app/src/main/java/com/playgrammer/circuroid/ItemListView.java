package com.playgrammer.circuroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by DES_LSY on 2017-05-22.
 */

public class ItemListView extends View {
    Bitmap background, machinegun, chainbolt, guided, laser, shield, tripleshot;
    Bitmap infobox, backgroundshadow;

    public static boolean showinfo;
    public static int showinfonum;
    public static int width, height;

    public ItemListView(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.mainview2);
        machinegun = BitmapFactory.decodeResource(getResources(), R.drawable.machinegun);
        laser = BitmapFactory.decodeResource(getResources(), R.drawable.laser);
        tripleshot = BitmapFactory.decodeResource(getResources(), R.drawable.tripleshot);
        shield = BitmapFactory.decodeResource(getResources(), R.drawable.shield);
        guided = BitmapFactory.decodeResource(getResources(), R.drawable.guided);
        chainbolt = BitmapFactory.decodeResource(getResources(), R.drawable.chainbolt);

        infobox = BitmapFactory.decodeResource(getResources(), R.drawable.skillinfobox);
        backgroundshadow = BitmapFactory.decodeResource(getResources(), R.drawable.backgroundshadow);

        showinfo = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = canvas.getWidth();
        height = canvas.getHeight();
        int imgwidth = width/5;

        Bitmap Resize = Bitmap.createScaledBitmap(background, width, height, true);
        Bitmap REmachinegun = Bitmap.createScaledBitmap(machinegun, imgwidth, imgwidth, true);
        Bitmap RElaser = Bitmap.createScaledBitmap(laser, imgwidth, imgwidth, true);
        Bitmap REtripleshot = Bitmap.createScaledBitmap(tripleshot, imgwidth, imgwidth, true);
        Bitmap REshield = Bitmap.createScaledBitmap(shield, imgwidth, imgwidth, true);
        Bitmap REguided = Bitmap.createScaledBitmap(guided, imgwidth, imgwidth, true);
        Bitmap REchainbolt = Bitmap.createScaledBitmap(chainbolt, imgwidth, imgwidth, true);

        Bitmap REinfobox = Bitmap.createScaledBitmap(infobox, width - (width/7), width - width/7, true);
        Bitmap REbackgroundshadow = Bitmap.createScaledBitmap(backgroundshadow, width, height, true);


        canvas.drawBitmap(Resize, 0, 0, null);
        canvas.drawBitmap(REmachinegun,     ((width/2)-(imgwidth*2)),   ((height/2)-imgwidth)/2,  null);
        canvas.drawBitmap(RElaser,          ((width)-imgwidth)/2,     ((height/2)-imgwidth)/2,  null);
        canvas.drawBitmap(REtripleshot,     ((width/2)+imgwidth),     ((height/2)-imgwidth)/2,  null);
        canvas.drawBitmap(REshield,         ((width/2)-(imgwidth*2)),   ((height)-imgwidth)/2,    null);
        canvas.drawBitmap(REguided,         ((width)-imgwidth)/2,     ((height)-imgwidth)/2,    null);
        canvas.drawBitmap(REchainbolt,      ((width/2)+imgwidth),     ((height)-imgwidth)/2,    null);

        if(showinfo == true)
        {
            canvas.drawBitmap(REbackgroundshadow, 0, 0, null);
            canvas.drawBitmap(REinfobox, ((width - REinfobox.getWidth())/2), ((height - REinfobox.getWidth())/2), null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int imgwidth = width/5;
        int MouseX = (int)event.getX();
        int MouseY = (int)event.getY();

        if(showinfo == true)
        {
            if(event.ACTION_UP == event.getAction()) {
                Rect RECTinfobox =   new Rect(((width - (width - (width/7)))/2), ((height - (width - (width/7)))/2), ((width - (width - (width/7)))/2) + (width - (width/7)), ((height - (width - (width/7)))/2) + (width - (width/7)));
                if(RECTinfobox.contains(MouseX, MouseY)){
                    showinfo = false;
                    invalidate();
                    return true;
                }
            }
        }

        Rect RECTmachinegun =   new Rect(((width/2)-(imgwidth*2)), (((height/2)-imgwidth)/2), ((width/2)-(imgwidth*2))+imgwidth, (((height/2)-imgwidth)/2)+imgwidth);
        if(RECTmachinegun.contains(MouseX, MouseY))
            if(event.ACTION_UP == event.getAction())
            {showinfo = true;    showinfonum = 1;     invalidate();       return true;}

        Rect RECTlaser =        new Rect((((width)-imgwidth)/2), (((height/2)-imgwidth)/2), (((width)-imgwidth)/2)+imgwidth, (((height/2)-imgwidth)/2)+imgwidth);
        if(RECTlaser.contains(MouseX, MouseY))
            if(event.ACTION_UP == event.getAction())
            {showinfo = true;    showinfonum = 2;     invalidate();       return true;}

        Rect RECTtripleshot =   new Rect(((width/2)+imgwidth), (((height/2)-imgwidth)/2), ((width/2)+imgwidth)+imgwidth, (((height/2)-imgwidth)/2)+imgwidth);
        if(RECTtripleshot.contains(MouseX, MouseY))
            if(event.ACTION_UP == event.getAction())
            {showinfo = true;    showinfonum = 3;     invalidate();       return true;}

        Rect RECTshield =       new Rect(((width/2)-(imgwidth*2)), (((height)-imgwidth)/2), ((width/2)-(imgwidth*2))+imgwidth, (((height)-imgwidth)/2)+imgwidth);
        if(RECTshield.contains(MouseX, MouseY))
            if(event.ACTION_UP == event.getAction())
            {showinfo = true;    showinfonum = 4;     invalidate();       return true;}

        Rect RECTguided =       new Rect((((width)-imgwidth)/2), (((height)-imgwidth)/2), (((width)-imgwidth)/2)+imgwidth, (((height)-imgwidth)/2)+imgwidth);
        if(RECTguided.contains(MouseX, MouseY))
            if(event.ACTION_UP == event.getAction())
            {showinfo = true;    showinfonum = 5;     invalidate();       return true;}

        Rect RECTchainbolt =    new Rect(((width/2)+imgwidth), (((height)-imgwidth)/2), ((width/2)+imgwidth)+imgwidth, (((height)-imgwidth)/2)+imgwidth);
        if(RECTchainbolt.contains(MouseX, MouseY))
            if(event.ACTION_UP == event.getAction())
            {showinfo = true;    showinfonum = 6;     invalidate();       return true;}

        return true;
    }
}
