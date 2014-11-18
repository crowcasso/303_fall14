package edu.elon.cs.mobile.examplebackservice;

import java.sql.Timestamp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

public class DoSomething extends IntentService {

	private LocationManager locManager;
	private boolean gotFix = false;
	
	public DoSomething() {
		super("DoSomething");
	}
	

	@Override
	protected void onHandleIntent(Intent intent) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		System.out.println(time);
		
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
				0, 0, locationListener, Looper.getMainLooper());
		locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 
				0, 0, locationListener, Looper.getMainLooper());
		
		while (!gotFix) {
			try {
				System.out.println("waiting for gotFix!");
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("done!");
		locManager.removeUpdates(locationListener);
	}
	
	private LocationListener locationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			float accuracy = location.getAccuracy();
			
			System.out.println(latitude + "," + longitude + "," + accuracy);
			
			gotFix = true;
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			System.out.println("onStatusChanged");
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
	};

}
