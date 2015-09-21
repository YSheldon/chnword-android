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
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.chnword.chnword.utils.MD5;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.vov.vitamio.LibsChecker;
import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by khtc on 15/5/17.
 */
public class ShowActivity extends Activity {
    private static final String TAG = ShowActivity.class.getSimpleName();

    private Word word ;


    private Fragment fragment;

    private GifFragment gifFragment;

    private FragmentManager manager;

    private String gifUri, videoUri;

    private LocalStore store;

    private ProgressDialog progressDialog;


    private ImageButton backImageButton;




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

        gifFragment = new GifFragment();


        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void requestNet() {

        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);;
        JSONObject param = NetParamFactory.showParam(userid, deviceId, word.getWordIndex());
        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_SHOW);
        progressDialog = ProgressDialog.show(this, "提示", "loading...");
        net.start();

    }

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
//            progressDialog = null;
            try {
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {

                    Bundle b = msg.getData();
                    String str = b.getString("responseBody");

                    JSONObject obj = new JSONObject(str);
                    JSONObject data = obj.getJSONObject("data");

                    String location = data.getString("gif");
//                    gifUri = Uri.parse(location);
                    gifUri = location;
//                    gifFragment.setUri(Uri.parse(location));

                    String video = data.getString("video");
//                    videoUri = Uri.parse(video);
                    videoUri = video;

                    //todo 下载gif图片。

                    //开启分享
                    initUmeng();



                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            File cache = new File(Environment.getExternalStorageDirectory(), "cache");
                            if(!cache.exists()){
                                cache.mkdirs();
                            }

                            try {
                                Log.e(TAG, gifUri);
                                Uri uri = getImageURI(gifUri, cache);


                                Message msg = new Message();
                                msg.what = 1001;

                                Bundle bundle = new Bundle();
                                bundle.putString("url", uri.toString());

                                msg.setData(bundle);

                                downloadImageHandler.handleMessage(msg);

                            } catch(Exception e) {
                                e.printStackTrace();
                            } finally {
//                                progressDialog.dismiss();
                            }


                        }
                    }).start();



                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(ShowActivity.this, "网络请求失败。", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    onBackPressed();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    Handler downloadImageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1001) {
                Bundle bundle = msg.getData();

                String str = bundle.getString("url", "");
                if ("".equalsIgnoreCase(str)) {
                    Toast.makeText(ShowActivity.this, "无效的文件url地址", Toast.LENGTH_LONG).show();
                } else {
                    Uri uri = Uri.parse(str);
                    Log.e(TAG, "gifuri : " + gifUri + " localuri : " + uri.toString());
                    gifFragment.setUri(uri);
                    getFragmentManager().beginTransaction().add(R.id.fragment_container, gifFragment).commit();
                }

            }

            progressDialog.dismiss();

            super.handleMessage(msg);
        }
    };


    /*
     * 从网络上获取图片，如果图片在本地存在的话就直接拿，如果不存在再去服务器上下载图片
     * 这里的path是图片的地址
     */
    public Uri getImageURI(String path, File cache) throws Exception {
        String name = MD5.getMD5(path) + path.substring(path.lastIndexOf("."));
        File file = new File(cache, name);
        // 如果图片存在本地缓存目录，则不去服务器下载
        if (file.exists()) {
            return Uri.fromFile(file);//Uri.fromFile(path)这个方法能得到文件的URI
        } else {
            // 从网络上获取图片
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == 200) {

                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();
                // 返回一个URI对象
                return Uri.fromFile(file);
            }
        }
        return null;
    }


    //Action event handler

    /**
     * 处理分类按钮点击
     * @param view
     */
    public void onBackStage(View view) {
        onBackPressed();
        finish();
    }




    //umeng
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    public void initUmeng() {
        // 设置分享内容
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
        // 设置分享图片, 参数2为图片的url地址
//        mController.setShareMedia(new UMImage(this, "http://www.umeng.com/images/pic/banner_module_social.png"));

        // 设置分享图片，参数2为本地图片的资源引用
        mController.setShareMedia(new UMImage(this, R.drawable.logo120));
        // 设置分享图片，参数2为本地图片的路径(绝对路径)
//        mController.setShareMedia(new UMImage(this, BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));

        // 设置分享视频
        UMVideo umVideo = new UMVideo(this.videoUri);
        // 设置视频缩略图
        umVideo.setThumb(new UMImage(this, R.drawable.logo80));
        umVideo.setTitle("友盟社会化分享!");
        mController.setShareMedia(umVideo);
        mController.getConfig().removePlatform(SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT);

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

    public void openUmeng(View view) {
        if (mController != null) {
            mController.openShare(this, false);
        } else {
            Toast.makeText(this, "分享初始化失败。", Toast.LENGTH_LONG).show();
            finish();
        }
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
