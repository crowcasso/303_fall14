package edu.elon.cs.mobile.doodle;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DoodleView extends View {
	
	private final int WIDTH = 30;
	private ArrayList<Dot> dots;

	public DoodleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		dots = new ArrayList<Dot>();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int action = event.getActionMasked();
		
		if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
			Dot dot = new Dot((int) event.getX(), (int) event.getY(), WIDTH);
			dots.add(dot);
			invalidate();
			System.out.println("FOUND A TOUCH!");
		}
		
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		for (Dot dot : dots) {
			canvas.drawCircle(dot.x, dot.y, dot.r, dot.paint);
		}
	}

}



