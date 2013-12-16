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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class NewPost extends Activity {

        @Override
        protected void onDestroy() {
                // TODO Auto-generated method stub
                super.onDestroy();
        }


        @Override
        protected void onRestart() {
                super.onRestart();
                setContentView(R.layout.new_post);
                publicButton = (RadioButton)findViewById(R.id.publicButton);
                privateButton = (RadioButton)findViewById(R.id.privateButton);
                
                postNameText = (EditText) findViewById(R.id.postName);
                difficultyText = (EditText) findViewById(R.id.difficulty);
                
                startButton = (Button) findViewById(R.id.startButton);
                publicButton.setOnClickListener(radioListener);
        privateButton.setOnClickListener(radioListener);
        categorySpinner = (Spinner) findViewById(R.id.category);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
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
                        Toast.makeText(getBaseContext(), "You have selected : " +selected_category, 
                                        Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });
        
        startButton.setOnClickListener(new View.OnClickListener() { 
                        @Override
                        public void onClick(View v) {
                                Log.v(LOG_TAG, "clicking start Button");
                                
                                RequestParams params = new RequestParams();
                                
                                String postname = postNameText.getText().toString();
                                String difficulty = difficultyText.getText().toString();
                                //String category = "";

                                params.put("title", postname);
                                params.put("difficulty", difficulty);
                                params.put("category", selected_category);
                                params.put("ispublic", isPublic);
                                
                                Log.v(LOG_TAG, postname);
                                Log.v(LOG_TAG, difficulty);
                                Log.v(LOG_TAG, selected_category);
                                Log.v(LOG_TAG,"" + isPublic);
                                
                                SplashScreen.myClient.post(getApplicationContext(),"http://step1.herokuapp.com/posts.json",params, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(JSONObject responseMSG) {
                            try {
                                                        String message = responseMSG.getString("message");
                                                        Log.v(LOG_TAG,message);
                                                        if(message.equals("post created successfully")){
                                                                String pid = responseMSG.getString("postid");
                                                                Log.v(LOG_TAG, "created new post");
                                                                Intent i = new Intent(NewPost.this, CreateStep.class); 
                                                                i.putExtra("pid", pid);
                                                                startActivity(i);
                                                                finish();
                                                        }
                                                        else{
                                                                Toast.makeText(getBaseContext(), "Can't Create Post", Toast.LENGTH_SHORT).show(); 
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
        protected void onResume() {
                super.onResume();
                setContentView(R.layout.new_post);
                publicButton = (RadioButton)findViewById(R.id.publicButton);
                privateButton = (RadioButton)findViewById(R.id.privateButton);
                
                postNameText = (EditText) findViewById(R.id.postName);
                difficultyText = (EditText) findViewById(R.id.difficulty);
                
                startButton = (Button) findViewById(R.id.startButton);
                publicButton.setOnClickListener(radioListener);
        privateButton.setOnClickListener(radioListener);
        categorySpinner = (Spinner) findViewById(R.id.category);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
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
                        Toast.makeText(getBaseContext(), "You have selected : " +selected_category, 
                                        Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });
        
        startButton.setOnClickListener(new View.OnClickListener() { 
                        @Override
                        public void onClick(View v) {
                                Log.v(LOG_TAG, "clicking start Button");
                                
                                RequestParams params = new RequestParams();
                                
                                String postname = postNameText.getText().toString();
                                String difficulty = difficultyText.getText().toString();
                                //String category = "";

                                params.put("title", postname);
                                params.put("difficulty", difficulty);
                                params.put("category", selected_category);
                                params.put("ispublic", isPublic);
                                
                                Log.v(LOG_TAG, postname);
                                Log.v(LOG_TAG, difficulty);
                                Log.v(LOG_TAG, selected_category);
                                Log.v(LOG_TAG,"" + isPublic);
                                
                                SplashScreen.myClient.post(getApplicationContext(),"http://step1.herokuapp.com/posts.json",params, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(JSONObject responseMSG) {
                            try {
                                                        String message = responseMSG.getString("message");
                                                        Log.v(LOG_TAG,message);
                                                        if(message.equals("post created successfully")){
                                                                String pid = responseMSG.getString("postid");
                                                                Log.v(LOG_TAG, "created new post");
                                                                Intent i = new Intent(NewPost.this, CreateStep.class); 
                                                                i.putExtra("pid", pid);
                                                                startActivity(i);
                                                                finish();
                                                        }
                                                        else{
                                                                Toast.makeText(getBaseContext(), "Can't Create Post", Toast.LENGTH_SHORT).show(); 
                                                        }
                                                } catch (JSONException e) {
                                                        // TODO Auto-generated catch block
                                                        e.printStackTrace();
                                                }
                                }
                                        @Override
                                                public void onFailure(Throwable arg0,String response) {

                                                        Log.d("failure", response);        
                                                } 
                                        });
                                
                                
                        }
        });
        }


        @Override
        protected void onStart() {
                super.onStart();
                setContentView(R.layout.new_post);
                publicButton = (RadioButton)findViewById(R.id.publicButton);
                privateButton = (RadioButton)findViewById(R.id.privateButton);
                
                postNameText = (EditText) findViewById(R.id.postName);
                difficultyText = (EditText) findViewById(R.id.difficulty);
                
                startButton = (Button) findViewById(R.id.startButton);
                publicButton.setOnClickListener(radioListener);
        privateButton.setOnClickListener(radioListener);
        categorySpinner = (Spinner) findViewById(R.id.category);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
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
                        Toast.makeText(getBaseContext(), "You have selected : " +selected_category, 
                                        Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });
        
        startButton.setOnClickListener(new View.OnClickListener() { 
                        @Override
                        public void onClick(View v) {
                                Log.v(LOG_TAG, "clicking start Button");
                                
                                RequestParams params = new RequestParams();
                                
                                String postname = postNameText.getText().toString();
                                String difficulty = difficultyText.getText().toString();
                                //String category = "";

                                params.put("title", postname);
                                params.put("difficulty", difficulty);
                                params.put("category", selected_category);
                                params.put("ispublic", isPublic);
                                
                                Log.v(LOG_TAG, postname);
                                Log.v(LOG_TAG, difficulty);
                                Log.v(LOG_TAG, selected_category);
                                Log.v(LOG_TAG,"" + isPublic);
                                
                                SplashScreen.myClient.post(getApplicationContext(),"http://step1.herokuapp.com/posts.json",params, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(JSONObject responseMSG) {
                            try {
                                                        String message = responseMSG.getString("message");
                                                        Log.v(LOG_TAG,message);
                                                        if(message.equals("post created successfully")){
                                                                String pid = responseMSG.getString("postid");
                                                                Log.v(LOG_TAG, "created new post");
                                                                Intent i = new Intent(NewPost.this, CreateStep.class); 
                                                                i.putExtra("pid", pid);
                                                                startActivity(i);
                                                                finish();
                                                        }
                                                        else{
                                                                Toast.makeText(getBaseContext(), "Can't Create Post", Toast.LENGTH_SHORT).show(); 
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


        private String LOG_TAG = "edu.wm.step1";

        
        private RadioButton publicButton;
        private RadioButton privateButton;
        private String isPublic;
        
        
        private EditText postNameText;
        private EditText difficultyText;
        private Spinner categorySpinner;
        private String[] category_arrays;
        private String selected_category;
        
        private Button startButton;

        private OnClickListener radioListener= new OnClickListener() {
                public void onClick(View v) {
                        RadioButton rb=(RadioButton) v;
                        if(rb.getText().equals("Private")){
                                isPublic = "0";
                        }
                        else if(rb.getText().equals("Public")){
                                isPublic = "1";
                        }
                }
        };
        


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.new_post);
                publicButton = (RadioButton)findViewById(R.id.publicButton);
                privateButton = (RadioButton)findViewById(R.id.privateButton);
                
                postNameText = (EditText) findViewById(R.id.postName);
                difficultyText = (EditText) findViewById(R.id.difficulty);
                
                startButton = (Button) findViewById(R.id.startButton);
                publicButton.setOnClickListener(radioListener);
        privateButton.setOnClickListener(radioListener);
        categorySpinner = (Spinner) findViewById(R.id.category);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
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
                        Toast.makeText(getBaseContext(), "You have selected : " +selected_category, 
                                        Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });
        
        startButton.setOnClickListener(new View.OnClickListener() { 
                        @Override
                        public void onClick(View v) {
                                Log.v(LOG_TAG, "clicking start Button");
                                
                                RequestParams params = new RequestParams();
                                
                                String postname = postNameText.getText().toString();
                                String difficulty = difficultyText.getText().toString();
                                //String category = "";

                                params.put("title", postname);
                                params.put("difficulty", difficulty);
                                params.put("category", selected_category);
                                params.put("ispublic", isPublic);
                                
                                Log.v(LOG_TAG, postname);
                                Log.v(LOG_TAG, difficulty);
                                Log.v(LOG_TAG, selected_category);
                                Log.v(LOG_TAG,"" + isPublic);
                                
                                SplashScreen.myClient.post(getApplicationContext(),"http://step1.herokuapp.com/posts.json",params, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(JSONObject responseMSG) {
                            try {
                                                        String message = responseMSG.getString("message");
                                                        Log.v(LOG_TAG,message);
                                                        if(message.equals("post created successfully")){
                                                                String pid = responseMSG.getString("postid");
                                                                Log.v(LOG_TAG, "created new post");
                                                                Intent i = new Intent(NewPost.this, CreateStep.class); 
                                                                i.putExtra("pid", pid);
                                                                startActivity(i);
                                                                finish();
                                                        }
                                                        else{
                                                                Toast.makeText(getBaseContext(), "Can't Create Post", Toast.LENGTH_SHORT).show(); 
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
                getMenuInflater().inflate(R.menu.new_post, menu);
                return true;
        }

}