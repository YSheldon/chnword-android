package com.chnword.chnword.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chnword.chnword.R;
import com.chnword.chnword.adapter.CatebuyAdapter;
import com.chnword.chnword.adapter.VerifyAdapter;
import com.chnword.chnword.beans.CateBuyItem;
import com.chnword.chnword.beans.CateBuyer;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by khtc on 15/9/19.
 */
public class ShopVerifyActivity extends Activity {
    private static final String TAG = ShopVerifyActivity.class.getSimpleName();

    private ImageButton backImageButton;
    private ListView shoplistView;

    private RadioGroup payRadioGroup;
    private RadioButton payBywexin, payByZfb;

    private ImageButton submitButton;
    private TextView totalPriceTextView;

    List<CateBuyItem> buyed = new ArrayList<CateBuyItem>();
    private VerifyAdapter adapter;
    private CateBuyer buyer = new CateBuyer(0);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopverify);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });

        payRadioGroup = (RadioGroup) findViewById(R.id.payRadioGroup);
        payBywexin = (RadioButton) findViewById(R.id.payBywexin);
        payByZfb = (RadioButton) findViewById(R.id.payByZfb);

        submitButton = (ImageButton) findViewById(R.id.submitButton);
        totalPriceTextView = (TextView) findViewById(R.id.totalPriceTextView);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        Parcelable[] parcelables = bundle.getParcelableArray("BUYITEMS");

        for (int i = 0; i < parcelables.length; i ++) {
            CateBuyItem item = (CateBuyItem) parcelables[i];
            buyed.add(item);
            buyer.add(item);
//            Log.e("ShopVerifyActivity", parcelables[i] + "");
        }

        shoplistView = (ListView) findViewById(R.id.shoplistView);
        adapter = new VerifyAdapter(this, buyed);
        shoplistView.setAdapter(adapter);
        totalPriceTextView.setText(buyer.getPriceText());

        payBywexin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                payByZfb.setChecked(!isChecked);

            }
        });

        payByZfb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                payBywexin.setChecked(!isChecked);

            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 15/9/20 添加微信或者支付宝的调用

                if (payBywexin.isChecked()) {
                    Log.e(TAG, "payBywexin CHECKED");
                }

                if (payByZfb.isChecked()) {
                    Log.e(TAG, "payBywexin CHECKED");
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
