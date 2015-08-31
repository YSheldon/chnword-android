package com.chnword.chnword.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Word;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by khtc on 15/8/31.
 */
public class VideoActivity extends Activity
{
    private static String TAG = VideoActivity.class.getSimpleName();

    private Uri uri;
    private Word word;

    private String path = "http://app.3000zi.com/upload/video/ebbb0cf8d6547612db98d061cf556baf.mp4";
    private VideoView mVideoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);

        path = getIntent().getExtras().getString("videoUrl");

        mVideoView = (VideoView) findViewById(R.id.surface_view);


        mVideoView.setVideoPath(path);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
