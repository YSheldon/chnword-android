package com.chnword.chnword.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.chnword.chnword.R;
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
                R.drawable.index,
                R.drawable.index_back,
                R.drawable.item_home,
                R.drawable.item_shop,
                R.drawable.item_user,
                R.drawable.sample,
                R.drawable.scan_box };
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
    }

    // getter and setter

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
