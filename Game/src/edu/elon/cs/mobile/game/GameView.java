package edu.elon.cs.mobile.game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	private Context context;
	private SurfaceHolder surfaceHolder;
	private GameViewThread thread;
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		
		thread = new GameViewThread(context);
	}

	// SurfaceHolder.Callback
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (thread.getState() == Thread.State.TERMINATED) {
			thread = new GameViewThread(context);
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
	
	// Game Loop Thread
	private class GameViewThread extends Thread {
		
		private boolean isRunning;
		private long lastTime;
		
		private int frames;
		private long nextUpdate;
		
		public GameViewThread(Context context) {
			isRunning = false;
			frames = 0;
		}
		
		public void setIsRunning(boolean isRunning) {
			this.isRunning = isRunning;
		}
		
		private void doDraw(Canvas canvas) {
			
		}
		
		private void doUpdate(double elapsed) {
			
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