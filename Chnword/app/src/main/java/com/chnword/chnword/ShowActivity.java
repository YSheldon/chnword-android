package com.chnword.chnword;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;

/**
 * Created by khtc on 15/5/17.
 */
public class ShowActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show);

        GifImageButton gib = new GifImageButton( this );
        setContentView( gib );
        gib.setImageResource( R.drawable.sample );
        final MediaController mc = new MediaController( this );
        mc.setMediaPlayer( (GifDrawable) gib.getDrawable() );
        mc.setAnchorView( gib );
        gib.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick ( View v )
            {
                mc.show();
            }
        } );


    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
