package edu.wm.step1;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private EditText loginUsernameText;
	private EditText loginPasswordText;
	private EditText verifyPasswordText;
	private EditText registerUsernameText;
	private EditText registerPasswordText;
	private Button registerButton;
	private Button loginButton;
	

	private static final String TAG = "MyActivity";
	
	
	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		
		loginUsernameText = (EditText) findViewById(R.id.usernameField);
		loginPasswordText = (EditText) findViewById(R.id.passwordField);
		registerUsernameText = (EditText) findViewById(R.id.usernameField2);
		registerPasswordText = (EditText) findViewById(R.id.passwordField2);		
		verifyPasswordText = (EditText) findViewById(R.id.verifyPasswordField);
		registerButton = (Button) findViewById(R.id.registerButton);
		loginButton = (Button) findViewById(R.id.logInButton);
		
		loginButton.setOnClickListener(loginListener);
		registerButton.setOnClickListener(registerListener);
		
		
	}
	
	private OnClickListener registerListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
	        ConnectivityManager connMgr = (ConnectivityManager) 
		    getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		    if (networkInfo != null && networkInfo.isConnected()) {
		    	Context context = getApplicationContext();
		    	CharSequence text = "Connection acquirred, about to do internet things";
		    	int duration = Toast.LENGTH_SHORT;
		    	Toast toast = Toast.makeText(context, text, duration);
		    	//toast.show();
		    	String unm = registerUsernameText.getText().toString();
		        String pwd = registerPasswordText.getText().toString();
		        String pwdcnfm = verifyPasswordText.getText().toString();
		        RequestParams params = new RequestParams();
	        	params.put("user[username]", unm);
	        	params.put("user[password]", pwd);
	        	params.put("user[password_confirmation]", pwdcnfm);
	        	Log.v("username", unm);
	        	Log.v("password", pwd);
	        	Log.v("password confirmation", pwdcnfm);
	        	
	        	SplashScreen.myClient.post(context,"http://step1.herokuapp.com/users.json",params, new JsonHttpResponseHandler() {
	        		@Override
	                public void onSuccess(JSONObject responseMSG) {
	                    try {
	                        String msg = responseMSG.getString("message");
	                        Log.v(TAG,msg);
	                        if (msg.equals("user created successfully")){
	                        	Context context = getApplicationContext();
	            		    	CharSequence text = "Registration Successful! Hit login button!";
	            		    	int duration = Toast.LENGTH_SHORT;
	            		    	Toast toast = Toast.makeText(context, text, duration);
	            		    	toast.show();
	            		    	loginUsernameText.setText(registerUsernameText.getText());
	            		    	loginPasswordText.setText(registerPasswordText.getText());
	            		    	registerUsernameText.setText("");
	            		    	registerPasswordText.setText("");
	            		    	verifyPasswordText.setText("");
	            		    	
	                        }
	                        else{
	                        	Context context = getApplicationContext();
	            	        	CharSequence text = "ERROR-user not created";
	            	        	int duration = Toast.LENGTH_SHORT;
	            	        	Toast toast = Toast.makeText(context, text, duration);
	            	        	toast.show();
	                        	
	                        }

	                    } catch (JSONException e) {
	                        e.printStackTrace();
	                    }
	        	}
	        	
	        	
	            
	        });

		    	
			
		    }
		    else {
	        	Context context = getApplicationContext();
	        	CharSequence text = "No Internet Connection";
	        	int duration = Toast.LENGTH_SHORT;

	        	Toast toast = Toast.makeText(context, text, duration);
	        	toast.show();
	        }
		}
		
	};
	
	
	
	
	
	private OnClickListener loginListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
	        // Gets the URL from the UI's text field.
	        ConnectivityManager connMgr = (ConnectivityManager) 
	            getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	        if (networkInfo != null && networkInfo.isConnected()) {
	        	Context context = getApplicationContext();
	        	CharSequence text = "Connection acquirred, about to do internet things";
	        	int duration = Toast.LENGTH_SHORT;
	        	Toast toast = Toast.makeText(context, text, duration);
	        	//toast.show();
	        	RequestParams params = new RequestParams();
	        	String unm = loginUsernameText.getText().toString();
		        String pwd = loginPasswordText.getText().toString();
	        	params.put("username", unm);
	        	params.put("password", pwd);
	        	Log.v("email", unm);
	        	Log.v("password", pwd);
	        	System.out.println(pwd);
	        	Log.v("IN SIGN IN ABOUT TO POST", "yay");
	        	SplashScreen.myClient.post(context,"http://step1.herokuapp.com/sessions.json",params, new JsonHttpResponseHandler() {
	        		@Override
	                public void onSuccess(JSONObject responseMSG) {
	                    try {
	                        String msg = responseMSG.getString("message");
	                        Log.v(TAG,msg);
	                        if (msg.equals("login successful")){
	                        	CharSequence text = "successful login, woo trying to start new activity";
	            	        	int duration = Toast.LENGTH_SHORT;
	            	        	Context context = getApplicationContext();
	            	        	Toast toast = Toast.makeText(context, text, duration);
	            	        	//toast.show();
	                        	context = getApplicationContext();
	                        	Intent intent = new Intent(context, Index.class);
	                        	startActivity(intent);
	                        	finish();
	                        }
	                        else{
	                        	Context context = getApplicationContext();
	            	        	CharSequence text = "Invalid Login";
	            	        	int duration = Toast.LENGTH_SHORT;
	            	        	Toast toast = Toast.makeText(context, text, duration);
	            	        	toast.show();
	                        	
	                        }

	                    } catch (JSONException e) {
	                        e.printStackTrace();
	                    }
	        	}
	        	
	        	
	            
	        });
	        	
            	
	        
	        
	        }

            	
	        	else {
	        	Context context = getApplicationContext();
	        	CharSequence text = "No Internet Connection";
	        	int duration = Toast.LENGTH_SHORT;

	        	Toast toast = Toast.makeText(context, text, duration);
	        	toast.show();
	        }
	    }
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}


}



