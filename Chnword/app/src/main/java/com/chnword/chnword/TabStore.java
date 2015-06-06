package com.chnword.chnword;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chnword.chnword.beans.Module;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.store.LocalStore;

import org.json.JSONArray;
import org.json.JSONObject;

import io.vov.vitamio.utils.Device;

/**
 * Created by khtc on 15/4/23.
 */
public class TabStore extends Fragment {
    private static final String TAG = TabStore.class.getSimpleName();

    private EditText activeCode;
    private Button actiiveButton;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragtab_store, container, false);

        activeCode = (EditText) view.findViewById(R.id.activeCode);
        actiiveButton = (Button) view.findViewById(R.id.activeButton);

        actiiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActiveCodeSubmit(v);
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


    // UIAction Event handler
    public void onActiveCodeSubmit(View view) {

        String inputActiveCode = activeCode.getText().toString();
        //进项网络请求

        LocalStore store = new LocalStore(getActivity());
        String userid = store.getDefaultUser();
//        String devideId = DeviceUtil.getPhoneNumber(getActivity());
        String devideId = DeviceUtil.getDeviceId(getActivity());


        JSONObject param = NetParamFactory.verifyParam(userid, devideId, inputActiveCode, devideId);
        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_VERIFY);
        progressDialog = ProgressDialog.show(getActivity(), "title", "loading");
        net.start();

    }


    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            progressDialog.dismiss();
            try {
                Bundle b = msg.getData();
                String str = b.getString("responseBody");
                JSONObject obj = new JSONObject(str);

                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {

                    Log.e(TAG, str);

                    JSONObject data = obj.getJSONObject("data");
                    if (data != null) {
                        String  unlock_all = data.getString("unlock_all");

                        if ("0".equalsIgnoreCase(unlock_all)) {
                            //解锁全部
                            Toast.makeText(getActivity(), "unlock_all", Toast.LENGTH_LONG).show();
                        } else {
                            //部分解锁
                            JSONArray array = obj.getJSONArray("unlock_zone");
                            //输出unlock_zone,并做存储。
                            Toast.makeText(getActivity(), array.toString(), Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), obj.getString("message  " + str), Toast.LENGTH_LONG).show();
                    }

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(getActivity(), "注册失败，请检查网络连接", Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


}
