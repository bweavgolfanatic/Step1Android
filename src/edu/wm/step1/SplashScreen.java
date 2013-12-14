package edu.wm.step1;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
 
public class SplashScreen extends Activity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    public static AsyncHttpClient myClient = new AsyncHttpClient();
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Context context = getApplicationContext();
		PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		myClient.setCookieStore(myCookieStore);
		
 
        new Handler().postDelayed(new Runnable() {
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
            	ConnectivityManager connMgr = (ConnectivityManager) 
    		    getSystemService(Context.CONNECTIVITY_SERVICE);
    		    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    		    if (networkInfo != null && networkInfo.isConnected()) {
    		    	Context context = getApplicationContext();
    		    	CharSequence text = "Connection acquirred, about to do internet things";
    		    	int duration = Toast.LENGTH_SHORT;
    		    	Toast toast = Toast.makeText(context, text, duration);
    		    	//toast.show();
    	        	SplashScreen.myClient.get(context,"http://step1.herokuapp.com/am_i_signed_in.json", new JsonHttpResponseHandler() {
    	        		@Override
    	                public void onSuccess(JSONObject responseMSG) {
    	                    try {
    	                        String msg = responseMSG.getString("message");
    	                        Log.v("splash",msg);
    	                        if (msg.equals("signed in")){
    	                        	Context context = getApplicationContext();
    	            		    	CharSequence text = "signed In";
    	            		    	int duration = Toast.LENGTH_SHORT;
    	            		    	Toast toast = Toast.makeText(context, text, duration);
    	            		    	//toast.show();
    	            		    	context = getApplicationContext();
    	                        	Intent intent = new Intent(context, Index.class);
    	                        	startActivity(intent);
    	                        	finish();
    	                        	
    	                        }
    	                        else{
    	                        	Context context = getApplicationContext();
    	            	        	CharSequence text = "Please sign in or register!";
    	            	        	int duration = Toast.LENGTH_SHORT;
    	            	        	Toast toast = Toast.makeText(context, text, duration);
    	            	        	toast.show();
    	            	        	context = getApplicationContext();
    	                        	Intent intent = new Intent(context, MainActivity.class);
    	                        	startActivity(intent);
    	                        	finish();
    	                        	
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
    	        	Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
     
                    // close this activity
                    finish();
    	        }
            	
            	
            	
                
            }
        }, SPLASH_TIME_OUT);
    }
 
}
