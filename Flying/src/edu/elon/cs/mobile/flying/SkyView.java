package edu.elon.cs.mobile.flying;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SkyView extends SurfaceView implements SurfaceHolder.Callback {

	private Context context;
	private SurfaceHolder surfaceHolder;
	private SkyViewThread thread;
	
	public SkyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		
		thread = new SkyViewThread(context);
	}

	// SurfaceHolder.Callback
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		// FIXME later
		
		return true;
	}
	
	// Game Loop Thread
	private class SkyViewThread extends Thread {
		
		private Bird bird;
		private float touchX, touchY;
		
		public SkyViewThread(Context context) {
			
			bird = new Bird(context);
		}
		
		public void onTouchEvent(MotionEvent event) {
			touchX = event.getX();
			touchY = event.getY();
		}
		
		private void doDraw(Canvas canvas) {
			canvas.drawColor(Color.BLUE);
			bird.doDraw(canvas);
		}
		
		private void doUpdate(double elapsed) {
			bird.doUpdate(elapsed, touchX, touchY);
		}
		
		@Override
		public void run() {
			
		}
		
	}

}







