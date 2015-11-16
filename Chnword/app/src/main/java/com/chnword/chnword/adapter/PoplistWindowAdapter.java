package com.chnword.chnword.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chnword.chnword.R;

import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.utils.StringUtils;

/**
 * Created by khtc on 15/11/16.
 */
public class PoplistWindowAdapter extends BaseAdapter {

    private static List<String> strs = new ArrayList<String>();
    static {
        strs.add("在线产品订购");
        strs.add("三千字课特点");
        strs.add("会员尊享权益");
        strs.add("知识创富计划");
        strs.add("关于中聿华源");
    }

    private Context mContext;
    public PoplistWindowAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return strs.size();
    }

    @Override
    public Object getItem(int position) {
        return strs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_pop_list, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.popItemText);
        textView.setText(strs.get(position));

        return convertView;
    }
}
