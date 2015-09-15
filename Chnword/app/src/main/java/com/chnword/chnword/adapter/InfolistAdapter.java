package com.chnword.chnword.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chnword.chnword.R;

import java.util.List;

/**
 * Created by khtc on 15/9/15.
 */
public class InfolistAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mLists;

    public InfolistAdapter(Context context, List<String> lists) {
        this.mContext = context;
        this.mLists = lists;
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_infolist, null);
        }

        TextView info_text = (TextView) convertView.findViewById(R.id.info_text);
        info_text.setText(mLists.get(position));


        return convertView;
    }
}
