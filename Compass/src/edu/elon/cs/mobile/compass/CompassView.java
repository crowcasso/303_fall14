package edu.elon.cs.mobile.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CompassView extends ImageView {
	
	private float mAzimuth;
	private float cx, cy;

	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mAzimuth = 0.0f;
		Drawable  image = getResources().getDrawable(R.drawable.compass);
		cx = image.getIntrinsicWidth() / 2;
		cy = image.getIntrinsicHeight() / 2;
	}
	
	public void setAzimuth(float azimuth) {
		mAzimuth = (azimuth + 360) % 360;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.rotate(mAzimuth, cx, cy);
		super.onDraw(canvas);
	}

}




