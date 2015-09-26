package com.chnword.chnword.activity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chnword.chnword.R;
import com.chnword.chnword.fragment.TabPage;
import com.chnword.chnword.fragment.TabStore;
import com.chnword.chnword.fragment.TabUser;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.popwindow.SharePopWindow;
import com.chnword.chnword.store.LocalStore;
import com.chnword.zxingwapper.zxing.activity.MipcaActivityCapture;
import com.umeng.socialize.bean.RequestType;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by khtc on 15/4/23.
 */
public class TabActivity extends FragmentActivity {

    private static final String TAG = TabActivity.class.getSimpleName();

    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mPageChangeListener;

    private ImageView tab1, tab2, tab3;

    SharePopWindow shareWindow;

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

        versionCheck();

        shareWindow = new SharePopWindow(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

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

        ViewPagerAdapter(FragmentManager fm, List<Fragment> list){
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



    private void versionCheck() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);

            int versionCode = info.versionCode;
            LocalStore store = new LocalStore(this);
            String userId = store.getDefaultUser();
            String deviceId = DeviceUtil.getDeviceId(this);

            JSONObject param = NetParamFactory.versionParam(versionCode, userId, deviceId);
            AbstractNet net = new VerifyNet(versionCheckHandler, param, NetConf.URL_VERSION_CHECK);
            net.start();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Handler versionCheckHandler = new Handler(){

//        private String url;

        @Override
        public void handleMessage(Message msg) {
            try {

                Bundle b = msg.getData();
                String str = b.getString("responseBody");
                JSONObject obj = new JSONObject(str);


                int result = obj.getInt("result");
                if (result == 0 ){
                    //无效参数,什么都不做
                    return;

                } else if (result >= 1) {
                    JSONObject data = obj.getJSONObject("data");

                    if (data == null) {
                        return;
                    }

                    String state = data.getString("status");
                    apkUrl = data.getString("url");
                    String note = data.getString("note");


                    if (msg.what == AbstractNet.NETWHAT_SUCESS) {
                        if ("1".equalsIgnoreCase(state)) {
                            //可选升级
                            AlertDialog.Builder builder = new AlertDialog.Builder(TabActivity.this);
//                    builder.setIcon(R.drawable.icon);
                            builder.setTitle("更新提示");
                            builder.setMessage("Message");
                            builder.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            updateApplication();
                                        }
                                    });
                            builder.setNeutralButton("下次再说",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
//                                    setTitle("点击了对话框上的Button2");
                                            dialog.dismiss();
                                        }

                                    });
                            builder.show();
                        } else if ("2".equalsIgnoreCase(state)) {
                            //强制升级
                            updateApplication();
                        }
                    }
                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {

                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }

    };

    public void updateApplication() {
        if (apkUrl!= null && !"".equalsIgnoreCase(apkUrl)) {

            try {
//                installApk(downloadApk(url, null));
                new Thread(new DownloadApkTask()).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 安装apk文件流程
     *
     * a. 设置Action : Intent.ACTION_VIEW.
     * b. 设置数据和类型 : 设置apk文件的uri 和 MIME类型
     * c. 开启安装文件的Activity.
     */
    protected void installApk(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private File apkFile;
    private String apkUrl;



    
    /**
     * 下载apk更新文件
     *
     * a. 根据SD卡路径创建文件对象, 这个文件用来保存下载的文件
     * b. 创建URL对象
     * c. 创建HttpUrlConnection对象
     * d. 设置链接对象超时时间
     * e. 设置请求方式 get
     * f. 如果请求成功执行下面的操作
     *
     * g. 通过链接对象获取网络资源的大小
     * h. 将文件大小设置给进度条对话框
     * i. 获取输入流, 并且读取输入流信息
     * j. 根据读取到的字节数, 将已经读取的数据设置给进度条对话框
     */
    public File downloadApk(String path,ProgressDialog pb) throws Exception{
        //创建本地文件对象
        File file = new File(Environment.getExternalStorageDirectory(), getFileName());
        //创建HttpURL连接
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == 200){
            int max = conn.getContentLength();
            //设置进度条对话框的最大值
//            pb.setMax(max);
            int count = 0;
            InputStream is = conn.getInputStream();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = is.read(buffer)) != -1){
                fos.write(buffer, 0, len);
                //设置进度条对话框进度
                count = count + len;
//                pb.setProgress(count);
            }
            is.close();
            fos.close();
        }
        return file;
    }

    private String getFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD-HH-mm-ss");
        StringBuffer sb = new StringBuffer();
        sb.append(format.format(new Date()));
        sb.append(".apk");
        return sb.toString();
    }

    /**
     * 在这个线程中主要执行downloadApk方法, 这个方法传入apk路径和进度条对话框
     * 注意 : 下载的前提是sd卡的状态是挂载的
     */
    private final class DownloadApkTask implements Runnable{
        public void run() {
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                try {
                    SystemClock.sleep(2000);
                    Message msg = new Message();
                    apkFile = downloadApk(apkUrl, null);
                    msg.what = SUCCESS_DOWNLOAD_APK;
                    downloadHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = ERROR_DOWNLOAD_APK;
                    downloadHandler.sendMessage(msg);
                }
            }
        }
    }
    private static int SUCCESS_DOWNLOAD_APK = 1, ERROR_DOWNLOAD_APK = 2;

    private Handler downloadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == SUCCESS_DOWNLOAD_APK) {
                installApk(apkFile);
            }

            if (msg.what == ERROR_DOWNLOAD_APK) {
                Toast.makeText(TabActivity.this, "下载错误", Toast.LENGTH_LONG);
            }

            super.handleMessage(msg);
        }

    };


    /**
     * 字动画
     * @param view
     */
    public void onAnimClicked(View view) {
        Log.e(TAG, "METHOD ");
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }


    private final static int SCANNIN_GREQUEST_CODE = 1;
    /**
     * 扫汉字
     * @param view
     */
    public void onScanClicked(View view) {

        Intent intent = new Intent();
        intent.setClass(this, MipcaActivityCapture.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNIN_GREQUEST_CODE);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    //现请求查找index
                    LocalStore store = new LocalStore(this);

                    String userid = store.getDefaultUser();
                    String deviceId = DeviceUtil.getDeviceId(this);
                    String word = bundle.getString("result");

                    if (word != null && !"".equalsIgnoreCase(word)){
                        JSONObject param = NetParamFactory.wordParam(userid, deviceId, word);
                        Log.e(TAG, param.toString());
                        AbstractNet net = new VerifyNet(wordHandler, param, NetConf.URL_WORD);

                        net.start();
                    } else {
                        //do nothing
                    }
                }
                break;
        }
    }



    /**
     * 三千字套装
     * @param view
     */
    public void onShopAnime(View view) {
        Intent intent = new Intent(this, ShopSuitActivity.class);
        startActivity(intent);
    }

    /**
     * 三千字资源库
     * @param view
     */
    public void onShopSuit(View view) {
        Intent intent = new Intent(this, ShopAnimeActivity.class);
        startActivity(intent);
    }




    /**
     * 开启分享
     */
    public void openUmeng() {
//        mController.openShare(this, false);

        View view = findViewById(R.id.tab_main);
        shareWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    public void onSharedButtonClicked() {
        // 是否只有已登录用户才能打开分享选择页
        View view = findViewById(R.id.tab_main);
        shareWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    private Handler wordHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

//            progressDialog.dismiss();
            try {
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {

                    Bundle b = msg.getData();
                    String str = b.getString("responseBody");
                    android.util.Log.e(TAG, str);
                    JSONObject obj = new JSONObject(str);

                    String result = obj.getString("result");
                    String message = obj.getString("message");
                    if (result != null && "1".equalsIgnoreCase(result)) {
//                        = obj.getJSONObject("data");

                        String dataString = obj.getString("data");
                        if (dataString != null && !"null".equalsIgnoreCase(dataString)) {
                            JSONObject objData = new JSONObject(dataString);

                            JSONArray word_names = objData.getJSONArray("word");
                            JSONArray word_indexs = objData.getJSONArray("unicode");

                            if (word_indexs.length() > 0 && word_names.length()> 0) {
                                String word = word_names.getString(0);
                                String word_index = word_indexs.getString(0);
                                Intent intent = new Intent(TabActivity.this, ShowActivity.class);
                                intent.putExtra("word", word);
                                intent.putExtra("word_index", word_index);
                                startActivity(intent);
                            } else {
                                Toast.makeText(TabActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(TabActivity.this, "请搜索指定的汉字!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(TabActivity.this, "网络请求失败，请检查网络!", Toast.LENGTH_LONG).show();
                    }

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(TabActivity.this, "请求失败，请检查网络设置", Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    };





    public void onInforListButtonClicked(View v) {
        Intent infolistIntent = new Intent(this, InfoListActivity.class);
        startActivity(infolistIntent);
    }

    public void onShareButtonClicked(View v) {
        openUmeng();
    }

}
