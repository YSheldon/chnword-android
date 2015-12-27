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
import android.widget.Toast;

import com.chnword.chnword.R;
import com.chnword.chnword.adapter.FreecateAdapter;
import com.chnword.chnword.adapter.UsercateAdapter;
import com.chnword.chnword.beans.Category;
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
public class UsercateActivity extends Activity {
    private static final String TAG = UsercateActivity.class.getSimpleName();

    private View backImageButton;

    private Dialog progressDialog;

    private List<Category> list;

    private GridView usercateGride;
    private UsercateAdapter usercateAdapter;

    public static int [] locked = {R.drawable.kindfolder1_a, R.drawable.kindfolder2_a, R.drawable.kindfolder3_a,
            R.drawable.kindfolder4_a, R.drawable.kindfolder5_a, R.drawable.kindfolder6_a,
            R.drawable.kindfolder7_a, R.drawable.kindfolder8_a, R.drawable.kindfolder9_a,
            R.drawable.kindfolder10_a};

    public static int [] unlocked = {R.drawable.kindfolder1_b, R.drawable.kindfolder2_b, R.drawable.kindfolder3_b,
            R.drawable.kindfolder4_b, R.drawable.kindfolder5_b, R.drawable.kindfolder6_b,
            R.drawable.kindfolder7_b, R.drawable.kindfolder8_b, R.drawable.kindfolder9_b,
            R.drawable.kindfolder10_b};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercate);

        backImageButton = findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });

        usercateGride = (GridView) findViewById(R.id.usercateGride);

        list = new ArrayList<Category>();
        usercateAdapter = new UsercateAdapter(this, list);
        usercateGride.setAdapter(usercateAdapter);
        usercateGride.setOnItemClickListener(onItemClickListener);

        requestNet();
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

    private void requestNet() {
        LocalStore store = new LocalStore(this);
        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);
        JSONObject param = NetParamFactory.listParam(userid, deviceId, 0, 0);
        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_LIST);
        progressDialog = DialogUtil.createLoadingDialog(this, "数据加载中...");
        progressDialog.show();
        net.start();
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Category m = list.get(position);
            Log.e(TAG, m.getName() + " " + m.getCname());

//            Intent i = new Intent(UsercateActivity.this, UserwordActivity.class);
//            i.putExtra("ZoneCode", m.getCname());
//            i.putExtra("ZoneIndex", position);
//            i.putExtra("module_name", m.getName());
//            startActivity(i);

//            if (!m.isLock()) {
                Intent i = new Intent(UsercateActivity.this, UserwordActivity.class);
                i.putExtra("ZoneCode", m.getCname());
                i.putExtra("ZoneIndex", position);
                i.putExtra("module_name", m.getName());

                startActivity(i);
//            } else {
//                Toast.makeText(UsercateActivity.this, "请购买三字字课产品!", Toast.LENGTH_LONG).show();
//            }
        }
    };


    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            try {
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {
                    list.clear();
                    Bundle b = msg.getData();
                    String str = b.getString("responseBody");
                    Log.e(TAG, str);
                    JSONObject obj = new JSONObject(str);

                    JSONArray array = obj.getJSONArray("data");
                    Log.e(TAG, array.toString());
                    for (int i = 0; i < array.length(); i ++) {
                        JSONObject cateObj = array.getJSONObject(i);
                        Category category = new Category();
                        category.setName(cateObj.getString("name"));
                        category.setCname(cateObj.getString("cname"));

                        String isLockStr = cateObj.getString("lock");
//                        boolean isLock = "".equals(isLockStr) ? false : "1".equalsIgnoreCase(isLockStr);
                        boolean isLock = true;
                        if (isLockStr == null || "".equalsIgnoreCase(isLockStr)) {
                            isLock = true;
                        } else {
                            if (!"1".equalsIgnoreCase(isLockStr)) {
                                isLock = true;
                            } else {
                                isLock = false;
                            }
                        }

                        category.setLock(isLock);
                        if (isLock) {
                            category.setRid(locked[i]);
                        } else {
                            category.setRid(unlocked[i]);
                        }

                        list.add(category);
                    }
                    usercateAdapter.notifyDataSetChanged();

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(UsercateActivity.this, "请求失败，请检查网络设置", Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }

    };
}
