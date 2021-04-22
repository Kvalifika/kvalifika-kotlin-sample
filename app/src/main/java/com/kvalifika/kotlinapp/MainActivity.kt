package com.kvalifika.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kvalifika.sdk.KvalifikaSDK
import com.kvalifika.sdk.KvalifikaSDKCallback
import com.kvalifika.sdk.KvalifikaSDKError
import com.kvalifika.sdk.KvalifikaSDKLocale
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var sdk: KvalifikaSDK? = null
    private val appId: String = ""
    private val secretKey: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sdk = KvalifikaSDK.Builder(this, appId, secretKey)
//            .logo(R.drawable.logo)
//            .documentIcon(R.drawable.logo)
                .locale(KvalifikaSDKLocale.GE)
//                .activeFlashIcon(R.drawable.flash_on)
//                .inactiveFlashIcon(R.drawable.flash_off)
//                .cancelIcon(R.drawable.cancel_icon)
                .build()

        sdk!!.callback(object : KvalifikaSDKCallback {
            override fun onInitialize() {
                Log.d("MainActivity", "initialized")
            }

            override fun onStart() {
                Log.d("MainActivity", "started")
            }

            override fun onFinish(sessionData: String) {
                val response = JSONObject(sessionData)
                Log.d("MainActivity", sessionData)
            }

            override fun onError(error: KvalifikaSDKError) {
                if(error == KvalifikaSDKError.USER_CANCELLED) {
                    Log.d("MainActivity", "user cancelled error")
                    Toast.makeText(applicationContext, "User cancelled", Toast.LENGTH_LONG).show()
                }

                if(error == KvalifikaSDKError.TIMEOUT) {
                    Log.d("MainActivity", "timeout")
                    Toast.makeText(applicationContext, "Timeout", Toast.LENGTH_LONG).show()
                }

                if(error == KvalifikaSDKError.SESSION_UNSUCCESSFUL) {
                    Log.d("MainActivity", "session failed")
                }

                if(error == KvalifikaSDKError.ID_UNSUCCESSFUL) {
                    Log.d("MainActivity", "Id scan failed")
                }

                if(error == KvalifikaSDKError.CAMERA_PERMISSION_DENIED) {
                    Log.d("MainActivity", "camera permission denied")
                    Toast.makeText(applicationContext, "Camera permission denied", Toast.LENGTH_LONG).show()
                }

                if(error == KvalifikaSDKError.LANDSCAPE_MODE_NOT_ALLOWED) {
                    Log.d("MainActivity", "Landscape mode is not allowed")
                    Toast.makeText(applicationContext, "Landscape mode is not allowed", Toast.LENGTH_LONG).show()
                }

                if(error == KvalifikaSDKError.REVERSE_PORTRAIT_NOT_ALLOWED) {
                    Log.d("MainActivity", "reverse portrait not allowed")
                    Toast.makeText(applicationContext, "Reverse portrait is not allowed", Toast.LENGTH_LONG).show()
                }

                if(error == KvalifikaSDKError.UNKNOWN_INTERNAL_ERROR) {
                    Toast.makeText(applicationContext, "Unknown error happened", Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    // Perform Photo ID Match, generating a username each time to guarantee uniqueness.
    fun onPhotoIDMatchPressed(view: View?) {
        sdk?.startSession()
    }
}