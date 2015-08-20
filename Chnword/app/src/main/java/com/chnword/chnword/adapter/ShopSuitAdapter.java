package com.chnword.chnword.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.ShopItem;

import java.util.List;

/**
 * Created by khtc on 15/8/18.
 */
public class ShopSuitAdapter extends BaseAdapter {

    private Context mContext;
    private List<ShopItem> list;

    public ShopSuitAdapter(Context mContext, List<ShopItem> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public List<ShopItem> getList() {
        return list;
    }

    public void setList(List<ShopItem> list) {
        this.list = list;
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

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_shopitem, null);
        }

        TextView shopPrice = (TextView) convertView.findViewById(R.id.shopPrice);
        TextView shopTitle = (TextView) convertView.findViewById(R.id.shopTitle);
        ImageView shopImage = (ImageView) convertView.findViewById(R.id.shopImage);

        ShopItem item = list.get(position);

        shopPrice.setText(item.getPrice());
        shopTitle.setText(item.getTitle());
        shopImage.setImageResource(item.getImageIndex());

        return null;
    }
}
