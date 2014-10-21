package edu.elon.cs.mobile.multitouch;

import android.graphics.Paint;

public class Touch {
	
	protected float x;
	protected float y;
	protected float p;
	protected int pointerID;
	protected Paint paint;
	
	public Touch(float x, float y, float p, int pointerID, Paint paint) {
		this.x = x;
		this.y = y;
		this.p = p;
		this.pointerID = pointerID;
		this.paint = paint;
	}

}
