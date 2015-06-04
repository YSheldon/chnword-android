package com.chnword.chnword.fragment;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.VideoView;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Word;



import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by khtc on 15/5/30.
 */
public class VideoFragment extends Fragment {
    private static final String TAG = VideoFragment.class.getSimpleName();

    private Uri uri;
    private Word word;

    private String path = "http://forum.ea3w.com/coll_ea3w/attach/2008_10/12237832415.3gp";
    private VideoView mVideoView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_video, container, false);

        mVideoView = (VideoView) view.findViewById(R.id.surface_view);


        mVideoView.setVideoPath(path);
        mVideoView.setMediaController(new MediaController(this.getActivity()));
        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });



        


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
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
//        videoView.setVideoURI(uri);
//        videoView.start();
//        videoView.requestFocus();
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

}
