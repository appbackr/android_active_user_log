package com.android.tracker;

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





public class Tracker {

	// This function send data through post
	public static void postData(Context context
							, String storeId
							, String androidPackage) {
        //try {
    		System.out.println("trying to post to home ");

        	String androidId = getUDID(context);
        	
        	// Guard condition: There is no point in sending empty data
        	if(androidId.compareTo("") == 0) { return; }
        	
        	// Create a new HttpClient and Post Header
            final HttpClient httpclient = new DefaultHttpClient();
            final HttpPost httppost = new HttpPost("http://yii.appbackr.com/xchange/analytics");
 
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("AndroidUniqueId", androidId));
            nameValuePairs.add(new BasicNameValuePair("AndroidPackage", androidPackage));
			nameValuePairs.add(new BasicNameValuePair("StoreId", storeId));
			nameValuePairs.add(new BasicNameValuePair("Manufacturer", Build.MANUFACTURER));
            try {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
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
      
	
	// This function get the unique id from the phone
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
	
    // This function performs md5 hashing
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
