package edu.wm.step1;


import java.util.HashMap;
import java.util.Map;

import com.loopj.android.http.*;

import edu.wm.step1.Index;
import edu.wm.step1.MainActivity;


import org.json.JSONException;
import org.json.JSONObject;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends Activity {


	private String LOG_TAG = "edu.wm.step1";
	private EditText usernameText;
	private EditText usernameText2;
	private EditText passwordText;
	private EditText passwordText2;
	private EditText verifyPasswordText;

	private Button registerButton;
	private Button loginButton;

	private String username;
	private String password;

	private String username2;
	private String password2;
	private String verifyPassword;


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		outState.putString("username",  username);
		outState.putString("password", password);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		//set all variables to values
		usernameText = (EditText) findViewById(R.id.usernameField);
		passwordText = (EditText) findViewById(R.id.passwordField);

		usernameText2 = (EditText) findViewById(R.id.usernameField2);
		passwordText2 = (EditText) findViewById(R.id.passwordField2);
		verifyPasswordText = (EditText) findViewById(R.id.verifyPasswordField);

		loginButton = (Button) findViewById(R.id.logInButton);

		registerButton = (Button) findViewById(R.id.registerButton);

		if (savedInstanceState == null)
		{
			username = "";

		}
		else
		{
			username = savedInstanceState.getString("username");
			password = savedInstanceState.getString("password");
		}


		loginButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking login");


				username = usernameText.getText().toString();
				password = passwordText.getText().toString();

				Log.v(LOG_TAG, username);
				Log.v(LOG_TAG, password);

				Log.v(LOG_TAG, "Creating AsyncHttpClient");
				RequestParams params = new RequestParams();

				params.put("username", username);
				params.put("password", password);

				Step1RestClient.post("sessions.json", params, new JsonHttpResponseHandler() {
					@Override
					public void onStart() {

						// Initiated the request
					}
					@Override
					public void onSuccess(JSONObject response) {
						try {
							String message = response.getString("message");
							Log.v(LOG_TAG,message);
							if(message.equals("login successful")){				//whatever ben does for JSON
								Intent i = new Intent(MainActivity.this, Index.class);     
								startActivity(i);
								//finish();
							}
							else{
								Toast.makeText(getApplicationContext(), "Login Unsuccessful",
										Toast.LENGTH_LONG).show();
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					@Override
					public void onFailure(Throwable arg0,String response) {

						Log.d("TAG", "Failure");        
					} 
				});


			}




		});

		registerButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking register");
				username2 = usernameText2.getText().toString();
				password2 = passwordText2.getText().toString();
				verifyPassword = verifyPasswordText.getText().toString();
				//Log.v(LOG_TAG, username2);
				//Log.v(LOG_TAG, password2);
				//Log.v(LOG_TAG, verifyPassword);

				//Log.v(LOG_TAG, "Creating AsyncHttpClient");
				RequestParams params = new RequestParams();

				Map<String, String> map = new HashMap<String, String>();
				map.put("username", username2);
				map.put("password", password2);
				map.put("password_confirmation", verifyPassword);
				params.put("user", map);
				Toast.makeText(getApplicationContext(), "Clicked Register Button",
						Toast.LENGTH_LONG).show();

				Step1RestClient.post("users.json", params, new JsonHttpResponseHandler() {


					@Override
					public void onStart() {

						// Initiated the request
					}
					@Override
					public void onSuccess(JSONObject response) {
						try {
							String message = response.getString("message");
							Log.v(LOG_TAG,message);
							if(message.equals("user created successfully")){
								Intent i = new Intent(MainActivity.this, Index.class);     
								startActivity(i);
								finish();
							}
							else{
								Toast.makeText(getApplicationContext(), "Registration Unsuccessful",
										Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					@Override
					public void onFailure(Throwable arg0,String response) {

						Log.d("TAG", "Failure");        
					} 
				});
			}



		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
