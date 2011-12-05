/*
* Tracker.java 
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
* Chris Beauchamp (Keep Safe)
* Louis Zhang (appbackr inc.)
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
package com.appbackr.android.tracker;

//Get UDID
import android.os.Build;
import android.telephony.TelephonyManager;
import android.provider.Settings.Secure;
import android.content.Context;

// md5
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Post
import java.util.List;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import org.apache.http.client.ClientProtocolException;

/**
 * Tracker generates let you generate a unique ID and post if to a HTTP endpoint to store server side.
 */
public class Tracker {


	/**
	 * Gets an unique ID for the device where it's run and sends a HTTP post request to
	 * a REST API endpoint. 
	 * Currently it sends over 4 parameters namely "AndroidUniqueId", 
	 * "AndroidPackage", "StoreId" and "Manufacturer". "AndroidUniqueId" is 
	 * generated from the phone. "AndroidPackage", "apiToken" and "StoreId" is passed in to 
	 * the function. "Manufacturer" is retrieved from the phone Please change the 
	 * "AndroidPackage" and "StoreId" respectively according to the app.
	 * 
	 * @param context The Context of the current Activity
	 * @param storeId self defined store ID to assosiate the app to a certain app store
	 * @param androidPackage android package name
	 * @param endPoint HTTP endpoint that is called to submit the loged information
	 * @param apiAuthenticationToken Optional API authentication token for your endpoint
	 */
	public static void postData (Context context,
							String storeId,
							String androidPackage,
							String endPoint,
							String apiAuthenticationToken) {
        try {
        	String androidId = getUDID(context);
        	
        	// Guard condition: There is no point in sending empty data
        	if(androidId.compareTo("") == 0) { 
				throw new RuntimeException('Empty endPoint location not allowed.');
			}
        	
        	// Create a new HttpClient and Post Header

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(endPoint);

            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("AndroidUniqueId", androidId));
            nameValuePairs.add(new BasicNameValuePair("AndroidPackage", androidPackage));
			nameValuePairs.add(new BasicNameValuePair("StoreId", storeId));
			nameValuePairs.add(new BasicNameValuePair("apiAuthenticationToken", apiAuthenticationToken));
			nameValuePairs.add(new BasicNameValuePair("Manufacturer", Build.MANUFACTURER));
            try {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
			//execute post in new thread to not block application on slow server response
            new Thread(new Runnable() {
            	    public void run() {
            	    	try {
            	    		// Execute HTTP Post 
            	    		httpclient.execute(httppost);				            
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            	    }
            	  }).start();
        }
      
	
	/**
	 * Generates a unique ID for the device.
	 * 
	 * This function obtain the Unique ID from the phone. The Unique ID consist of
	 * 	Build.BOARD + Build.BRAND + Build.CPU_ABI
	 * 	+ Build.DEVICE + Build.DISPLAY + Build.FINGERPRINT + Build.HOST
	 * 	+ Build.ID + Build.MANUFACTURER + Build.MODEL + Build.PRODUCT
	 * 	+ Build.TAGS + Build.TYPE + Build.USER;
	 * 	+ IMEI (GSM) or MEID/ESN (CDMA)
	 * 	+ Android-assigned id
	 * 
	 * The Android ID may be changed everytime the user perform Factory Reset
	 * I heard that IMEI values might not be unique because phone factory
	 * might reuse IMEI values to cut cost.
	 * 
	 * While the ID might be different from the same device, but resetting of the
	 * Android phone should not occur that often. The values should be close 
	 * enough.
	 *
	 * @param c android application contact 
	 * @return unique ID as md5 hash generated of available parameters from device and cell phone service provider
	 */
	private static String getUDID(Context c) {

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
	

	/**
	 * This function performs md5 hashing. The reason for this function is because
	 * android package does not have any default md5 hashing function.
	 * @param s string encode as md5 hash
	 * @return md5 hash string
	 */
	private static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(),0,s.length());
            return new BigInteger(1, digest.digest()).toString(16);
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return "";
    }
}
