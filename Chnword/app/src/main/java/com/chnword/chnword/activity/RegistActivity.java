package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.chnword.chnword.R;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.popwindow.PopErrorWindow;
import com.chnword.chnword.popwindow.PopManyWindow;
import com.chnword.chnword.store.LocalStore;

import org.json.JSONObject;

import java.util.UUID;
import android.view.ViewGroup.LayoutParams;

/**
 * Created by khtc on 15/4/23.
 */
public class RegistActivity extends Activity {
    private String TAG = RegistActivity.class.getSimpleName();

    private EditText editText;

    private ProgressDialog progressDialog;

    // 声明PopupWindow对象的引用
    private PopupWindow toManyPopupWindow;
    private PopupWindow errorPopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        LocalStore store = new LocalStore(this);
        if (!"0".equalsIgnoreCase(store.getDefaultUser()))
        {
            userLoginOn(store.getDefaultUser());

        }
        editText = (EditText) findViewById(R.id.editText);

        toManyPopupWindow = new PopManyWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        errorPopupWindow = new PopErrorWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.btn_buy:
                    {
                        //进行跳转
                        Log.e(TAG, "BUY BUTTON CLICKED.");

                    }
                        break;

                    default:
                        break;
                }
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


    public void userLoginOn(String userId) {
        //默认用户登录接口

        String deviceId = DeviceUtil.getDeviceId(this);
        JSONObject param = NetParamFactory.loginParam(userId, deviceId);

        AbstractNet net = new VerifyNet(loginHandler, param, NetConf.URL_LOGIN);
        Log.e(TAG, "USER LOGIN. URL" + NetConf.URL_LOGIN);
        progressDialog = ProgressDialog.show(this, "title", "loading");
        net.start();
        Log.e(TAG, param.toString());


    }


    //提交
    public void onLoginClick(View view){
        Log.i(TAG, "METHOD onLoginClick");

        LocalStore store = new LocalStore(this);

        //这里需要进行下注册。

//        userid = DeviceUtil.getPhoneNumber(this) == null ?  UUID.randomUUID().toString()  : DeviceUtil.getPhoneNumber(this) ;

        userid = editText.getText().toString();

        String userCode = editText.getText().toString();
        String deviceId = DeviceUtil.getDeviceId(this);
        JSONObject param = NetParamFactory.verifyParam(userid, deviceId, userCode);

        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_VERIFY);
        progressDialog = ProgressDialog.show(this, "title", "loading");
        net.start();
        Log.e(TAG, param.toString());

    }

    private String userid;
    //使用
    public void onTryButtonClicked(View view){
        Log.e(TAG, "METHOD onTryButtonClicked");


        Intent i = new Intent(this, TabActivity.class);
        startActivity(i);
        finish();
    }


    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            try {
                Bundle b = msg.getData();
                String str = b.getString("responseBody");
                Log.e(TAG, "returned code. " + str);
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {
                    JSONObject obj = new JSONObject(str);
                    int result = obj.getInt("result");
                    if (result == 0 ){
                        //激活码无效
                        errorPopupWindow.showAtLocation(findViewById(R.id.register_main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

                    } else if (result == 2) {
                        //绑定设备太多了
                        toManyPopupWindow.showAtLocation(findViewById(R.id.register_main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

                    } else if (result == 1) {
                        //正确
                        LocalStore store = new LocalStore(RegistActivity.this);
                        store.setDefaultUser(userid);

                        JSONObject data = obj.getJSONObject("data");
                        int usertype = data.getInt("type");
                        store.setUserType(usertype);

                        int userBind = data.getInt("binding");
                        if (userBind == 1) {
                            store.setUserBind(true);
                        }


                        Intent intent = new Intent(RegistActivity.this, TabActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    
                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(RegistActivity.this, "请检查网络设置。", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    Handler loginHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            try {
                Bundle b = msg.getData();
                String str = b.getString("responseBody");
                Log.e(TAG, "returned code. " + str);
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {
                    JSONObject obj = new JSONObject(str);
                    int result = obj.getInt("result");




                    if (result == 0 ){
                        //该用户不存在
                        Toast.makeText(RegistActivity.this, "该用户不存在", Toast.LENGTH_LONG).show();

                    } else if (result == 1) {
                        //正确

//                        JSONObject data = obj.getJSONObject("data");
//                        int usertype = data.getInt("type");
//                        LocalStore store = new LocalStore(RegistActivity.this);
//                        store.setUserType(usertype);

                        Intent intent = new Intent(RegistActivity.this, TabActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(RegistActivity.this, "请检查网络设置。", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
