package com.chnword.chnword.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.chnword.chnword.R;
import com.chnword.chnword.fragment.AllzikeFragment;
import com.chnword.chnword.fragment.CardzikeFragment;
import com.chnword.chnword.fragment.CatezikeFragment;
import com.chnword.chnword.view.SegmentedRadioGroup;

/**
 * Created by khtc on 15/9/15.
 */
public class ShopActivity extends FragmentActivity {

    private FrameLayout shop_container;
    private FragmentManager manager;

    private CardzikeFragment cardzikeFragment;
    private CatezikeFragment catezikeFragment;
    private AllzikeFragment allzikeFragment;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragtab_store);


        shop_container = (FrameLayout) findViewById(R.id.shop_container);

        manager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        cardzikeFragment = new CardzikeFragment();
        catezikeFragment = new CatezikeFragment();
        allzikeFragment = new AllzikeFragment();

        fragmentTransaction.add(R.id.shop_container, cardzikeFragment);
        fragmentTransaction.add(R.id.shop_container, catezikeFragment);
        fragmentTransaction.add(R.id.shop_container, allzikeFragment);
        fragmentTransaction.hide(cardzikeFragment);
        fragmentTransaction.hide(catezikeFragment);
        fragmentTransaction.commit();


        SegmentedRadioGroup group = (SegmentedRadioGroup) findViewById(R.id.segment_text);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.button_one:
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.hide(cardzikeFragment);
                        fragmentTransaction.hide(catezikeFragment);
                        fragmentTransaction.show(allzikeFragment);
                        fragmentTransaction.commit();
                        break;

                    case R.id.button_two:
                        FragmentTransaction fragmentTransaction1 = manager.beginTransaction();
                        fragmentTransaction1.hide(allzikeFragment);
                        fragmentTransaction1.hide(cardzikeFragment);
                        fragmentTransaction1.show(catezikeFragment);
                        fragmentTransaction1.commit();
                        break;

                    case R.id.button_three:
                        FragmentTransaction fragmentTransaction2 = manager.beginTransaction();
                        fragmentTransaction2.hide(allzikeFragment);
                        fragmentTransaction2.hide(catezikeFragment);
                        fragmentTransaction2.show(cardzikeFragment);
                        fragmentTransaction2.commit();
                        break;

                    default:
                        break;
                }
            }
        });
    }

}
