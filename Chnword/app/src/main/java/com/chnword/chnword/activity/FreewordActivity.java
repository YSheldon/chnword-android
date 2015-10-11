package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Word;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.popwindow.SharePopWindow;
import com.chnword.chnword.store.LocalStore;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khtc on 15/9/15.
 */
public class FreewordActivity extends Activity {
    private static final String TAG = FreewordActivity.class.getSimpleName();

    private ImageButton backImageButton;
    private ProgressDialog progressDialog;
    private Word currentWord;
    private LocalStore store;

    String zoneCode = "";
    String title = "";
    int index = 0;
    int resourceIndex = 0;  //模块的下标

    private TextView titleTextView;
    private ImageView wordImageView;
    private TextView modulNameTextView;

    private LinearLayout freewordTop;//draw图片
    private LinearLayout bottomLinear;//mid图片
    private LinearLayout freeTopLinear;//top big图片


    private static int [] draws = {R.drawable.draw_1,
            R.drawable.draw_2,
            R.drawable.draw_3,
            R.drawable.draw_4,
            R.drawable.draw_5,
            R.drawable.draw_6,
            R.drawable.draw_7,
            R.drawable.draw_8,
            R.drawable.draw_9,
            R.drawable.draw_10};

    private static int[] tops = {R.drawable.topbg_1,
            R.drawable.topbg_2,
            R.drawable.topbg_3,
            R.drawable.topbg_4,
            R.drawable.topbg_5,
            R.drawable.topbg_6,
            R.drawable.topbg_7,
            R.drawable.topbg_8,
            R.drawable.topbg_9,
            R.drawable.topbg_10};

    private static int[] mids = {R.drawable.midbg_1,
            R.drawable.midbg_2,
            R.drawable.midbg_3,
            R.drawable.midbg_4,
            R.drawable.midbg_5,
            R.drawable.midbg_6,
            R.drawable.midbg_7,
            R.drawable.midbg_8,
            R.drawable.midbg_9,
            R.drawable.midbg_10};


    SharePopWindow shareWindow;
    String url = "";

    private TextView freewordCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeword);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });

        wordImageView = (ImageView) findViewById(R.id.wordImageView);
        modulNameTextView = (TextView) findViewById(R.id.modulNameTextView);

        freewordTop = (LinearLayout) findViewById(R.id.freewordTop);
        bottomLinear = (LinearLayout) findViewById(R.id.bottomLinear);
        freeTopLinear = (LinearLayout) findViewById(R.id.freeTopLinear);

        freewordCode = (TextView) findViewById(R.id.freewordCode);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        store = new LocalStore(this);

        zoneCode = b.getString("ZoneCode");
        index = b.getInt("ZoneIndex");

        title = b.getString("module_name", "");
        resourceIndex = b.getInt("module_index", 0);
        if ("".equalsIgnoreCase(title)){
            Log.e(TAG, "NO TITLE, BACK");
            finish();
        }



        //
        modulNameTextView.setText(title);

        freewordTop.setBackgroundResource(draws[resourceIndex]);
        bottomLinear.setBackgroundResource(mids[resourceIndex]);
        freeTopLinear.setBackgroundResource(tops[resourceIndex]);

        shareWindow = new SharePopWindow(this);

        titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText(title);


        requestNet(zoneCode);

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

    private void requestNet(String zoneCode) {

        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);
        JSONObject param = NetParamFactory.subListParam(userid, deviceId, zoneCode, 0, 0);
        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_SUBLIST);
        progressDialog = ProgressDialog.show(this, "提示", "loading...");
        net.start();

    }

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            progressDialog = null;
            try {
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {

                    Bundle b = msg.getData();
                    String str = b.getString("responseBody");
                    Log.e(TAG, str);
                    JSONObject obj = new JSONObject(str);
                    if (!obj.isNull("data")){

                        JSONArray wordArray = obj.getJSONArray("data");
                        for (int i = 0; i < wordArray.length(); i ++) {
                            JSONObject wordObj = wordArray.getJSONObject(i);
                            Word word = new Word();
                            word.setWord(wordObj.getString("word"));
                            word.setWordIndex(wordObj.getString("unicode"));

                            if ("0".equalsIgnoreCase(wordObj.getString("free"))) {
                                currentWord = word;
                                url = wordObj.getString("icon");
                                freewordCode.setText(currentWord.getWord());
                                break;
                            }
                        }

//                        ImageLoader imageLoader = ImageLoader.getInstance();
//                        imageLoader.displayImage(url, wordImageView);

                    } else {
                        Toast.makeText(FreewordActivity.this, "服务器无数据返回", Toast.LENGTH_LONG).show();
                    }

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * 用户点击分享按钮
     * @param v
     *
     */
    public void onShareButtonClicked(View v) {
        View view = findViewById(R.id.freeword_main);
        shareWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 用户点击获取用户码按钮
     * @param view
     */
    public void onShopButtonClicked(View view) {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }


}
