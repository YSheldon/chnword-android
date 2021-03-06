package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chnword.chnword.R;
import com.chnword.chnword.adapter.WordAdapter;
import com.chnword.chnword.beans.Word;
import com.chnword.chnword.dialogs.DialogUtil;
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

/**
 * Created by khtc on 15/9/15.
 */
public class UserwordActivity extends Activity {
    private static final String TAG = UserwordActivity.class.getSimpleName();

    private View backImageButton;

    String zoneCode = "";
    String title = "";
    int index = 0;

    private TextView titleTextView;
    private GridView userwordGridView;

    private LocalStore store;
    private Dialog progressDialog;
    private List<Word> wordList;

    private WordAdapter wordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userword);

        backImageButton = findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        store = new LocalStore(this);

        zoneCode = b.getString("ZoneCode");
        index = b.getInt("ZoneIndex");

        title = b.getString("module_name", "");
        if ("".equalsIgnoreCase(title)){
            Log.e(TAG, "NO TITLE, BACK");
            finish();
        }
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText(title);

        userwordGridView = (GridView) findViewById(R.id.userwordGridView);
        wordList = new ArrayList<Word>();
        wordAdapter = new WordAdapter(this, wordList);
        userwordGridView.setAdapter(wordAdapter);
        userwordGridView.setOnItemClickListener(wordOnItemListener);

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
        progressDialog = DialogUtil.createLoadingDialog(this, "数据加载中...");
        progressDialog.show();
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
                    wordList.clear();
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
                            Log.e(TAG, word.getWord() + " " + word.getWordIndex());
                            wordList.add(word);
                        }
                        wordAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(UserwordActivity.this, "服务器无数据返回", Toast.LENGTH_LONG).show();
                    }
                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(UserwordActivity.this, "请选择网络", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private AdapterView.OnItemClickListener wordOnItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Word word = wordList.get(position);
            Intent intent = new Intent(UserwordActivity.this, ShowActivity.class);

            intent.putExtra("word", word.getWord());
            intent.putExtra("word_index", word.getWordIndex());

            startActivity(intent);
        }
    };
}
