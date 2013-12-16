package edu.wm.step1;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;


import android.os.Bundle;
import android.widget.*;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class ViewStep extends Activity {

	private String LOG_TAG = "edu.wm.step1";
	private String num_steps;
	private int current_step;
	private TextView curStep;
	private TextView totalSteps;
	private TextView stepText;
	private ImageView stepImage;
	private String next_step_id;
	private Button next;
	private Button comment;
	private String first_step_id;
	private String post;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_step);

		curStep = (TextView)findViewById(R.id.curStep);
		totalSteps = (TextView)findViewById(R.id.totalSteps);
		stepText = (TextView)findViewById(R.id.stepText);

		stepImage = (ImageView)findViewById(R.id.imageView1);

		comment = (Button)findViewById(R.id.indexButton);
		next = (Button)findViewById(R.id.returnStepButton);

		Intent intent = getIntent();
		first_step_id = intent.getExtras().getString("first_step_id");
		num_steps = intent.getExtras().getString("num_steps");
		current_step = intent.getExtras().getInt("current_step");
		post = intent.getExtras().getString("post");
		Log.v(LOG_TAG,"first step id: "+ first_step_id);
		Log.v(LOG_TAG,"total steps: " + num_steps);
		Log.v(LOG_TAG,"current_step is: " + current_step);
		Log.v(LOG_TAG,"post name: " + post);


		curStep.setText(Integer.toString(current_step));
		totalSteps.setText(num_steps);

		if(Integer.toString(current_step).equals(num_steps)){
			Log.v(LOG_TAG, "setting next to finish");
			next.setText("Finish");
		}


		SplashScreen.myClient.get(getApplicationContext(), "http://step1.herokuapp.com/steps/" + first_step_id + ".json",  new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject responseMSG) {
				Log.v(LOG_TAG, "accessing current step");
				try {
					String picture = responseMSG.getString("picture");
					//Log.v("picture", picture);
					stepText.setText(responseMSG.getString("text"));            
					byte[] decodedString = Base64.decode(picture, Base64.DEFAULT);
					Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
					stepImage.setImageBitmap(decodedByte);
					next_step_id = responseMSG.getString("next_step_id");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}




			}

			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d(LOG_TAG, "steps/" + first_step_id + "/.json didn't work" );        
			} 


		});

		next.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking next step Button");
				Log.v(LOG_TAG,"next step id: " + next_step_id);
				if(next.getText().equals("Finish")){
					Intent intent = new Intent(ViewStep.this, ViewPost.class);
					intent.putExtra("post", post);
					startActivity(intent);
					finish();
				}

				if(!next_step_id.equals("-5")){
					Intent intent = new Intent(ViewStep.this, ViewStep.class);
					intent.putExtra("first_step_id", next_step_id);
					intent.putExtra("num_steps",num_steps);
					intent.putExtra("current_step", current_step+1 );
					intent.putExtra("post", post);
					startActivity(intent);
				}

			}
		});

		comment.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking view first step Button");
				//pass extras of post name/id?
				Intent i = new Intent(ViewStep.this, StepComments.class); 
				i.putExtra("first_step_id", first_step_id);
				i.putExtra("num_steps",num_steps);
				i.putExtra("current_step", current_step );
				i.putExtra("post", post);
				i.putExtra("step", first_step_id);
				startActivity(i);
			}
		});



	}


	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		//setContentView(R.layout.view_step);
		curStep = (TextView)findViewById(R.id.curStep);
		totalSteps = (TextView)findViewById(R.id.totalSteps);
		stepText = (TextView)findViewById(R.id.stepText);

		stepImage = (ImageView)findViewById(R.id.imageView1);

		comment = (Button)findViewById(R.id.indexButton);
		next = (Button)findViewById(R.id.returnStepButton);

		Intent intent = getIntent();
		first_step_id = intent.getExtras().getString("first_step_id");
		num_steps = intent.getExtras().getString("num_steps");
		current_step = intent.getExtras().getInt("current_step");
		post = intent.getExtras().getString("post");
		Log.v(LOG_TAG,"first step id: "+ first_step_id);
		Log.v(LOG_TAG,"total steps: " + num_steps);
		Log.v(LOG_TAG,"current_step is: " + current_step);


		curStep.setText(Integer.toString(current_step));
		totalSteps.setText(num_steps);

		if(Integer.toString(current_step).equals(num_steps)){
			Log.v(LOG_TAG, "setting next to finish");
			next.setText("Finish");
		}


		SplashScreen.myClient.get(getApplicationContext(), "http://step1.herokuapp.com/steps/" + first_step_id + ".json",  new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject responseMSG) {
				Log.v(LOG_TAG, "accessing current step");
				try {
					String picture = responseMSG.getString("picture");
					//Log.v("picture", picture);
					stepText.setText(responseMSG.getString("text"));            
					byte[] decodedString = Base64.decode(picture, Base64.DEFAULT);
					Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
					stepImage.setImageBitmap(decodedByte);
					next_step_id = responseMSG.getString("next_step_id");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}

			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d(LOG_TAG, "steps/" + first_step_id + "/.json didn't work" );          
			} 


		});

		next.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking next step Button");

				if(next.getText().equals("Finish")){
					Intent intent = new Intent(ViewStep.this, ViewPost.class);
					intent.putExtra("post", post);
					startActivity(intent);
					finish();
				}

				if(!next_step_id.equals("-5")){
					Log.v(LOG_TAG,"next step id: " + next_step_id);
					Intent intent = new Intent(ViewStep.this, ViewStep.class);
					intent.putExtra("first_step_id", next_step_id);
					intent.putExtra("num_steps",num_steps);
					intent.putExtra("current_step", current_step+1 );
					intent.putExtra("post", post);
					startActivity(intent);
				}

			}
		});
		
		comment.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking view first step Button");
				//pass extras of post name/id?
				
				Intent i = new Intent(ViewStep.this, StepComments.class);
				i.putExtra("first_step_id", first_step_id);
				i.putExtra("num_steps",num_steps);
				i.putExtra("current_step", current_step );
				i.putExtra("post", post);
				i.putExtra("step", first_step_id);
				
				startActivity(i);
			}
		});

	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//setContentView(R.layout.view_step);
		curStep = (TextView)findViewById(R.id.curStep);
		totalSteps = (TextView)findViewById(R.id.totalSteps);
		stepText = (TextView)findViewById(R.id.stepText);

		stepImage = (ImageView)findViewById(R.id.imageView1);

		comment = (Button)findViewById(R.id.indexButton);
		next = (Button)findViewById(R.id.returnStepButton);

		Intent intent = getIntent();
		first_step_id = intent.getExtras().getString("first_step_id");
		num_steps = intent.getExtras().getString("num_steps");
		current_step = intent.getExtras().getInt("current_step");
		post = intent.getExtras().getString("post");
		Log.v(LOG_TAG,"first step id: "+ first_step_id);
		Log.v(LOG_TAG,"total steps: " + num_steps);
		Log.v(LOG_TAG,"current_step is: " + current_step);


		curStep.setText(Integer.toString(current_step));
		totalSteps.setText(num_steps);

		if(Integer.toString(current_step).equals(num_steps)){
			Log.v(LOG_TAG, "setting next to finish");
			next.setText("Finish");
		}


		SplashScreen.myClient.get(getApplicationContext(), "http://step1.herokuapp.com/steps/" + first_step_id + ".json",  new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject responseMSG) {
				Log.v(LOG_TAG, "accessing current step");
				try {
					String picture = responseMSG.getString("picture");
					//Log.v("picture", picture);
					stepText.setText(responseMSG.getString("text"));            
					byte[] decodedString = Base64.decode(picture, Base64.DEFAULT);
					Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
					stepImage.setImageBitmap(decodedByte);
					next_step_id = responseMSG.getString("next_step_id");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}

			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d(LOG_TAG, "steps/" + first_step_id + "/.json didn't work" );        
			} 


		});

		next.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking next step Button");

				if(next.getText().equals("Finish")){
					Intent intent = new Intent(ViewStep.this, ViewPost.class);
					intent.putExtra("post", post);
					startActivity(intent);
					finish();
				}

				if(!next_step_id.equals("-5")){
					Log.v(LOG_TAG,"next step id: " + next_step_id);
					Intent intent = new Intent(ViewStep.this, ViewStep.class);
					intent.putExtra("first_step_id", next_step_id);
					intent.putExtra("num_steps",num_steps);
					intent.putExtra("current_step", current_step+1 );
					intent.putExtra("post", post);
					startActivity(intent);
				}

			}
		});
		
		comment.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking view first step Button");
				//pass extras of post name/id?
				Intent i = new Intent(ViewStep.this, StepComments.class); 
				i.putExtra("first_step_id", first_step_id);
				i.putExtra("num_steps",num_steps);
				i.putExtra("current_step", current_step );
				i.putExtra("post", post);
				i.putExtra("step", first_step_id);
				startActivity(i);
			}
		});

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_step, menu);
		return true;
	}

}