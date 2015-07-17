package jp.ac.hal.ths35033.mylibrary;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private SoundPool mSoundPool;
    private int closeSoundId;
    private int openSoundId;


    FrameLayout fl ;

    ImageView button1 ;
    ImageView button2 ;
    ImageView button3 ;
    ImageView button4 ;
    ImageView button5 ;
    ImageView button6 ;
    ImageView button7 ;

    float xPos;
    float yPos;
    float childXPos;
    float childYPos;

    boolean clickFlg = true;
    boolean animeFlg = false;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Windowアニメーションを無効化
        overridePendingTransition(0, 0);

        actionBar = this.getSupportActionBar();
        actionBar.hide();

        //Property Animation: Changing TextColor
        button1 = (ImageView) findViewById(R.id.button1);
        button2 = (ImageView) findViewById(R.id.button2);
        button3 = (ImageView) findViewById(R.id.button3);
        button4 = (ImageView) findViewById(R.id.button4);
        button5 = (ImageView) findViewById(R.id.button5);
        button6 = (ImageView) findViewById(R.id.button6);
        button7 = (ImageView) findViewById(R.id.button7);

        button2.setImageResource(R.drawable.book);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!animeFlg) {
                    if (button1.getX() <= childXPos + 1 && button1.getX() >= childXPos - 1){
                        onClickOpenMenu();
                    } else {
                        onClickCloseMenu();
                    }
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookListActivity.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookListActivity.class);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookListActivity.class);
                startActivity(intent);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookAddActivity.class);
                intent.putExtra("key",1);
                startActivity(intent);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookAddActivity.class);
                intent.putExtra("key",0);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        fl = (FrameLayout)findViewById(R.id.screen);
        xPos = (fl.getWidth()- button2.getWidth())/2;
        yPos = (fl.getHeight() - button2.getHeight())/2;
        setViewPos();
        button2.setX(xPos);
        button2.setY(yPos);
    }

    public void setViewPos(){
        childXPos = (fl.getWidth() - button1.getWidth())/2;
        childYPos = (fl.getHeight() - button1.getHeight())/2;
        button1.setX(childXPos);
        button1.setY(childYPos);
        button3.setX(childXPos);
        button3.setY(childYPos);
        button4.setX(childXPos);
        button4.setY(childYPos);
        button5.setX(childXPos);
        button5.setY(childYPos);
        button6.setX(childXPos);
        button6.setY(childYPos);
        button7.setX(childXPos);
        button7.setY(childYPos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 予め音声データを読み込む
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        openSoundId = mSoundPool.load(getApplicationContext(), R.raw.open, 0);
        closeSoundId = mSoundPool.load(getApplicationContext(), R.raw.close, 0);
        clickFlg = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // リリース
        mSoundPool.release();
        clickFlg = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        clickFlg = true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        clickFlg = true;
    }

    private void playFromOpenSoundPool() {
        // 再生
        //mSoundPool.play(openSoundId, 1.0F, 1.0F, 0, 0, 1.0F);
    }
    private void playFromCloseSoundPool() {
        // 再生
        //mSoundPool.play(closeSoundId, 1.0F, 1.0F, 0, 0, 1.0F);
    }

    public void chengeColorAnime(View v,String propetyName,int colorA,int colorB){
        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(v, propetyName, colorA, colorB);
        colorAnimator.setDuration(3000);
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimator.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimator.start();
    }

    private void animateRotation(View target) {
        // rotationプロパティを0fから360fに変化させます
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "rotation", 0f, 360f);
        // 3秒かけて実行させます
        objectAnimator.setDuration(3000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        // アニメーションを開始します
        objectAnimator.start();
    }

    private void onClickOpenMenu(){
        playFromOpenSoundPool();
        animateAnimatorSetSample(button1, 45f * 1, fl.getWidth()/2);
        animateAnimatorSetSample(button3, 45f * 2, fl.getWidth()/2);
        animateAnimatorSetSample(button4, 45f * 3, fl.getWidth()/2);
        animateAnimatorSetSample(button5, 45f * 5, fl.getWidth()/2);
        animateAnimatorSetSample(button6, 45f * 6, fl.getWidth()/2);
        animateAnimatorSetSample(button7, 45f * 7, fl.getWidth()/2);
    }

    private void onClickCloseMenu(){
        playFromCloseSoundPool();
        animateAnimatorSetSample(button1, 45f * 1 - 180f, fl.getWidth()/2);
        animateAnimatorSetSample(button3, 45f * 2 - 180f ,fl.getWidth()/2);
        animateAnimatorSetSample(button4, 45f * 3 - 180f ,fl.getWidth()/2);
        animateAnimatorSetSample(button5, 45f * 5 - 180f ,fl.getWidth()/2);
        animateAnimatorSetSample(button6, 45f * 6 - 180f ,fl.getWidth()/2);
        animateAnimatorSetSample(button7, 45f * 7 - 180f ,fl.getWidth()/2);
    }

    private void animateAnimatorSetSample(View target, float degree, float distance) {
        // AnimatorSetに渡すAnimatorのリストです
        List<Animator> animatorList= new ArrayList<Animator>();
        // 距離と半径から到達点となるX座標、Y座標を求めます
        float toX = (float) ( distance * Math.cos( Math.toRadians(degree)));
        float toY = (float) ( distance * Math.sin( Math.toRadians(degree)));
        // translationXプロパティを0fからtoXに変化させます
        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat( "translationX", target.getX(),target.getX() + toX );
        // translationYプロパティを0fからtoYに変化させます
        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat( "translationY", target.getY(), target.getY() + toY );
        // rotationプロパティを0fから360に変化させます
        PropertyValuesHolder holderRotaion = PropertyValuesHolder.ofFloat( "rotation", 0f, 360f );
        // targetに対してholderX, holderY, holderRotationを同時に実行します
        ObjectAnimator translationXYAnimator =
                ObjectAnimator.ofPropertyValuesHolder( target, holderX, holderY, holderRotaion );

        //translationXYAnimator.setInterpolator(new LinearInterpolator());
        // 2秒かけて実行させます
        translationXYAnimator.setDuration(700);
        translationXYAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animeFlg = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animeFlg = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        translationXYAnimator.start();

    }


}
