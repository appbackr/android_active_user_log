This is a sample program to send the phone's unique ID out. 

1) Folder Structure
2) Code Overview

/***********************************************
 * Folder Structure
***********************************************/
/android_active_user_log
	/assets
	/bin
	/gen
	/res
	/src
		/com
			/appbackr
				/android
					Appbackr.java	
			/android
				/tracker
					Tracker.java
	AndroidManifest.xml

1) Android Manifest file	

There are only 3 files that require to editing for the tracking to work, namely
Appbackr.java, Tracker,java and AndroidManifest.xml.

If the app does not request for INTERNET permission, the following line must be
added. A sample of the location in which the line has to be added to can be 
found in the manifest file.

	<uses-permission android:name="android.permission.INTERNET" />

2) Appbackr java file

	This file demostrate how to call the Tracker class. Eg:
	
	Tracker.postData(this.getApplicationContext()
					, ""		// Enter your store ID
					, "");		// Enter your Android Package

	this.getApplicationContext() : will only work if the class that
	is calling the Track is an Activity class.
	
3) Tracker java file

	The second file holds all the code for sending of the data. The main 
	information of how the code works can be separated into 3 functions.
	To make it work, copy the code for the following 3 functions and run
	the code for function "postData" in the "onCreate" function of the 
	main Activity.

		- String md5(String s)
		- String getUDID(Context c)
		- void postData(Context context
						, String storeId
						, String androidPackage)
	
- String md5(String s) : 
	This function performs md5 hashing. The reason for this function is because
	android package does not have any default md5 hashing function.

- String getUDID(Context c)
	This function obtain the Unique ID from the phone. The Unique ID consist of
		Build.BOARD + Build.BRAND + Build.CPU_ABI
		+ Build.DEVICE + Build.DISPLAY + Build.FINGERPRINT + Build.HOST
		+ Build.ID + Build.MANUFACTURER + Build.MODEL + Build.PRODUCT
		+ Build.TAGS + Build.TYPE + Build.USER;
		+ IMEI (GSM) or MEID/ESN (CDMA)
		+ Android-assigned id

	The Android ID may be changed everytime the user perform Factory Reset
	I heard that IMEI values might not be unique because phone factory
	might reuse IMEI values to cut cost.

	While the ID might be different from the same device, but resetting of the
	Android phone should not occur that often. The values should be close 
	enough.
		
	Note that the returned String is hashed. 

- void postData(Context context
				, String storeId
				, String androidPackage)	

	Context context: The Context of the current Activity.
	String storeId: The store id of your android app 
	String androidPackage: The android package used to upload to the Android Marketplace
	
	This function performs a HTTP Post to the address 
	"http://direct.appbackr.com/xchange/scrape".

	Currently it sends over 4 parameters namely "AndroidUniqueId", 
	"AndroidPackage", "StoreId" and "Manufacturer". "AndroidUniqueId" is 
	generated from the phone. "AndroidPackage" and "StoreId" is passed in to 
	the function. "Manufacturer" is retrieved from the phone Please change the 
	"AndroidPackage" and "StoreId" respectively according to the app.

