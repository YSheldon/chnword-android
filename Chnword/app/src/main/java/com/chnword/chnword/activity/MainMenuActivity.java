package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chnword.chnword.R;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.store.LocalStore;
import com.chnword.zxingwapper.zxing.activity.MipcaActivityCapture;
import com.umeng.socialize.sso.UMSsoHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import io.vov.vitamio.utils.Log;

/**
 * Created by khtc on 15/9/13.
 */
public class MainMenuActivity extends Activity {
    private static final String TAG = MainMenuActivity.class.getSimpleName();

    private ImageButton btn_scan, btn_study;

    private final static int SCANNIN_GREQUEST_CODE = 1;//扫汉字
    private ProgressDialog progressDialog;          //弹出框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
