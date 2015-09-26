package com.chnword.chnword.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Category;
import com.chnword.chnword.beans.WordShare;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.util.List;

/**
 * Created by khtc on 15/9/26.
 */
public class ShareEditAdapter extends BaseAdapter {

    private static final String TAG = UsercateAdapter.class.getSimpleName();

    private Context mContext;
    private List<WordShare> categoryList;

    public ShareEditAdapter(Context mContext, List<WordShare> categoryList) {
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
            convertView = inflater.inflate(R.layout.item_share_edit, null);
        }


        WordShare word = (WordShare) getItem(position);

        RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.shared_radio);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.sharedWordImage);


        ImageLoader.getInstance().displayImage(word.getIconUrl(), imageView);


        return convertView;
    }

}
