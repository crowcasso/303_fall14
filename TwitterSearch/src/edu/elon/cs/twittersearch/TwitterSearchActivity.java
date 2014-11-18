package edu.elon.cs.twittersearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;

public class TwitterSearchActivity extends Activity {
	
	private String bearerToken = null;
	private TextView tweetDisplay;
	private Button searchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter_search);
		
		tweetDisplay = (TextView) findViewById(R.id.tweetText);
		searchButton = (Button) findViewById(R.id.searchButton);
		
		searchButton.setVisibility(View.INVISIBLE);
		
		new ObtainBearerToken().execute();
	}
	
	public void searchTwitter(View view) {
		EditText searchText = (EditText) findViewById(R.id.searchEdit);
		String searchTerm = searchText.getText().toString();
		
		if (searchTerm.length() > 0) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(searchText.getApplicationWindowToken(), 0);
			
			new GetTweets().execute(searchTerm);
		} else {
			tweetDisplay.setText("Enter a search query!");
		}
	}
	
	private class GetTweets extends AsyncTask<String, Void, String> {
		
		private final String TWITTER_URL = "https://api.twitter.com/1.1/search/tweets.json?q=";

		@Override
		protected String doInBackground(String... searchTerms) {
			
			String searchTerm = searchTerms[0];
			String searchURL = null;
			
			// build a search URL around the search term
			try {
				String encodedSearchTerm = URLEncoder.encode(searchTerm, "UTF-8");
				searchURL = TWITTER_URL + encodedSearchTerm;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(searchURL);
			get.setHeader("Authorization", "Bearer " + bearerToken);
			get.setHeader("Content-type", "application/json");
			
			// put the response into a StringBuilder
			StringBuilder builder = new StringBuilder();
			try {
				HttpResponse response = client.execute(get);
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
				JSONArray tweetArray = resultObject.getJSONArray("statuses");
				
				for (int t = 0; t < tweetArray.length(); t++) {
					JSONObject tweetObject = tweetArray.getJSONObject(t);
					JSONObject userObject = new JSONObject(tweetObject.getString("user"));
					tweetResultBuilder.append(userObject.getString("name") + ": ");
					tweetResultBuilder.append(tweetObject.getString("text") + "\n\n");
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			if (tweetResultBuilder.length() > 0) {
				tweetDisplay.setText(tweetResultBuilder.toString());
			} else {
				tweetDisplay.setText("Sorry -- no tweets found for your search.");
				
			}
			
		}
		
	}


	private class ObtainBearerToken extends AsyncTask<Void, Void, String> {
		
		private final String BEARER_TOKEN_URL = "https://api.twitter.com/oauth2/token";
		private final String API_KEY = "NMTAvcikZ2WH8wVSyUcw";
		private final String API_SECRET = "hrzOIEVlEWnuQaSOkPdFyIc0yzLXNMNP5RGhpTzpDw";
		

		@Override
		protected String doInBackground(Void... params) {
			
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(BEARER_TOKEN_URL);
			
			// encode the KEY and the SECRET
			String apiString = API_KEY + ":" + API_SECRET;
			String authorization = "Basic " + Base64.encodeToString(apiString.getBytes(), Base64.NO_WRAP);
			
			// set POST headers and body
			post.setHeader("Authorization", authorization);
			post.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
			
			try {
				post.setEntity(new StringEntity("grant_type=client_credentials"));
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			// POST and wait for a response, build the response into a String
			StringBuilder builder = new StringBuilder();
			try {
				HttpResponse response = client.execute(post);
				
				// build the response into the string
				HttpEntity entity = response.getEntity();
				InputStream inStream = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
				String line = null;
				while ((line = reader.readLine()) != null) {
					builder.append(line + "\n");
				}
				
				inStream.close();
				reader.close();
			}
			catch (ClientProtocolException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
			return builder.toString();
		}
		
		@Override
		protected void onPostExecute(String result) {
			// parse the JSON string
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