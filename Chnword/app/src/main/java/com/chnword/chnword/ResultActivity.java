package com.chnword.chnword;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

import com.chnword.chnword.beans.Module;
import com.chnword.chnword.beans.Word;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

/**
 * Created by khtc on 15/4/27.
 */
public class ResultActivity extends Activity {
    private static final String TAG = ResultActivity.class.getSimpleName();

    private String zoneCode;

    private ProgressDialog progressDialog;

    private ListView listView;
    private GridView grideView;
    private List<Module> moduleList;
    private List<Word> wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        zoneCode = b.getString("ZoneCode");

        ArrayList<String> names = b.getStringArrayList("moduleName");
        ArrayList<String> cnames = b.getStringArrayList("moduleCname");




        moduleList = new ArrayList<Module>();
        wordList = new ArrayList<Word>();

        for (int i = 0; i < names.size(); i ++) {
            Module m = new Module();
            m.setName(names.get(i));
            m.setCname(cnames.get(i));
            moduleList.add(m);
            Log.e(TAG, m.getName() + " " + m.getCname());
        }


        listView = (ListView)findViewById(R.id.moduleList);
        listView.setAdapter(moduleAdapter);
        listView.setOnItemClickListener(moduleOnItemListener);
        grideView = (GridView) findViewById(R.id.wordGrid);
        grideView.setAdapter(wordAdapter);
        grideView.setOnItemClickListener(wordOnItemListener);


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
            Module m = moduleList.get(position);
            requestNet(m.getCname());
        }
    };


    private AdapterView.OnItemClickListener wordOnItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Word word = wordList.get(position);
            Intent intent = new Intent(ResultActivity.this, ShowActivity.class);
            startActivity(intent);
        }
    };

    private BaseAdapter moduleAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return moduleList.size();
        }

        @Override
        public Object getItem(int position) {
            return moduleList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(ResultActivity.this);
                convertView = inflater.inflate(R.layout.item_module_sublist, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.module_name_item);
            textView.setText(moduleList.get(position).getName());

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
                LayoutInflater inflater = LayoutInflater.from(ResultActivity.this);
                convertView = inflater.inflate(R.layout.item_word_sublista, null);
            }

            TextView word = (TextView) convertView.findViewById(R.id.word_aname);

            Log.e(TAG, wordList.get(position).getWord());

            word.setText(wordList.get(position).getWord());

            return convertView;
        }
    };

}
