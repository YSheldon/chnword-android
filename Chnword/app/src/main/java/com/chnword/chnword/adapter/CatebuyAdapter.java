package com.chnword.chnword.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.CateBuyItem;
import com.chnword.chnword.beans.Category;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by khtc on 15/9/19.
 */
public class CatebuyAdapter extends BaseAdapter {

    private static final String TAG = CategoryAdapter.class.getSimpleName();

    private Context mContext;
    private List<CateBuyItem> categoryList;

    public CatebuyAdapter(Context mContext, List<CateBuyItem> categoryList) {
        this.mContext = mContext;
        this.categoryList = categoryList;
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(mContext);
        ImageLoader.getInstance().init(configuration);
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
            convertView = inflater.inflate(R.layout.item_catebuy, null);
        }

        CateBuyItem item = (CateBuyItem) getItem(position);

        TextView cateNameTextView = (TextView) convertView.findViewById(R.id.cateNameTextView);
        TextView catePriceTextView = (TextView) convertView.findViewById(R.id.catePriceTextView);
        ImageView cateImageView = (ImageView) convertView.findViewById(R.id.cateImageView);
        ImageView cateCheckView = (ImageView) convertView.findViewById(R.id.cateCheckView);

        cateNameTextView.setText(item.getName());
        catePriceTextView.setText(item.getPriceString());


        if (item.getIconUrl() != null) {
            ImageLoader.getInstance().displayImage(item.getIconUrl(), cateImageView); // imageUrl代表图片的URL地址，imageView代表承载图片的IMAGEVIEW控件
        } else {
            cateImageView.setImageResource(item.getResourceId());
        }


        if (item.isChecked()) {
            cateCheckView.setImageResource(R.drawable.choose_y);
        } else {
            cateCheckView.setImageResource(R.drawable.choose_n);
        }

        return convertView;
    }
}
