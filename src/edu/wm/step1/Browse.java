package edu.wm.step1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;



import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class Browse extends Activity {

	private String LOG_TAG = "edu.wm.step1";
	

	private Button searchButton;

	private RadioButton latest;
	private RadioButton oldest;
	private RadioButton popular;
	private RadioButton username;

	private EditText username_edit_text;
	private EditText search_by_title;

	private Spinner categorySpinner;
	private String[] category_arrays;
	private String selected_category;

	private ListView posts_list;
	private List<String> listContents = new ArrayList<String>();
	private ArrayAdapter<String> postAdapter;
	private ArrayAdapter<CharSequence> adapter;

	private OnClickListener radioListener= new OnClickListener() {
		public void onClick(View v) {
			RadioButton rb=(RadioButton) v;
			if(rb.getText().equals("Latest")){
				//populate list with latest
				SplashScreen.myClient.get(getApplicationContext(),"http://step1.herokuapp.com/latest_posts.json",  new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject responseMSG) {

						Log.v(LOG_TAG, "populating latest Posts List");
						listContents.clear();
						int i= 1;
						while (i<=responseMSG.length()){
							try {
								listContents.add((String)responseMSG.get(Integer.toString(i)));
								i++;
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}/*
						Iterator<?> keys = responseMSG.keys();
						listContents.clear();
						while(keys.hasNext())
						{
							try {
								String key = (String)keys.next();
								Log.v(LOG_TAG, (String) responseMSG.get(key));
								
								listContents.add((String) responseMSG.get(key));
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}*/
						postAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listContents){
						    @Override
						    public View getView(int position, View convertView, ViewGroup parent) {
						        View view = super.getView(position, convertView, parent);
						        TextView text = (TextView) view.findViewById(android.R.id.text1);
						        text.setTextColor(Color.BLACK);
						        return view;
						    }
						};
						posts_list.setAdapter(postAdapter);
						posts_list.setOnItemClickListener( new OnItemClickListener() {


							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1,
									int position, long id) {
								String post = postAdapter.getItem(position);
								Log.v(LOG_TAG, "post: " + post);
								//eventually go to post in ViewPost (with comments, etc.)
								Context context = getApplicationContext();
	                        	Intent intent = new Intent(context, ViewPost.class);
	                        	intent.putExtra("post", post);
	                        	startActivity(intent);
							}
						});
					}
					@Override
		    		public void onFailure(Throwable arg0,String response) {

		    			Log.d(LOG_TAG, "Latest Posts didn't work" );        
		    		} 


					});

				}
				else if(rb.getText().equals("Oldest")){
					//populate list with oldest
					SplashScreen.myClient.get(getApplicationContext(),"http://step1.herokuapp.com/oldest_posts.json",  new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject responseMSG) {

							Log.v(LOG_TAG, "populating oldest Posts List");
							//Iterator<?> keys = responseMSG.keys();
							listContents.clear();
							int i = 1;
							while (i<=responseMSG.length()){
								try {
									listContents.add((String)responseMSG.get(Integer.toString(i)));
									i++;
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							postAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listContents){
							    @Override
							    public View getView(int position, View convertView, ViewGroup parent) {
							        View view = super.getView(position, convertView, parent);
							        TextView text = (TextView) view.findViewById(android.R.id.text1);
							        text.setTextColor(Color.BLACK);
							        return view;
							    }
							};
							posts_list.setAdapter(postAdapter);
							posts_list.setOnItemClickListener( new OnItemClickListener() {


								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1,
										int position, long id) {
									String post = postAdapter.getItem(position);
									Log.v(LOG_TAG, "post: " + post);
									//eventually go to post in ViewPost (with comments, etc.)
									Context context = getApplicationContext();
		                        	Intent intent = new Intent(context, ViewPost.class);
		                        	intent.putExtra("post", post);
		                        	startActivity(intent);
								}
							});
						}
						@Override
			    		public void onFailure(Throwable arg0,String response) {

			    			Log.d(LOG_TAG, "Oldest Posts didn't work" );        
			    		} 


						});
				}
				else if(rb.getText().equals("Popular")){
					//populate list with popular
					SplashScreen.myClient.get(getApplicationContext(),"http://step1.herokuapp.com/popular_posts.json",  new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject responseMSG) {

							Log.v(LOG_TAG, "populating Popluar Posts List");
							//Iterator<?> keys = responseMSG.keys();
							listContents.clear();
							int i = 1;
							while (i<=responseMSG.length()){
								try {
									listContents.add((String)responseMSG.get(Integer.toString(i)));
									i++;
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							postAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listContents){
							    @Override
							    public View getView(int position, View convertView, ViewGroup parent) {
							        View view = super.getView(position, convertView, parent);
							        TextView text = (TextView) view.findViewById(android.R.id.text1);
							        text.setTextColor(Color.BLACK);
							        return view;
							    }
							};
							posts_list.setAdapter(postAdapter);
							posts_list.setOnItemClickListener( new OnItemClickListener() {


								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1,
										int position, long id) {
									String post = postAdapter.getItem(position);
									Log.v(LOG_TAG, "post: " + post);
									//eventually go to post in ViewPost (with comments, etc.)
									Context context = getApplicationContext();
		                        	Intent intent = new Intent(context, ViewPost.class);
		                        	intent.putExtra("post", post);
		                        	startActivity(intent);
								}
							});
						}
						@Override
			    		public void onFailure(Throwable arg0,String response) {

			    			Log.d(LOG_TAG, "Popular Posts didn't work" );        
			    		} 


						});
				}
				else if(rb.getText().equals("Username")){
					String user = username_edit_text.getText().toString();
					Log.v(LOG_TAG,user);
					//populate list with username
					RequestParams params = new RequestParams();
					params.put("username", user);
					SplashScreen.myClient.get("http://step1.herokuapp.com/user_posts.json", params,  new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject responseMSG) {

							Log.v(LOG_TAG, "populating user Posts List");
							Iterator<?> keys = responseMSG.keys();
							listContents.clear();
							while(keys.hasNext())
							{
								try {
									String key = (String)keys.next();
									Log.v(LOG_TAG, (String) responseMSG.get(key));
									
									listContents.add((String) responseMSG.get(key));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							postAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listContents){
							    @Override
							    public View getView(int position, View convertView, ViewGroup parent) {
							        View view = super.getView(position, convertView, parent);
							        TextView text = (TextView) view.findViewById(android.R.id.text1);
							        text.setTextColor(Color.BLACK);
							        return view;
							    }
							};
							posts_list.setAdapter(postAdapter);
							posts_list.setOnItemClickListener( new OnItemClickListener() {


								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1,
										int position, long id) {
									String post = postAdapter.getItem(position);
									Log.v(LOG_TAG, "post: " + post);
									//eventually go to post in ViewPost (with comments, etc.)
									Context context = getApplicationContext();
		                        	Intent intent = new Intent(context, ViewPost.class);
		                        	intent.putExtra("post", post);
		                        	startActivity(intent);
								}
							});
						}
						@Override
			    		public void onFailure(Throwable arg0,String response) {

			    			Log.d(LOG_TAG, "User Posts didn't work" );        
			    		} 


						});
				}
			}
		};


		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.browse_posts);

			posts_list = (ListView) findViewById(R.id.posts_list);
			posts_list.setClickable(true);

			latest = (RadioButton)findViewById(R.id.latest);
			oldest = (RadioButton)findViewById(R.id.oldest);
			popular = (RadioButton)findViewById(R.id.popular);
			username = (RadioButton)findViewById(R.id.username);

			latest.setOnClickListener(radioListener);
			oldest.setOnClickListener(radioListener);
			popular.setOnClickListener(radioListener);
			username.setOnClickListener(radioListener);

			username_edit_text = (EditText) findViewById(R.id.username_edit_text);
			search_by_title = (EditText) findViewById(R.id.search_by_title);

			searchButton = (Button) findViewById(R.id.searchButton);

			categorySpinner = (Spinner) findViewById(R.id.category);

			adapter = ArrayAdapter.createFromResource(this,
					R.array.category_arrays, android.R.layout.simple_spinner_item);

			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


			categorySpinner.setAdapter(adapter);

			categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long id) { 
					// TODO Auto-generated method stub
					int index = arg0.getSelectedItemPosition();
					category_arrays = getResources().getStringArray(R.array.category_arrays);
					selected_category = category_arrays[index];
					Log.v(LOG_TAG, selected_category);
					RequestParams params = new RequestParams();
					params.put("category", selected_category);
					SplashScreen.myClient.get(getApplicationContext(), "http://step1.herokuapp.com/category_posts.json", params,  new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject responseMSG) {

							Log.v(LOG_TAG, "populating category Posts List");
							Iterator<?> keys = responseMSG.keys();
							listContents.clear();
							while(keys.hasNext())
							{
								try {
									String key = (String)keys.next();
									Log.v(LOG_TAG, (String) responseMSG.get(key));
									
									listContents.add((String) responseMSG.get(key));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							postAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listContents){
							    @Override
							    public View getView(int position, View convertView, ViewGroup parent) {
							        View view = super.getView(position, convertView, parent);
							        TextView text = (TextView) view.findViewById(android.R.id.text1);
							        text.setTextColor(Color.BLACK);
							        return view;
							    }
							};
							posts_list.setAdapter(postAdapter);
							posts_list.setOnItemClickListener( new OnItemClickListener() {


								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1,
										int position, long id) {
									String post = postAdapter.getItem(position);
									Log.v(LOG_TAG, "post: " + post);
									Context context = getApplicationContext();
		                        	Intent intent = new Intent(context, ViewPost.class);
		                        	intent.putExtra("post", post);
		                        	startActivity(intent);
									//eventually go to post in ViewPost (with comments, etc.)
								}
							});
						}
						@Override
			    		public void onFailure(Throwable arg0,String response) {

			    			Log.d(LOG_TAG, "Category Posts didn't work" );        
			    		} 


						});



				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}

			});

			searchButton.setOnClickListener(new View.OnClickListener() { 
				@Override
				public void onClick(View v) {
					Log.v(LOG_TAG, "clicking search Button");
					String title = search_by_title.getText().toString();
					RequestParams params = new RequestParams();
					params.put("search", title);
					SplashScreen.myClient.get("http://step1.herokuapp.com/search_posts.json", params,  new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject responseMSG) {

							Log.v(LOG_TAG, "populating search Posts List");
							Iterator<?> keys = responseMSG.keys();
							listContents.clear();
							while(keys.hasNext())
							{
								try {
									String key = (String)keys.next();
									Log.v(LOG_TAG, (String) responseMSG.get(key));

									listContents.add((String) responseMSG.get(key));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							postAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listContents){
							    @Override
							    public View getView(int position, View convertView, ViewGroup parent) {
							        View view = super.getView(position, convertView, parent);
							        TextView text = (TextView) view.findViewById(android.R.id.text1);
							        text.setTextColor(Color.BLACK);
							        return view;
							    }
							};
							posts_list.setAdapter(postAdapter);
							posts_list.setOnItemClickListener( new OnItemClickListener() {


								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1,
										int position, long id) {
									String post = postAdapter.getItem(position);
									Log.v(LOG_TAG, "post: " + post);
									//eventually go to post in ViewPost (with comments, etc.)
									Context context = getApplicationContext();
		                        	Intent intent = new Intent(context, ViewPost.class);
		                        	intent.putExtra("post", post);
		                        	startActivity(intent);
								}
							});
						}
						@Override
			    		public void onFailure(Throwable arg0,String response) {

			    			Log.d(LOG_TAG, "Search Posts didn't work" );        
			    		} 


						});


				}
			});





		}

		@Override
		protected void onRestart() {
			super.onRestart();
			setContentView(R.layout.browse_posts);

			posts_list = (ListView) findViewById(R.id.posts_list);
			posts_list.setClickable(true);

			latest = (RadioButton)findViewById(R.id.latest);
			oldest = (RadioButton)findViewById(R.id.oldest);
			popular = (RadioButton)findViewById(R.id.popular);
			username = (RadioButton)findViewById(R.id.username);

			latest.setOnClickListener(radioListener);
			oldest.setOnClickListener(radioListener);
			popular.setOnClickListener(radioListener);
			username.setOnClickListener(radioListener);

			username_edit_text = (EditText) findViewById(R.id.username_edit_text);
			search_by_title = (EditText) findViewById(R.id.search_by_title);

			searchButton = (Button) findViewById(R.id.searchButton);

			categorySpinner = (Spinner) findViewById(R.id.category);

			adapter = ArrayAdapter.createFromResource(this,
					R.array.category_arrays, android.R.layout.simple_spinner_item);

			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


			categorySpinner.setAdapter(adapter);

			categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long id) { 
					// TODO Auto-generated method stub
					int index = arg0.getSelectedItemPosition();
					category_arrays = getResources().getStringArray(R.array.category_arrays);
					selected_category = category_arrays[index];
					Log.v(LOG_TAG, selected_category);
					RequestParams params = new RequestParams();
					params.put("category", selected_category);
					SplashScreen.myClient.get(getApplicationContext(), "http://step1.herokuapp.com/category_posts.json", params,  new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject responseMSG) {

							Log.v(LOG_TAG, "populating category Posts List");
							Iterator<?> keys = responseMSG.keys();
							listContents.clear();
							while(keys.hasNext())
							{
								try {
									String key = (String)keys.next();
									Log.v(LOG_TAG, (String) responseMSG.get(key));
									
									listContents.add((String) responseMSG.get(key));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							postAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listContents){
							    @Override
							    public View getView(int position, View convertView, ViewGroup parent) {
							        View view = super.getView(position, convertView, parent);
							        TextView text = (TextView) view.findViewById(android.R.id.text1);
							        text.setTextColor(Color.BLACK);
							        return view;
							    }
							};
							posts_list.setAdapter(postAdapter);
							posts_list.setOnItemClickListener( new OnItemClickListener() {


								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1,
										int position, long id) {
									String post = postAdapter.getItem(position);
									Log.v(LOG_TAG, "post: " + post);
									Context context = getApplicationContext();
		                        	Intent intent = new Intent(context, ViewPost.class);
		                        	intent.putExtra("post", post);
		                        	startActivity(intent);
									//eventually go to post in ViewPost (with comments, etc.)
								}
							});
						}
						@Override
			    		public void onFailure(Throwable arg0,String response) {

			    			Log.d(LOG_TAG, "Category Posts didn't work" );        
			    		} 


						});



				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}

			});
			searchButton.setOnClickListener(new View.OnClickListener() { 
				@Override
				public void onClick(View v) {
					Log.v(LOG_TAG, "clicking search Button");
					String title = search_by_title.getText().toString();
					RequestParams params = new RequestParams();
					params.put("search", title);
					SplashScreen.myClient.get("http://step1.herokuapp.com/search_posts.json", params,  new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject responseMSG) {

							Log.v(LOG_TAG, "populating search Posts List");
							Iterator<?> keys = responseMSG.keys();
							listContents.clear();
							while(keys.hasNext())
							{
								try {
									String key = (String)keys.next();
									Log.v(LOG_TAG, (String) responseMSG.get(key));

									listContents.add((String) responseMSG.get(key));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							postAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listContents){
							    @Override
							    public View getView(int position, View convertView, ViewGroup parent) {
							        View view = super.getView(position, convertView, parent);
							        TextView text = (TextView) view.findViewById(android.R.id.text1);
							        text.setTextColor(Color.BLACK);
							        return view;
							    }
							};
							posts_list.setAdapter(postAdapter);
							posts_list.setOnItemClickListener( new OnItemClickListener() {


								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1,
										int position, long id) {
									String post = postAdapter.getItem(position);
									Log.v(LOG_TAG, "post: " + post);
									//eventually go to post in ViewPost (with comments, etc.)
									Context context = getApplicationContext();
		                        	Intent intent = new Intent(context, ViewPost.class);
		                        	intent.putExtra("post", post);
		                        	startActivity(intent);
								}
							});
						}
						@Override
			    		public void onFailure(Throwable arg0,String response) {

			    			Log.d(LOG_TAG, "Search Posts didn't work" );        
			    		} 


						});


				}
			});





		}

		@Override
		protected void onResume() {
			super.onResume();
			setContentView(R.layout.browse_posts);
			

			posts_list = (ListView) findViewById(R.id.posts_list);
			posts_list.setClickable(true);

			latest = (RadioButton)findViewById(R.id.latest);
			oldest = (RadioButton)findViewById(R.id.oldest);
			popular = (RadioButton)findViewById(R.id.popular);
			username = (RadioButton)findViewById(R.id.username);

			latest.setOnClickListener(radioListener);
			oldest.setOnClickListener(radioListener);
			popular.setOnClickListener(radioListener);
			username.setOnClickListener(radioListener);

			username_edit_text = (EditText) findViewById(R.id.username_edit_text);
			search_by_title = (EditText) findViewById(R.id.search_by_title);

			searchButton = (Button) findViewById(R.id.searchButton);

			categorySpinner = (Spinner) findViewById(R.id.category);

			adapter = ArrayAdapter.createFromResource(this,
					R.array.category_arrays, android.R.layout.simple_spinner_item);

			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


			categorySpinner.setAdapter(adapter);

			categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long id) { 
					// TODO Auto-generated method stub
					int index = arg0.getSelectedItemPosition();
					category_arrays = getResources().getStringArray(R.array.category_arrays);
					selected_category = category_arrays[index];
					Log.v(LOG_TAG, selected_category);
					RequestParams params = new RequestParams();
					params.put("category", selected_category);
					SplashScreen.myClient.get(getApplicationContext(),"http://step1.herokuapp.com/category_posts.json", params,  new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject responseMSG) {

							Log.v(LOG_TAG, "populating category Posts List");
							Iterator<?> keys = responseMSG.keys();
							listContents.clear();
							while(keys.hasNext())
							{
								try {
									String key = (String)keys.next();
									Log.v(LOG_TAG, (String) responseMSG.get(key));
									
									listContents.add((String) responseMSG.get(key));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							postAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listContents){
							    @Override
							    public View getView(int position, View convertView, ViewGroup parent) {
							        View view = super.getView(position, convertView, parent);
							        TextView text = (TextView) view.findViewById(android.R.id.text1);
							        text.setTextColor(Color.BLACK);
							        return view;
							    }
							};
							posts_list.setAdapter(postAdapter);
							posts_list.setOnItemClickListener( new OnItemClickListener() {


								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1,
										int position, long id) {
									String post = postAdapter.getItem(position);
									Log.v(LOG_TAG, "post: " + post);
									//eventually go to post in ViewPost (with comments, etc.)
									Context context = getApplicationContext();
		                        	Intent intent = new Intent(context, ViewPost.class);
		                        	intent.putExtra("post", post);
		                        	startActivity(intent);
								}
							});
						}
						@Override
			    		public void onFailure(Throwable arg0,String response) {

			    			Log.d(LOG_TAG, "Category Posts didn't work" );        
			    		} 


						});



				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}

			});

			searchButton.setOnClickListener(new View.OnClickListener() { 
				@Override
				public void onClick(View v) {
					Log.v(LOG_TAG, "clicking search Button");
					String title = search_by_title.getText().toString();
					RequestParams params = new RequestParams();
					params.put("search", title);
					SplashScreen.myClient.get("http://step1.herokuapp.com/search_posts.json", params,  new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject responseMSG) {

							Log.v(LOG_TAG, "populating search Posts List");
							Iterator<?> keys = responseMSG.keys();
							listContents.clear();
							while(keys.hasNext())
							{
								try {
									String key = (String)keys.next();
									Log.v(LOG_TAG, (String) responseMSG.get(key));

									listContents.add((String) responseMSG.get(key));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							postAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listContents){
							    @Override
							    public View getView(int position, View convertView, ViewGroup parent) {
							        View view = super.getView(position, convertView, parent);
							        TextView text = (TextView) view.findViewById(android.R.id.text1);
							        text.setTextColor(Color.BLACK);
							        return view;
							    }
							};
							posts_list.setAdapter(postAdapter);
							posts_list.setOnItemClickListener( new OnItemClickListener() {


								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1,
										int position, long id) {
									String post = postAdapter.getItem(position);
									Log.v(LOG_TAG, "post: " + post);
									//eventually go to post in ViewPost (with comments, etc.)
									Context context = getApplicationContext();
		                        	Intent intent = new Intent(context, ViewPost.class);
		                        	intent.putExtra("post", post);
		                        	startActivity(intent);
								}
							});
						}
						@Override
			    		public void onFailure(Throwable arg0,String response) {

			    			Log.d(LOG_TAG, "Search Posts didn't work" );        
			    		} 


						});


				}
			});





		
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.browse, menu);
			return true;
		}

	}
