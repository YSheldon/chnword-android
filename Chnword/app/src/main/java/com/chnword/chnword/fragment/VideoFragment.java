package com.chnword.chnword.fragment;

import android.app.Fragment;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
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
public class VideoFragment extends Fragment {

    private VideoView videoView;
    private Uri uri;
    private Word word;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        videoView = (VideoView) view.findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(this.getActivity()));

//        String path = this.getActivity().getAssets().;
//        videoView.setVideoURI();
//        Uri uri = Uri.parse("file:///android_asset/cft.mp4");
//        videoView.setVideoURI(uri);
//        videoView.start();

        try {
            AssetFileDescriptor descriptor = getActivity().getAssets().openFd("cft.mp4");

            FileDescriptor fileDescriptor = descriptor.getFileDescriptor();
//            Uri uri1 = Uri.p

//            videoView.set
            String uri1 = "android.resource://" + this.getActivity().getPackageName() + "/" + "cft.mp4";


            videoView.setVideoURI(Uri.parse(uri1));
            videoView.start();
        } catch (IOException e) {
            e.printStackTrace();
        }




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
