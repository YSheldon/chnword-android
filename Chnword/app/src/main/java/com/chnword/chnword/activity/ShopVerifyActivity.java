package com.chnword.chnword.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chnword.chnword.R;

import org.w3c.dom.Text;

/**
 * Created by khtc on 15/9/19.
 */
public class ShopVerifyActivity extends Activity {

    private ImageButton backImageButton;
    private ListView shoplistView;

    private RadioGroup payRadioGroup;
    private RadioButton payBywexin, payByZfb;

    private ImageButton submitButton;
    private TextView totalPriceTextView;


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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 15/9/20 添加微信或者支付宝的调用



            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
