package com.chnword.chnword.activity;

import android.app.Activity;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chnword.chnword.R;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
/**
 * Created by khtc on 15/7/14.
 */
public class QRScanActivity extends Activity implements QRCodeView.ResultHandler{

    private static final String TAG = QRScanActivity.class.getSimpleName();

    private ZXingView mZXingView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);
        mZXingView = (ZXingView) findViewById(R.id.zxingview);
        mZXingView.setResultHandler(this);
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "METHOD ONSTART");
        super.onStart();
        mZXingView.startCamera();
        mZXingView.setResultHandler(this);
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera();
        super.onStop();
    }

    /**
     * 震动提示
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void handleResult(String result) {
//        Log.i("bingo", "result:" + result);
        Log.e(TAG, "SEARCH RESULT:" + result);
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
        vibrate();
        mZXingView.startSpot();
    }

    @Override
    public void handleCameraError() {
        Toast.makeText(this, "无法打开相机.", Toast.LENGTH_LONG).show();
    }

}
