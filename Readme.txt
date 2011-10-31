This is a sample program to send the phone's unique ID out. 

1) Folder Structure
2) Code Overview

/***********************************************
 * Folder Structure
***********************************************/
/HelloAppbackr
	/assets
	/bin
	/gen
	/res
	/src
		/com
			/appbackr
				/android
					Appbackr.java
	AndroidManifest.xml

1) Android Manifest file	

There are only 2 files that require to editing for the tracking to work, namely
Appbackr.java and AndroidManifest.xml.

If the app does not request for INTERNET permission, the following line must be
added. A sample of the location in which the line has to be added to can be 
found in the manifest file.

	<uses-permission android:name="android.permission.INTERNET" />
	
2) Appbackr java file

	The second file holds all the code for sending of the data. The main 
	information of how the code works can be separated into 3 functions.
	To make it work, copy the code for the following 3 functions and run
	the code for function "postData" in the "onCreate" function of the 
	main Activity.

		- String md5(String s)
		- String getUDID(Context c)
		- void postData()
	
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

- void postData()
	
	This function performs a HTTP Post to the address 
	"http://yii.appbackr.com/index.php/xchange/scrape".

	Currently it sends over 2 parameters namely "AndroidUniqueId" and 
	"ProjectId". "AndroidUniqueId" is generated from the phone and "ProjectId"
	is hardcoded to	the phone. Please change the "ProjectId" respectively 
	according to the app.

