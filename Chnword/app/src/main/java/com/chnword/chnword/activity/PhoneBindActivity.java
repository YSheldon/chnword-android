package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Category;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.store.LocalStore;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by khtc on 15/9/14.
 */
public class PhoneBindActivity extends Activity {
    private static final String TAG = PhoneBindActivity.class.getSimpleName();

    private ImageButton backImageButton;

    private EditText phoneNumber;
    private EditText verifyNumber;

    private ImageButton sendButton, verifyButton;

    private ProgressDialog progressDialog;

    private String sessionId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebind);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        verifyNumber = (EditText) findViewById(R.id.verifyNumber);

        sendButton = (ImageButton) findViewById(R.id.sendButton);
        verifyButton = (ImageButton) findViewById(R.id.verifyButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButtonClicked();
            }
        });

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyButtonClicked();
            }
        });

        verifyButton.setEnabled(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void sendButtonClicked() {
        requestYzm();
    }

    private void verifyButtonClicked() {
        verifyYzm();
    }


    private void requestYzm() {
        LocalStore store = new LocalStore(this);
        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);
        String tel = phoneNumber.getText().toString();
        JSONObject param = NetParamFactory.yzmParam(userid, deviceId, tel);
        AbstractNet net = new VerifyNet(yzmHandler, param, NetConf.URL_SEND);
        progressDialog = ProgressDialog.show(this, "title", "loading");
        net.start();
    }


    private void verifyYzm() {

        LocalStore store = new LocalStore(this);
        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);
        JSONObject param = NetParamFactory.verifyParam(userid, deviceId, 0, 0);
        AbstractNet net = new VerifyNet(verifyHandler, param, NetConf.URL_BIND);
        progressDialog = ProgressDialog.show(this, "title", "loading");
        net.start();
    }

    Handler yzmHandler = new Handler(){

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

                    int result = obj.getInt("result");
                    if (result == 0 ){
                        //短信服务异常
                        Toast.makeText(PhoneBindActivity.this, "短信服务异常", Toast.LENGTH_LONG).show();
                        return;

                    } else if (result == 1) {
                        phoneNumber.setEnabled(false);//设置不可用
                        sendButton.setEnabled(false);

                        verifyButton.setEnabled(true);

                    } else if (result == -1) {
                        //手机号有误
                        Toast.makeText(PhoneBindActivity.this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                        return;

                    }

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(PhoneBindActivity.this, "请求失败，请检查网络设置", Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }

    };

    Handler verifyHandler = new Handler(){

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

                    JSONArray array = obj.getJSONArray("data");
                    Log.e(TAG, array.toString());
                    for (int i = 0; i < array.length(); i ++) {
                        JSONObject cateObj = array.getJSONObject(i);
                        Category category = new Category();
                        category.setName(cateObj.getString("name"));
                        category.setCname(cateObj.getString("cname"));

                        String isLockStr = cateObj.getString("lock");
                        boolean isLock = "".equals(isLockStr) ? false : "1".equalsIgnoreCase(isLockStr);
                        category.setLock(isLock);

                    }

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(PhoneBindActivity.this, "请求失败，请检查网络设置", Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }

    };
}
