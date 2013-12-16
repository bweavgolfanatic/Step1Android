package edu.wm.step1;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.widget.*;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class StepComments extends Activity {

	private String LOG_TAG = "edu.wm.step1";
	private ListView listview;
	private Button commentButton;
	private EditText commentText;
	private Button returnButton;
	private Button indexButton;
	private Button browseButton;
	private List<String> listContents = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	

	private String step;
	private String next_step_id;
	private String first_step_id;
	private String num_steps;
	private int current_step;
	private String post;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);

		listview = (ListView)findViewById(R.id.listView1);
		commentButton = (Button) findViewById(R.id.commentButton);
		commentText = (EditText) findViewById(R.id.commentText);
		returnButton = (Button) findViewById(R.id.returnStepButton);
		indexButton = (Button) findViewById(R.id.indexButton);
		browseButton = (Button) findViewById(R.id.browseButton2);

		Intent intent = getIntent();
		step = intent.getExtras().getString("step");
		first_step_id = intent.getExtras().getString("first_step_id");
		num_steps = intent.getExtras().getString("num_steps");
		current_step = intent.getExtras().getInt("current_step");
		post = intent.getExtras().getString("post");
		Log.v(LOG_TAG, "first step id: " + first_step_id);
		Log.v(LOG_TAG, "num_steps: " + num_steps);
		Log.v(LOG_TAG, "current_step: " + current_step);
		Log.v(LOG_TAG,"current step id: "+ step);
		
		RequestParams params = new RequestParams();
		params.put("step", step);
		Log.v(LOG_TAG,"current step: " + step);

		SplashScreen.myClient.get("http://step1.herokuapp.com/get_step_comments.json", params,  new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray responseMSG) {

				Log.v(LOG_TAG, "populating Step comments List");
				listContents.clear();
				for(int i=0;i<responseMSG.length();i++){
					JSONObject row;
					try {
						row = responseMSG.getJSONObject(i);

						listContents.add(row.getString("text"));




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
				/*listview.setOnItemClickListener( new OnItemClickListener() {


					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long id) {
						String post = adapter.getItem(position);
						Log.v(LOG_TAG, "post: " + post);
						//eventually go to post in ViewPost (with comments, etc.)
						AlertDialog.Builder builder = new AlertDialog.Builder(PostComments.this);
						builder.setTitle("Comment");
						builder.setMessage(post);
						builder.setPositiveButton("OK", null);
						AlertDialog dialog = builder.show();

						TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
						messageView.setGravity(Gravity.CENTER);


					}
				});*/
			}
			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d(LOG_TAG, "Getting the Comments didn't work" );        
			} 


		});


		returnButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking return to post Button");
				//pass extras of post name/id?
				Intent i = new Intent(StepComments.this, ViewStep.class); 
				i.putExtra("first_step_id", first_step_id);
				i.putExtra("num_steps",num_steps);
				i.putExtra("current_step", current_step );
				i.putExtra("post", post);
				//i.putExtra("step", step);
				startActivity(i);
				finish();
			}
		});

		browseButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking browse Button");
				//pass extras of post name/id?
				Intent i = new Intent(StepComments.this, Browse.class); 
				//i.putExtra("post", post);
				startActivity(i);
				finish();
			}
		});
		indexButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking Index Button");
				//pass extras of post name/id?
				Intent i = new Intent(StepComments.this, Index.class); 
				//i.putExtra("post", post);
				startActivity(i);
				finish();
			}
		});

		commentButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking comment Button");
				String comment_text = commentText.getText().toString();
				RequestParams params = new RequestParams();
				params.put("text", comment_text);
				params.put("step_id", step);
				Log.v(LOG_TAG, comment_text);
				//pass extras of post name/id?
				SplashScreen.myClient.post("http://step1.herokuapp.com/scomments.json", params,  new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject responseMSG) {
						try {
							if (responseMSG.getString("message").equals("step comment created successfully")){
								Log.v(LOG_TAG, "created comment!");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});


			}
		});

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		setContentView(R.layout.comment);
		listview = (ListView)findViewById(R.id.listView1);
		commentButton = (Button) findViewById(R.id.commentButton);
		commentText = (EditText) findViewById(R.id.commentText);
		returnButton = (Button) findViewById(R.id.returnStepButton);
		indexButton = (Button) findViewById(R.id.indexButton);
		browseButton = (Button) findViewById(R.id.browseButton2);

		Intent intent = getIntent();
		step = intent.getExtras().getString("step");
		first_step_id = intent.getExtras().getString("first_step_id");
		num_steps = intent.getExtras().getString("num_steps");
		current_step = intent.getExtras().getInt("current_step");
		post = intent.getExtras().getString("post");
		Log.v(LOG_TAG, "first step id: " + first_step_id);
		Log.v(LOG_TAG, "num_steps: " + num_steps);
		Log.v(LOG_TAG, "current_step: " + current_step);
		Log.v(LOG_TAG,"current step id: "+ step);
		
		RequestParams params = new RequestParams();
		params.put("step", step);
		Log.v(LOG_TAG,"current step: " + step);

		SplashScreen.myClient.get("http://step1.herokuapp.com/get_step_comments.json", params,  new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray responseMSG) {

				Log.v(LOG_TAG, "populating Step comments List");
				listContents.clear();
				for(int i=0;i<responseMSG.length();i++){
					JSONObject row;
					try {
						row = responseMSG.getJSONObject(i);

						listContents.add(row.getString("text"));




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
				/*listview.setOnItemClickListener( new OnItemClickListener() {


					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long id) {
						String post = adapter.getItem(position);
						Log.v(LOG_TAG, "post: " + post);
						//eventually go to post in ViewPost (with comments, etc.)
						AlertDialog.Builder builder = new AlertDialog.Builder(PostComments.this);
						builder.setTitle("Comment");
						builder.setMessage(post);
						builder.setPositiveButton("OK", null);
						AlertDialog dialog = builder.show();

						TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
						messageView.setGravity(Gravity.CENTER);


					}
				});*/
			}
			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d(LOG_TAG, "Getting the Comments didn't work" );        
			} 


		});


		returnButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking return to post Button");
				//pass extras of post name/id?
				Intent i = new Intent(StepComments.this, ViewStep.class); 
				i.putExtra("first_step_id", first_step_id);
				i.putExtra("num_steps",num_steps);
				i.putExtra("current_step", current_step );
				i.putExtra("post", post);
				//i.putExtra("step", step);
				startActivity(i);
				finish();
			}
		});

		browseButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking browse Button");
				//pass extras of post name/id?
				Intent i = new Intent(StepComments.this, Browse.class); 
				//i.putExtra("post", post);
				startActivity(i);
				finish();
			}
		});
		indexButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking Index Button");
				//pass extras of post name/id?
				Intent i = new Intent(StepComments.this, Index.class); 
				//i.putExtra("post", post);
				startActivity(i);
				finish();
			}
		});

		commentButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking comment Button");
				String comment_text = commentText.getText().toString();
				RequestParams params = new RequestParams();
				params.put("text", comment_text);
				params.put("step_id", step);
				Log.v(LOG_TAG, comment_text);
				//pass extras of post name/id?
				SplashScreen.myClient.post("http://step1.herokuapp.com/scomments.json", params,  new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject responseMSG) {
						try {
							if (responseMSG.getString("message").equals("step comment created successfully")){
								Log.v(LOG_TAG, "created comment!");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});


			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setContentView(R.layout.comment);
		listview = (ListView)findViewById(R.id.listView1);
		commentButton = (Button) findViewById(R.id.commentButton);
		commentText = (EditText) findViewById(R.id.commentText);
		returnButton = (Button) findViewById(R.id.returnStepButton);
		indexButton = (Button) findViewById(R.id.indexButton);
		browseButton = (Button) findViewById(R.id.browseButton2);

		Intent intent = getIntent();
		step = intent.getExtras().getString("step");
		first_step_id = intent.getExtras().getString("first_step_id");
		num_steps = intent.getExtras().getString("num_steps");
		current_step = intent.getExtras().getInt("current_step");
		post = intent.getExtras().getString("post");
		Log.v(LOG_TAG, "first step id: " + first_step_id);
		Log.v(LOG_TAG, "num_steps: " + num_steps);
		Log.v(LOG_TAG, "current_step: " + current_step);
		Log.v(LOG_TAG,"current step id: "+ step);
		
		RequestParams params = new RequestParams();
		params.put("step", step);
		Log.v(LOG_TAG,"current step: " + step);

		SplashScreen.myClient.get("http://step1.herokuapp.com/get_step_comments.json", params,  new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray responseMSG) {

				Log.v(LOG_TAG, "populating Step comments List");
				listContents.clear();
				for(int i=0;i<responseMSG.length();i++){
					JSONObject row;
					try {
						row = responseMSG.getJSONObject(i);

						listContents.add(row.getString("text"));




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
				/*listview.setOnItemClickListener( new OnItemClickListener() {


					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long id) {
						String post = adapter.getItem(position);
						Log.v(LOG_TAG, "post: " + post);
						//eventually go to post in ViewPost (with comments, etc.)
						AlertDialog.Builder builder = new AlertDialog.Builder(PostComments.this);
						builder.setTitle("Comment");
						builder.setMessage(post);
						builder.setPositiveButton("OK", null);
						AlertDialog dialog = builder.show();

						TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
						messageView.setGravity(Gravity.CENTER);


					}
				});*/
			}
			@Override
			public void onFailure(Throwable arg0,String response) {

				Log.d(LOG_TAG, "Getting the Comments didn't work" );        
			} 


		});


		returnButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking return to post Button");
				//pass extras of post name/id?
				Intent i = new Intent(StepComments.this, ViewStep.class); 
				i.putExtra("first_step_id", first_step_id);
				i.putExtra("num_steps",num_steps);
				i.putExtra("current_step", current_step );
				i.putExtra("post", post);
				//i.putExtra("step", step);
				startActivity(i);
				finish();
			}
		});

		browseButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking browse Button");
				//pass extras of post name/id?
				Intent i = new Intent(StepComments.this, Browse.class); 
				//i.putExtra("post", post);
				startActivity(i);
				finish();
			}
		});
		indexButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking Index Button");
				//pass extras of post name/id?
				Intent i = new Intent(StepComments.this, Index.class); 
				//i.putExtra("post", post);
				startActivity(i);
				finish();
			}
		});

		commentButton.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				Log.v(LOG_TAG, "clicking comment Button");
				String comment_text = commentText.getText().toString();
				RequestParams params = new RequestParams();
				params.put("text", comment_text);
				params.put("step_id", step);
				Log.v(LOG_TAG, comment_text);
				//pass extras of post name/id?
				SplashScreen.myClient.post("http://step1.herokuapp.com/scomments.json", params,  new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject responseMSG) {
						try {
							if (responseMSG.getString("message").equals("step comment created successfully")){
								Log.v(LOG_TAG, "created comment!");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});


			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comments, menu);
		return true;
	}

}
