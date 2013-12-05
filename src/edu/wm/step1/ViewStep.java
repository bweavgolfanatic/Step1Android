package edu.wm.step1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ViewStep extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_step);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_step, menu);
		return true;
	}

}
