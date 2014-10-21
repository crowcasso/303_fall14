package edu.elon.cs.mobile.examplenotification;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		NotificationCompat.Builder mBuilder = 
				new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("My notification")
				.setContentText("Hello world!");
		NotificationManager notificationManager = 
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(3, mBuilder.build());
	}
}
