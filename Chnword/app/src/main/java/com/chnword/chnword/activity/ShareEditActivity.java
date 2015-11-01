package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.UserDictionary;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chnword.chnword.R;
import com.chnword.chnword.adapter.ShareEditAdapter;
import com.chnword.chnword.beans.Word;
import com.chnword.chnword.beans.WordShare;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
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
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by khtc on 15/9/17.
 */
public class ShareEditActivity extends Activity {

    private static final String TAG = ShareEditActivity.class.getSimpleName();

    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    private ProgressDialog progressDialog;


    private SHARE_MEDIA mediaType;
    private String type;

    private ImageButton backImageButton;
    private ImageButton sharedButton;
    private EditText shareText;

    private List<WordShare> wordList;
    ShareEditAdapter adapter;
    GridView gridView;

    String zoneCode = ""; //cname
    String currentShareWord = "";
    private LocalStore store;

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
        zoneCode = bundle.getString("ZoneCode");

        final String share_type = bundle.getString("share_type");
        type = bundle.getString("share_type_user");
        if (share_type == null || "".equalsIgnoreCase(share_type))
        {
            Log.e(TAG, "INVALIDAGE TYPE.");
            finish();
        }
        store = new LocalStore(this);

        mediaType = SHARE_MEDIA.convertToEmun(share_type);

        shareText = (EditText) findViewById(R.id.shareText);

        wordList = new ArrayList<WordShare>();
        adapter = new ShareEditAdapter(this, wordList);
        gridView = (GridView) findViewById(R.id.shared_edit_grid);
        gridView.setAdapter(adapter);
        gridView.requestFocus();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //

                for (int i = 0; i < wordList.size(); i ++) {
                    WordShare share = wordList.get(i);
                    if (i == position) {
                        share.setIsSelected(true);
                        currentShareWord = share.getWord();
                    } else {
                        share.setIsSelected(false);
                    }
                }
                WordShare wordShare = wordList.get(position);

                // 设置分享内容
                mController.setShareContent(shareText.getText().toString());
                // 设置分享图片, 参数2为图片的url地址
                mController.setShareMedia(new UMImage(ShareEditActivity.this, wordShare.getGifUrl()));
                // 设置分享视频
                UMVideo umVideo = new UMVideo(wordShare.getVideoUrl());
                // 设置视频缩略图
                umVideo.setThumb(wordShare.getIconUrl());
                umVideo.setTitle("三千字");
                mController.setShareMedia(umVideo);

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

                adapter.notifyDataSetChanged();

            }
        });


        sharedButton = (ImageButton) findViewById(R.id.sharedButton);
        sharedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 进行分享
                performShare(mediaType);
            }
        });

        requestNet();

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

                    //调用说明成功
                    boolean flag = false;
                    for (int i = 0; i < wordList.size(); i ++) {
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


    private void requestNet() {
        LocalStore store = new LocalStore(this);
        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);

        JSONObject param = NetParamFactory.sharedWordParam(userid, deviceId, type);
        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_SHARED);
        progressDialog = ProgressDialog.show(this, "提示", "loading...");
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
                            word.setIsSelected(false);
                            if (i == 0 ) {
                                word.setIsSelected(true);
                            }

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


                        if (wordList.size() > 0) {
                            WordShare wordShare = wordList.get(0);

                            // 设置分享内容
                            mController.setShareContent(shareText.getText().toString());
                            // 设置分享图片, 参数2为图片的url地址
                            mController.setShareMedia(new UMImage(ShareEditActivity.this, wordShare.getGifUrl()));
                            // 设置分享视频
                            UMVideo umVideo = new UMVideo(wordShare.getVideoUrl());
                            // 设置视频缩略图
                            umVideo.setThumb(wordShare.getIconUrl());
                            umVideo.setTitle("三千字");
                            mController.setShareMedia(umVideo);

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
                        }


//                        ImageLoader imageLoader = ImageLoader.getInstance();
//                        imageLoader.displayImage(url, wordImageView);
                        adapter.notifyDataSetChanged();


                    } else {
                        Toast.makeText(ShareEditActivity.this, "服务器无数据返回", Toast.LENGTH_LONG).show();
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
