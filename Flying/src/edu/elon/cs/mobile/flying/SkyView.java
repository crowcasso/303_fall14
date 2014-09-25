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
		if (thread.getState() == Thread.State.TERMINATED) {
			thread = new SkyViewThread(context);
		}
		
		thread.setIsRunning(true);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setIsRunning(false);
		
		while (retry) {
			try {
				thread.join();
				retry = false;
			}
			catch (InterruptedException e) { }
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		thread.onTouchEvent(event);
		return true;
	}
	
	// Game Loop Thread
	private class SkyViewThread extends Thread {
		
		private Bird bird;
		private float touchX, touchY;
		private boolean isRunning;
		private long lastTime;
		
		private int frames;
		private long nextUpdate;
		
		public SkyViewThread(Context context) {
			isRunning = false;
			bird = new Bird(context);
			
			frames = 0;
		}
		
		public void setIsRunning(boolean isRunning) {
			this.isRunning = isRunning;
		}
		
		public void onTouchEvent(MotionEvent event) {
			touchX = event.getX();
			touchY = event.getY();
		}
		
		private void doDraw(Canvas canvas) {
			canvas.drawColor(Color.YELLOW);
			bird.doDraw(canvas);
		}
		
		private void doUpdate(double elapsed) {
			bird.doUpdate(elapsed, touchX, touchY);
		}
		
		private void updateFPS(long now) {
			float fps = 0.0f;
			++frames;
			float overtime = now - nextUpdate;
			if (overtime > 0) {
				fps = frames / (1 + overtime/1000.0f);
				frames = 0;
				nextUpdate = System.currentTimeMillis() + 1000;
				
				System.out.println("FPS: " + fps);
			}
		}
		
		@Override
		public void run() {
			
			lastTime = System.currentTimeMillis() + 100;
			
			while (isRunning) {
				Canvas canvas = null;
				try {
					canvas = surfaceHolder.lockCanvas();
					if (canvas == null) {
						isRunning = false;
						continue;
					}
					
					synchronized(surfaceHolder) {
						long now = System.currentTimeMillis();
						double elapsed = (now - lastTime) / 1000.0;
						lastTime = now;
						
						updateFPS(now);
						doUpdate(elapsed);
						doDraw(canvas);
					}
				} finally {
					if (canvas != null) {
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
			
		}
		
	}
}