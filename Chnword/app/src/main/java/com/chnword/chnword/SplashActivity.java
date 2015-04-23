package com.chnword.chnword;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;


public class SplashActivity extends Activity {

    private Context mContext;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContext = this;
        mLayout = (LinearLayout) findViewById(R.id.ll_splash);

        Animation alphaAnim = new AlphaAnimation(0.3f, 1.0f);
        alphaAnim.setDuration(2000);
        mLayout.startAnimation(alphaAnim);

    }

    @Override
    protected void onStart(){
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(mContext, GuideActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }
}
