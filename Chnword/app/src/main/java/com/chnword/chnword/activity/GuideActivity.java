package com.chnword.chnword.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chnword.chnword.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khtc on 15/4/23.
 */
public class GuideActivity extends Activity {


    private List<View> mGuides = new ArrayList<View>();
    private List<ImageView> mdots = new ArrayList<ImageView>();

    private ViewPager mViewPager;
    private PagerAdapter mPageAdapter;
    private ViewPager.OnPageChangeListener mPageChangerLister;

    private int lastIndex = 0;

    private boolean isFromSetting = false;//是否从setting界面过来



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String str = getIntent().getExtras().getString("flag", "");
            if ("flag".equalsIgnoreCase(str)) {
                isFromSetting = true;
            }
        }





        mViewPager = (ViewPager) findViewById(R.id.view_page);

        LayoutInflater inflater = LayoutInflater.from(this);

        ImageView dot1 = (ImageView) findViewById(R.id.item_dot1);
        ImageView dot2 = (ImageView) findViewById(R.id.item_dot2);
        ImageView dot3 = (ImageView) findViewById(R.id.item_dot3);
        ImageView dot4 = (ImageView) findViewById(R.id.item_dot4);

        mdots.add(dot1);
        mdots.add(dot2);
        mdots.add(dot3);
        mdots.add(dot4);

        View item1 = (View) inflater.inflate(R.layout.guide_item1, null);
        View item2 = (View) inflater.inflate(R.layout.guide_item2, null);
        View item3 = (View) inflater.inflate(R.layout.guide_item3, null);
        View item4 = (View) inflater.inflate(R.layout.guide_item4, null);

        mGuides.add(item1);
        mGuides.add(item2);
        mGuides.add(item3);
        mGuides.add(item4);

//        mViewPager.addView(item1);
//        mViewPager.addView(item2);

        mPageAdapter = new ViewPagerAdapter(mGuides);
        mPageChangerLister = new ViewPagerChanger();

        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOnPageChangeListener(mPageChangerLister);
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

    public void onButtonClick(View v){

        if (isFromSetting) {
            finish();
        } else {
            Intent i = new Intent(this, RegistActivity.class);
            startActivity(i);
            finish();
        }
    }





    class ViewPagerAdapter extends PagerAdapter{

        private List<View> mListViews;

        ViewPagerAdapter(List<View> lists){
            mListViews = lists;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)   {
            container.removeView(mListViews.get(position));//删除页卡
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡

            if (position == mListViews.size()) {
                onButtonClick(null);
            } else {
                container.addView(mListViews.get(position), 0);//添加页卡
                return mListViews.get(position);
            }
            return null;

        }

        @Override
        public int getCount() {
            return  mListViews.size() + 1;//返回页卡的数量
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;//官方提示这样写
        }



    }

    class ViewPagerChanger implements ViewPager.OnPageChangeListener{


        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int index) {

            for (int i = 0; i < mdots.size(); i ++){
                if (i == index){
                    mdots.get(i).setImageResource(R.drawable.pageindicatorfocused);
                }else {
                    mdots.get(i).setImageResource(R.drawable.pageindicatorunfocused);
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}
