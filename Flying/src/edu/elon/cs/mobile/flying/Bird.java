package edu.elon.cs.mobile.flying;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Bird {

	protected float x, y;
	private float width, height;
	private Bitmap bitmap;
	
	private final float SCALE = 0.75f;
	
	public Bird(Context context) {
		bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird);
		
		width = bitmap.getWidth() * SCALE;
		height = bitmap.getHeight() * SCALE;
		
		x = width/2;
		y = height/2;
	}
	
	public void doDraw(Canvas canvas) {
		canvas.drawBitmap(bitmap, 
				null,
				new Rect((int) (x - width/2), (int) (y - height/2),
						 (int) (x + width/2), (int) (y + height/2)),
				null);
	}
	
	public void doUpdate(double elapsed, float touchX, float touchY) {
		x = (float) (x + ((touchX - x) * elapsed));
		y = (float) (y + ((touchY - y) * elapsed));
	}
	
}





