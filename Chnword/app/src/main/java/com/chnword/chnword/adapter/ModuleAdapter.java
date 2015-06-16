package com.chnword.chnword.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Module;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by khtc on 15/6/16.
 */
public class ModuleAdapter extends BaseAdapter {

    private Context mContext;
    private List<Module> moduleList;

    public ModuleAdapter(Context mContext, List<Module> moduleList) {
        this.mContext = mContext;
        this.moduleList = moduleList;
    }

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
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.element_module, null);
        }

        return convertView;
    }
}
