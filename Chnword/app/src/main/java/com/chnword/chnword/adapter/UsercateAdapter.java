package com.chnword.chnword.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Category;

import java.util.List;

/**
 * Created by khtc on 15/9/16.
 */
public class UsercateAdapter extends BaseAdapter {

    private static final String TAG = UsercateAdapter.class.getSimpleName();

    private Context mContext;
    private List<Category> categoryList;

    public UsercateAdapter(Context mContext, List<Category> categoryList) {
        this.mContext = mContext;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_usercate, null);
        }

        Category m = (Category) getItem(position);
        TextView moduleName = (TextView) convertView.findViewById(R.id.usercate_name);


        ImageView freecateImage = (ImageView) convertView.findViewById(R.id.usercate_image);
        freecateImage.setImageResource(m.getRid());

        Log.e(TAG, (moduleName == null) + " is " + "" + m.getName());

        moduleName.setText(m.getName());

        return convertView;
    }
}
