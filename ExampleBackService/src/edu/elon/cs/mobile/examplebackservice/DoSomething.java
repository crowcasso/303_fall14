package edu.elon.cs.mobile.examplebackservice;

import java.sql.Timestamp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

public class DoSomething extends IntentService {

	public DoSomething() {
		super("DoSomething");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		System.out.println(time);
	}

}
