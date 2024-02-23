package com.example.myussd

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    private var pendingUssdCode: String? = null

    companion object {
        private const val REQUEST_PHONE_CALL = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(onExecuteUssd = { ussdCode ->
                pendingUssdCode = ussdCode
                if (isCallPermissionGranted()) {
                    executePendingUssdCode()
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
                }
            })
        }
    }

    private fun isCallPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
    }

    private fun executePendingUssdCode() {
        pendingUssdCode?.let { ussdCode ->
            val encodedHash = Uri.encode("#")
            val ussd = ussdCode.replace("#", encodedHash)
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$ussd")))
            pendingUssdCode = null // Clear the pending USSD code after executing
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PHONE_CALL && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission was granted. Execute the pending USSD code.
            executePendingUssdCode()
        } else {
            // Permission was denied. Handle the failure.
            // Optionally, show a message to the user explaining that the USSD code cannot be executed without the permission.
            pendingUssdCode = null // Clear the pending USSD code as we can't execute it
        }
    }
}
