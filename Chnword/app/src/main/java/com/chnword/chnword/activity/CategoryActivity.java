package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khtc on 15/4/27.
 */
public class CategoryActivity extends Activity {
    private static final String TAG = CategoryActivity.class.getSimpleName();

    private GridView gridView;
    private List<Category> list;
    private ModuleListAdapter moduleListAdapter;
    private ProgressDialog progressDialog;

    private LocalStore store;

    public static int [] categorys ; //{R.drawable.category01, R.drawable.category02, R.drawable.category03,
//                        R.drawable.category04, R.drawable.category05, R.drawable.category06,
//                        R.drawable.category07, R.drawable.category08, R.drawable.category09,
//                        R.drawable.category10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        store = new LocalStore(this);

        list = new ArrayList<Category>();
        list.addAll(store.getDefaultModule());
        moduleListAdapter = new ModuleListAdapter(this);

        int[] ints = {2, 3};

        gridView = (GridView) findViewById(R.id.gridView3);
        gridView.setAdapter(moduleListAdapter);
        gridView.setOnItemClickListener(onItemClickListener);

    }

    @Override
    protected void onStart() {
        super.onStart();

        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);
        JSONObject param = NetParamFactory.listParam(userid, deviceId, 0, 0);
        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_LIST);
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

    class ModuleListAdapter extends BaseAdapter {
        private Context mContext;
        LayoutInflater inflater;
//        private List<Category> list;

        ModuleListAdapter(Context context)
        {
            mContext = context;
            inflater  = LayoutInflater.from(mContext);
        }
        ModuleListAdapter(Context context, List<Category> list) {

            mContext = context;
            inflater  = LayoutInflater.from(mContext);
//            this.list = list;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null){
                convertView = (View) inflater.inflate(R.layout.item_category, null);
            }
            TextView moduleName = (TextView) convertView.findViewById(R.id.module_name_tab);
            Category m = (Category) getItem(position);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.module_image);
            imageView.setImageResource(categorys[position % 10]);

            Log.e(TAG, (moduleName == null) + " is " +
                    "" + m.getName());
            moduleName.setText(m.getName());


            return convertView;
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Category m = list.get(position);
            Log.e(TAG, m.getName() + " " + m.getCname());
            Intent i = new Intent(CategoryActivity.this, WordActivity.class);
            i.putExtra("ZoneCode", m.getCname());
            i.putExtra("ZoneIndex", position);

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
                        boolean isLock = "".equals(isLockStr) ? false : "1".equalsIgnoreCase(isLockStr);
                        category.setLock(isLock);

                        list.add(category);
                    }
//                    store.setDefaultModule(list);
                    moduleListAdapter.notifyDataSetChanged();

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(CategoryActivity.this, "请求失败，请检查网络设置", Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }

    };


}
