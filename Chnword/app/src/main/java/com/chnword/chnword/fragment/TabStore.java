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


        SegmentedRadioGroup group = (SegmentedRadioGroup) view.findViewById(R.id.segment_text);
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
