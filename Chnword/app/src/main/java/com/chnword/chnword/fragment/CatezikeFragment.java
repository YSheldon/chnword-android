package com.chnword.chnword.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chnword.chnword.R;

/**
 * Created by khtc on 15/9/15.
 */
public class CatezikeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_catezike, container, false);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
