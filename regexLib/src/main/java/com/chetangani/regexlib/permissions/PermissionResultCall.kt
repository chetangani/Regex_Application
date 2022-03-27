package com.chetangani.regexlib.permissions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.chetangani.regexlib.R
import com.chetangani.regexlib.constants.ConstantValues.ACTION_NOTIFICATION_LISTENER_SETTINGS
import com.chetangani.regexlib.interfaces.PermissionReceiver
import java.lang.Exception

class PermissionResultCall(private val activity: AppCompatActivity, private val permissionReceiver: PermissionReceiver) {

    private val getResult =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_CANCELED) permissionReceiver.notificationCallResult()
        }

    fun launchNotificationCall() {
        getResult.launch(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS))
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun launchStorageCall() {
        val intent = Intent()
        try {
            intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
            val uri = Uri.fromParts(activity.getString(R.string.package_text), activity.packageName, null)
            intent.data = uri
        } catch (e: Exception) {
            intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
        }
        getStorageResult.launch(intent)
    }

    private val getStorageResult =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_CANCELED) permissionReceiver.storageCallResult()
        }

}