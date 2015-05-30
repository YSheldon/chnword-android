package com.chnword.chnword;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;

import com.chnword.chnword.beans.Word;
import com.chnword.chnword.fragment.GifFragment;
import com.chnword.chnword.fragment.VideoFragment;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;

import org.json.JSONArray;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;

/**
 * Created by khtc on 15/5/17.
 */
public class ShowActivity extends Activity {
    private static final String TAG = ShowActivity.class.getSimpleName();

    private Word word ;
//    private GifImageButton gib;


    private Fragment fragment;

    private VideoFragment videoFragment;

    private Uri gifUri, videoUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Intent intent = getIntent();
        String word = intent.getStringExtra("word");
        String word_index = intent.getStringExtra("word_index");
//        word = new Word();
        this.word = new Word();
        this.word.setWordIndex(word_index);
        this.word.setWord(word);



        videoFragment = (VideoFragment) getFragmentManager().findFragmentById(R.id.fragment);




//        gib = new GifImageButton( this );
//        setContentView( gib );
//        gib.setImageResource( R.drawable.sample );

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


//        Uri uri = Uri.parse("");
//        gib.setImageURI(uri);
//        final MediaController mc = new MediaController( this );
//        mc.setMediaPlayer( (GifDrawable) gib.getDrawable() );
//        mc.setAnchorView( gib );
//        gib.setOnClickListener( new View.OnClickListener()
//        {
//            @Override
//            public void onClick ( View v )
//            {
//                mc.show();
//            }
//        } );


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

        GifFragment gifFragment = new GifFragment();
        gifFragment.setUri(gifUri);
        getFragmentManager().beginTransaction().add(R.id.fragment, gifFragment).commit();

        finish();
    }

    public void onScan(View view) {
        //切换到scan页面
        Intent scanIntent = new Intent(this, ScanActivity.class);

        startActivity(scanIntent);
        finish();
    }





}
