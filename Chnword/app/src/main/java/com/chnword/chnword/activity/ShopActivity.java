package com.chnword.chnword.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
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

    private ImageButton cardZikeButton;
    private ImageButton cateZikeButton;
    private ImageButton allZikeButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

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

        cardZikeButton = (ImageButton) findViewById(R.id.cardZikeButton);
        cateZikeButton = (ImageButton) findViewById(R.id.cateZikeButton);
        allZikeButton = (ImageButton) findViewById(R.id.allZikeButton);

        allZikeButton.setSelected(true);
        allZikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allZikeButton.isSelected()) {
                    allZikeButton.setSelected(true);
                    cateZikeButton.setSelected(false);
                    cardZikeButton.setSelected(false);
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    fragmentTransaction.hide(cardzikeFragment);
                    fragmentTransaction.hide(catezikeFragment);
                    fragmentTransaction.show(allzikeFragment);
                    fragmentTransaction.commit();

                }
            }
        });

        cateZikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cateZikeButton.isSelected()) {
                    allZikeButton.setSelected(false);
                    cateZikeButton.setSelected(true);
                    cardZikeButton.setSelected(false);
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    fragmentTransaction.hide(cardzikeFragment);
                    fragmentTransaction.hide(catezikeFragment);
                    fragmentTransaction.show(allzikeFragment);
                    fragmentTransaction.commit();

                }
            }
        });

        cardZikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cardZikeButton.isSelected()) {
                    allZikeButton.setSelected(false);
                    cateZikeButton.setSelected(false);
                    cardZikeButton.setSelected(true);
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    fragmentTransaction.hide(cardzikeFragment);
                    fragmentTransaction.hide(catezikeFragment);
                    fragmentTransaction.show(allzikeFragment);
                    fragmentTransaction.commit();

                }
            }
        });

    }

}
