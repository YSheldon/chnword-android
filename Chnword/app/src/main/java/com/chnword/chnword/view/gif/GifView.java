package com.chnword.chnword.view.gif;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class GifView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder mHolder;
	private Gif mGif;
	private boolean isReady = false;
	private Rect mdRc;
	private Rect msRc;
	private Paint mPaint;
	GestureDetector mGestureDetector;
	private int mCurrentImage;

	public GifView(Context context) {
		super(context);
		mHolder = this.getHolder();
		mHolder.addCallback(this);
		mPaint = new Paint();
		mGestureDetector = new GestureDetector(gestureListener);
	}

	public void setImages(Gif gif) {
		this.mGif = gif;
		init();
	}

	private void init() {
		if (isReady && mGif != null) {
			msRc = new Rect(0, 0, mGif.getWidth(), mGif.getHeight());
			Rect vRc = new Rect();
			getLocalVisibleRect(vRc);
			mdRc = getDstRc(msRc, vRc);
			mHandler.removeCallbacksAndMessages(null);
			mHandler.sendEmptyMessage(0);
		}
	}

	private Rect getDstRc(Rect image, Rect view) {
		double xRate = view.width() * 1.0 / image.width();
		double yRate = view.height() * 1.0 / image.height();
		if (xRate > yRate) {
			return new Rect((int) (view.width() - image.width() * yRate) / 2, 0, (int) (image.width() * yRate) + (int) (view.width() - image.width() * yRate)
					/ 2, (int) (image.height() * yRate));
		} else {
			return new Rect(0, (int) (view.height() - image.height() * xRate) / 2, (int) (image.width() * xRate), (int) (image.height() * xRate)
					+ (int) (view.height() - image.height() * xRate) / 2);
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int idx = msg.what;
			if (idx < 0 || idx >= mGif.getFrameCount())
				idx = 0;
			mCurrentImage = idx;
			Gif.Frame f = mGif.getFrame(idx);
			if(f == null){
				Log.e("Ray", "f = null when idx = " + idx);
				this.sendEmptyMessageDelayed(idx, 100);
				return;
			}
			Rect rc = new Rect(mdRc);
			Canvas cv = mHolder.lockCanvas(rc);
			if (rc.equals(mdRc)) {
				cv.drawBitmap(f.getImage(), msRc, mdRc, mPaint);
				mHolder.unlockCanvasAndPost(cv);
				this.sendEmptyMessageDelayed(++idx, f.getDelay());
			} else {
				mHolder.unlockCanvasAndPost(cv);
				this.sendEmptyMessageDelayed(idx, 100);
			}
		}
	};

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		init();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		isReady = true;
		init();
		this.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return mGestureDetector.onTouchEvent(event);
			}});
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isReady = false;
		mHandler.removeCallbacksAndMessages(null);
	}
	
	private GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {
		
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			if(mGif.getFrame(mCurrentImage).isUserInput())
			{
				mHandler.removeMessages(mCurrentImage + 1);
				mHandler.handleMessage(mHandler.obtainMessage(mCurrentImage + 1));
			}
			return true;
		}
		
		@Override
		public void onShowPress(MotionEvent e) {
			;
		}
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			return true;
		}
		
		@Override
		public void onLongPress(MotionEvent e) {	
			;
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return true;
		}
		
		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}
	};
}