package edu.wm.step1;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class ViewPost extends Activity {

	private String LOG_TAG = "edu.wm.step1";

	private TextView post_difficulty;
	private TextView post_name;
	private TextView post_rating;
	private TextView post_user;
	private String first_step_id;
	private String num_steps;
	private int current_step = 1;

	private Button view_first_step;
	private Button comment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_post);
		post_difficulty = (TextView)findViewById(R.id.stepTitle);
		post_name = (TextView)findViewById(R.id.totalSteps);
		post_user = (TextView)findViewById(R.id.post_user);
		post_rating = (TextView)findViewById(R.id.curStep);

		view_first_step = (Button)findViewById(R.id.view_first_step);
		comment = (Button)findViewById(R.id.comment);

		Intent intent = getIntent();
		String post = intent.getExtras().getString("post");
		Log.v(LOG_TAG,"post:"+ post);
		RequestParams params = new RequestParams();
		params.put("title", post);

		SplashScreen.myClient.get("http://step1.herokuapp.com/getapost.json",params,  new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject responseMSG) {
				try {
					Log.v(LOG_TAG, "successfully got a post");
					post_difficulty.setText(responseMSG.getString("difficulty"));
					post_rating.setText(responseMSG.getString("rating"));
					post_name.setText(responseMSG.getString("title"));
					first_step_id = responseMSG.getString("first_step_id");
					num_steps = responseMSG.getString("num_steps");


				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d(LOG_TAG, "getting post info didn't work" );        
			}
		});
		
		SplashScreen.myClient.get(getApplicationContext(),"http://step1.herokuapp.com/my_details.json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {

					post_user.setText(response.getString("username"));


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
		
		view_first_step.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking view first step Button");
				//pass extras of post name/id?
				Log.v(LOG_TAG,"first_step_id" + first_step_id);
				Log.v(LOG_TAG,"total steps: " + num_steps);
				Intent i = new Intent(ViewPost.this, ViewStep.class);
            	i.putExtra("first_step_id", first_step_id);
            	i.putExtra("num_steps",num_steps);
            	i.putExtra("current_step", current_step);
            	startActivity(i);
			}
		});
		
		comment.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking view first step Button");
				//pass extras of post name/id?
				//Intent i = new Intent(ViewPost.this, Comment.class);     
				//startActivity(i);
			}
		});
		
		post_user.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	Log.v(LOG_TAG, post_user.toString());
                // TODO Auto-generated method stub
            	Intent i = new Intent(ViewPost.this, UserPosts.class);
            	i.putExtra("username", post_user.getText().toString());
            	startActivity(i);

            }
        });

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		setContentView(R.layout.view_post);
		post_difficulty = (TextView)findViewById(R.id.stepTitle);
		post_name = (TextView)findViewById(R.id.totalSteps);
		post_user = (TextView)findViewById(R.id.post_user);
		post_rating = (TextView)findViewById(R.id.curStep);

		view_first_step = (Button)findViewById(R.id.view_first_step);
		comment = (Button)findViewById(R.id.comment);

		Intent intent = getIntent();
		String post = intent.getExtras().getString("post");
		Log.v(LOG_TAG,"post:"+ post);
		RequestParams params = new RequestParams();
		params.put("title", post);

		SplashScreen.myClient.get("http://step1.herokuapp.com/getapost.json",params,  new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject responseMSG) {
				try {
					Log.v(LOG_TAG, "successfully got a post");
					post_difficulty.setText(responseMSG.getString("difficulty"));
					post_rating.setText(responseMSG.getString("rating"));
					post_name.setText(responseMSG.getString("title"));
					first_step_id = responseMSG.getString("first_step_id");
					num_steps = responseMSG.getString("num_steps");


				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d(LOG_TAG, "getting post info didn't work" );        
			}
		});
		
		SplashScreen.myClient.get(getApplicationContext(),"http://step1.herokuapp.com/my_details.json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {

					post_user.setText(response.getString("username"));


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
		
		view_first_step.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking view first step Button");
				Log.v(LOG_TAG,"first_step_id" + first_step_id);
				Log.v(LOG_TAG,"total steps: " + num_steps);
				Intent i = new Intent(ViewPost.this, ViewStep.class);
            	i.putExtra("first_step_id", first_step_id);
            	i.putExtra("num_steps",num_steps);
            	i.putExtra("current_step", current_step);
            	startActivity(i);
			}
		});
		
		comment.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking view first step Button");
				//pass extras of post name/id?
				//Intent i = new Intent(ViewPost.this, Comment.class);     
				//startActivity(i);
			}
		});
		
		post_user.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	Log.v(LOG_TAG, post_user.toString());
                // TODO Auto-generated method stub
            	Intent i = new Intent(ViewPost.this, UserPosts.class);
            	i.putExtra("username", post_user.getText().toString());
            	startActivity(i);

            }
        });


	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setContentView(R.layout.view_post);
		post_difficulty = (TextView)findViewById(R.id.stepTitle);
		post_name = (TextView)findViewById(R.id.totalSteps);
		post_user = (TextView)findViewById(R.id.post_user);
		post_rating = (TextView)findViewById(R.id.curStep);

		view_first_step = (Button)findViewById(R.id.view_first_step);
		comment = (Button)findViewById(R.id.comment);

		Intent intent = getIntent();
		String post = intent.getExtras().getString("post");
		Log.v(LOG_TAG,"post:"+ post);
		RequestParams params = new RequestParams();
		params.put("title", post);

		SplashScreen.myClient.get("http://step1.herokuapp.com/getapost.json", params,  new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject responseMSG) {
				try {
					Log.v(LOG_TAG, "successfully got a post");
					post_difficulty.setText(responseMSG.getString("difficulty"));
					post_rating.setText(responseMSG.getString("rating"));
					post_name.setText(responseMSG.getString("title"));
					first_step_id = responseMSG.getString("first_step_id");
					num_steps = responseMSG.getString("num_steps");


				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d(LOG_TAG, "getting post info didn't work" );        
			}
		});
		
		SplashScreen.myClient.get(getApplicationContext(),"http://step1.herokuapp.com/my_details.json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {

					post_user.setText(response.getString("username"));


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
		
		view_first_step.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking view first step Button");
				Log.v(LOG_TAG,"first_step_id" + first_step_id);
				Log.v(LOG_TAG,"total steps: " + num_steps);
				Intent i = new Intent(ViewPost.this, ViewStep.class);
            	i.putExtra("first_step_id", first_step_id);
            	i.putExtra("num_steps",num_steps);
            	i.putExtra("current_step", current_step);
            	startActivity(i);
			}
		});
		
		comment.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking view first step Button");
				//pass extras of post name/id?
				//Intent i = new Intent(ViewPost.this, Comment.class);     
				//startActivity(i);
			}
		});
		
		post_user.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	Log.v(LOG_TAG, post_user.toString());
                // TODO Auto-generated method stub
            	Intent i = new Intent(ViewPost.this, UserPosts.class);
            	i.putExtra("username", post_user.getText().toString());
            	startActivity(i);

            }
        });



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_post, menu);
		return true;
	}

}
