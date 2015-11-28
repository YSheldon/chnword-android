package com.chnword.chnword.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.chnword.chnword.activity.RegistActivity;
import com.chnword.chnword.activity.TabActivity;
import com.chnword.chnword.dialogs.DialogUtil;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.store.LocalStore;

import org.json.JSONObject;

/**
 * Created by khtc on 15/11/21.
 */
public class LoginServices extends Service {

    private static final String TAG = LoginServices.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "LOGIN SERVICES.");
        LocalStore store = new LocalStore(this);
        if (!"0".equalsIgnoreCase(store.getDefaultUser()))
        {
            userLoginOn(store.getDefaultUser());

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void userLoginOn(String userId) {
        //默认用户登录接口
        Log.e(TAG, "LOGIN SERVICES. " + userId);
        String deviceId = DeviceUtil.getDeviceId(this);
        JSONObject param = NetParamFactory.loginParam(userId, deviceId);

        AbstractNet net = new VerifyNet(loginHandler, param, NetConf.URL_LOGIN);
        Log.e(TAG, "USER LOGIN. URL" + NetConf.URL_LOGIN);
        net.start();
        Log.e(TAG, param.toString());


    }

    Handler loginHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            try {
                Bundle b = msg.getData();
                String str = b.getString("responseBody");
                Log.e(TAG, "returned code. " + str);
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {
                    JSONObject obj = new JSONObject(str);
//                    int result = obj.getInt("result");

                    String result = obj.getString("result");



                    if ("0".equalsIgnoreCase(result)){
                        //该用户不存在
                        Toast.makeText(LoginServices.this, "请重新登录", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(LoginServices.this, TabActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(intent);

                    } else if ("1".equalsIgnoreCase(result)) {
                        //正确

                    }

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Intent intent = new Intent(LoginServices.this, RegistActivity.class);
                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
