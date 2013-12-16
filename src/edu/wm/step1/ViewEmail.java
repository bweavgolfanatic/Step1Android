package edu.wm.step1;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewEmail extends Activity{
	private Button returnButton;
	private Button replyButton;
	private TextView fromText;
	private TextView subText;
	private TextView bodyText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewanemail);
		Intent intent = getIntent();
		String post = intent.getStringExtra("post");
		RequestParams params = new RequestParams();
		returnButton = (Button) findViewById(R.id.retrn);
		replyButton = (Button) findViewById(R.id.reply);
		returnButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ViewEmail.this,Inbox.class);
				startActivity(i);
				finish();

			}
		});

		replyButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ViewEmail.this,ComposeMessage.class);
				startActivity(i);
				finish();

			}
		});
		params.put("body", post);
		SplashScreen.myClient.get(getApplicationContext(),"http://step1.herokuapp.com/get_email.json",params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					fromText = (TextView) findViewById(R.id.whofrom);
					bodyText = (TextView) findViewById(R.id.body);
					subText = (TextView) findViewById(R.id.sub);
					fromText.setText(response.getString("sender"));
					bodyText.setText(response.getString("body"));
					subText.setText(response.getString("subject"));                        

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
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Intent intent = getIntent();
		String post = intent.getStringExtra("post");
		RequestParams params = new RequestParams();
		returnButton = (Button) findViewById(R.id.retrn);
		replyButton = (Button) findViewById(R.id.reply);
		returnButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ViewEmail.this,Inbox.class);
				startActivity(i);
				finish();

			}
		});

		replyButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ViewEmail.this,ComposeMessage.class);
				startActivity(i);
				finish();

			}
		});
		params.put("body", post);
		SplashScreen.myClient.get(getApplicationContext(),"http://step1.herokuapp.com/get_email.json",params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					fromText = (TextView) findViewById(R.id.whofrom);
					bodyText = (TextView) findViewById(R.id.body);
					subText = (TextView) findViewById(R.id.sub);
					fromText.setText(response.getString("sender"));
					bodyText.setText(response.getString("body"));
					subText.setText(response.getString("subject"));                        

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

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Intent intent = getIntent();
		String post = intent.getStringExtra("post");
		RequestParams params = new RequestParams();
		returnButton = (Button) findViewById(R.id.retrn);
		replyButton = (Button) findViewById(R.id.reply);
		returnButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ViewEmail.this,Inbox.class);
				startActivity(i);
				finish();

			}
		});

		replyButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ViewEmail.this,ComposeMessage.class);
				startActivity(i);
				finish();

			}
		});
		params.put("body", post);
		SplashScreen.myClient.get(getApplicationContext(),"http://step1.herokuapp.com/get_email.json",params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					fromText = (TextView) findViewById(R.id.whofrom);
					bodyText = (TextView) findViewById(R.id.body);
					subText = (TextView) findViewById(R.id.sub);
					fromText.setText(response.getString("sender"));
					bodyText.setText(response.getString("body"));
					subText.setText(response.getString("subject"));                        

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
	}

}