package com.chnword.chnword;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.chnword.chnword.beans.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khtc on 15/4/27.
 */
public class AnimActivity extends Activity {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        gridView = (GridView) findViewById(R.id.gridView3);
        gridView.setAdapter(new ModuleListAdapter(this));

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
        private List<Module> list;
        LayoutInflater inflater;

        ModuleListAdapter(Context context)
        {
            mContext = context;
            this.list = new ArrayList<Module>();
            inflater  = LayoutInflater.from(mContext);

            for (int i = 0; i < 20; i ++){
                Module m = new Module();
                m.setName("模块" + i);
                m.setLock(false);
            }
        }
        ModuleListAdapter(Context context, List<Module> list) {

            mContext = context;
            this.list = list;
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

                convertView = (View) inflater.inflate(R.layout.tab_page_item_grid, null);
            }

            return convertView;
        }
    }
}
