package edu.elon.cs.mobile.compass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements SensorEventListener {
	
	private SensorManager mSensorManager;
	
	private float[] magneticFieldValues = null;
	private float[] gravityValues = null;
	
	private float[] rMatrix = new float[9];
	private float[] iMatrix = new float[9];
	private float[] actualOrientation = new float[3];
	
	private CompassView compass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        compass = (CompassView) findViewById(R.id.compassview);
    }
    
    @Override
    public void onResume() {
    		super.onResume();
    		
    		mSensorManager.registerListener(this,
    				mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
    				SensorManager.SENSOR_DELAY_FASTEST);
    		mSensorManager.registerListener(this,
    				mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
    				SensorManager.SENSOR_DELAY_FASTEST);
    }
    
    @Override
    public void onPause() {
    		super.onPause();
    		
    		mSensorManager.unregisterListener(this);
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		switch(event.sensor.getType()) {
		case Sensor.TYPE_GRAVITY:
			gravityValues = event.values.clone();
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			magneticFieldValues = event.values.clone();
			break;
		}
		
		if (magneticFieldValues != null && gravityValues != null) {
			if (SensorManager.getRotationMatrix(rMatrix, iMatrix, gravityValues, magneticFieldValues)) {
				SensorManager.getOrientation(rMatrix, actualOrientation);
				
				float currentOrientation = (float) -Math.toDegrees(actualOrientation[0]);
				compass.setAzimuth(currentOrientation);
				compass.invalidate();
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
}







