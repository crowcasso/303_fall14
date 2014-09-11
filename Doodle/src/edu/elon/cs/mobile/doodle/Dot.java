package edu.elon.cs.mobile.doodle;

import android.graphics.Paint;

public class Dot {

	protected int x;
	protected int y;
	protected int r;
	protected Paint paint;
	
	public Dot(int x, int y, int r) {
		this.x = x;
		this.y = y;
		this.r = r;
		
		// random color
		paint = new Paint();
		int alpha = (int) (Math.random() * 256);
		int red = (int) (Math.random() * 256);
		int green = (int) (Math.random() * 256);
		int blue = (int) (Math.random() * 256);
		paint.setARGB(alpha, red, green, blue);
	}
}
