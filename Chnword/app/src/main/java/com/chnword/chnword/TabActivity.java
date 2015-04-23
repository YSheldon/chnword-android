package com.chnword.chnword;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        mViewPager = (ViewPager) findViewById(R.id.main_tab);

        mFragments.add(new TabPage());
        mFragments.add(new TabStore());
        mFragments.add(new TabUser());



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

    class ViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return false;
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
}
