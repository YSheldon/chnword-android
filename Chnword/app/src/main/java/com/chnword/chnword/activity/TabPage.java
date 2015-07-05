package com.chnword.chnword.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chnword.chnword.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khtc on 15/4/23.
 */
public class TabPage extends Fragment {

    private TabActivityListener listener;
    private ListView gameListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragtab_page, container, false);





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



    public TabActivityListener getListener() {
        return listener;
    }

    public void setListener(TabActivityListener listener) {
        this.listener = listener;
    }


    //事件处理


    public void onPageAnim() {

        Intent i = new Intent(getActivity(), CategoryActivity.class);
        startActivity(i);
    }

    public void onPageScan(){

        Intent i = new Intent(getActivity(), ScanActivity.class);
        startActivity(i);
    }

}
