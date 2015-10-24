package com.chnword.chnword.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chnword.chnword.R;
import com.chnword.chnword.activity.ShopVerifyActivity;
import com.chnword.chnword.adapter.CatebuyAdapter;
import com.chnword.chnword.beans.CateBuyItem;
import com.chnword.chnword.beans.CateBuyer;
import com.chnword.chnword.beans.Category;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.store.LocalStore;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khtc on 15/9/15.
 */
public class CatezikeFragment extends Fragment {
    private static final String TAG = CatezikeFragment.class.getSimpleName();

    private TextView priceTextView;
    private ImageButton catebuyButton;
    private List<CateBuyItem> lists;
    private ListView catebuyListView;
    private CatebuyAdapter adapter;

    private CateBuyer buyer = new CateBuyer(0);

    ProgressDialog progressDialog;
    private boolean shouldRequestNet = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_catezike, container, false);

        priceTextView = (TextView)view.findViewById(R.id.priceTextView);
        catebuyButton = (ImageButton)view.findViewById(R.id.cateBuyButton);
        catebuyListView = (ListView) view.findViewById(R.id.catebuyListView);

        lists = new ArrayList<CateBuyItem>();
//        preparedata();

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
                priceTextView.setText(buyer.getPriceText() + "");
                adapter.notifyDataSetChanged();
            }
        });


        catebuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                progressDialog.show();
                //循环，找出checkedItem,

                List<CateBuyItem> buyed = new ArrayList<CateBuyItem>();

                for (int i = 0; i < lists.size(); i ++ ) {
                    CateBuyItem item = lists.get(i);
                    if (item.isChecked()) {
                        buyed.add(item);
                    }
                }

                if (buyed.size() < 1) {
                    Toast.makeText(getActivity(), "请选择要购买的字课.", Toast.LENGTH_LONG).show();
                    return;
                }

                CateBuyItem[] parcelables = new CateBuyItem[buyed.size()];
                for (int i = 0; i < buyed.size(); i ++) {
                    parcelables[i] = buyed.get(i);
                }

                Intent intent = new Intent(getActivity(), ShopVerifyActivity.class);
                Bundle bundle = new Bundle();

                bundle.putParcelableArray("BUYITEMS", parcelables);
                intent.putExtra("bundle", bundle);

                getActivity().startActivity(intent);

            }
        });





        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (shouldRequestNet) {
            requestNet();
        }
    }

    private void requestNet() {
        LocalStore store = new LocalStore(getActivity());
        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(getActivity());
        JSONObject param = NetParamFactory.listParam(userid, deviceId, 0, 0);
        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_SHOPLIST);
        progressDialog = ProgressDialog.show(getActivity(), "title", "loading");
        net.start();
    }
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            try {
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {
                    lists.clear();
                    buyer.reset();
                    shouldRequestNet = false;
                    Bundle b = msg.getData();
                    String str = b.getString("responseBody");
                    Log.e(TAG, str);
                    JSONObject obj = new JSONObject(str);

                    JSONArray array = obj.getJSONArray("data");
                    Log.e(TAG, array.toString());
                    for (int i = 0; i < array.length(); i ++) {
                        JSONObject cateObj = array.getJSONObject(i);

                        CateBuyItem category = new CateBuyItem();

                        String id = cateObj.getString("id");
                        String icon = cateObj.getString("icon");
                        String price = cateObj.getString("price");
                        String sort = cateObj.getString("sort");
                        String cname = cateObj.getString("cname");
                        String name = cateObj.getString("name");

                        category.setName(name);
                        category.setPriceString(price);

                        if ("".equalsIgnoreCase(price)) {
                            category.setPrice(0);
                        } else {
                            try {
                                category.setPrice(Float.parseFloat(price));
                            } catch (Exception e) {
                                e.printStackTrace();
                                category.setPrice(0);
                            }
                        }


//                        category.setResourceId();
                        category.setIconUrl(icon);

                        lists.add(category);
                    }
                    adapter.notifyDataSetChanged();
                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(getActivity(), "请求失败，请检查网络设置", Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }

    };

    private void preparedata() {

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


    }
}
