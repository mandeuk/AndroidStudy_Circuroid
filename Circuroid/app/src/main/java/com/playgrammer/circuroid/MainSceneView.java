package com.playgrammer.circuroid;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by DES_LSY on 2017-05-22.
 */

public class MainSceneView extends View {
    public static final int STATE_MAIN = 0;
    public static final int STATE_GAME = 1;
    public static final int STATE_ITEM = 2;

    /*이미지 사용을 위한 Bitmap변수 선언*/
    Bitmap mainView;
    Bitmap mainPlay;
    Bitmap mainItem;

    /*화면크기를 저장하기 위한 int형 변수*/
    static int width;
    static int height;

    /*화면 상태를 저장하기 위한 변수수*/
    int viewState;

    /*아이템뷰*/
    Bitmap Itembackground, machinegun, chainbolt, guided, laser, shield, tripleshot;
    Bitmap infobox, backgroundshadow;

    public static boolean showinfo;
    public static int showinfonum;


    /*게임뷰*/
    Bitmap background, Circle, Player;
    float playerX = 0;
    float playerY = 0;
    static float circleRad, playerSize;
    boolean gamecount = false;
    long lastTime, curTime;
    ArrayList<Missile> missile_list = new ArrayList<Missile>();


    public MainSceneView(Context context) {
        super(context);
        //화면상태 변수 초기화
        viewState = STATE_MAIN;


        //메인화면 이미지들
        mainView = BitmapFactory.decodeResource(getResources(), R.drawable.mainview2);
        mainPlay = BitmapFactory.decodeResource(getResources(), R.drawable.mainplaybtn);
        mainItem = BitmapFactory.decodeResource(getResources(), R.drawable.mainitembtn);
        //메인화면 이미지 END


        //아이템뷰 이미지, 변수들
        Itembackground = BitmapFactory.decodeResource(getResources(), R.drawable.mainview2);
        machinegun = BitmapFactory.decodeResource(getResources(), R.drawable.machinegun);
        laser = BitmapFactory.decodeResource(getResources(), R.drawable.laser);
        tripleshot = BitmapFactory.decodeResource(getResources(), R.drawable.tripleshot);
        shield = BitmapFactory.decodeResource(getResources(), R.drawable.shield);
        guided = BitmapFactory.decodeResource(getResources(), R.drawable.guided);
        chainbolt = BitmapFactory.decodeResource(getResources(), R.drawable.chainbolt);

        infobox = BitmapFactory.decodeResource(getResources(), R.drawable.skillinfobox);
        backgroundshadow = BitmapFactory.decodeResource(getResources(), R.drawable.backgroundshadow);

        showinfo = false;
        //아이템뷰 이미지,변수 END


        //게임뷰
        lastTime = System.currentTimeMillis();
        background = BitmapFactory.decodeResource(getResources(), R.drawable.mainview2);
        Circle = BitmapFactory.decodeResource(getResources(), R.drawable.playcircle);
        Player = BitmapFactory.decodeResource(getResources(), R.drawable.basicplayer);
        //게임뷰 END
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //화면크기 구하기
        width = canvas.getWidth();
        height = canvas.getHeight();

        switch(viewState)
        {
            case STATE_MAIN: {
                Bitmap ReMainImg = Bitmap.createScaledBitmap(mainView, width, height, true);
                int Playwidth = width / 4;
                int Playheight = (int) (Playwidth * 0.875);
                Bitmap ReMainPlay = Bitmap.createScaledBitmap(mainPlay, Playwidth, Playheight, true);
                Bitmap ReMainItem = Bitmap.createScaledBitmap(mainItem, width / 8, width / 8, true);

                canvas.drawBitmap(ReMainImg, 0, 0, null);
                canvas.drawBitmap(ReMainPlay, (width - ReMainPlay.getWidth()) / 2, (height - ReMainPlay.getHeight()) / 2, null);
                canvas.drawBitmap(ReMainItem, (int) (width - (ReMainItem.getWidth() * 1.1)), (int) (height - (ReMainItem.getHeight() * 1.1)), null);

                break;
            }//end of case STATE_MAIN:


            case STATE_ITEM: {
                int imgwidth = width/5;

                Bitmap Resize = Bitmap.createScaledBitmap(Itembackground, width, height, true);
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
                break;
            }//end of case STATE_ITEM:


            case STATE_GAME: {
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
                break;
            }//end of case STATE_GAME:
        }//end of switch()
    }//end of OnDraw()

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int MouseX = (int)event.getX();
        int MouseY = (int)event.getY();

        switch(viewState)
        {
            case STATE_MAIN: {
                int Playwidth = width / 4;
                int Playheight = (int) (Playwidth * 0.875);
                int playbtnXpos = ((width - Playwidth ) / 2);
                int playbtnYpos = (height - Playheight) / 2;

                Rect RECTplaybtn =   new Rect(playbtnXpos, playbtnYpos,    (playbtnXpos + Playwidth), (playbtnYpos + Playheight) );
                if(RECTplaybtn.contains(MouseX, MouseY))
                    if(event.ACTION_UP == event.getAction())
                    {viewState = STATE_GAME;     invalidate();       return true;}


                Rect RECTitembtn =   new Rect((int) (width - ((width / 8) * 1.1)), (int) (height - ((width / 8) * 1.1)),    (int) (width - ((width / 8) * 1.1)) + (width / 8), (int) (height - ((width / 8) * 1.1)) + (width / 8) );
                if(RECTitembtn.contains(MouseX, MouseY))
                    if(event.ACTION_UP == event.getAction())
                    {viewState = STATE_ITEM;     invalidate();       return true;}
                break;
            }//end of case STATE_MAIN:

            case STATE_ITEM: {
                int imgwidth = width/5;

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
                break;
            }//end of case STATE_ITEM:

            case STATE_GAME: {
                //float MouseX = event.getX();
                //float MouseY = event.getY();

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
                break;
            }//end of case STATE_GAME:
        }


        return true;
    }//end of onTouchEvent()

}//end of class MainSceneView

/*

package com.playgrammer.circuroid;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;


public class MainSceneView extends View {
    Bitmap mainView;
    Bitmap mainPlay;
    Bitmap mainItem;
    int width;
    int height;


    public MainSceneView(Context context) {
        super(context);
        mainView = BitmapFactory.decodeResource(getResources(), R.drawable.mainview2);
        mainPlay = BitmapFactory.decodeResource(getResources(), R.drawable.mainplaybtn);
        mainItem = BitmapFactory.decodeResource(getResources(), R.drawable.mainitembtn);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = canvas.getWidth();
        height = canvas.getHeight();
        Bitmap ReMainImg = Bitmap.createScaledBitmap(mainView, width, height, true);
        int Playwidth = width / 4;
        int Playheight = (int) (Playwidth * 0.875);
        Bitmap ReMainPlay = Bitmap.createScaledBitmap(mainPlay, Playwidth, Playheight, true);
        Bitmap ReMainItem = Bitmap.createScaledBitmap(mainItem, width / 8, width / 8, true);

        canvas.drawBitmap(ReMainImg, 0, 0, null);
        canvas.drawBitmap(ReMainPlay, (width - ReMainPlay.getWidth()) / 2, (height - ReMainPlay.getHeight()) / 2, null);
        canvas.drawBitmap(ReMainItem, (int) (width - (ReMainItem.getWidth() * 1.1)), (int) (height - (ReMainItem.getHeight() * 1.1)), null);
    }
}

*/