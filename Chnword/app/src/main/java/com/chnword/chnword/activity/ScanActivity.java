package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.HciAccountInfo;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.store.LocalStore;
import com.chnword.chnword.utils.hci.HciCloudSysHelper;
import com.sinovoice.hcicloudsdk.android.ocr.capture.CaptureErrCode;
import com.sinovoice.hcicloudsdk.android.ocr.capture.CaptureEvent;
import com.sinovoice.hcicloudsdk.android.ocr.capture.OCRCapture;
import com.sinovoice.hcicloudsdk.android.ocr.capture.OCRCaptureListener;
import com.sinovoice.hcicloudsdk.android.ocr.capture.UIDeviceOrientation;
import com.sinovoice.hcicloudsdk.android.ocr.capture.UIDeviceOrientationManager;
import com.sinovoice.hcicloudsdk.common.HciErrorCode;
import com.sinovoice.hcicloudsdk.common.ocr.OcrCornersResult;
import com.sinovoice.hcicloudsdk.common.ocr.OcrInitParam;
import com.sinovoice.hcicloudsdk.common.ocr.OcrRecogResult;
import com.sinovoice.hcicloudsdk.common.ocr.OcrTemplateId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by khtc on 15/4/27.
 */
public class ScanActivity extends Activity {
    private static final String TAG = ScanActivity.class.getSimpleName();

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;


    private HciAccountInfo mAccountInfo;
    /**
     * HciCloud帮助类，可完成灵云系统初始化，释放操作。
     */
    private HciCloudSysHelper mHciCloudSysHelper;

    private Handler hander;
    private String capKey;
    //显示摄像头预览视图的布局文件
    private FrameLayout cameraPreviewLayout;
    private Button btnTakePicture;
    private ProgressDialog pDialog;

    //识别配置参数
    private String recogConfig;
    //是否需要加载模板文件
    private boolean isNeedLoadTemplate;
    //模板文件相关
    private OcrTemplateId currTemplateId;
    private int templateId;

    private ProgressDialog progressDialog;

    //拍照器模块
    private OCRCapture ocrCapture;
    private OCRCaptureListener ocrCaptureListener = new OCRCaptureListener() {

        @Override
        public void onCaptureEventStateChange(CaptureEvent captureEvent) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onCaptureEventRecogFinish(CaptureEvent captureEvent,
                                              OcrRecogResult ocrRecogResult) {
            switch (captureEvent) {
                case CAPTURE_EVENT_RECOGNIZE_FINISH:
                    if(ocrRecogResult != null){
                        Log.v(TAG, "recog result = " + ocrRecogResult.getResultText());
                        showResultView(ocrRecogResult.getResultText());
                    }
                    break;

                default:
                    break;
            }
        }

        @Override
        public void onCaptureEventError(CaptureEvent captureEvent, int errorCode) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onCaptureEventCapturing(CaptureEvent captureEvent,
                                            final byte[] imageData, OcrCornersResult ocrCornersResult) {
            Log.i(TAG, "onCaptureEventCapturing. imageData len = " + imageData.length);

            //文档识别
            if(capKey.equalsIgnoreCase("ocr.cloud")
                    || capKey.equalsIgnoreCase("ocr.cloud.english")
                    || capKey.equalsIgnoreCase("ocr.local")){

                hander.post(new Runnable() {

                    @Override
                    public void run() {
                        ocrCapture.hciOcrCaptureRecog(imageData, recogConfig, null);
                    }
                });
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scan);

//        this.imageView = (ImageView) this.findViewById(R.id.imageView1);
//        Button photoButton = (Button) this.findViewById(R.id.button1);
//
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);
//        photoButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//构造intent
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);//发出intent，并要求返回调用结果
//            }
//        });

        //设置屏幕显示方向

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        cameraPreviewLayout = (FrameLayout) findViewById(R.id.layout_camera_preview);
        btnTakePicture = (Button) findViewById(R.id.btn_take_picture);


        hander = new Handler();

        pDialog = ProgressDialog.show(this, getText(R.string.dialog_title_tips), getText(R.string.dialog_msg_hcicloud_sysinit));

        mAccountInfo = HciAccountInfo.getInstance();
//        boolean loadResult = mAccountInfo.loadAccountInfo(this);
//        if (loadResult) {
//            // 加载信息成功进入主界面
//            Toast.makeText(getApplicationContext(), "加载灵云账号成功",
//                    Toast.LENGTH_SHORT).show();
//        } else {
//            // 加载信息失败，显示失败界面
//            Toast.makeText(getApplicationContext(), "加载灵云账号失败！请在assets/AccountInfo.txt文件中填写正确的灵云账户信息，账户需要从www.hcicloud.com开发者社区上注册申请。",
//                    Toast.LENGTH_SHORT).show();
//            return;
//        }

        mHciCloudSysHelper = HciCloudSysHelper.getInstance();

        // 此方法是线程阻塞的，当且仅当有结果返回才会继续向下执行。
        // 此处只是演示合成能力用法，没有对耗时操作进行处理。需要开发者放入后台线程进行初始化操作
        // 必须首先调用HciCloudSys的初始化方法
        int sysInitResult = mHciCloudSysHelper.init(this);
        if (sysInitResult != HciErrorCode.HCI_ERR_NONE) {
            Log.e(TAG, "hci init error, error code = " + sysInitResult);
            return;
        }

        // 读取用户的调用的能力
        capKey = mAccountInfo.getCapKey();

        if(capKey.equalsIgnoreCase("ocr.cloud")
                || capKey.equalsIgnoreCase("ocr.cloud.english")
                || capKey.equalsIgnoreCase("ocr.local")){
            //文档识别
            recogConfig = "capKey=" + capKey;
        }else if(capKey.equalsIgnoreCase("ocr.local.bizcard.v6")){
            //名片识别
            recogConfig = "capkey=ocr.local.bizcard,cutedge=yes";
        }else if(capKey.equalsIgnoreCase("ocr.local.template.v6")){
            //模板识别
            isNeedLoadTemplate = true;
        }else{
            Log.e(TAG, "未知的capKey。 capKey = " + capKey);
        }

        ocrCapture = new OCRCapture();

        initOCRCapture();
        dismissDialog();

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

        //释放拍照器资源
        if(ocrCapture != null){
            if(isNeedLoadTemplate){
                int captureErrorCode = ocrCapture.hciOcrCaptureUnloadTemplate(currTemplateId);
                Log.v(TAG, "hciOcrCaptureUnloadTemplate(), captureErrorCode = " + captureErrorCode);
            }

            int captureErrorCode = ocrCapture.hciOcrCaptureRelease();
            Log.v(TAG, "hciOcrCaptureRelease(), captureErrorCode = " + captureErrorCode);

            ocrCapture = null;
        }

        mHciCloudSysHelper.release();
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


    /**
     * 接收intent传回的信息
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            //System.exit(0);
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }


    /**
     * 初始化OCR模块
     */
    private void initOCRCapture() {
        OcrInitParam ocrInitParam = new OcrInitParam();
        String sdPath = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        String packageName = this.getPackageName();
        String dataPath = sdPath + File.separator + "sinovoice"
                + File.separator + packageName + File.separator + "data"
                + File.separator;

        String str = getFilesDir().getAbsolutePath();

        if (capKey.contains("local")) {
//            ocrInitParam.addParam(OcrInitParam.PARAM_KEY_DATA_PATH, dataPath);
            ocrInitParam.addParam(OcrInitParam.PARAM_KEY_DATA_PATH, str);
            ocrInitParam.addParam(OcrInitParam.PARAM_KEY_INIT_CAP_KEYS, capKey);
            ocrInitParam.addParam(OcrInitParam.PARAM_KEY_FILE_FLAG, "none");
        }

        String initParam = ocrInitParam.getStringConfig();
        int captureErrorCode = ocrCapture.hciOcrCaptureInit(getApplicationContext(), initParam, ocrCaptureListener);
        dismissDialog();

        //初始化成功，如果需要加载模板就启动新线程加载模板，加载成功后显示摄像头预览界面，否则直接显示摄像头预览界面
        if(captureErrorCode == CaptureErrCode.CAPTURE_ERR_NONE){
            Log.i(TAG, "hciOcrCaptureInit success.");
            if(isNeedLoadTemplate){
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        //载入模板
                        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                        String dataPath = sdPath + File.separator + "sinovoice"
                                + File.separator + "com.sinovoice.example.ocrcapture" + File.separator + "data"
                                + File.separator;
                        String templatePath = dataPath + "/templates/IDCard_EN.xml";
                        currTemplateId = new OcrTemplateId();
//                        currTemplateId.setTemplateId(90);


                        UIDeviceOrientationManager uiDeviceOrientationManager = new UIDeviceOrientationManager() {
                            @Override
                            public UIDeviceOrientation getDeviceOrientation() {
                                return UIDeviceOrientation.UIDeviceOrientationPortrait;
//                                UIDeviceOrientationPortration
                            }
                        } ;


                        ocrCapture.setDeviceOrientationManager(uiDeviceOrientationManager);


                        Camera.Parameters parameters = ocrCapture.getCameraParameters();
//                        Camera.Parameters parameters = new Camera.Parameters();


                        parameters.set("rotation", 90);
                        parameters.set("orientation", "UIDeviceOrientationPortrait");
                        parameters.set("DisplayOrientation", 90);
                        parameters.set("displayorientation", 90);

                        ocrCapture.setCameraParameters(parameters);



                        int errorCode = ocrCapture.hciOcrCaptureLoadTemplate(templatePath, currTemplateId);
                        if (errorCode != CaptureErrCode.CAPTURE_ERR_NONE) {
                            Log.e(TAG, "hciOcrLoadTemplate() error. errorcode = " + errorCode);
                        }else{
                            templateId = currTemplateId.getTemplateId();
                            recogConfig = "capkey="+ capKey + ",cutedge=no,templateid="+ templateId +",templateIndex=0,templatePageIndex=0";
                            hander.post(new Runnable() {

                                @Override
                                public void run() {
                                    showCaptureView();
                                }
                            });
                        }



                    }
                }).start();
            }else{
                showCaptureView();
            }
        }else{
            Log.e(TAG, "hciOcrCaptureInit fail. captureErrorCode = " + captureErrorCode);
        }
    }
    /**
     * 关闭对话框
     */
    private void dismissDialog() {
        if(pDialog != null && pDialog.isShowing()){
            pDialog.dismiss();
            pDialog = null;
        }
    }

    /**
     * 显示摄像头预览界面
     */
    private void showCaptureView(){
//        setContentView(R.layout.ocr_capture_camera_preview);
//        setContentView(R.layout.activity_scan);

        if(cameraPreviewLayout != null){
            cameraPreviewLayout.removeAllViews();
//            cameraPreviewLayout = null;
        }


        cameraPreviewLayout.addView(ocrCapture.getCameraPreview());

        btnTakePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ocrCapture.hciOcrCaptureStopAndRecog();
            }
        });

        ocrCapture.hciOcrCaptureStart(recogConfig);
    }



    /**
     * 显示结果界面
     * @param text
     */
    private void showResultView(String text){
        if(cameraPreviewLayout != null){
            cameraPreviewLayout.removeAllViews();
            cameraPreviewLayout = null;
        }
        Log.e(TAG, text);

//        setContentView(R.layout.ocr_capture_result);
//
//        TextView tvResult = (TextView) findViewById(R.id.tv_result);
//        tvResult.setText(text);

//        Intent i = new Intent(this, ResultActivity.class);
//        i.putExtra("ScanResult", text);
//        startActivity(i);
        new  AlertDialog.Builder(this)
                .setTitle("识别字体" )
                .setMessage(text )
                .setPositiveButton("是" ,  null )
//                .setNegativeButton("否" , null)
                .show();
        searchResult = text;

        LocalStore store = new LocalStore(ScanActivity.this);
        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);
        JSONObject param = NetParamFactory.wordParam(userid, deviceId, text);
        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_WORD);
        net.start();

//        showCaptureView();

    }
    private String searchResult ;

    private Handler handler  = new Handler() {
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            //todo 处理结果并跳转。
            try {
                Bundle b = msg.getData();
                String str = b.getString("responseBody");
                Log.e(TAG, str);
                JSONObject obj = new JSONObject(str);
                JSONObject data = obj.getJSONObject("data");


                if (msg.what == AbstractNet.NETWHAT_SUCESS) {
                    if (data != null) {
                        JSONArray word_name = data.getJSONArray("word_name");
                        JSONArray word_index = data.getJSONArray("word_index");
                        String word_tip = data.getString("word_tip");

                        ArrayList<String> word_names = new ArrayList<String>();
                        ArrayList<String> word_indexs = new ArrayList<String>();
                        for (int i = 0; i < word_name.length(); i ++) {
                            word_names.add(word_name.getString(i));
                        }
                        for (int i = 0; i < word_index.length(); i ++) {
                            word_indexs.add(word_index.getString(i));
                        }

                        //todo 跳转到结果显示页面
                        Intent intent = new Intent(ScanActivity.this, ResultActivity.class);
                        intent.putExtra("SEARCH_RESULT", searchResult);
                        intent.putExtra("WORD_TIP", word_tip);
                        intent.putStringArrayListExtra("WORD_NAME", word_names);
                        intent.putStringArrayListExtra("WORD_INDEX", word_indexs);
                        intent.putExtra("ISFROMSEARCH", true);



                        startActivity(intent);

                    }
                }
                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                        Toast.makeText(ScanActivity.this, "联网请求视频失败", Toast.LENGTH_LONG).show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }


            super.handleMessage(msg);
        }
    };
}
