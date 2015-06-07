package com.chnword.chnword;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
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
import android.widget.ListAdapter;
import android.widget.TextView;

import com.chnword.chnword.beans.Module;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.store.LocalStore;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khtc on 15/4/27.
 */
public class AnimActivity extends Activity {
    private static final String TAG = AnimActivity.class.getSimpleName();

    private GridView gridView;
    private List<Module> list;
    private ModuleListAdapter moduleListAdapter;
    private ProgressDialog progressDialog;

    private LocalStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        store = new LocalStore(this);

        list = new ArrayList<Module>();
        list.addAll(store.getDefaultModule());
        moduleListAdapter = new ModuleListAdapter(this);


        gridView = (GridView) findViewById(R.id.gridView3);
        gridView.setAdapter(moduleListAdapter);
        gridView.setOnItemClickListener(onItemClickListener);

    }

    @Override
    protected void onStart() {
        super.onStart();

        String userid = "userid";
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

        ModuleListAdapter(Context context)
        {
            mContext = context;

        }
        ModuleListAdapter(Context context, List<Module> list) {

            mContext = context;
            inflater  = LayoutInflater.from(mContext);

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

                inflater  = LayoutInflater.from(mContext);
                convertView = (View) inflater.inflate(R.layout.tab_page_item_grid, null);
            }
            TextView moduleName = (TextView) convertView.findViewById(R.id.module_name_tab);
            TextView isLock = (TextView) findViewById(R.id.isLock);
            Module m = (Module) getItem(position);

            Log.e(TAG, (moduleName == null) + " is " +
                    "" + m.getName());
            moduleName.setText(m.getName());

            if (store.isUnlockAll(store.getDefaultUser()) || store.getUnlockModels(store.getDefaultUser()).contains(m.getCname())) {
                isLock.setText("解锁");
            }


            return convertView;
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Module m = list.get(position);
            Log.e(TAG, m.getName() + " " + m.getCname());
            Intent i = new Intent(AnimActivity.this, ResultActivity.class);
            i.putExtra("ZoneCode", m.getCname());

            ArrayList<String> names = new ArrayList<String>();
            ArrayList<String> cnames = new ArrayList<String>();
            for (Module module : list) {
                names.add(module.getName());
                cnames.add(module.getCname());
            }

            i.putStringArrayListExtra("moduleName", names);
            i.putStringArrayListExtra("moduleCname", cnames);

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
                    JSONObject data = obj.getJSONObject("data");
                    JSONArray names = data.getJSONArray("name");
                    JSONArray cnames = data.getJSONArray("cname");

                    for(int i = 0; i < names.length(); i ++) {
                        String name = names.getString(i);
                        String cname = cnames.getString(i);
                        Module m = new Module();
                        m.setName(name);
                        m.setCname(cname);
                        list.add(m);
                    }
                    store.setDefaultModule(list);
                    moduleListAdapter.notifyDataSetChanged();

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    new AlertDialog.Builder(AnimActivity.this)
                            .setTitle("提示")
                            .setMessage("注册失败")
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


}
