package com.chnword.chnword;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by khtc on 15/4/23.
 */
public class WelcomeActivity extends Activity {
    private String TAG = WelcomeActivity.class.getSimpleName();

    private Button btn_submit;
    private Button btn_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        btn_submit = (Button) findViewById(R.id.btn_submit_t);
        btn_regist = (Button) findViewById(R.id.btn_regist_t);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClick();
            }
        });
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegistClick();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



    //提交
    public void onLoginClick(){
        Log.i(TAG, "METHOD onLoginClick");
        Intent i = new Intent(this, TabActivity.class);
        startActivity(i);
    }

    //使用
    public void onRegistClick(){
        Log.i(TAG, "METHOD onRegistClick");
        Intent i = new Intent(this, TabActivity.class);
        startActivity(i);
    }
}
