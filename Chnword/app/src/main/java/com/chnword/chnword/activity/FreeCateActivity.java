package com.chnword.chnword.activity;

import android.app.Activity;
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
import com.chnword.chnword.beans.Category;
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
public class FreeCateActivity extends Activity {
    private static final String TAG = FreeCateActivity.class.getSimpleName();

    private ImageButton backImageButton;
    private GridView freecateGrid;
    private ProgressDialog progressDialog;

    private FreecateAdapter freecateAdapter;
    private List<Category> list;

    public static int [] kinds = {R.drawable.kind1, R.drawable.kind2, R.drawable.kind3,
            R.drawable.kind4, R.drawable.kind5, R.drawable.kind6,
            R.drawable.kind7, R.drawable.kind8, R.drawable.kind9,
            R.drawable.kind10};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freecate);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });

        freecateGrid = (GridView) findViewById(R.id.freecateGrid);

        list = new ArrayList<Category>();
        freecateAdapter = new FreecateAdapter(this, list);
        freecateGrid.setAdapter(freecateAdapter);
        freecateGrid.setOnItemClickListener(onItemClickListener);

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
        progressDialog = ProgressDialog.show(this, "title", "loading");
        net.start();
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Category m = list.get(position);
            Log.e(TAG, m.getName() + " " + m.getCname());

            Intent i = new Intent(FreeCateActivity.this, FreewordActivity.class);
            i.putExtra("ZoneCode", m.getCname());
            i.putExtra("ZoneIndex", position);
            i.putExtra("module_name", m.getName());

            startActivity(i);
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
//                        boolean isLock = cateObj.isNull("lock") ? true : cateObj.getInt("lock") == 1;
                        boolean isLock = "".equals(isLockStr) ? false : "1".equalsIgnoreCase(isLockStr);
                        category.setLock(isLock);
                        category.setRid(kinds[i]);

                        list.add(category);
                    }
                    freecateAdapter.notifyDataSetChanged();

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(FreeCateActivity.this, "请求失败，请检查网络设置", Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }

    };

}
