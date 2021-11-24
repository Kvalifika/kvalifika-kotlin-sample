package com.kvalifika.kotlinapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kvalifika.sdk.KvalifikaSDK
import com.kvalifika.sdk.KvalifikaSDKCallback
import com.kvalifika.sdk.KvalifikaSDKError
import com.kvalifika.sdk.KvalifikaSDKLocale

class MainActivity : AppCompatActivity() {
    private lateinit var sdk: KvalifikaSDK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appId = ""
        sdk = KvalifikaSDK.Builder(this, appId)
            .locale(KvalifikaSDKLocale.GE)
            .build()

        sdk.callback(object : KvalifikaSDKCallback {
            override fun onInitialize() {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Initialized", Toast.LENGTH_LONG).show()
                }
            }

            override fun onStart(sessionId: String) {
                Log.d("MainActivity", "started")
            }

            override fun onFinish(sessionId: String) {
                Log.d("MainActivity", "finished")
            }

            override fun onError(error: KvalifikaSDKError, message: String?) {
                if (error == KvalifikaSDKError.INVALID_APP_ID) {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Invalid App ID", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                if (error == KvalifikaSDKError.USER_CANCELLED) {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "User cancelled", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                if (error == KvalifikaSDKError.TIMEOUT) {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Timeout", Toast.LENGTH_LONG).show()
                    }
                }

                if (error == KvalifikaSDKError.SESSION_UNSUCCESSFUL) {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Session failed", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                if (error == KvalifikaSDKError.ID_UNSUCCESSFUL) {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "ID scan failed", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                if (error == KvalifikaSDKError.CAMERA_PERMISSION_DENIED) {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Camera permission denied",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                if (error == KvalifikaSDKError.LANDSCAPE_MODE_NOT_ALLOWED) {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Landscape mode is not allowed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                if (error == KvalifikaSDKError.REVERSE_PORTRAIT_NOT_ALLOWED) {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Reverse portrait is not allowed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                if (error == KvalifikaSDKError.FACE_IMAGES_UPLOAD_FAILED) {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Could not upload face images",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                if (error == KvalifikaSDKError.DOCUMENT_IMAGES_UPLOAD_FAILED) {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Could not upload Id card or passport images",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                if (error == KvalifikaSDKError.NO_MORE_ATTEMPTS) {
                    runOnUiThread {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }
                }

                if (error == KvalifikaSDKError.UNKNOWN_INTERNAL_ERROR) {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Unknown error happened: $message",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }

    fun onVerificationPress(view: View?) {
        sdk.startSession()
    }
}