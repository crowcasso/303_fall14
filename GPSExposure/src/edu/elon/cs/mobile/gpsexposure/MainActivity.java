package edu.elon.cs.mobile.gpsexposure;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView latTextView;
	private TextView longTextView;
	private TextView accTextView;
	
	private LocationManager locManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		latTextView = (TextView) findViewById(R.id.tvlatitude);
		longTextView = (TextView) findViewById(R.id.tvlongitude);
		accTextView = (TextView) findViewById(R.id.tvaccuracy);
		
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
				0, 0, locationListener);
		locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 
				0, 0, locationListener);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		locManager.removeUpdates(locationListener);
	}
	
	private LocationListener locationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			float accuracy = location.getAccuracy();
			
			latTextView.setText("latitude: " + latitude);
			longTextView.setText("longitude: " + longitude);
			accTextView.setText("accuracy: " + accuracy);
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
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






