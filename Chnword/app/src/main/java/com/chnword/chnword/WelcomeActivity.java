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


//        JSONObject param = NetParamFactory.registParam(userid, devideId, "usercode", "new DeviceId", "sessionid", "verifycode");
//        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_REGIST);
//        progressDialog = ProgressDialog.show(this, "title", "loading");
//        net.start();

        //创建一个用户并提交
        UUID uuid = UUID.randomUUID();
        LocalStore store = new LocalStore(this);
        store.addUser(uuid.toString());
        store.setDefaultUser(uuid.toString());

        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);

    }

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            progressDialog.dismiss();
            try {
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {
                    Bundle b = msg.getData();
                    String str = b.getString("responseBody");
                    Log.e(TAG, str);
                    JSONObject obj = new JSONObject(str);
                    JSONObject data = obj.getJSONObject("data");
                    JSONArray names = data.getJSONArray("name");
                    JSONArray cnames = data.getJSONArray("cname");

                    for(int i = 0; i < names.length(); i ++) {
                        String name = names.getString(i);
                        String cname = cnames.getString(i);
                        Module m = new Module();
                        m.setName(name);
                        m.setCname(cname);
                    }

                    Intent intent = new Intent(WelcomeActivity.this, AnimActivity.class);
                    startActivity(intent);

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(WelcomeActivity.this, "注册失败，请检查网络连接", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
