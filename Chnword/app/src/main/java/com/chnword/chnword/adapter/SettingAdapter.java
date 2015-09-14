package com.chnword.chnword.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.TabuserItem;

import java.util.List;

/**
 * Created by khtc on 15/8/27.
 */
public class SettingAdapter extends BaseAdapter {

    private Context mContext;
    private List<TabuserItem> infos;

    public SettingAdapter(Context context, List<TabuserItem> info)
    {
        this.mContext = context;
        this.infos = info;
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_setting, null);
        }

        TabuserItem item = infos.get(position);

        TextView textView = (TextView) convertView.findViewById(R.id.settingLabel);
        textView.setText(item.getTitle());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.settingImg);
        imageView.setImageResource(item.getResourceId());

        return convertView;
    }
}
