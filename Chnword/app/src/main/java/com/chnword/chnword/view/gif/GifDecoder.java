package com.chnword.chnword.view.gif;

import java.io.File;
import java.io.FileNotFoundException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GifDecoder {

	private static final String MYTAG = "Ray";
	private static final String CLASS_NAME = "GifDecoder";

	public interface DecodeResult {
		public void onDecodeFinished(int count);
	}

	private static Gif sGif;
	private static DecodeResult sListener;
	private static boolean sIsReady = false;

	static void decode(String filePath, DecodeResult result) throws FileNotFoundException {
		File f = new File(filePath);
		if (f.exists()) {
			sListener = result;
			sIsReady = false;
			sGif = null;
			WorkThread thread = new WorkThread(filePath);
			thread.start();
		} else
			throw new FileNotFoundException("can not find file:" + filePath);
	}

	static Gif getImage() {
		return sGif;
	}

	private static void onDecodeFinished(String count) {
		Log.d(MYTAG, CLASS_NAME + ": onDecodeFinished, count = " + count);
		int c = Integer.parseInt(count);
		getFrames(c);
		if(c == 0)
			mHandler.obtainMessage(c).sendToTarget();
	}

	private static void getFrames(int idx) {
		if(idx == 0)
			sGif = new Gif(getWidth(), getHeight());
		sGif.addFrame(getDelay(idx), getColors(idx), getUserInput(idx));
	}

	private static class WorkThread extends Thread {
		private String mPath;

		public WorkThread(String path) {
			mPath = path;
		}

		@Override
		public void run() {
			doDecode(mPath);
		}
	}

	private static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			sListener.onDecodeFinished(msg.what);
		}
	};

	private static native void doDecode(String path);

	private static native int getWidth();

	private static native int getHeight();

	private static native int getDelay(int index);

	private static native boolean getUserInput(int index);

	private static native int[] getColors(int index);
}
