package edu.elon.cs.mobile.searchtwitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
	
	private String bearerToken = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	
	private class ObtainBearerToken extends AsyncTask<Void, Void, String> {
		
		private final String BEARER_TOKEN_URL = "https://api.twitter.com/oauth2/token";
		private final String API_KEY = "nnnnnnn";
		private final String API_SECRET = "mmmmmmm";

		@Override
		protected String doInBackground(Void... params) {
			
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(BEARER_TOKEN_URL);
			
			// encode the KEY and the SECRET
			String apiString = API_KEY + ":" + API_SECRET;
			String authorization = "Basic " + 
					Base64.encodeToString(apiString.getBytes(), Base64.NO_WRAP);
			
			// set POST headers and body
			post.setHeader("Authorization", authorization);
			post.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
			
			try {
				post.setEntity(new StringEntity("grant_type=client_credentials"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			StringBuilder builder = new StringBuilder();
			try {
				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				InputStream inStream = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
				String line = null;
				while ((line = reader.readLine()) != null) {
					builder.append(line + "\n");
				}
				inStream.close();
				reader.close();
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return builder.toString();
		}
		
		@Override
		protected void onPostExecute(String result) {
			StringBuilder tweetResultBuilder = new StringBuilder();
			
			try {
				JSONObject resultObject = new JSONObject(result);
				bearerToken = resultObject.getString("access_token");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			searchButton.setVisibility(View.VISIBLE);
		}
		
	}
}



