package edu.wm.step1;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class Index extends Activity {

	private String LOG_TAG = "edu.wm.step1";

	
	private TextView usernameText;
	private TextView ratingText;
	private TextView numPostsText;
	
	private Spinner categorySpinner;

	private Button newPostButton;
	private Button browseButton;
	private Button myPostsButton;


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
		

		Step1RestClient.get("my_details.json", null, new JsonHttpResponseHandler() {
			@Override
			public void onStart() {

				// Initiated the request
			}

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
				startActivity(i);
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
