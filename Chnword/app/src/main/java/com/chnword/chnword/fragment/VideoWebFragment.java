package com.chnword.chnword.fragment;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Word;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by khtc on 15/10/26.
 */
public class VideoWebFragment extends Fragment {
    private String TAG = VideoWebFragment.class.getSimpleName();

    private Uri uri;
    private Word word;

    private String path = "http://app.3000zi.com/upload/video/ebbb0cf8d6547612db98d061cf556baf.mp4";
    private WebView mVideoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_videoweb, container, false);

        mVideoView = (WebView) view.findViewById(R.id.mVideoView);
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
