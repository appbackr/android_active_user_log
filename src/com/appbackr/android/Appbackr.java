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
	
	// This function send data through post
	/*public void postData() {
        try {
			String storeId = "0";
        	String projectId = "0";
        	String androidId = getUDID(this.getApplicationContext());
        	
        	// Guard condition: There is no point in sending empty data
        	if(androidId.compareTo("") == 0) { return; }
        	
        	// Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://yii.appbackr.com/index.php/xchange/scrape");

            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("AndroidUniqueId", androidId));
            nameValuePairs.add(new BasicNameValuePair("ProjectId", projectId));
			nameValuePairs.add(new BasicNameValuePair("StoreId", storeId));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            
            // Execute HTTP Post Request
            httpclient.execute(httppost);
        
            sent = true;
        } catch (ClientProtocolException e) {
        	Log.e("ClientProtocolException: ", e.getMessage());  
        } catch (IOException e) {
        	Log.e("IOException: ", e.getMessage());  
        } 
    }
    
	// This function get the unique id from the phone
    public String getUDID(Context c) {

	   // Get some of the hardware information
	   String buildParams = Build.BOARD + Build.BRAND + Build.CPU_ABI
	   + Build.DEVICE + Build.DISPLAY + Build.FINGERPRINT + Build.HOST
	   + Build.ID + Build.MANUFACTURER + Build.MODEL + Build.PRODUCT
	   + Build.TAGS + Build.TYPE + Build.USER;
	
	   // Requires READ_PHONE_STATE
	   TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
	
	   // gets the imei (GSM) or MEID/ESN (CDMA)
	   String imei = tm.getDeviceId();
	
	   // gets the android-assigned id
	   String androidId = Secure.getString(c.getContentResolver(), Secure.ANDROID_ID);
	
	   // concatenate the string
	   String fullHash = buildParams.toString() + imei + androidId;
	
	   return md5(fullHash);
	}
    
    // This function performs md5 hashing
    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(),0,s.length());
            return new BigInteger(1, digest.digest()).toString(16);
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return "";
    }*/
}

