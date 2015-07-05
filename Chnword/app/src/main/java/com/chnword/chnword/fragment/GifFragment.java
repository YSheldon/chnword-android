package com.chnword.chnword.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;

import com.chnword.chnword.R;
import com.chnword.chnword.activity.ShowActivity;
import com.chnword.chnword.adapter.ImageAdapter;
import com.chnword.chnword.gallery.GalleryFlow;
import com.chnword.chnword.utils.BitmapScaleDownUtil;

/**
 * Created by khtc on 15/5/31.
 */
public class GifFragment extends Fragment {

    private Context mContext;

    // 图片缩放倍率（相对屏幕尺寸的缩小倍率）
    public static final int SCALE_FACTOR = 8;

    // 图片间距（控制各图片之间的距离）
    private final int GALLERY_SPACING = -10;

    // 控件
    private GalleryFlow mGalleryFlow;

    private SeekBar seekBar;


    private Uri uri;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gif, container, false);



        mContext = getActivity().getApplicationContext();


        initGallery(view);



        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
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
    public void onDestroy() {
        super.onDestroy();
    }


    private void initGallery(View view)
    {
        // 图片ID
        int[] images = {
                R.drawable.bgmain,
                R.drawable.playbg,
                R.drawable.subbg01,
                R.drawable.subbg02,
                R.drawable.subbg03};
//        int[] images = {R.drawable.index};

        ImageAdapter adapter = new ImageAdapter(mContext, images);
        // 计算图片的宽高
        int[] dimension = BitmapScaleDownUtil.getScreenDimension(getActivity().getWindowManager().getDefaultDisplay());
        int imageWidth = dimension[0] / SCALE_FACTOR;
        int imageHeight = dimension[1] / SCALE_FACTOR;
        // 初始化图片
        adapter.createImages(imageWidth, imageHeight);

        // 设置Adapter，显示位置位于控件中间，这样使得左右均可"无限"滑动
        mGalleryFlow = (GalleryFlow) view.findViewById(R.id.gallery_flow_gif);
        mGalleryFlow.setSpacing(GALLERY_SPACING);
        mGalleryFlow.setAdapter(adapter);
        mGalleryFlow.setSelection(Integer.MAX_VALUE / 2);

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        seekBar.setMax(images.length);
        seekBar.setProgress(0);

        //添加事件

        mGalleryFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seekBar.setProgress(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mGalleryFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("GIFFRAGMENT", "ON GIF VIEW CLICK.");
                ShowActivity activity = (ShowActivity) getActivity();
                //todo 转换并执行position
                activity.onChangePosition(position/mGalleryFlow.getAdapter().getCount());
            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mGalleryFlow.setSelection(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // getter and setter

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
