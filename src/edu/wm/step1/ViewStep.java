package edu.wm.step1;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewStep extends Activity {
	private String path;
	private String text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_step);
		SplashScreen.myClient.get("http://step1.herokuapp.com/steps/9.json", new JsonHttpResponseHandler() {
		    @Override
		    public void onSuccess(JSONObject responseMSG) {
		    	try {
					String picture = responseMSG.getString("picture");
					Log.v("path", picture);
					text = responseMSG.getString("text");
					TextView tt = (TextView) findViewById(R.id.textView5);
					tt.setText(text);
					byte[] decodedString = Base64.decode(picture, Base64.DEFAULT);
					Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length); 
					ImageView mvw = (ImageView) findViewById(R.id.imageView1);
					mvw.setImageBitmap(decodedByte);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		        
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
