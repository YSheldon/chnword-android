package com.chnword.chnword.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chnword.chnword.R;
import com.chnword.chnword.activity.ShopVerifyActivity;
import com.chnword.chnword.adapter.CatebuyAdapter;
import com.chnword.chnword.beans.CateBuyItem;
import com.chnword.chnword.beans.CateBuyer;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khtc on 15/9/15.
 */
public class CatezikeFragment extends Fragment {

    private TextView priceTextView;
    private ImageButton catebuyButton;
    private List<CateBuyItem> lists;
    private ListView catebuyListView;
    private CatebuyAdapter adapter;

    private CateBuyer buyer = new CateBuyer(0);

    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_catezike, container, false);

        priceTextView = (TextView)view.findViewById(R.id.priceTextView);
        catebuyButton = (ImageButton)view.findViewById(R.id.cateBuyButton);
        catebuyListView = (ListView) view.findViewById(R.id.catebuyListView);

        progressDialog = ProgressDialog.show(getActivity(), "title", "loading");

        preparedata();
        adapter = new CatebuyAdapter(getActivity(), lists);
        catebuyListView.setAdapter(adapter);
        catebuyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CateBuyItem item = lists.get(position);
                if (item.isChecked()) {
                    item.setIsChecked(false);
                } else {
                    item.setIsChecked(true);
                }
                item.calcute(buyer);
            }
        });


        catebuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                //循环，找出checkedItem,
                Intent intent = new Intent(getActivity(), ShopVerifyActivity.class);
                getActivity().startActivity(intent);

            }
        });





        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void preparedata() {
        lists = new ArrayList<CateBuyItem>();

        CateBuyItem item;

        item = new CateBuyItem("天文篇", 18.0f, "￥18.0", false, R.drawable.sm_folder1);
        lists.add(item);

        item = new CateBuyItem("地理篇", 18.0f, "￥18.0", false, R.drawable.sm_folder2);
        lists.add(item);

        item = new CateBuyItem("动物篇", 18.0f, "￥18.0", false, R.drawable.sm_folder3);
        lists.add(item);

        item = new CateBuyItem("植物篇", 18.0f, "￥18.0", false, R.drawable.sm_folder4);
        lists.add(item);

        item = new CateBuyItem("人姿篇", 18.0f, "￥18.0", false, R.drawable.sm_folder5);
        lists.add(item);

        item = new CateBuyItem("身体篇", 18.0f, "￥18.0", false, R.drawable.sm_folder6);
        lists.add(item);

        item = new CateBuyItem("生理篇", 18.0f, "￥18.0", false, R.drawable.sm_folder7);
        lists.add(item);

        item = new CateBuyItem("生活篇", 18.0f, "￥18.0", false, R.drawable.sm_folder8);
        lists.add(item);

        item = new CateBuyItem("活动篇", 18.0f, "￥18.0", false, R.drawable.sm_folder9);
        lists.add(item);

        item = new CateBuyItem("文化篇", 18.0f, "￥18.0", false, R.drawable.sm_folder10);
        lists.add(item);

        lists.add(item);


    }
}
