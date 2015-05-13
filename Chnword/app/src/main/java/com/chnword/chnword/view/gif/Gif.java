package com.chnword.chnword.view.gif;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class Gif {
	public class Frame {
		private int delayTime;
		private Bitmap image;
		private boolean userInput = false;

		public Frame(int delay, int[] color) {
			delayTime = delay;
			image = Bitmap.createBitmap(color, mWidth, mHeight, Config.RGB_565);
		}

		private Frame setUserInput() {
			userInput = true;
			return this;
		}

		public int getDelay() {
			return delayTime;
		}

		public Bitmap getImage() {
			return image;
		}

		public boolean isUserInput() {
			return userInput;
		}
	}

	private int mWidth;
	private int mHeight;
	private List<Frame> mFrames = new ArrayList<Frame>();

	public Gif(int width, int height) {
		mWidth = width;
		mHeight = height;
	}

	public int getWidth() {
		return mWidth;
	}

	public int getHeight() {
		return mHeight;
	}

	public void addFrame(int delay, int[] color, boolean userInput) {
		synchronized (mFrames) {
			if (!userInput)
				mFrames.add(new Frame(delay, color));
			else
				mFrames.add(new Frame(delay, color).setUserInput());
		}
	}

	public int getFrameCount() {
		synchronized (mFrames) {
			return mFrames.size();
		}
	}

	public Frame getFrame(int idx) {
		synchronized (mFrames) {
			if (idx < 0 || idx >= mFrames.size())
				return null;
			return mFrames.get(idx);
		}
	}
}
