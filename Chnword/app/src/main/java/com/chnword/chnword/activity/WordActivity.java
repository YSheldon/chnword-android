package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Category;
import com.chnword.chnword.beans.Word;
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
 * Created by khtc on 15/4/27.
 */
public class WordActivity extends Activity {
    private static final String TAG = WordActivity.class.getSimpleName();

    private String zoneCode;

    private ProgressDialog progressDialog;

    private GridView grideView;
    private List<Word> wordList;
    private LocalStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        wordList = new ArrayList<Word>();
        store = new LocalStore(this);

        zoneCode = b.getString("ZoneCode");

    }

    @Override
    protected void onStart() {
        super.onStart();

        requestNet(zoneCode);
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
                    wordList.clear();
                    Bundle b = msg.getData();
                    String str = b.getString("responseBody");
                    Log.e(TAG, str);
                    JSONObject obj = new JSONObject(str);
                    JSONArray wordArray = obj.getJSONArray("data");
                    for (int i = 0; i < wordArray.length(); i ++) {
                        JSONObject wordObj = wordArray.getJSONObject(i);
                        Word word = new Word();
                        word.setWord(wordObj.getString("word"));
                        word.setWordIndex(wordObj.getString("unicode"));
                        wordList.add(word);
                    }
                    wordAdapter.notifyDataSetChanged();


                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {

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
            Intent intent = new Intent(WordActivity.this, ShowActivity.class);

            intent.putExtra("word", word.getWord());
            intent.putExtra("word_index", word.getWordIndex());

            startActivity(intent);
        }
    };



    private BaseAdapter wordAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return wordList.size();
        }

        @Override
        public Object getItem(int position) {
            return wordList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(WordActivity.this);
                convertView = inflater.inflate(R.layout.item_word, null);
            }

            TextView word = (TextView) convertView.findViewById(R.id.word_aname);

            Log.e(TAG, wordList.get(position).getWord());

            word.setText(wordList.get(position).getWord());

            return convertView;
        }
    };

}
