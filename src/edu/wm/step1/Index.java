package edu.wm.step1;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import edu.wm.step1.MainActivity;
import edu.wm.step1.SplashScreen;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Index extends Activity {

	private String LOG_TAG = "edu.wm.step1";

	
	private TextView usernameText;
	private TextView ratingText;
	private TextView numPostsText;
	
	private Spinner categorySpinner;

	private Button newPostButton;
	private Button browseButton;
	private Button myPostsButton;
	private Button logoutButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);

		usernameText = (TextView) findViewById(R.id.usernameText);
		ratingText = (TextView) findViewById(R.id.ratingText);
		numPostsText = (TextView) findViewById(R.id.numPosts);
		newPostButton = (Button) findViewById(R.id.newPostButton);
		browseButton = (Button) findViewById(R.id.browseButton);
		myPostsButton = (Button) findViewById(R.id.myPostsButton);
		logoutButton = (Button) findViewById(R.id.logout);
		

		SplashScreen.myClient.get(getApplicationContext(),"http://step1.herokuapp.com/my_details.json", new JsonHttpResponseHandler() {
    		@Override
			public void onSuccess(JSONObject response) {
			try {

				usernameText.setText(response.getString("username"));
				ratingText.setText(response.getString("rating"));
				numPostsText.setText(response.getString("numposts"));

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



		newPostButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking newPost Button");
				//check if newPost possible?
				Intent i = new Intent(Index.this, NewPost.class);     
				startActivity(i);
			}
		});

		browseButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking browse Button");
				//check if browse possible?
				Intent i = new Intent(Index.this, Browse.class);     
				startActivity(i);
			}
		});

		myPostsButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking myPosts Button");
				//check if browse possible?
				Intent i = new Intent(Index.this, MyPosts.class);  
				i.putExtra("username", usernameText.getText().toString());
				startActivity(i);
			}
		});
		
		logoutButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				ConnectivityManager connMgr = (ConnectivityManager) 
			            getSystemService(Context.CONNECTIVITY_SERVICE);
			        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			        if (networkInfo != null && networkInfo.isConnected()) {
			        	Context context = getApplicationContext();
			        	CharSequence text = "Connection acquirred, about to do internet things";
			        	int duration = Toast.LENGTH_SHORT;
			        	Toast toast = Toast.makeText(context, text, duration);
			        	//toast.show();
			        	Log.v("ABOUT TO SIGN OUT", "yay");
			        	SplashScreen.myClient.get(context,"http://step1.herokuapp.com/log_out.json", new JsonHttpResponseHandler() {
			        		@Override
			                public void onSuccess(JSONObject responseMSG) {
			                    try {
			                        String msg = responseMSG.getString("message");
			                        Log.v("json response",msg);
			                        if (msg.equals("logged out")){
			                        	CharSequence text = "successful logout, redirecting to mainactivity";
			            	        	int duration = Toast.LENGTH_SHORT;
			            	        	Context context = getApplicationContext();
			            	        	Toast toast = Toast.makeText(context, text, duration);
			            	        	//toast.show();
			                        	context = getApplicationContext();
			                        	Intent intent = new Intent(context, MainActivity.class);
			                        	startActivity(intent);
			                        	finish();
			                        }
			                        else{
			                        	Context context = getApplicationContext();
			            	        	CharSequence text = "logout failed";
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
		
		
		});


		


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}

}
