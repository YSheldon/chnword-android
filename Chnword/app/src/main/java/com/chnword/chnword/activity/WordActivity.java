package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Module;
import com.chnword.chnword.beans.Word;
import com.chnword.chnword.fragment.ModuleFragment;
import com.chnword.chnword.fragment.ScanResultFragment;
import com.chnword.chnword.fragment.WordFragment;
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
 * Created by khtc on 15/6/16.
 */
public class WordActivity extends Activity {
    private static final String TAG = WordActivity.class.getSimpleName();

    ModuleFragment moduleFragment;
    ScanResultFragment scanResultFragment;
    WordFragment wordFragment;

    private List<Module> modules = new ArrayList<Module>();
    private List<Word> words = new ArrayList<Word>();

    private ProgressDialog progressDialog;

    private LocalStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        moduleFragment = new ModuleFragment();
        scanResultFragment = new ScanResultFragment();
        wordFragment = new WordFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.word_fragment_container, moduleFragment);
        transaction.add(R.id.word_fragment_container, wordFragment);
        transaction.add(R.id.word_fragment_container, scanResultFragment);
        transaction.hide(scanResultFragment);
        transaction.hide(wordFragment);
        transaction.commit();

        //请求数据
        store = new LocalStore(this);

        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);
        JSONObject param = NetParamFactory.listParam(userid, deviceId, 0, 0);
        AbstractNet net = new VerifyNet(moduleHandler, param, NetConf.URL_LIST);
        progressDialog = ProgressDialog.show(this, "title", "loading");
        net.start();
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


    public void showModule() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(wordFragment);
        transaction.hide(scanResultFragment);
        transaction.show(moduleFragment);
        transaction.commit();
    }

    public void showWord(Module module) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(moduleFragment);
        transaction.hide(scanResultFragment);
        transaction.show(wordFragment);
        transaction.commit();

        //请求网络数据  sublist
        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);
        JSONObject param = NetParamFactory.subListParam(userid, deviceId, module.getCname(), 0, 0);
        AbstractNet net = new VerifyNet(wordResultHandler, param, NetConf.URL_SUBLIST);
        progressDialog = ProgressDialog.show(this, "title", "loading");
        net.start();

    }

    public void showScanResult() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(wordFragment);
        transaction.hide(moduleFragment);
        transaction.show(scanResultFragment);
        transaction.commit();
    }







    public List<Module> getModules() {
        return modules;
    }

    public List<Word> getWords() {
        return words;
    }




    Handler moduleHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            try {
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {
                    modules.clear();
                    Bundle b = msg.getData();
                    String str = b.getString("responseBody");
                    Log.e(TAG, str);
                    JSONObject obj = new JSONObject(str);
                    JSONObject data = obj.getJSONObject("data");
                    JSONArray names = data.getJSONArray("name");
                    JSONArray cnames = data.getJSONArray("cname");

                    for(int i = 0; i < names.length(); i ++) {
                        String name = names.getString(i);
                        String cname = cnames.getString(i);
                        Module m = new Module();
                        m.setName(name);
                        m.setCname(cname);
                        modules.add(m);
                    }
                    Log.e(TAG, modules.size() + "");
                    store.setDefaultModule(modules);
//                    moduleListAdapter.notifyDataSetChanged();
                    moduleFragment.updateData();

                    //todo 发起网络请求，请求第一项的二级菜单。

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    new AlertDialog.Builder(WordActivity.this)
                            .setTitle("提示")
                            .setMessage("网络请求失败，请检查网络。")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    Handler wordResultHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            progressDialog = null;
            try {
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {
                    words.clear();
                    Bundle b = msg.getData();
                    String str = b.getString("responseBody");
                    Log.e(TAG, str);
                    JSONObject obj = new JSONObject(str);
                    JSONObject data = obj.getJSONObject("data");

                    if (data != null){
                        JSONArray word = data.getJSONArray("word");
                        JSONArray unicodes = data.getJSONArray("unicode");

                        for(int i = 0; i < word.length(); i ++) {
                            String wordt = word.getString(i);
                            String unicode = unicodes.getString(i);
                            Word w = new Word();
                            w.setWord(wordt);
                            w.setWordIndex(unicode);

                            words.add(w);
                        }

//
//                        wordAdapter.notifyDataSetChanged();
                        wordFragment.updateData();
                    }


                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
