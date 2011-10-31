package com.appbackr.android;

// emulator.exe @my_avd_market -sdcard android_sdcard.img

import com.android.tracker.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

// Tracker

public class Appbackr extends Activity {
	protected String uniqueId;
	protected Integer number;
	protected Boolean sent;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		number = 0;
	    sent = false;
	        
	    // Run your one time code
	    Tracker.postData(this.getApplicationContext()
	        				, ""		// Enter your store ID
	        				, "");		// Enter your Android Package
	        
	    setContentView(R.layout.main);
	    
	    final TextView textViewToChange = (TextView) findViewById(R.id.count);
	    textViewToChange.setText(number.toString());
	}
    
	public void postId(View view) {
		sent = false;
		
		final TextView textViewCount = (TextView) findViewById(R.id.count);
		textViewCount.setText((number++).toString());
		
		Tracker.postData(this.getApplicationContext()
							, ""
							, "");
		
		final TextView textViewSuccess = (TextView) findViewById(R.id.success);
		textViewSuccess.setText("Success: " + sent.toString());
	}
}

