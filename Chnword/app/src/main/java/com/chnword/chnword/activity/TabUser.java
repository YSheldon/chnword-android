package com.chnword.chnword.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.chnword.chnword.R;
import com.chnword.chnword.adapter.SettingAdapter;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.store.LocalStore;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by khtc on 15/4/23.
 */
public class TabUser extends Fragment {

    private EditText registPhoneNumber;

    private ListView settingListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragtab_user, container, false);


//        registPhoneNumber = (EditText) view.findViewById(R.id.registPhoneNumber);

        LocalStore store = new LocalStore(getActivity());

//        webView.loadUrl("file:///android_asset/guide.html");

        settingListView = (ListView) view.findViewById(R.id.settingListView);


        List<String> info = new ArrayList<String>();
        info.add("会员特权");
        info.add("建议反馈");
        info.add("用户指引");
        info.add("邀请好友");
        info.add("关于");

        SettingAdapter adapter = new SettingAdapter(this.getActivity(), info);
        settingListView.setAdapter(adapter);
        settingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:

                        //显示会员特权
                        Intent intent1 = new Intent(getActivity(), WebActivity.class);
                        intent1.putExtra("title", "会员特权");
                        intent1.putExtra("url", NetConf.URL_MEMBER);
                        startActivity(intent1);


                        break;

                    case 1:

                        //显示建议反馈的界面。
                        Intent feedback = new Intent(getActivity(), FeedbackActivity.class);
                        startActivity(feedback);

                        break;

                    case 2:

                        //显示用户指引
                        Intent guideIntent = new Intent(getActivity(), GuideActivity.class);
                        guideIntent.putExtra("flag", "flag");
                        startActivity(guideIntent);

                        break;

                    case 3:

                        //进行分享
                        ((TabActivity)getActivity()).setUmeng();

                        break;

                    case 4:

                        //关于
                        Intent intent2 = new Intent(getActivity(), WebActivity.class);
                        intent2.putExtra("title", "关于");
                        intent2.putExtra("url", NetConf.URL_ABOUT);
                        startActivity(intent2);

                        break;

                    default:
                        break;
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
