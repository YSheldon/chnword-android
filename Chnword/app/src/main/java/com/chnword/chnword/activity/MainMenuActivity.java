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

        btn_scan = (ImageButton) findViewById(R.id.btn_scan);
        btn_study = (ImageButton) findViewById(R.id.btn_study);

        btn_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, "main menu activity. btn scan clicked.");
                LocalStore store = new LocalStore(MainMenuActivity.this);
                String user = store.getDefaultUser();
//                if ("0".equalsIgnoreCase(user)) {
//                    Log.e(TAG, "default user.");

                    Intent tabIntent = new Intent(MainMenuActivity.this, TabActivity.class);
                    startActivity(tabIntent);

//                } else {
//                    Log.e(TAG, "register user.");
//                }

            }
        });

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, "main menu activity. btn study clicked.");

                LocalStore store = new LocalStore(MainMenuActivity.this);
                String user = store.getDefaultUser();
//                if ("0".equalsIgnoreCase(user)) {
//                    Log.e(TAG, "default user.");
//                } else {
                    Log.e(TAG, "register user.");
                    Intent scanIntent = new Intent();
                    scanIntent.setClass(MainMenuActivity.this, MipcaActivityCapture.class);
                    scanIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(scanIntent, SCANNIN_GREQUEST_CODE);
//                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    //现请求查找index
                    LocalStore store = new LocalStore(this);
                    String userid = store.getDefaultUser();
                    String deviceId = DeviceUtil.getDeviceId(this);
                    String word = bundle.getString("result");

                    if (word != null && !"".equalsIgnoreCase(word)){
                        JSONObject param = NetParamFactory.wordParam(userid, deviceId, word);
                        io.vov.vitamio.utils.Log.e(TAG, param.toString());
                        AbstractNet net = new VerifyNet(wordHandler, param, NetConf.URL_WORD);
                        progressDialog = ProgressDialog.show(this, "title", "loading");
                        net.start();
                    } else {
                        //do nothing
                    }
                }
                break;
        }
    }

    private Handler wordHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            progressDialog.dismiss();
            try {
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {

                    Bundle b = msg.getData();
                    String str = b.getString("responseBody");
                    android.util.Log.e(TAG, str);
                    JSONObject obj = new JSONObject(str);

                    String result = obj.getString("result");
                    String message = obj.getString("message");
                    if (result != null && "1".equalsIgnoreCase(result)) {
//                        = obj.getJSONObject("data");

                        String dataString = obj.getString("data");
                        if (dataString != null && !"null".equalsIgnoreCase(dataString)) {
                            JSONObject objData = new JSONObject(dataString);

                            JSONArray word_names = objData.getJSONArray("word");
                            JSONArray word_indexs = objData.getJSONArray("unicode");

                            if (word_indexs.length() > 0 && word_names.length()> 0) {
                                String word = word_names.getString(0);
                                String word_index = word_indexs.getString(0);
                                Intent intent = new Intent(MainMenuActivity.this, ShowActivity.class);
                                intent.putExtra("word", word);
                                intent.putExtra("word_index", word_index);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainMenuActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainMenuActivity.this, "请搜索指定的汉字!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainMenuActivity.this, "网络请求失败，请检查网络!", Toast.LENGTH_LONG).show();
                    }

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(MainMenuActivity.this, "请求失败，请检查网络设置", Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    };
}
