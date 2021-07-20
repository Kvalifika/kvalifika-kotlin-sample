# Kvalifika SDK Kotlin Sample

Use Kvalifika SDK to easily integrate into your Android app

Table of content:
- [Installation](#installation)
- [Initialize the SDK](#initialize-the-sdk)
- [Start Verification](#start-verification)
- [Handling Verifications](#handling-verifications)
	- [Callback Methods](#callback-methods)
	- [Error Codes](#error-codes)
- [UI Customizations](#ui-customizations)
	- [Appearance](#appearance)
	- [Language](#language)
- [ProGuard](#proguard)


&nbsp;
## Installation
Please use `minSdkVersion` `21`  in your `build.gradle (Module)` file

&nbsp;
Add following to project's main `build.gradle` file
```groovy
allprojects {
    repositories {
        // Existing repositories, like google() and jcenter()
        maven { url 'https://s3.eu-central-1.amazonaws.com/com.kvalifika.sdk' }
    }
}
```
&nbsp;
To install Kvalifika Android SDK, add following to `build.gradle (Module)` file:

```groovy
dependencies {
  // Insert line below to include our client library as a dependency.
  implementation 'com.kvalifika:sdk:0.5.0'
}
```
&nbsp;
## Initialize the SDK

```kotlin
import com.kvalifika.sdk.KvalifikaSDK
import com.kvalifika.sdk.KvalifikaSDKLocale
import com.kvalifika.sdk.KvalifikaSDKCallback
import com.kvalifika.sdk.KvalifikaSDKError
```
After that you need to initialize SDK with **your appId and secretKey**.

```kotlin
class MainActivity : AppCompatActivity() {
	 private lateinit var sdk: KvalifikaSDK
 	 private val appId: String = "YOUR APP ID"

	 override fun onCreate(savedInstanceState: Bundle?) {
			super.onCreate(savedInstanceState)
			setContentView(R.layout.activity_main)

			sdk = KvalifikaSDK.Builder(this, appId)
					.locale(KvalifikaSDKLocale.EN)
					.build()
	 }
}

```
&nbsp;

## Start Verification
Call `sdk.startSession()` on button click event

```kotlin
class MainActivity : AppCompatActivity() {
	 private lateinit var sdk: KvalifikaSDK
 	 private val appId: String = "YOUR APP ID"

	 override fun onCreate(savedInstanceState: Bundle?) {
			super.onCreate(savedInstanceState)
			setContentView(R.layout.activity_main)

			sdk = KvalifikaSDK.Builder(this, appId)
					.locale(KvalifikaSDKLocale.GE)
					.build()

	 }

	 // Start verification on button click
	 fun onVerificationPress(view: View?) {
			sdk.startSession()
	 }
}

```

&nbsp;
## Handling Verifications
It's useful to know if a user has completed the verification flow or canceled it. For this, you can implement the callback methods.

&nbsp;
### Callback Methods

| Method | Description |
|---------------------|--------------------------|
| onInitialize        | This callback method is triggered when SDK initializes. |
| onStart            | This callback method is triggered when user starts verification. |
| onFinish             | This callback method is triggered when user completes verification. Get session data here. |
| onError             | This callback method is triggered on error. [Error Codes](#error-codes). |

```kotlin
sdk.callback(object : KvalifikaSDKCallback {
    override fun onInitialize() {
        Log.d("MainActivity", "initialized")
    }

    override fun onStart(sessionId: String) {
        Log.d("MainActivity", "started")
    }

    override fun onFinish(sessionId: String) {
		// Fetch session data here from your server
    }

    override fun onError(error: KvalifikaSDKError, message: String?) {
        if(error == KvalifikaSDKError.INVALID_APP_ID) {
    		Log.d("MainActivity", "Invalid App ID")
    	}

        if (error == KvalifikaSDKError.USER_CANCELLED) {
            Toast.makeText(applicationContext, "User cancelled", Toast.LENGTH_LONG).show()
        }

        if (error == KvalifikaSDKError.TIMEOUT) {
            Toast.makeText(applicationContext, "Timeout", Toast.LENGTH_LONG).show()
        }

        if (error == KvalifikaSDKError.SESSION_UNSUCCESSFUL) {
            Toast.makeText(applicationContext, "Session failed", Toast.LENGTH_LONG).show()
        }

        if (error == KvalifikaSDKError.ID_UNSUCCESSFUL) {
            Toast.makeText(applicationContext, "ID scan failed", Toast.LENGTH_LONG).show()
        }

        if (error == KvalifikaSDKError.CAMERA_PERMISSION_DENIED) {
            Toast.makeText(applicationContext, "Camera permission denied", Toast.LENGTH_LONG).show()
        }

        if (error == KvalifikaSDKError.LANDSCAPE_MODE_NOT_ALLOWED) {
            Toast.makeText(applicationContext, "Landscape mode is not allowed", Toast.LENGTH_LONG).show()
        }

        if (error == KvalifikaSDKError.REVERSE_PORTRAIT_NOT_ALLOWED) {
            Toast.makeText(applicationContext, "Reverse portrait is not allowed", Toast.LENGTH_LONG).show()
        }

        if (error == KvalifikaSDKError.FACE_IMAGES_UPLOAD_FAILED) {
            Toast.makeText(applicationContext, "Could not upload face images", Toast.LENGTH_LONG).show()
        }

        if (error == KvalifikaSDKError.DOCUMENT_IMAGES_UPLOAD_FAILED) {
            Toast.makeText(applicationContext, "Could not upload Id card or passport images", Toast.LENGTH_LONG).show()
        }

        if (error == KvalifikaSDKError.COMPARE_IMAGES_FAILED) {
            Toast.makeText(applicationContext, "Could not compare images", Toast.LENGTH_LONG).show()
        }

        if (error == KvalifikaSDKError.UNKNOWN_INTERNAL_ERROR) {
            Toast.makeText(applicationContext, "Unknown error happened", Toast.LENGTH_LONG).show()
        }
    }
})
```


&nbsp;
### Error Codes
| Error Code | Description |
|---------------------|--------------------------|
| INVALID_APP_ID        | Kvalifika App Id is incorrect |
| USER_CANCELLED        | User cancelled before completing verification. |
| TIMEOUT        | Cancelled due to inactivity. |
| SESSION_UNSUCCESSFUL        | The Session was not performed successfully |
| ID_UNSUCCESSFUL        | The ID Scan was not performed successfully and identity document data was not generated. |
| CAMERA_PERMISSION_DENIED        | Camera is required but access prevented by user settings. |
| LANDSCAPE_MODE_NOT_ALLOWED        | Verification cancelled because device is in landscape mode. |
| REVERSE_PORTRAIT_NOT_ALLOWED        | Verification cancelled because device is in reverse portrait mode. |
| FACE_IMAGES_UPLOAD_FAILED        | Could not upload face images. Internal request failed. |
| DOCUMENT_IMAGES_UPLOAD_FAILED        | Could not upload ID card or passport images. Internal request failed. |
| COMPARE_IMAGES_FAILED        | Could not compare images. Internal request failed. |
| UNKNOWN_INTERNAL_ERROR        | Session failed because of an unhandled internal error. |

&nbsp;
## UI Customizations

### Appearance
You can customize logo and icons.
Provide drawable resources.

```kotlin
sdk = KvalifikaSDK.Builder(this, appId, secretKey)
	.locale(KvalifikaSDKLocale.EN)
	.logo(R.drawable.logo)
	.documentIcon(R.drawable.document_icon)
	.activeFlashIcon(R.drawable.flash_on)
	.inactiveFlashIcon(R.drawable_flash_off)
	.cancelIcon(R.drawable.cancel_icon)
	.build()
```

&nbsp;
### Language
You can set locale when initializing SDK
Supported locales are:

| Code | Language |
|---------------------|--------------------------|
| EN        | English |
| GE        | Georgian |


```kotlin
sdk = KvalifikaSDK.Builder(this, appId, secretKey)
        .locale(KvalifikaSDKLocale.EN)
        .build()
```

&nbsp;
### ProGuard
If you are using ProGuard in release build add following options:
```
-keep class com.facetec.sdk.** { *; }
```
