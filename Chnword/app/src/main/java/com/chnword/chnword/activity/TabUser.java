package com.chnword.chnword.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.chnword.chnword.R;
import com.chnword.chnword.store.LocalStore;

import java.io.File;

/**
 * Created by khtc on 15/4/23.
 */
public class TabUser extends Fragment {

    private WebView webView;

    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragtab_user, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
        textView = (TextView) view.findViewById(R.id.tab_user_code);

        LocalStore store = new LocalStore(getActivity());
        textView.setText(store.getDefaultUser());

//        File f = getActivity().getAssets();
        //todo 看是否可用
        webView.loadUrl("file:///android_asset/guide.html");


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
