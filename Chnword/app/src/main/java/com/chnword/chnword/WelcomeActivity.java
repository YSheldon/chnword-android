package com.chnword.chnword;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chnword.chnword.beans.Module;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.store.LocalStore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by khtc on 15/4/23.
 */
public class WelcomeActivity extends Activity {
    private String TAG = WelcomeActivity.class.getSimpleName();

    private Button btn_submit;
    private Button btn_regist;
    private EditText editText;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        LocalStore store = new LocalStore(this);
        if (!"NULL".equalsIgnoreCase(store.getDefaultUser()))
        {
            Intent intent = new Intent(this, TabActivity.class);
            startActivity(intent);
        }


        btn_submit = (Button) findViewById(R.id.btn_submit_t);
        btn_regist = (Button) findViewById(R.id.btn_regist_t);
        editText = (EditText) findViewById(R.id.editText);

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

        String userCode = editText.getText().toString();

        LocalStore store = new LocalStore(this);
        store.addUser(userCode);
        store.setDefaultUser(userCode);

        Intent i = new Intent(this, TabActivity.class);
        startActivity(i);
    }

    //使用
    public void onRegistClick(){

        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);

    }


}
