package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Word;
import com.chnword.chnword.beans.WordShare;
import com.chnword.chnword.dialogs.DialogUtil;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.popwindow.SharePopWindow;
import com.chnword.chnword.store.LocalStore;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khtc on 15/9/15.
 */
public class FreewordActivity extends Activity {
    private static final String TAG = FreewordActivity.class.getSimpleName();

    private ImageButton backImageButton;
    private Dialog progressDialog;
    private Word currentWord;
    private LocalStore store;

    String zoneCode = ""; //cname
    String title = "";
    String type = "";
    int index = 0;
    int resourceIndex = 0;  //模块的下标

    private TextView titleTextView;
    private ImageView wordImageView;

    private ImageView wordImageView2;

    private RelativeLayout guideLayout;

    private TextView modulNameTextView;

    private LinearLayout freewordTop;//draw图片
    private LinearLayout bottomLinear;//mid图片
    private LinearLayout freeTopLinear;//top big图片

    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    String currentShareWord = "";


    private static int [] draws = {R.drawable.draw_1,
            R.drawable.draw_2,
            R.drawable.draw_3,
            R.drawable.draw_4,
            R.drawable.draw_5,
            R.drawable.draw_6,
            R.drawable.draw_7,
            R.drawable.draw_8,
            R.drawable.draw_9,
            R.drawable.draw_10};

    private static int[] tops = {R.drawable.topbackg_1,
            R.drawable.topbackg_2,
            R.drawable.topbackg_3,
            R.drawable.topbackg_4,
            R.drawable.topbackg_5,
            R.drawable.topbackg_6,
            R.drawable.topbackg_7,
            R.drawable.topbackg_8,
            R.drawable.topbackg_9,
            R.drawable.topbackg_10};

    private static int[] mids = {R.drawable.midbackg_1,
            R.drawable.midbackg_2,
            R.drawable.midbackg_3,
            R.drawable.midbackg_4,
            R.drawable.midbackg_5,
            R.drawable.midbackg_6,
            R.drawable.midbackg_7,
            R.drawable.midbackg_8,
            R.drawable.midbackg_9,
            R.drawable.midbackg_10};


    SharePopWindow shareWindow;
    String url = "";

    private TextView freewordCode, freewordCode2;
    private List<WordShare> wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeword);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });

        wordImageView = (ImageView) findViewById(R.id.wordImageView);
        modulNameTextView = (TextView) findViewById(R.id.modulNameTextView);

        wordImageView2 = (ImageView) findViewById(R.id.wordImageView2);

        guideLayout = (RelativeLayout) findViewById(R.id.guideLayout);

        freewordTop = (LinearLayout) findViewById(R.id.freewordTop);
        bottomLinear = (LinearLayout) findViewById(R.id.bottomLinear);
        freeTopLinear = (LinearLayout) findViewById(R.id.freeTopLinear);

        freewordCode = (TextView) findViewById(R.id.freewordCode);
        freewordCode2 = (TextView) findViewById(R.id.freewordCode2);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        store = new LocalStore(this);

        zoneCode = b.getString("ZoneCode");
        index = b.getInt("ZoneIndex");

        title = b.getString("module_name", "");
        type = b.getString("module_type", "");
        resourceIndex = b.getInt("module_index", 0);
        if ("".equalsIgnoreCase(title)){
            Log.e(TAG, "NO TITLE, BACK");
            finish();
        }

        wordList = new ArrayList<WordShare>();
        //
        modulNameTextView.setText(title);

        freewordTop.setBackgroundResource(draws[resourceIndex]);
        bottomLinear.setBackgroundResource(mids[resourceIndex]);
        freeTopLinear.setBackgroundResource(tops[resourceIndex]);

        shareWindow = new SharePopWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent shareEditIntent = new Intent(FreewordActivity.this, ShareEditActivity.class);
//
//                shareEditIntent.putExtra("share_type_user", type);

                switch (v.getId()) {
                    case R.id.snslogo1 :
//                        shareEditIntent.putExtra("share_type", SHARE_MEDIA.WEIXIN.toString());
//                        shareEditIntent.putExtra("ZoneCode", zoneCode);
                        performShare(SHARE_MEDIA.WEIXIN);
                        break;

                    case R.id.snslogo2:
//                        shareEditIntent.putExtra("share_type", SHARE_MEDIA.WEIXIN_CIRCLE.toString());
//                        shareEditIntent.putExtra("ZoneCode", zoneCode);
                        performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;

                    case R.id.snslogo3:
//                        shareEditIntent.putExtra("share_type", SHARE_MEDIA.SINA.toString());
//                        shareEditIntent.putExtra("ZoneCode", zoneCode);
                        performShare(SHARE_MEDIA.SINA);
                        break;
                    case R.id.snslogo4:
//                        shareEditIntent.putExtra("share_type", SHARE_MEDIA.QQ.toString());
//                        shareEditIntent.putExtra("ZoneCode", zoneCode);
                        performShare(SHARE_MEDIA.QQ);
                        break;
                    case R.id.snslogo5:
//                        shareEditIntent.putExtra("share_type", SHARE_MEDIA.QZONE.toString());
//                        shareEditIntent.putExtra("ZoneCode", zoneCode);
                        performShare(SHARE_MEDIA.QZONE);
                        break;
                    case R.id.snslogo6:
//                        shareEditIntent.putExtra("share_type", SHARE_MEDIA.TENCENT.toString());
//                        shareEditIntent.putExtra("ZoneCode", zoneCode);
                        performShare(SHARE_MEDIA.TENCENT);
                        break;
                    default:
                        break;
                }
//                startActivity(shareEditIntent);
                shareWindow.dismiss();
            }
        });
        shareWindow.setType(type);

        titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText(title);


        requestSharedWord(zoneCode);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    private void requestSharedWord(String zoneCode) {
        LocalStore store = new LocalStore(this);
        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);

        JSONObject param = NetParamFactory.sharedWordParam(userid, deviceId, type);
        AbstractNet net = new VerifyNet(sharehandler, param, NetConf.URL_SHARED);
        progressDialog = DialogUtil.createLoadingDialog(this, "数据加载中...");
        progressDialog.show();
        net.start();
    }



    private void setUpSharedContent(WordShare word) {
        // 设置分享内容
        mController.setShareContent("你真的认识汉字吗? 他让你大吃一惊, 让孩子受益终身!");
//        // 设置分享图片, 参数2为图片的url地址
//        mController.setShareMedia(new UMImage(FreewordActivity.this, "123333333"));
        // 设置分享视频
        UMVideo umVideo = new UMVideo(word.getVideoUrl());
        // 设置视频缩略图
        umVideo.setThumb(word.getIconUrl());
        umVideo.setTitle("三千字");
        mController.setShareMedia(umVideo);


        //添加微信和朋友圈
        String appId = "wx523e7fec6968506f";
        String appSecret = "4a01f28bf8671d6b5487094caaffc72e";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(FreewordActivity.this, appId, appSecret);
        wxHandler.addToSocialSDK();

        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(FreewordActivity.this, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        //添加qq的
        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(FreewordActivity.this, "1104685705", "TaZo5RPmrGX11nPO");
        qqSsoHandler.addToSocialSDK();

        //qq空间
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(FreewordActivity.this, "100424468", "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();

        //添加新浪的
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
    }

    /**
     * 分享成功的回调
     * @param platform
     */
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

                    //调用说明成功
                    boolean flag = false;
                    for (int i = 0; i < wordList.size(); i++) {
                        WordShare wordShare = wordList.get(i);
                        if (!store.isWordShared(wordShare.getWord())) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        //有没分享的字
                        store.setWordShared(currentShareWord);
                    } else {
                        store.unLockNextCategory();
                    }

                    //// TODO: 15/11/21  添加调用，分享成功后的。
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String url = NetConf.URL_SHARED_LINK + "?userid=" + store.getDefaultUser() + "&wordid=" + currentWord;
                                HttpClient httpClient = new DefaultHttpClient();
                                HttpPost httpPost = new HttpPost(url);
                                HttpResponse response = httpClient.execute(httpPost);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });


                } else {
                    showText += "平台分享失败";

                }
//                Toast.makeText(FreewordActivity.this, showText, Toast.LENGTH_SHORT).show();

            }
        });
    }




    Handler sharehandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            progressDialog = null;
            try {
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {
                    wordList.clear();
                    Bundle b = msg.getData();
                    String str = b.getString("responseBody");
                    android.util.Log.e(TAG, str);
                    JSONObject obj = new JSONObject(str);
                    Log.e(TAG, obj.toString());
                    if (!obj.isNull("data")){

                        Log.e(TAG, obj.getString("data"));
                        JSONArray wordArray = obj.getJSONArray("data");
                        Log.e(TAG, wordArray.toString());
                        for (int i = 0; i < wordArray.length(); i ++) {
                            JSONObject wordObj = wordArray.getJSONObject(i);
                            WordShare word = new WordShare();

                            word.setGifUrl(wordObj.getString("gif"));
                            word.setVideoUrl(wordObj.getString("video"));
                            word.setIconUrl(wordObj.getString("icon"));
                            word.setWord(wordObj.getString("word"));
                            word.setSort(wordObj.getString("sort"));
                            word.setShareTitle(wordObj.getString("share_title"));
                            word.setShareDesc(wordObj.getString("share_desc"));
                            word.setShareIcon(wordObj.getString("share_icon"));
                            word.setShareUrl(wordObj.getString("share_url"));
//                            word.setWordIndex(wordObj.getString("unicode"));
                            Log.e(TAG, "OBJ:" + wordObj.toString());
                            Log.e(TAG, word.toString());
                            wordList.add(word);
                        }
                        //// TODO: 15/11/14 根据显示的数目进行判断
                        if (wordList.size() == 1) {
                            ImageLoader imageLoader = ImageLoader.getInstance();
                            imageLoader.displayImage(url, wordImageView);

                            WordShare word = wordList.get(0);
//                            guideLayout.setVisibility(View.INVISIBLE);
//                            imageLoader.displayImage(url, wordImageView2);
                            freewordCode2.setText(word.getWord());
                            setUpSharedContent(word);

                        } else if (wordList.size() == 2) {

                            ImageLoader imageLoader = ImageLoader.getInstance();
                            WordShare word = wordList.get(0);
                            guideLayout.setVisibility(View.INVISIBLE);
//                            imageLoader.displayImage(word.getIconUrl(), wordImageView2);
                            freewordCode.setText(word.getWord());

                            WordShare word2 = wordList.get(1);
                            guideLayout.setVisibility(View.INVISIBLE);
//                            imageLoader.displayImage(word2.getIconUrl(), wordImageView);
                            freewordCode2.setText(word.getWord());

                            setUpSharedContent(word2);

                        } else {
                            //多于两个，错误

                        }

                    } else {
                        Toast.makeText(FreewordActivity.this, "服务器无数据返回", Toast.LENGTH_LONG).show();
                    }

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * 用户点击分享按钮
     * @param v
     *
     */
    public void onShareButtonClicked(View v) {
        View view = findViewById(R.id.freeword_main);
        shareWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 用户点击获取用户码按钮
     * @param view
     */
    public void onShopButtonClicked(View view) {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }




    /**
     * 请求列表
     * @param zoneCode
     */
    private void requestNet(String zoneCode) {

        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);
        JSONObject param = NetParamFactory.subListParam(userid, deviceId, zoneCode, 0, 0);
        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_SUBLIST);
        progressDialog = DialogUtil.createLoadingDialog(this, "数据加载中...");
        progressDialog.show();
        net.start();
    }

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
                    Log.e(TAG, str);
                    JSONObject obj = new JSONObject(str);
                    if (!obj.isNull("data")){

                        JSONArray wordArray = obj.getJSONArray("data");
                        for (int i = 0; i < wordArray.length(); i ++) {
                            JSONObject wordObj = wordArray.getJSONObject(i);
                            Word word = new Word();
                            word.setWord(wordObj.getString("word"));
                            word.setWordIndex(wordObj.getString("unicode"));

                            if ("0".equalsIgnoreCase(wordObj.getString("free"))) {
                                currentWord = word;
                                url = wordObj.getString("icon");
                                freewordCode.setText(currentWord.getWord());
                                break;
                            }
                        }

//                        ImageLoader imageLoader = ImageLoader.getInstance();
//                        imageLoader.displayImage(url, wordImageView);

                    } else {
                        Toast.makeText(FreewordActivity.this, "服务器无数据返回", Toast.LENGTH_LONG).show();
                    }

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };



}
