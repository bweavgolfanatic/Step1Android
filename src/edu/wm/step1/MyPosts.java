package edu.wm.step1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class MyPosts extends Activity {

	private String LOG_TAG = "edu.wm.step1";

	private TextView username;
	private TextView numPosts;
	private TextView rating;
	private String usernameString;
	
	private Button uploadButton;
	private ListView listview;
	private List<String> listContents = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	
	private Intent intent;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_posts);

		setContentView(R.layout.my_posts);

		listview = (ListView) findViewById(R.id.listView1);
		listview.setClickable(true);

		username = (TextView)findViewById(R.id.textView1);
		rating = (TextView)findViewById(R.id.textView2);
		numPosts = (TextView)findViewById(R.id.textView3);

		uploadButton =(Button)findViewById(R.id.uploadButton);
		
		Intent intent = getIntent();
		String post = intent.getExtras().getString("username");
		Log.v(LOG_TAG,"username: "+ post);

		SplashScreen.myClient.get(getApplicationContext(),"http://step1.herokuapp.com/my_details.json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {

					username.setText(response.getString("username"));
					usernameString = response.getString("username");
					Log.v(LOG_TAG,usernameString);
					//intent.putExtra("username", usernameString);
					rating.setText(response.getString("rating"));
					numPosts.setText(response.getString("numposts"));
					


				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d("TAG", "get user details failed");        
			}



		});
		//String user = username.getText().toString();
		
		//populate list with username
		//usernameString = username.getText().toString();
		RequestParams params = new RequestParams();
		params.put("username", post);
		Log.v(LOG_TAG,"usernameString is " + post);

		
		SplashScreen.myClient.get("http://step1.herokuapp.com/user_posts.json", params,  new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject responseMSG) {

				Log.v(LOG_TAG, "populating user Posts List");
				Iterator<?> keys = responseMSG.keys();
				listContents.clear();
				while(keys.hasNext())
				{
					try {
						String key = (String)keys.next();
						Log.v(LOG_TAG, (String) responseMSG.get(key));

						listContents.add((String) responseMSG.get(key));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listContents){
					@Override
					public View getView(int position, View convertView, ViewGroup parent) {
						View view = super.getView(position, convertView, parent);
						TextView text = (TextView) view.findViewById(android.R.id.text1);
						text.setTextColor(Color.BLACK);
						return view;
					}
				};
				listview.setAdapter(adapter);
				listview.setOnItemClickListener( new OnItemClickListener() {


					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long id) {
						String post = adapter.getItem(position);
						Log.v(LOG_TAG, "post: " + post);
						//eventually go to post in ViewPost (with comments, etc.)
						Context context = getApplicationContext();
						Intent intent = new Intent(context, ViewPost.class);
						intent.putExtra("post", post);
						startActivity(intent);
					}
				});
			}
			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d(LOG_TAG, "User Posts didn't work" );        
			} 


		});

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		setContentView(R.layout.my_posts);

		setContentView(R.layout.my_posts);

		listview = (ListView) findViewById(R.id.listView1);
		listview.setClickable(true);

		username = (TextView)findViewById(R.id.textView1);
		rating = (TextView)findViewById(R.id.textView2);
		numPosts = (TextView)findViewById(R.id.textView3);

		uploadButton =(Button)findViewById(R.id.uploadButton);
		
		Intent intent = getIntent();
		String post = intent.getExtras().getString("username");
		Log.v(LOG_TAG,"username: "+ post);

		SplashScreen.myClient.get(getApplicationContext(),"http://step1.herokuapp.com/my_details.json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {

					username.setText(response.getString("username"));
					usernameString = response.getString("username");
					Log.v(LOG_TAG,usernameString);
					//intent.putExtra("username", usernameString);
					rating.setText(response.getString("rating"));
					numPosts.setText(response.getString("numposts"));
					


				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d("TAG", "get user details failed");        
			}



		});
		//String user = username.getText().toString();
		
		//populate list with username
		//usernameString = username.getText().toString();
		RequestParams params = new RequestParams();
		params.put("username", post);
		Log.v(LOG_TAG,"params is " + post);

		
		SplashScreen.myClient.get("http://step1.herokuapp.com/user_posts.json", params,  new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject responseMSG) {

				Log.v(LOG_TAG, "populating user Posts List");
				Iterator<?> keys = responseMSG.keys();
				listContents.clear();
				while(keys.hasNext())
				{
					try {
						String key = (String)keys.next();
						Log.v(LOG_TAG, (String) responseMSG.get(key));

						listContents.add((String) responseMSG.get(key));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listContents){
					@Override
					public View getView(int position, View convertView, ViewGroup parent) {
						View view = super.getView(position, convertView, parent);
						TextView text = (TextView) view.findViewById(android.R.id.text1);
						text.setTextColor(Color.BLACK);
						return view;
					}
				};
				listview.setAdapter(adapter);
				listview.setOnItemClickListener( new OnItemClickListener() {


					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long id) {
						String post = adapter.getItem(position);
						Log.v(LOG_TAG, "post: " + post);
						//eventually go to post in ViewPost (with comments, etc.)
						Context context = getApplicationContext();
						Intent intent = new Intent(context, ViewPost.class);
						intent.putExtra("post", post);
						startActivity(intent);
					}
				});
			}
			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d(LOG_TAG, "User Posts didn't work" );        
			} 


		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setContentView(R.layout.my_posts);

		listview = (ListView) findViewById(R.id.listView1);
		listview.setClickable(true);

		username = (TextView)findViewById(R.id.textView1);
		rating = (TextView)findViewById(R.id.textView2);
		numPosts = (TextView)findViewById(R.id.textView3);

		uploadButton =(Button)findViewById(R.id.uploadButton);
		
		Intent intent = getIntent();
		String post = intent.getExtras().getString("username");
		Log.v(LOG_TAG,"username: "+ post);

		SplashScreen.myClient.get(getApplicationContext(),"http://step1.herokuapp.com/my_details.json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {

					username.setText(response.getString("username"));
					usernameString = response.getString("username");
					Log.v(LOG_TAG,usernameString);
					//intent.putExtra("username", usernameString);
					rating.setText(response.getString("rating"));
					numPosts.setText(response.getString("numposts"));
					


				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d("TAG", "get user details failed");        
			}



		});
		//String user = username.getText().toString();
		
		//populate list with username
		//usernameString = username.getText().toString();
		RequestParams params = new RequestParams();
		params.put("username", post);
		Log.v(LOG_TAG,"params is " + post);

		
		SplashScreen.myClient.get("http://step1.herokuapp.com/user_posts.json", params,  new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject responseMSG) {

				Log.v(LOG_TAG, "populating user Posts List");
				Iterator<?> keys = responseMSG.keys();
				listContents.clear();
				while(keys.hasNext())
				{
					try {
						String key = (String)keys.next();
						Log.v(LOG_TAG, (String) responseMSG.get(key));

						listContents.add((String) responseMSG.get(key));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listContents){
					@Override
					public View getView(int position, View convertView, ViewGroup parent) {
						View view = super.getView(position, convertView, parent);
						TextView text = (TextView) view.findViewById(android.R.id.text1);
						text.setTextColor(Color.BLACK);
						return view;
					}
				};
				listview.setAdapter(adapter);
				listview.setOnItemClickListener( new OnItemClickListener() {


					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long id) {
						String post = adapter.getItem(position);
						Log.v(LOG_TAG, "post: " + post);
						//eventually go to post in ViewPost (with comments, etc.)
						Context context = getApplicationContext();
						Intent intent = new Intent(context, ViewPost.class);
						intent.putExtra("post", post);
						startActivity(intent);
					}
				});
			}
			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d(LOG_TAG, "User Posts didn't work" );        
			} 


		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_posts, menu);
		return true;
	}

}
