package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

import io.vov.vitamio.utils.Log;


/**
 * Created by khtc on 15/8/27.
 */
public class FeedbackActivity extends Activity {

    private static final String TAG = FeedbackActivity.class.getSimpleName();

    private TextView feedtextinfo;
    private EditText feedbacktext, phoneNumber;

    private ProgressDialog progressDialog;


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedtextinfo = (TextView) findViewById(R.id.feedtextinfo);
        feedbacktext = (EditText) findViewById(R.id.feedbackText);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);


        feedbacktext.addTextChangedListener(new TextWatcher() {

            private CharSequence temp;//监听前的文本

            private int editStart;//光标开始位置
            private int editEnd;//光标结束位置
            private final int charMaxNum = 200;



            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                feedtextinfo.setText(s.length() + "/" + charMaxNum);
            }

            @Override
            public void afterTextChanged(Editable s) {

                editStart = feedbacktext.getSelectionStart();
                editEnd = feedbacktext.getSelectionEnd();
                if (temp.length() > charMaxNum) {
//                    Toast.makeText(getApplicationContext(), "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();
                    s.delete(editStart - 1, editEnd);
                    int tempSelection = editStart;
                    feedbacktext.setText(s);
                    feedbacktext.setSelection(tempSelection);
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    public void onSubmit(View view) {
        Log.e(TAG, "ON SUBMIT");
        LocalStore store = new LocalStore(this);

        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);
        String content = this.feedbacktext.getText().toString();
        String contact = this.phoneNumber.getText().toString();
        JSONObject param = NetParamFactory.feedbackParam(userid, deviceId, content, contact);
        Log.e(TAG, param.toString());
        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_FEEDBACK);
        progressDialog = ProgressDialog.show(this, "title", "loading");
        net.start();

    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            progressDialog.dismiss();
            try {
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {
                    Toast.makeText(FeedbackActivity.this, "感谢您的宝贵意见，工作人员会及时处理", Toast.LENGTH_LONG).show();
                    //
//                    Bundle b = msg.getData();
//                    String str = b.getString("responseBody");
//                    android.util.Log.e(TAG, str);
                    finish();

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(FeedbackActivity.this, "请求失败，请检查网络设置", Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            super.handleMessage(msg);
        }
    };
}
