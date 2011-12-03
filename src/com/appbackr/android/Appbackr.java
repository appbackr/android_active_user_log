/*
* Appbackr.java 
* 
* android active user log
* copyright (c) 2011 appbackr inc. 
* 
* version 0.8
*
* This project will get differenct device specific data to generate a unique
* device ID to track active usage. 
* It sends the generated device ID with the app ID and a self defined store ID
* to a HTTP post endpoint to store the information on a server.
* 
* Contributers:
* Louis Zeng (appbackr inc.)
* Ethan Herdrick (appbackr inc.)
* Philipp Berner (appbackr inc.)
* 
* MIT licence:
* Permission is hereby granted, free of charge, to any person obtaining a 
* copy of this software and associated documentation files (the "Software"), 
* to deal in the Software without restriction, including without limitation the 
* rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
* copies of the Software, and to permit persons to whom the Software is furnished 
* to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in 
* all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
* INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS 
* FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
* WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR 
* IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
* 
*/
package com.appbackr.android;

import com.appbackr.android.tracker.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Appbackr is a example implementation on where to integrate the Tracker in you android application.
 * This is non executable code by itself.
 */
public class Appbackr extends Activity {
	protected String uniqueId;
	protected Integer number;
	protected Boolean sent;
	
	/** 
	  * Called when the activity is first created. 
	  */
	@Override
	public void onResume(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		number = 0;
	    sent = false;
	        
	    // Run your one time code
	    Tracker.postData(this.getApplicationContext(),
	        				""												// Enter your store ID
	        				this.getApplicationContext().getPackageName(),	// You Android Package name
							"",												// end point URL
							"");											// API authentication token
	        
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

