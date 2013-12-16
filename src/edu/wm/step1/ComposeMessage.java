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
import android.widget.EditText;

public class ComposeMessage extends Activity {
	private Button sendEmailButton;
	private EditText recipientText;
	private EditText messageText;
	private EditText subjectText;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compose_mail);
		sendEmailButton = (Button) findViewById(R.id.send_message);
		recipientText = (EditText) findViewById(R.id.email_recipient);
		messageText = (EditText) findViewById(R.id.message_body);
		subjectText = (EditText) findViewById(R.id.subject);
		sendEmailButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				RequestParams params= new RequestParams();
				recipientText = (EditText) findViewById(R.id.email_recipient);
				messageText = (EditText) findViewById(R.id.message_body);
				subjectText = (EditText) findViewById(R.id.subject);
				params.put("subject",subjectText.getText().toString());
				params.put("body", messageText.getText().toString());
				params.put("recipient", recipientText.getText().toString());
				Log.v("body", messageText.getText().toString());
				Log.v("subject", subjectText.getText().toString());
				Log.v("recipient", recipientText.getText().toString());
				SplashScreen.myClient.post("http://step1.herokuapp.com/send_message.json", params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						try {
							if (response.getString("message").equals("success")){
								Log.v("message sent", "woooo");
								Intent i = new Intent(ComposeMessage.this, Inbox.class);
								startActivity(i);
								finish();
							}                                                                
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
		});
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		sendEmailButton = (Button) findViewById(R.id.send_message);
		recipientText = (EditText) findViewById(R.id.email_recipient);
		messageText = (EditText) findViewById(R.id.message_body);
		subjectText = (EditText) findViewById(R.id.subject);
		sendEmailButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				RequestParams params= new RequestParams();
				recipientText = (EditText) findViewById(R.id.email_recipient);
				messageText = (EditText) findViewById(R.id.message_body);
				subjectText = (EditText) findViewById(R.id.subject);
				params.put("subject",subjectText.getText().toString());
				params.put("body", messageText.getText().toString());
				params.put("recipient", recipientText.getText().toString());
				Log.v("body", messageText.getText().toString());
				Log.v("subject", subjectText.getText().toString());
				Log.v("recipient", recipientText.getText().toString());
				SplashScreen.myClient.post("http://step1.herokuapp.com/send_message.json", params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						try {
							if (response.getString("message").equals("success")){
								Log.v("message sent", "woooo");
								Intent i = new Intent(ComposeMessage.this, Inbox.class);
								startActivity(i);
								finish();
							}                                                                
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
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sendEmailButton = (Button) findViewById(R.id.send_message);
		recipientText = (EditText) findViewById(R.id.email_recipient);
		messageText = (EditText) findViewById(R.id.message_body);
		subjectText = (EditText) findViewById(R.id.subject);
		sendEmailButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				RequestParams params= new RequestParams();
				recipientText = (EditText) findViewById(R.id.email_recipient);
				messageText = (EditText) findViewById(R.id.message_body);
				subjectText = (EditText) findViewById(R.id.subject);
				params.put("subject",subjectText.getText().toString());
				params.put("body", messageText.getText().toString());
				params.put("recipient", recipientText.getText().toString());
				Log.v("body", messageText.getText().toString());
				Log.v("subject", subjectText.getText().toString());
				Log.v("recipient", recipientText.getText().toString());
				SplashScreen.myClient.post("http://step1.herokuapp.com/send_message.json", params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						try {
							if (response.getString("message").equals("success")){
								Log.v("message sent", "woooo");
								Intent i = new Intent(ComposeMessage.this, Inbox.class);
								startActivity(i);
								finish();
							}                                                                
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
		});
	}

}