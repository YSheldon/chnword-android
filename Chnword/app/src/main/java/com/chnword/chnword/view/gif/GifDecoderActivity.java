package com.chnword.chnword.view.gif;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.chnword.chnword.R;


public class GifDecoderActivity extends Activity implements GifDecoder.DecodeResult {
	
	static{
		System.loadLibrary("gif");
	}
	
//	private ImageView view1;
//	private ImageView view2;
//	private ImageView view3;
//	private ImageView view4;

//	private static String FILE_PATH = "/sdcard/0";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.setContentView(R.layout.main);
//		findViews();
		try {
			GifDecoder.decode("/sdcard/animal.gif", this);
		} catch (FileNotFoundException e) {
			;
		}
	}

	@Override
	public void onDecodeFinished(int count) {
		GifView view = new GifView(this);
		view.setImages(GifDecoder.getImage());
		this.setContentView(view);
	}
	
//	private void findViews(){
//		view1 = (ImageView)this.findViewById(R.id.imgFirst);
//		view2 = (ImageView)this.findViewById(R.id.imgSecond);
//		view3 = (ImageView)this.findViewById(R.id.imgThird);
//		view4 = (ImageView)this.findViewById(R.id.imgForth);
//	}
}
