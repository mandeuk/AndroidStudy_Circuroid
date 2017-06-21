package com.playgrammer.circuroid;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by DES_LSY on 2017-05-22.
 */

public class MainSceneView extends View {
    static int myitem;
    public static final int STATE_MAIN = 0;
    public static final int STATE_GAME = 1;
    public static final int STATE_ITEM = 2;
    public static final int STATE_GAMEOVER = 3;

    public static final int ITEM_Normal = 0;
    public static final int ITEM_Machinegun = 1;
    public static final int ITEM_Tripleshot = 2;

    /*이미지 사용을 위한 Bitmap변수 선언*/
    Bitmap mainView;
    Bitmap mainPlay;
    Bitmap mainItem;

    /*화면크기를 저장하기 위한 int형 변수*/
    static int width;
    static int height;
    static int halfwidth;
    static int halfheight;
    static boolean screensizecalculated = false;

    /*화면 상태를 저장하기 위한 변수수*/
    static int viewState;

    /*글로벌*/
    int bestscore;
    Bitmap globalReturn;

    /*아이템뷰*/
    Bitmap Itembackground, machinegun, chainbolt, guided, laser, shield, tripleshot;
    Bitmap infobox, backgroundshadow;

    public static boolean showinfo;
    public static int showinfonum;


    /*게임뷰*/
    Bitmap background, Circle, Player, PlayerMG, PlayerTriple;
    Bitmap resizeBackground,resizeCircle,resizePlayer;
    boolean isresized = false;
    static float playerX, playerX2, playerX3;
    static float playerY, playerY2, playerY3;
    static float circleRad, playerSize;
    boolean gamecount = false;
    long lastTime, curTime, getitemTime, enditemTime;
    long curTimeAsteroid, lastTimeAsteroid;
    ArrayList<Missile> missile_list = new ArrayList<Missile>();
    ArrayList<Asteroid> asteroid_list = new ArrayList<Asteroid>();
    double dAngle = 90;
    int score, timeAsteroidspawn, timeMissilelaunch;
    boolean istouched = false;
    boolean itemAvailable = false;


    public MainSceneView(Context context) {
        super(context);
        //화면상태 변수 초기화
        viewState = STATE_MAIN;
        bestscore = 0;
        timeAsteroidspawn = 2000;
        timeMissilelaunch = 1200;

        //글로벌버튼//
        globalReturn = BitmapFactory.decodeResource(getResources(), R.drawable.backbtn);

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
        PlayerMG = BitmapFactory.decodeResource(getResources(), R.drawable.player_white);
        PlayerTriple = BitmapFactory.decodeResource(getResources(), R.drawable.player_green);

        //게임뷰 END
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //화면크기 구하기
        if(screensizecalculated == false) {
            width = canvas.getWidth();
            height = canvas.getHeight();
            halfwidth = width/2;
            halfheight = height/2;
            screensizecalculated = true;
        }
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

                Bitmap ReMainPlayer = Bitmap.createScaledBitmap(Player, width / 4, width / 4, true);
                canvas.drawBitmap(ReMainPlayer, halfwidth-(ReMainPlayer.getWidth()/2), halfheight/5*2, null);

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

                Bitmap ReReturn = Bitmap.createScaledBitmap(globalReturn, width/8, width/8, true);
                canvas.drawBitmap(ReReturn, width/8, height/10*8, null);
                break;
            }//end of case STATE_ITEM:


            case STATE_GAME: {
                circleRad = (width * 0.9f) / 2.0f;
                playerSize = width / 10.0f;



                if(gamecount == false)
                {
                    playerX = (halfwidth);
                    playerY = (halfheight) + circleRad;

                    gamecount = true;
                }

                if(isresized == false) {//리사이즈에서 렉이 심각하게 걸려 게임 켰을때 한번만 리사이징 하도록 수정
                    resizeBackground = Bitmap.createScaledBitmap(background, width, height, true);
                    resizeCircle = Bitmap.createScaledBitmap(Circle, (int) circleRad * 2, (int) circleRad * 2, true);
                    resizePlayer = Bitmap.createScaledBitmap(Player, (int) playerSize, (int) playerSize, true);

                    isresized = true;
                }

                //투사체 생성
                curTime = System.currentTimeMillis();
                if(curTime - lastTime > timeMissilelaunch)//미사일 추가
                {
                    missile_list.add(new Missile(getContext(), width, height, playerX, playerY, circleRad, dAngle));
                    if((myitem == ITEM_Tripleshot) && (itemAvailable == true))
                    {
                        missile_list.add(new Missile(getContext(), width, height, playerX2, playerY2, circleRad, dAngle+120));
                        missile_list.add(new Missile(getContext(), width, height, playerX3, playerY3, circleRad, dAngle+240));
                    }
                    lastTime = curTime;
                }
                curTimeAsteroid = System.currentTimeMillis();
                if(curTimeAsteroid - lastTimeAsteroid > timeAsteroidspawn)//운석
                {
                    asteroid_list.add(new Asteroid(getContext(), width, height));
                    lastTimeAsteroid = curTimeAsteroid;
                }


                canvas.drawBitmap(resizeBackground, 0, 0, null);
                canvas.drawBitmap(resizeCircle, (halfwidth) - (resizeCircle.getWidth()/2), (halfheight) - (resizeCircle.getHeight()/2), null);


                //투사체 드로우
                for(Missile mis : missile_list)
                {
                    mis.Draw(canvas);
                    //if(mis.posX < (halfwidth + 10) && mis.posX > (halfwidth - 10) && mis.posY < (halfheight + 10) && mis.posY > (halfheight - 10))
                    if((Math.pow(mis.posX-halfwidth, 2) + Math.pow(mis.posY-halfheight,2)) < Math.pow(circleRad/8,2))
                    {
                        missile_list.remove(mis);
                        break;
                    }
                    else if((Math.pow(mis.posX-halfwidth, 2) + Math.pow(mis.posY-halfheight,2)) > Math.pow(circleRad*2,2)){
                        missile_list.remove(mis);
                        break;
                    }
                }
                for(Asteroid ast : asteroid_list)
                {
                    ast.Draw(canvas);
                    if((Math.pow(ast.posX-halfwidth, 2) + Math.pow(ast.posY-halfheight,2)) > Math.pow(circleRad,2))
                    {
                        //asteroid_list.remove(ast);
                        viewState = STATE_GAMEOVER;
                        timeAsteroidspawn = 2000;
                        if(score > bestscore)
                            bestscore = score;
                        invalidate();
                        return;
                        //break;
                    }
                }

                boolean isdeleted = false;
                for (Asteroid ast : asteroid_list) {
                    for (Missile mis : missile_list) {
                        if ((Math.pow(ast.posX - mis.posX, 2) + Math.pow(ast.posY - mis.posY, 2)) <= Math.pow(ast.asteroidSizeX/10*6, 2)) {
                            asteroid_list.remove(ast);
                            missile_list.remove(mis);
                            isdeleted = true;
                            score++;
                            if(score%15 == 0){
                                getitemTime = System.currentTimeMillis();
                                itemAvailable = true;
                                myitem = (int)(Math.random()*2)+1;
                                switch(myitem){
                                    case ITEM_Machinegun:
                                        resizePlayer = Bitmap.createScaledBitmap(PlayerMG, (int) playerSize, (int) playerSize, true);
                                        timeMissilelaunch = 500;
                                        break;
                                    case ITEM_Tripleshot:
                                        resizePlayer = Bitmap.createScaledBitmap(PlayerTriple, (int) playerSize, (int) playerSize, true);
                                        break;
                                }

                            }
                            if(timeAsteroidspawn <= 900){
                                timeAsteroidspawn = 900;
                            }
                            else{
                                timeAsteroidspawn-=10;
                            }
                            break;
                        }
                    }
                    if (isdeleted == true) {
                        break;
                    }
                }//end of for

                if(itemAvailable == true){
                    enditemTime = System.currentTimeMillis();
                    if(enditemTime - getitemTime > 7000){
                        resizePlayer = Bitmap.createScaledBitmap(Player, (int) playerSize, (int) playerSize, true);
                        itemAvailable = false;
                        if(myitem == ITEM_Machinegun) timeMissilelaunch = 1200;
                    }

                }


                //플레이어 이미지회전
                Matrix playermatrix = new Matrix();
                playermatrix.postRotate((float)dAngle - 90);
                Bitmap rotatedPlayer = Bitmap.createBitmap(resizePlayer, 0, 0, (int) playerSize, (int) playerSize, playermatrix, true);


                float temp = (halfwidth + circleRad - playerX);
                float XCalibrate = ((temp/(circleRad*2))*playerSize);
                float temp2 = (halfheight + circleRad - playerY);
                float YCalibrate = ((temp2/(circleRad*2))*playerSize);
                canvas.drawBitmap(rotatedPlayer, playerX - XCalibrate , playerY - YCalibrate, null);

                if((itemAvailable == true)&&(myitem == ITEM_Tripleshot)){
                    Matrix player2matrix = new Matrix();
                    playermatrix.postRotate((float)dAngle -90-120);
                    Bitmap rotatedPlayer2 = Bitmap.createBitmap(resizePlayer, 0, 0, (int) playerSize, (int) playerSize, player2matrix, true);
                    temp = (halfwidth + circleRad - playerX2);
                    XCalibrate = ((temp/(circleRad*2))*playerSize);
                    temp2 = (halfheight + circleRad - playerY2);
                    YCalibrate = ((temp2/(circleRad*2))*playerSize);
                    canvas.drawBitmap(rotatedPlayer2, playerX2 - XCalibrate , playerY2 - YCalibrate, null);

                    Matrix player3matrix = new Matrix();
                    playermatrix.postRotate((float)dAngle -90-240);
                    Bitmap rotatedPlayer3 = Bitmap.createBitmap(resizePlayer, 0, 0, (int) playerSize, (int) playerSize, player3matrix, true);
                    temp = (halfwidth + circleRad - playerX3);
                    XCalibrate = ((temp/(circleRad*2))*playerSize);
                    temp2 = (halfheight + circleRad - playerY3);
                    YCalibrate = ((temp2/(circleRad*2))*playerSize);
                    canvas.drawBitmap(rotatedPlayer3, playerX3 - XCalibrate , playerY3 - YCalibrate, null);
                }

                //점수표시
                Paint pScoretext = new Paint();
                pScoretext.setTextSize(100);
                pScoretext.setColor(Color.CYAN);
                pScoretext.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(""+score,halfwidth,height/2, pScoretext);



                //Bitmap check = BitmapFactory.decodeResource(getResources(), R.drawable.poscheck);
                //canvas.drawBitmap(check, playerX , playerY, null);
                //canvas.drawBitmap(check, halfwidth , halfheight, null);
                if(istouched == true){
                    invalidate();
                }
                else {
                    //일시정지시 배경색 어둡게
                    Bitmap RE_OVER_bgshadow = Bitmap.createScaledBitmap(backgroundshadow, width, height, true);
                    canvas.drawBitmap(RE_OVER_bgshadow, 0, 0, null);

                    //일시정지 텍스트 출력
                    Paint pausetext = new Paint();
                    pausetext.setTextAlign(Paint.Align.CENTER);
                    pausetext.setColor(Color.CYAN);
                    pausetext.setTextSize(200);
                    canvas.drawText("일시정지", halfwidth, halfheight, pausetext);


                    Paint pingameScoretext = new Paint();
                    pingameScoretext.setTextSize(150);
                    pingameScoretext.setColor(Color.CYAN);
                    pingameScoretext.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(""+score,halfwidth,height/10*7, pingameScoretext);
                }
                break;
            }//end of case STATE_GAME:


            case STATE_GAMEOVER:
            {
                Paint p = new Paint();
                p.setTextSize(100);
                p.setColor(Color.WHITE);
                p.setTextAlign(Paint.Align.CENTER);

                Paint pScoretext = new Paint();
                pScoretext.setTextSize(300);
                pScoretext.setColor(Color.CYAN);
                pScoretext.setTextAlign(Paint.Align.CENTER);

                Bitmap RE_OVER_bgshadow = Bitmap.createScaledBitmap(mainView, width, height, true);
                canvas.drawBitmap(RE_OVER_bgshadow, 0, 0, null);
                Bitmap ReReturn = Bitmap.createScaledBitmap(globalReturn, width/8, width/8, true);
                canvas.drawBitmap(ReReturn, width/8, height/10*8, null);

                canvas.drawText("점수",halfwidth,height/10*2, p);
                canvas.drawText(""+score,halfwidth,height/10*4, pScoretext);
                canvas.drawText("최고기록 : " + bestscore,halfwidth,height/10*6, p);

                invalidate();
                break;
            }

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
                    {viewState = STATE_GAME;     score = 0; invalidate();       return true;}


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

                Rect RECTmachinegun =   new Rect(((halfwidth)-(imgwidth*2)), (((halfheight)-imgwidth)/2), ((halfwidth)-(imgwidth*2))+imgwidth, (((halfheight)-imgwidth)/2)+imgwidth);
                if(RECTmachinegun.contains(MouseX, MouseY))
                    if(event.ACTION_UP == event.getAction())
                    {showinfo = true;    showinfonum = 1;     invalidate();       return true;}

                Rect RECTlaser =        new Rect((((width)-imgwidth)/2), (((halfheight)-imgwidth)/2), (((width)-imgwidth)/2)+imgwidth, (((halfheight)-imgwidth)/2)+imgwidth);
                if(RECTlaser.contains(MouseX, MouseY))
                    if(event.ACTION_UP == event.getAction())
                    {showinfo = true;    showinfonum = 2;     invalidate();       return true;}

                Rect RECTtripleshot =   new Rect(((halfwidth)+imgwidth), (((halfheight)-imgwidth)/2), ((halfwidth)+imgwidth)+imgwidth, (((halfheight)-imgwidth)/2)+imgwidth);
                if(RECTtripleshot.contains(MouseX, MouseY))
                    if(event.ACTION_UP == event.getAction())
                    {showinfo = true;    showinfonum = 3;     invalidate();       return true;}

                Rect RECTshield =       new Rect(((halfwidth)-(imgwidth*2)), (((height)-imgwidth)/2), ((halfwidth)-(imgwidth*2))+imgwidth, (((height)-imgwidth)/2)+imgwidth);
                if(RECTshield.contains(MouseX, MouseY))
                    if(event.ACTION_UP == event.getAction())
                    {showinfo = true;    showinfonum = 4;     invalidate();       return true;}

                Rect RECTguided =       new Rect((((width)-imgwidth)/2), (((height)-imgwidth)/2), (((width)-imgwidth)/2)+imgwidth, (((height)-imgwidth)/2)+imgwidth);
                if(RECTguided.contains(MouseX, MouseY))
                    if(event.ACTION_UP == event.getAction())
                    {showinfo = true;    showinfonum = 5;     invalidate();       return true;}

                Rect RECTchainbolt =    new Rect(((halfwidth)+imgwidth), (((height)-imgwidth)/2), ((halfwidth)+imgwidth)+imgwidth, (((height)-imgwidth)/2)+imgwidth);
                if(RECTchainbolt.contains(MouseX, MouseY))
                    if(event.ACTION_UP == event.getAction())
                    {showinfo = true;    showinfonum = 6;     invalidate();       return true;}

                Rect RECTglobalreturn =    new Rect((width/8), (height/10*8), (width/8)+(width/8), (height/10*8)+(width/8));
                if(RECTglobalreturn.contains(MouseX, MouseY)) {
                    if (event.ACTION_UP == event.getAction()) {
                        viewState = STATE_MAIN;
                        invalidate();
                        return true;
                    }
                }
                break;
            }//end of case STATE_ITEM:


            case STATE_GAME: {
                //float MouseX = event.getX();
                //float MouseY = event.getY();
                if(event.ACTION_DOWN == event.getAction()){
                    istouched = true;
                    invalidate();
                }
                else if(event.ACTION_UP == event.getAction()) {
                    istouched = false;
                }

                if(istouched == true) {
                    if (event.ACTION_MOVE == event.getAction()) {
                        double cx, cy;

                        cx = (double) halfwidth - (double) MouseX;
                        cy = (double) halfheight - (double) MouseY;
                        if (cy == 0.0f)
                            cy = 0.000001f;
                        dAngle = Math.atan(-cx / cy);
                        if (cy < 0) dAngle += 3.141592;
                        //if (MouseX <= halfwidth && MouseY <= halfheight) dAngle += 2 * 3.141592;

                        dAngle = (dAngle * 57.295791) - 90;

                        playerX = (float) (Math.cos(dAngle * 3.141592 / 180) * circleRad) + halfwidth;
                        playerY = (float) (Math.sin(dAngle * 3.141592 / 180) * circleRad) + halfheight;

                        if(itemAvailable == true){
                            playerX2 = (float) (Math.cos((dAngle+120) * 3.141592 / 180) * circleRad) + halfwidth;
                            playerY2 = (float) (Math.sin((dAngle+120) * 3.141592 / 180) * circleRad) + halfheight;
                            playerX3 = (float) (Math.cos((dAngle+240) * 3.141592 / 180) * circleRad) + halfwidth;
                            playerY3 = (float) (Math.sin((dAngle+240) * 3.141592 / 180) * circleRad) + halfheight;
                        }
                    }
                }

                break;
            }//end of case STATE_GAME:


            case STATE_GAMEOVER:{
                if(event.ACTION_UP == event.getAction()) {
                    Rect RECTglobalreturn =    new Rect((width/8), (height/10*8), (width/8)+(width/8), (height/10*8)+(width/8));
                    if(RECTglobalreturn.contains(MouseX, MouseY)) {
                        if (event.ACTION_UP == event.getAction()) {
                            viewState = STATE_MAIN;

                            missile_list.clear();
                            asteroid_list.clear();
                            gamecount = false;
                            isresized = false;

                            score = 0;

                            invalidate();

                            return true;
                        }
                    }
                }
                //break;
            }//end of case STATE_GAMEOVER:
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