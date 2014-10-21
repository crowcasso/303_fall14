package edu.elon.cs.mobile.multitouch;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MultitouchView extends View {

	private ArrayList<Touch> touches;

	public MultitouchView(Context context, AttributeSet attrs) {
		super(context, attrs);

		touches = new ArrayList<Touch>();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// get pointer index from event object
		int pointerIndex = event.getActionIndex();

		// get pointer ID
		int pointerID = event.getPointerId(pointerIndex);

		// get masked (not specific to a pointer) action
		int maskedAction = event.getActionMasked();

		switch(maskedAction) {

		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN: {
			// we have a new pointer. Lets add it to the list of pointers
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			paint.setColor(Color.argb(255, (int)(Math.random() * 256), 
					(int)(Math.random() * 256), (int)(Math.random() * 256)));
			Touch touch = new Touch(event.getX(pointerIndex),
					event.getY(pointerIndex), event.getPressure(pointerIndex),
					pointerID, paint);
			touches.add(touch);
			break;
		}
		case MotionEvent.ACTION_MOVE : {
			// pointer was moved
			for (int i = 0; i < event.getPointerCount(); i++) {
				for (Touch touch : touches) {
					if (touch.pointerID == event.getPointerId(i)) {
						touch.x = event.getX(i);
						touch.y = event.getY(i);
						touch.p = event.getPressure(i);
					}
				}
			}
			break;
		}
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_CANCEL: {
			for (int i = 0; i < touches.size(); i++) {
				if (touches.get(i).pointerID == pointerID) {
					touches.remove(i);
				}
			}
			break;
		}	
		} // end of the switch

		invalidate();

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (Touch touch : touches) {
			canvas.drawCircle(touch.x, touch.y, 200 * touch.p, touch.paint);
		}
	}

}







