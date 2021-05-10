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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sdk = KvalifikaSDK.Builder(this, appId)
                .locale(KvalifikaSDKLocale.GE)
                .build()

        sdk!!.callback(object : KvalifikaSDKCallback {
            override fun onInitialize() {
                Log.d("MainActivity", "initialized")
            }

            override fun onStart(sessionId: String) {
                Log.d("MainActivity", "started")
            }

            override fun onFinish(sessionId: String) {
                Log.d("MainActivity", "finished")
            }

            override fun onError(error: KvalifikaSDKError, message: String?) {
                if (error == KvalifikaSDKError.INVALID_APP_ID) {
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
                    Toast.makeText(applicationContext, "Unknown error happened: $message", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun onVerificationPress(view: View?) {
        sdk?.startSession()
    }
}