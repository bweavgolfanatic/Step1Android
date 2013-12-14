package edu.wm.step1;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import edu.wm.step1.MainActivity;
import edu.wm.step1.R;
import edu.wm.step1.SpashScreen;
import edu.wm.step1.Step1RestClient;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class SpashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		Log.v("testing", "spash screen");
		new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	Step1RestClient.get("am_i_signed_in.json", null, new JsonHttpResponseHandler() {
            		@Override
                    public void onStart() {
                		
                        // Initiated the request
                    }
                	@Override
                	public void onSuccess(JSONObject response) {
                		try {
                			
							String message = response.getString("message");
						
							if(message.equals("signed in")){
								
								Intent i = new Intent(SpashScreen.this, Index.class);     
							       startActivity(i);
							       
							}
							else{
								Intent i = new Intent(SpashScreen.this, MainActivity.class);     
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
                		
                         Log.d("TAG", "Failure");        
                    } 
                 });
            }
        }, 3000);
					
			
	}



	//Destroy Welcome_screen.java after it goes to next activity
	@Override
	protected void onPause() 
	{
		// TODO Auto-generated method stub
		super.onPause();
		finish();

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spash_screen, menu);
		return true;
	}

}
