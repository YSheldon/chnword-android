package com.chnword.chnword;


import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khtc on 15/4/23.
 */
public class TabActivity extends FragmentActivity {

    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private ViewPager.OnPageChangeListener mPageChangeListener;

    private ImageView tab1, tab2, tab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        mViewPager = (ViewPager) findViewById(R.id.main_tab);

        mFragments.add(new TabPage());
        mFragments.add(new TabStore());
        mFragments.add(new TabUser());

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new ViewPagerAdapter(fm, mFragments));

        tab1 = (ImageView) findViewById(R.id.tab_page);
        tab2 = (ImageView) findViewById(R.id.tab_store);
        tab3 = (ImageView) findViewById(R.id.tab_user);
        tab1.setOnClickListener(new BarItemOnClickListener(0));
        tab2.setOnClickListener(new BarItemOnClickListener(1));
        tab3.setOnClickListener(new BarItemOnClickListener(2));


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{

        List<Fragment> items;

        ViewPagerAdapter(FragmentManager fm,List<Fragment> list){
            super(fm);
            this.items = list;
        }

        @Override
        public Fragment getItem(int i) {
            return items.get(i);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }

    class ViewPagerChanger implements ViewPager.OnPageChangeListener{


        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int index) {

//            for (int i = 0; i < mdots.size(); i ++){
//                if (i == index){
//                    mdots.get(i).setImageResource(R.drawable.page_indicator_focused);
//                }else {
//                    mdots.get(i).setImageResource(R.drawable.page_indicator_unfocused);
//                }
//            }


        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    class BarItemOnClickListener implements View.OnClickListener{

        private int index;
        BarItemOnClickListener(int i){
            this.index = i;
        }

        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(index);
        }
    }
}
