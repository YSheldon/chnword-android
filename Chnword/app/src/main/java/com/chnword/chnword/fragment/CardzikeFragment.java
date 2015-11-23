package com.chnword.chnword.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.chnword.chnword.R;
import com.chnword.chnword.activity.WebActivity;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.store.LocalStore;

/**
 * Created by khtc on 15/9/15.
 */
public class CardzikeFragment extends Fragment{

    private ImageButton orderBuyButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cardzike, container, false);

        orderBuyButton = (ImageButton) view.findViewById(R.id.orderBuyButton);
        orderBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转
                LocalStore store = new LocalStore(getActivity());
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra(WebActivity.URL_KEY, NetConf.URL_SHOPLIST + "?userid=" + store.getDefaultUser());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
