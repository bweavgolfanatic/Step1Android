package edu.wm.step1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Inbox extends Activity{

	private Button newMessage;
	private ListView emailList;
	private List<String> listContents = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inbox);
		newMessage = (Button) findViewById(R.id.newmessage);
		emailList = (ListView) findViewById(R.id.emailList);
		newMessage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Inbox.this, ComposeMessage.class);
				startActivity(i);
			}
		});            
		SplashScreen.myClient.get("http://step1.herokuapp.com/my_messages.json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray responseMSG) {
				listContents.clear();
				for (int i = 0; i < responseMSG.length(); i++) {
					JSONObject row;
					try {
						row = responseMSG.getJSONObject(i);
						listContents.add(row.getString("body"));
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
				emailList.setAdapter(adapter);
				emailList.setOnItemClickListener( new OnItemClickListener() {


					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long id) {
						String post = adapter.getItem(position);
						Log.v("clicking post", "post: " + post);
						//eventually go to post in ViewPost (with comments, etc.)
						Context context = getApplicationContext();
						Intent intent = new Intent(context, ViewEmail.class);
						intent.putExtra("post", post);
						startActivity(intent);
					}
				});
			}
			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d("errorrrr", "User Posts didn't work" );        
			}


		});

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		newMessage = (Button) findViewById(R.id.newmessage);
		emailList = (ListView) findViewById(R.id.emailList);
		newMessage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Inbox.this, ComposeMessage.class);
				startActivity(i);
			}
		});            
		SplashScreen.myClient.get("http://step1.herokuapp.com/my_messages.json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray responseMSG) {
				
				listContents.clear();
				for (int i = 0; i < responseMSG.length(); i++) {
					JSONObject row;
					try {
						row = responseMSG.getJSONObject(i);
						listContents.add(row.getString("body"));
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
				emailList.setAdapter(adapter);
				emailList.setOnItemClickListener( new OnItemClickListener() {


					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long id) {
						String post = adapter.getItem(position);
						Log.v("clicking post", "post: " + post);
						//eventually go to post in ViewPost (with comments, etc.)
						Context context = getApplicationContext();
						Intent intent = new Intent(context, ViewEmail.class);
						intent.putExtra("post", post);
						startActivity(intent);
					}
				});
			}
			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d("errorrrr", "User Posts didn't work" );        
			}


		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		newMessage = (Button) findViewById(R.id.newmessage);
		emailList = (ListView) findViewById(R.id.emailList);
		newMessage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Inbox.this, ComposeMessage.class);
				startActivity(i);
			}
		});            
		SplashScreen.myClient.get("http://step1.herokuapp.com/my_messages.json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray responseMSG) {
				listContents.clear();
				for (int i = 0; i < responseMSG.length(); i++) {
					JSONObject row;
					try {
						row = responseMSG.getJSONObject(i);
						listContents.add(row.getString("body"));
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
				emailList.setAdapter(adapter);
				emailList.setOnItemClickListener( new OnItemClickListener() {


					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long id) {
						String post = adapter.getItem(position);
						Log.v("clicking post", "post: " + post);
						//eventually go to post in ViewPost (with comments, etc.)
						Context context = getApplicationContext();
						Intent intent = new Intent(context, ViewEmail.class);
						intent.putExtra("post", post);
						startActivity(intent);
					}
				});
			}
			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d("errorrrr", "User Posts didn't work" );        
			}


		});
	}

}