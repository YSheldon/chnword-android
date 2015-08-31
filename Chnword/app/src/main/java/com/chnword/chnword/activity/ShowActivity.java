package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Word;
import com.chnword.chnword.fragment.GifFragment;
import com.chnword.chnword.fragment.VideoFragment;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.store.LocalStore;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import org.json.JSONObject;

import io.vov.vitamio.LibsChecker;
import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by khtc on 15/5/17.
 */
public class ShowActivity extends Activity {
    private static final String TAG = ShowActivity.class.getSimpleName();

    private Word word ;
//    private GifImageButton gib;


    private Fragment fragment;

    private VideoFragment videoFragment;
    private GifFragment gifFragment;

    private FragmentManager manager;

    private Uri gifUri, videoUri;

    private LocalStore store;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        if (!LibsChecker.checkVitamioLibs(this))
            return;

        store = new LocalStore(this);

        Intent intent = getIntent();
        String word = intent.getStringExtra("word");
        String word_index = intent.getStringExtra("word_index");

        this.word = new Word();
        this.word.setWordIndex(word_index);
        this.word.setWord(word);

        videoFragment = new VideoFragment();
        gifFragment = new GifFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, gifFragment).commit();



        try {

            GifDrawable drawable = new GifDrawable(getResources(), R.drawable.sample);
            Log.e(TAG, "NUMBER COUNT " + drawable.getNumberOfFrames());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        requestNet();

    }

    private void requestNet() {

        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);;
        JSONObject param = NetParamFactory.showParam(userid, deviceId, word.getWordIndex());
        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_SHOW);
        progressDialog = ProgressDialog.show(this, "提示", "loading...");
        net.start();

    }

    private ProgressDialog progressDialog;

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            progressDialog = null;
            try {
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {

                    Bundle b = msg.getData();
                    String str = b.getString("responseBody");

                    JSONObject obj = new JSONObject(str);
                    JSONObject data = obj.getJSONObject("data");

                    String location = data.getString("gif");
                    gifUri = Uri.parse(location);

                    String video = data.getString("video");
                    videoUri = Uri.parse(video);
                    videoFragment.setUri(videoUri);
                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };



    //Action event handler

    /**
     * 处理分类按钮点击
     * @param view
     */
    public void onBackStage(View view) {
        onBackPressed();
        finish();
    }

    /**
     * 处理快览按钮点击
     * @param view
     */
    public void onQuickLook(View view) {
        //更换fargment

//        if (gifUri == null) {
//            return;
//        }

        gifFragment.setUri(gifUri);
        Log.e(TAG, "METHOD onQuickLook");

//        manager.beginTransaction().hide(videoFragment).replace(R.id.fragment, gifFragment).commit();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(videoFragment);
//        transaction.addToBackStack(null);
//        transaction.replace(R.id.fragment_container, gifFragment);
        transaction.add(R.id.fragment_container, gifFragment);
        transaction.commit();
        videoFragment.pause();
    }

    /**
     * 处理扫描按钮点击
     * @param view
     */
    public void onScan(View view) {
        //切换到scan页面
//        Intent scanIntent = new Intent(this, QRScanActivity.class);
//        startActivity(scanIntent);
//        finish();
    }


    public void onChangePosition(float position) {
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.remove(gifFragment);
        tx.show(videoFragment);
        tx.commit();
        videoFragment.onChangePosition(position);
        videoFragment.start();

    }

    //umeng
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    public void setUmeng() {
        // 设置分享内容
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
        // 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(this, "http://www.umeng.com/images/pic/banner_module_social.png"));

        // 设置分享图片，参数2为本地图片的资源引用
        mController.setShareMedia(new UMImage(this, R.drawable.logo1));
        // 设置分享图片，参数2为本地图片的路径(绝对路径)
//        mController.setShareMedia(new UMImage(this, BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));

        // 设置分享视频
        UMVideo umVideo = new UMVideo( "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
        // 设置视频缩略图
        umVideo.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
        umVideo.setTitle("友盟社会化分享!");
        mController.setShareMedia(umVideo);
//        mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);

        //添加微信和朋友圈
        String appId = "wx523e7fec6968506f";
        String appSecret = "4a01f28bf8671d6b5487094caaffc72e";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this, appId, appSecret);
        wxHandler.addToSocialSDK();
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        //添加qq的
        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1104685705",
                "TaZo5RPmrGX11nPO");
        qqSsoHandler.addToSocialSDK();

        //qq空间
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();

        //添加新浪的
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

    }

    public void onSharedButtonClicked() {
        // 是否只有已登录用户才能打开分享选择页
        mController.openShare(this, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }


    public void onShowVideo(View view) {
        Log.e(TAG, "METHOD onShowVideo");

        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra("videoUrl", "http://app.3000zi.com/upload/video/ebbb0cf8d6547612db98d061cf556baf.mp4");
        startActivity(intent);
    }

}
