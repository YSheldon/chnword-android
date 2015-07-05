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

    private ListView listView;
    private GridView grideView;
    private List<Category> categoryList;
    private List<Word> wordList;
    private TextView word_tip ;
    private LocalStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        categoryList = new ArrayList<Category>();
        wordList = new ArrayList<Word>();
        store = new LocalStore(this);
        word_tip = (TextView) findViewById(R.id.result_tip);

        if (b.getBoolean("ISFROMSEARCH", false)) {

            String searchResult = b.getString("SEARCH_RESULT");
            String wordTip = b.getString("WORD_TIP");
            ArrayList<String> names = b.getStringArrayList("WORD_NAME");
            ArrayList<String> indexs = b.getStringArrayList("WORD_INDEX");

            categoryList.addAll(store.getDefaultModule());

            for (int i = 0; i < names.size(); i ++) {
                Word w = new Word();
                w.setWord(names.get(i));
                w.setWordIndex(indexs.get(i));
                wordList.add(w);

            }

            word_tip.setText(wordTip);


        }else {
            zoneCode = b.getString("ZoneCode");

            ArrayList<String> names = b.getStringArrayList("moduleName");
            ArrayList<String> cnames = b.getStringArrayList("moduleCname");





            boolean isOk = false;
            for (int i = 0; i < names.size(); i ++) {
                Category m = new Category();
                m.setName(names.get(i));
                m.setCname(cnames.get(i));
                categoryList.add(m);
                Log.e(TAG, m.getName() + " " + m.getCname());
                isOk = true;
            }
            if (!isOk) {
                categoryList.addAll(store.getDefaultModule());
            }
        }






        wordList.addAll(store.getDefaultWord(zoneCode));

//        listView = (ListView)findViewById(R.id.moduleList);
//        listView.setAdapter(moduleAdapter);
//        listView.setOnItemClickListener(moduleOnItemListener);
//        grideView = (GridView) findViewById(R.id.wordGrid);
//        grideView.setAdapter(wordAdapter);
//        grideView.setOnItemClickListener(wordOnItemListener);


    }

    @Override
    protected void onStart() {
        super.onStart();

        requestNet(zoneCode);
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


    private void requestNet(String zoneCode) {
        String userid = "userid";
        String deviceId = DeviceUtil.getDeviceId(this);
        JSONObject param = NetParamFactory.subListParam(userid, deviceId, zoneCode, 0, 0);
        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_SUBLIST);
        progressDialog = ProgressDialog.show(this, "title", "loading");
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
                    JSONObject data = obj.getJSONObject("data");

                    if (data != null){
                        JSONArray words = data.getJSONArray("word");
                        JSONArray unicodes = data.getJSONArray("unicode");

                        for(int i = 0; i < words.length(); i ++) {
                            String word = words.getString(i);
                            String unicode = unicodes.getString(i);
                            Word w = new Word();
                            w.setWord(word);
                            w.setWordIndex(unicode);
                            wordList.add(w);
                        }

//
                        wordAdapter.notifyDataSetChanged();
                    }

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };



    private AdapterView.OnItemClickListener moduleOnItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Category m = categoryList.get(position);

            store.getUnlockModels(store.getDefaultUser());
            if (store.isUnlockAll(store.getDefaultUser())
                    ||
                    store.getUnlockModels(store.getDefaultUser()).contains(m.getCname())) {

                requestNet(m.getCname());
            } else {
                Toast.makeText(WordActivity.this, "未解锁", Toast.LENGTH_LONG).show();
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

//            Intent galleryIntent = new Intent(ResultActivity.this, Gallerty3DActivity.class);
//            startActivity(galleryIntent);
        }
    };

    private BaseAdapter moduleAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return categoryList.size();
        }

        @Override
        public Object getItem(int position) {
            return categoryList.get(position);
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

            TextView textView = (TextView) convertView.findViewById(R.id.word_aname);
            textView.setText(categoryList.get(position).getName());

            return convertView;
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
