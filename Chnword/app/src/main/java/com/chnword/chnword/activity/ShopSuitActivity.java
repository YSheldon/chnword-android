package com.chnword.chnword.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chnword.chnword.R;
import com.chnword.chnword.adapter.ShopSuitAdapter;
import com.chnword.chnword.beans.ShopItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 家庭版套装
 * Created by khtc on 15/7/1.
 */
public class ShopSuitActivity extends Activity {

    private static final String TAG = ShopSuitActivity.class.getSimpleName();

    private ListView shopListView;
    private List<ShopItem> shopItemList;
    private ShopSuitAdapter shopSuitAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_suit);
        // item item_shop_suit
        Log.e(TAG, "METHOD ONCREATE");

        shopListView = (ListView) findViewById(R.id.shopListView);
        shopItemList = new ArrayList<ShopItem>();
        initShopItemList();
        shopSuitAdapter = new ShopSuitAdapter(this, shopItemList);

        shopListView.setAdapter(shopSuitAdapter);
        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo 添加事件处理
                Log.e(TAG, shopItemList.get(position).toString() + ", postion: " + position);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private String[] titles = {"天文篇完整版", "地理篇完整版", "植物篇完整版", "动物篇完整版", "人姿篇完整版",
            "身体篇完整版","生理篇完整版", "生活篇完整版", "活动篇完整版", "文化篇完整版"
    };

    private String[] prices = {
            "￥ 20.00", "￥ 20.00", "￥ 20.00", "￥ 20.00", "￥ 20.00",
            "￥ 20.00", "￥ 20.00", "￥ 20.00", "￥ 20.00", "￥ 20.00",
    };

    private int[] images = {
            R.drawable.folder01, R.drawable.folder02, R.drawable.folder03, R.drawable.folder04, R.drawable.folder05,
            R.drawable.folder06, R.drawable.folder07, R.drawable.folder08, R.drawable.folder09, R.drawable.folder10
    };

    private void initShopItemList() {
        for (int i = 0; i < 10; i ++) {
            ShopItem shopItem = new ShopItem();

            shopItem.setTitle(titles[i]);
            shopItem.setImageIndex(images[i]);
            shopItem.setPrice(prices[i]);

            shopItemList.add(shopItem);
        }
    }
}
