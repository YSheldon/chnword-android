package com.chnword.chnword.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Category;

import java.util.List;

/**
 * Created by khtc on 15/6/16.
 */
public class CategoryAdapter extends BaseAdapter {
    private static final String TAG = CategoryAdapter.class.getSimpleName();

    private Context mContext;
    private List<Category> categoryList;

    public CategoryAdapter(Context mContext, List<Category> categoryList) {
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
            convertView = inflater.inflate(R.layout.item_category, null);
        }

        TextView moduleName = (TextView) convertView.findViewById(R.id.module_name_tab);
//        TextView isLock = (TextView) convertView.findViewById(R.id.isLock);
        Category m = (Category) getItem(position);

        Log.e(TAG, (moduleName == null) + " is " + "" + m.getName());

        moduleName.setText(m.getName());

        return convertView;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
