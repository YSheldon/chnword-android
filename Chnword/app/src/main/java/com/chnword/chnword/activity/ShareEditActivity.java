package com.chnword.chnword.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chnword.chnword.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import io.vov.vitamio.utils.Log;

/**
 * Created by khtc on 15/9/17.
 */
public class ShareEditActivity extends Activity {

    private static final String TAG = ShareEditActivity.class.getSimpleName();

    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    private SHARE_MEDIA mediaType;

    private ImageButton backImageButton;
    private ImageButton sharedButton;
    private EditText shareText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_edit);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String type = bundle.getString("share_type");
        if (type == null || "".equalsIgnoreCase(type))
        {
            Log.e(TAG, "INVALIDAGE TYPE.");
            finish();
        }

        mediaType = SHARE_MEDIA.convertToEmun(type);

        shareText = (EditText) findViewById(R.id.shareText);


        sharedButton = (ImageButton) findViewById(R.id.sharedButton);
        sharedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 进行分享

                // 设置分享内容
                mController.setShareContent("我正在使用三千字，非常适合你，推荐给你吧。http://app.3000zi.com/web/download.php");
                // 设置分享图片, 参数2为图片的url地址
//        mController.setShareMedia(new UMImage(this, "http://www.umeng.com/images/pic/banner_module_social.png"));

                // 设置分享图片，参数2为本地图片的资源引用
                mController.setShareMedia(new UMImage(ShareEditActivity.this, R.drawable.logo80));

                // 设置分享图片，参数2为本地图片的路径(绝对路径)
//        mController.setShareMedia(new UMImage(this, BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));

                // 设置分享视频
//        UMVideo umVideo = new UMVideo( "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
//        // 设置视频缩略图
//        umVideo.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
//        umVideo.setTitle("三千字");
//        mController.setShareMedia(umVideo);

//        mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT);




//                UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;

                //添加微信和朋友圈
                String appId = "wx523e7fec6968506f";
                String appSecret = "4a01f28bf8671d6b5487094caaffc72e";
                // 添加微信平台
                UMWXHandler wxHandler = new UMWXHandler(ShareEditActivity.this, appId, appSecret);
                wxHandler.addToSocialSDK();

                // 添加微信朋友圈
                UMWXHandler wxCircleHandler = new UMWXHandler(ShareEditActivity.this, appId, appSecret);
                wxCircleHandler.setToCircle(true);
                wxCircleHandler.addToSocialSDK();

                //添加qq的
                //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
                UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(ShareEditActivity.this, "1104685705", "TaZo5RPmrGX11nPO");
                qqSsoHandler.addToSocialSDK();

                //qq空间
                QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(ShareEditActivity.this, "100424468", "c7394704798a158208a74ab60104f0ba");
                qZoneSsoHandler.addToSocialSDK();

                //添加新浪的
                mController.getConfig().setSsoHandler(new SinaSsoHandler());



                performShare(mediaType);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void performShare(SHARE_MEDIA platform) {
        mController.postShare(this, platform, new SocializeListeners.SnsPostListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                String showText = platform.toString();
                if (eCode == StatusCode.ST_CODE_SUCCESSED) {
                    showText += "平台分享成功";

                    Intent successIntent = new Intent(ShareEditActivity.this, ShareSuccessActivity.class);
                    startActivity(successIntent);
                    finish();

                } else {
                    showText += "平台分享失败";

                }
                Toast.makeText(ShareEditActivity.this, showText, Toast.LENGTH_SHORT).show();

            }
        });
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

}
