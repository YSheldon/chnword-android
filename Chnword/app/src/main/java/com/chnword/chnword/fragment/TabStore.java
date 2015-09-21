package com.chnword.chnword.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.chnword.chnword.R;
import com.chnword.chnword.view.SegmentedRadioGroup;

/**
 * Created by khtc on 15/4/23.
 */
public class TabStore extends Fragment {
    private static final String TAG = TabStore.class.getSimpleName();


    private FrameLayout shop_container;
    private FragmentManager manager;

    private CardzikeFragment cardzikeFragment;
    private CatezikeFragment catezikeFragment;
    private AllzikeFragment allzikeFragment;

    private ImageButton allZikeButton, cateZikeButton, cardZikeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragtab_store, container, false);

        shop_container = (FrameLayout) view.findViewById(R.id.shop_container);

        manager = getActivity().getSupportFragmentManager();

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

        allZikeButton = (ImageButton) view.findViewById(R.id.allZikeButton);
        cateZikeButton = (ImageButton) view.findViewById(R.id.cateZikeButton);
        cardZikeButton = (ImageButton) view.findViewById(R.id.cardZikeButton);

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
                    allZikeButton.setSelected(true);
                    cateZikeButton.setSelected(false);
                    cardZikeButton.setSelected(false);

                    FragmentTransaction fragmentTransaction1 = manager.beginTransaction();
                    fragmentTransaction1.hide(allzikeFragment);
                    fragmentTransaction1.hide(cardzikeFragment);
                    fragmentTransaction1.show(catezikeFragment);
                    fragmentTransaction1.commit();

                }
            }
        });

        cardZikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cardZikeButton.isSelected()) {
                    allZikeButton.setSelected(true);
                    cateZikeButton.setSelected(false);
                    cardZikeButton.setSelected(true);

                    FragmentTransaction fragmentTransaction2 = manager.beginTransaction();
                    fragmentTransaction2.hide(allzikeFragment);
                    fragmentTransaction2.hide(catezikeFragment);
                    fragmentTransaction2.show(cardzikeFragment);
                    fragmentTransaction2.commit();
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
