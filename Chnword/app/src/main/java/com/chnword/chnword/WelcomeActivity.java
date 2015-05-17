package com.chnword.chnword;

import android.app.ActionBar;
import android.app.Activity;
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
import android.widget.ImageView;

import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
//        Log.i(TAG, "METHOD onRegistClick");
//        Intent i = new Intent(this, TabActivity.class);
//        startActivity(i);

        //测试入口
        String devideId = DeviceUtil.getDeviceId(this);
        String userid = "userid";



//        String url = "http://app.3000zi.com/api/verify.php";
//        JSONObject param = NetParamFactory.verifyParam(userid, devideId, "code", "user");
//        AbstractNet net = new VerifyNet(handler, param, url);
//        net.start();

//        String url = "http://app.3000zi.com/api/list.php";
//        JSONObject param = NetParamFactory.listParam(userid, devideId, 0, 0);
//        AbstractNet net = new VerifyNet(handler, param, url);
//        net.start();

//        String url = "";
//        List<String> list = new ArrayList<String>();
//        list.add("zone_000");
//        JSONObject param = NetParamFactory.subListParam(userid, devideId, list);
//        AbstractNet net = new VerifyNet(handler, param, url);
//        net.start();

//        String url = "";
//        JSONObject param = NetParamFactory.wordParam(userid, devideId, "字");
//        AbstractNet net = new VerifyNet(handler, param, url);
//        net.start();

//        String url = "";
//        JSONObject param = NetParamFactory.showParam(userid, devideId, "zone_000");
//        AbstractNet net = new VerifyNet(handler, param, url);
//        net.start();

        String url = "http://app.3000zi.com/api/regist.php";

        JSONObject param = NetParamFactory.registParam(userid, devideId, "usercode", "new DeviceId", "sessionid", "verifycode");
        AbstractNet net = new VerifyNet(handler, param, url);
        net.start();



    }

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == AbstractNet.NETWHAT_SUCESS)
            {

            }

            if (msg.what == AbstractNet.NETWHAT_FAIL) {

            }
        }
    };
}
