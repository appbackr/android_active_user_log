# Introduction
_android active user log_ is a simple tool to track the active users of your android app.

# How does it work?

_android active user log_ generates a unique device ID from an android smart phone by collecting different parameters and applying a hash function on them. It then sends the hash together with a unique store id and the application id to a web server.

This tracker requires internet access from the app.

# How to install?

**1. Import com.appbackr.Tracker.java into your project. This is the core to generate the unique ID and post the data to your server.**

**2. Call the post data function with your parameters in the onResume() methode in your android code to track each time when a user starts the app or brings it back up from from idle. Samlple implementation in Appbackr.java**
```java
Tracker.postData(this.getApplicationContext(),
    				""												// Enter your store ID
    				this.getApplicationContext().getPackageName(),	// You Android Package name
					"",												// end point URL
					"");
```

**3. Add missing permissions to your Android manifest file to allow the code to collect the nessecary data and to send it back to the server. Sample implimentaton in AndroidManifest.xml**
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
```

# Code Overview

**Android Manifest file**

There are only 3 files that require to editing for the tracking to work, namely Appbackr.java, Tracker,java and AndroidManifest.xml.

If the app does not request for INTERNET permission, the following line must be added. A sample of the location in which the line has to be added to can be found in the manifest file.

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
```

**Appbackr.java file**

	Demostrate how to call the Tracker class. Eg:
	
```java
Tracker.postData(this.getApplicationContext(),
    				""												// Enter your store ID
    				this.getApplicationContext().getPackageName(),	// You Android Package name
					"",												// end point URL
					"");											// API authentication token
```

	_this.getApplicationContext()_ : will only work if the class that is calling the Track is an Activity class.
	
**Tracker.java file**

Holds all the code for sending of the data. The main information of how the code works can be separated into 3 functions. To make it work, copy the code for the following 3 functions and run the code for function "postData" in the "onCreate" function of the main Activity.

* String md5(String s)
* String getUDID(Context c)
* void postData(Context context, String storeId, String androidPackage, String endPoint, String apiAuthenticationToken)
	
* String md5(String s) : 
	This function performs md5 hashing. The reason for this function is because	android package does not have any default md5 hashing function.

* String getUDID(Context c)
	The Android ID may be changed everytime the user perform Factory Reset I heard that IMEI values might not be unique because phone factory might reuse IMEI values to cut cost.
	While the ID might be different from the same device, but resetting of the Android phone should not occur that often. The values should be close enough.	
	Note that the returned String is hashed. 

* void postData(Context context, String storeId, String androidPackage, String endPoint, String apiAuthenticationToken))	
	Currently it sends over 4 parameters namely "AndroidUniqueId", 
	"AndroidPackage", "StoreId" and "Manufacturer". "AndroidUniqueId" is 
	generated from the phone. "AndroidPackage" and "StoreId" is passed in to 
	the function. "Manufacturer" is retrieved from the phone Please change the 
	"AndroidPackage" and "StoreId" respectively according to the app.

# Change History
**Version 0.9:**

* Initial version that generates unique ID and sends it back on POST request in a seperate thread.

# Contributers

* Louis Zeng (appbackr inc.)
* Ethan Herdrick (appbackr inc.)
* Philipp Berner (appbackr inc.)

# Code License

[MIT License](http://www.opensource.org/licenses/mit-license.php)