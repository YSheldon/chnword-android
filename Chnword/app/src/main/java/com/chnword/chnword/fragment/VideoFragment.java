package com.chnword.chnword.fragment;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Word;

/**
 * Created by khtc on 15/5/30.
 */
public class VideoFragment extends Fragment {

    private VideoView videoView;
    private Uri uri;
    private Word word;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        videoView = (VideoView) view.findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(this.getActivity()));

        String path = this.getActivity().getAssets().;
        videoView.setVideoURI();


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

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
}
