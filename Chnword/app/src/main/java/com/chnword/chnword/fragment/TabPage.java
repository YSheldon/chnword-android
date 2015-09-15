package com.chnword.chnword.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.chnword.chnword.R;
import com.chnword.chnword.activity.TabActivity;
import com.chnword.chnword.popwindow.PopScanWindow;
import com.chnword.chnword.store.LocalStore;
import com.chnword.zxingwapper.zxing.activity.MipcaActivityCapture;

/**
 * Created by khtc on 15/4/23.
 */
public class TabPage extends Fragment {

    private static final String TAG = TabPage.class.getSimpleName();


    private ImageButton btn_scan, btn_study;

    private final static int SCANNIN_GREQUEST_CODE = 1;//扫汉字
    private ProgressDialog progressDialog;          //弹出框

    private PopScanWindow popScanWindow;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragtab_page, container, false);

        btn_scan = (ImageButton) view.findViewById(R.id.btn_scan);
        btn_study = (ImageButton) view.findViewById(R.id.btn_study);

        btn_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, "main menu activity. btn scan clicked.");
                LocalStore store = new LocalStore(getActivity());
                String user = store.getDefaultUser();
//                if ("0".equalsIgnoreCase(user)) {
//                    Log.e(TAG, "default user.");

                Intent tabIntent = new Intent(getActivity(), TabActivity.class);
                getActivity().startActivity(tabIntent);


//                } else {
//                    Log.e(TAG, "register user.");
//                }

            }
        });


        popScanWindow = new PopScanWindow(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.btn_submit:

                        break;

                    case R.id.btn_card:

                        break;

                    default:
                        break;
                }
            }
        });


        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, "main menu activity. btn study clicked.");

                LocalStore store = new LocalStore(getActivity());
                String user = store.getDefaultUser();
                if ("0".equalsIgnoreCase(user)) {
                    Log.e(TAG, "default user.");

                    popScanWindow.showAtLocation(getActivity().findViewById(R.id.tab_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


                } else {
                    Log.e(TAG, "register user.");
                    Intent scanIntent = new Intent();
                    scanIntent.setClass(getActivity(), MipcaActivityCapture.class);
                    scanIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getActivity().startActivityForResult(scanIntent, SCANNIN_GREQUEST_CODE);
                }

            }
        });

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
