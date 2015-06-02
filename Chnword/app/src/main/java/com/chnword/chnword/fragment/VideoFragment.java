package com.chnword.chnword.fragment;

import android.app.Fragment;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Word;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by khtc on 15/5/30.
 */
public class VideoFragment extends Fragment implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback {
    private static final String TAG = VideoFragment.class.getSimpleName();

    private VideoView videoView;
    private Uri uri;
    private Word word;


    private MediaPlayer mMediaPlayer;
    private SurfaceView mPreview;
    private SurfaceHolder holder;

    private int mVideoWidth;
    private int mVideoHeight;
    private boolean mIsVideoSizeKnown = false;
    private boolean mIsVideoReadyToBePlayed = false;
    private String path;


    private int errorTime = 0;

//    http://www.cnblogs.com/devinzhang/archive/2012/02/04/2338125.html
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_video, container, false);

        videoView = (VideoView) view.findViewById(R.id.video_view);
        MediaController mediaController = new MediaController(this.getActivity());

        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        mediaController.setAnchorView(videoView);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer = mp;
                mp.start();
                mp.setLooping(true);
                Log.e(TAG, "topVideoView.setOnPreparedListener -mp.start()");
            }
        });
        /*
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e(TAG, what + " " + extra);
                if (errorTime < 6) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            startTest();
                            videoView.start();
                        }
                    }, 2000);
                    errorTime++;
                    return true;
                }
                return false;
            }
        });
        */


//        videoView.start();

//        mPreview = videoView;

//        mPreview = (SurfaceView) view.findViewById(R.id.surfaceView);
//        holder = mPreview.getHolder();
//        holder = mPreview.getHolder();
//        holder.addCallback(this);
//        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        videoView.setVideoURI(Uri.parse("http://forum.ea3w.com/coll_ea3w/attach/2008_10/12237832415.3gp"));
//        videoView.setVideoURI( Uri.parse("rtsp://v2.cache2.c.youtube.com/CjgLENy73wIaLwm3JbT_%ED%AF%80%ED%B0%819HqWohMYESARFEIJbXYtZ29vZ2xlSARSB3Jlc3VsdHNg_vSmsbeSyd5JDA==/0/0/0/video.3gp"));

        videoView.requestFocus();
        videoView.start();
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    // setter and getter

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {

        if (uri == null) {
            return ;
        }
        this.uri = uri;
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }


    public MediaPlayer.OnErrorListener videoErrorListener = new MediaPlayer.OnErrorListener()
    {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            Log.e(TAG, what + " " + extra);
            return false;
        }
    };





    private void playVideo() {
        doCleanUp();
        try {
            /*
             * Set path variable to progressive streamable mp4 or 3gpp
             * format URL. Http protocol should be used. Mediaplayer can only
             * play "progressive streamable contents" which basically means: 1.
             * the movie atom has to precede all the media data atoms. 2. The
             * clip has to be reasonably interleaved.
             */
            path = "http://www.androidbook.com/akc/filestorage/android/documentfiles/3389/movie.mp4";
            path = "http://forum.ea3w.com/coll_ea3w/attach/2008_10/12237832415.3gp";

            // Create a new media player and set the listeners
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.setDisplay(holder);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mMediaPlayer.start();

        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage(), e);
        }
    }


    public void onBufferingUpdate(MediaPlayer arg0, int percent) {
        Log.d(TAG, "onBufferingUpdate percent:" + percent);

    }

    public void onCompletion(MediaPlayer arg0) {
        Log.d(TAG, "onCompletion called");
    }

    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.v(TAG, "onVideoSizeChanged called");
        if (width == 0 || height == 0) {
            Log.e(TAG, "invalid video width(" + width + ") or height(" + height
                    + ")");
            return;
        }
        mIsVideoSizeKnown = true;
        mVideoWidth = width;
        mVideoHeight = height;
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            startVideoPlayback();
        }
    }

    public void onPrepared(MediaPlayer mediaplayer) {
        Log.d(TAG, "onPrepared called");
        mIsVideoReadyToBePlayed = true;
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            startVideoPlayback();
        }
    }

    public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
        Log.e(TAG, "surfaceChanged called");
    }

    public void surfaceDestroyed(SurfaceHolder surfaceholder) {
        Log.e(TAG, "surfaceDestroyed called");
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG, "surfaceCreated called");
        playVideo();

    }

    @Override
    public void onPause() {
        super.onPause();
        releaseMediaPlayer();
        doCleanUp();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
        doCleanUp();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void doCleanUp() {
        mVideoWidth = 0;
        mVideoHeight = 0;
        mIsVideoReadyToBePlayed = false;
        mIsVideoSizeKnown = false;
    }

    private void startVideoPlayback() {
        Log.v(TAG, "startVideoPlayback");
        holder.setFixedSize(mVideoWidth, mVideoHeight);
        mMediaPlayer.start();
    }
}
