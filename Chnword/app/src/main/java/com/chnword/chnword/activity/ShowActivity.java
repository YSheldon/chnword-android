package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        if (!LibsChecker.checkVitamioLibs(this))
            return;

        Intent intent = getIntent();
        String word = intent.getStringExtra("word");
        String word_index = intent.getStringExtra("word_index");

        this.word = new Word();
        this.word.setWordIndex(word_index);
        this.word.setWord(word);

        videoFragment = new VideoFragment();
        gifFragment = new GifFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, videoFragment).commit();



        try {
            Log.e(TAG, " counts .");
            GifDrawable drawable = new GifDrawable(getResources(), R.drawable.sample);
            Log.e(TAG, "NUMBER COUNT " + drawable.getNumberOfFrames());
//            Bitmap bitmap = drawable.getCurrentFrame();
            for (int i = 0; i < drawable.getNumberOfFrames(); i ++ ) {
//                drawable.seekToFrameAndGet();

            }

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

        requestNet("1");

    }

    private void requestNet(String wordCode) {
        String userid = "1";
        String deviceId = "1";
//        JSONObject param = NetParamFactory.subListParam(userid, deviceId, zoneCode, 0, 0);
        JSONObject param = NetParamFactory.showParam(userid, deviceId, wordCode);
        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_SHOW);
        progressDialog = ProgressDialog.show(this, "title", "loading");
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
    public void onBackStage(View view) {
        onBackPressed();
        finish();
    }

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


    public void onScan(View view) {
        //切换到scan页面
        Intent scanIntent = new Intent(this, ScanActivity.class);

        startActivity(scanIntent);
        finish();
    }


    public void onChangePosition(float position) {
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.remove(gifFragment);
        tx.show(videoFragment);
        tx.commit();
        videoFragment.onChangePosition(position);
        videoFragment.start();

    }


}
