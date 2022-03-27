package com.chetangani.regexlib.permissions

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.chetangani.regexlib.constants.ConstantValues.FALSE
import com.chetangani.regexlib.constants.ConstantValues.OTHER_PERMISSION
import com.chetangani.regexlib.constants.ConstantValues.STORAGE_PERMISSION
import com.chetangani.regexlib.constants.ConstantValues.TRUE
import com.chetangani.regexlib.interfaces.PermissionCallInterface
import com.chetangani.regexlib.interfaces.PermissionReceiver

class PermissionCall(private val activity: AppCompatActivity,
                     private val permissionReceiver: PermissionReceiver): PermissionCallInterface {

    private var requestPermissionCode: Int = 0
    private lateinit var permissions: Array<String>

    companion object {
        lateinit var permissionCallInterface: PermissionCallInterface
    }

    init {
        permissionCallInterface = this
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun checkPermissionsMAndAbove(requestedPermission: Array<String>) {
        requestPermissionCode = OTHER_PERMISSION
        permissions = requestedPermission
        if (checkPermission()) permissionReceiver.permissionGranted(TRUE) else requestPermission()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun checkStoragePermission() {
        requestPermissionCode = STORAGE_PERMISSION
        permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (checkPermission()) permissionReceiver.storagePermissionGranted(TRUE) else requestPermission()
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity, permissions, requestPermissionCode
        )
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun checkPermission(): Boolean {
        val resultList: MutableList<Boolean> = ArrayList()
        for (i in permissions.indices)
            resultList.add(ContextCompat.checkSelfPermission(activity.applicationContext, permissions[i]) == PackageManager.PERMISSION_GRANTED)
        return !resultList.contains(FALSE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if ((requestCode == OTHER_PERMISSION) || (requestCode == STORAGE_PERMISSION)) {
            if (grantResults.isNotEmpty()) {
                val permissionList: MutableList<Boolean> = ArrayList()
                for (i in grantResults.indices)
                    permissionList.add(grantResults[i] == PackageManager.PERMISSION_GRANTED)
                if (!permissionList.contains(FALSE)) {
                    if (requestPermissionCode == OTHER_PERMISSION)
                        permissionReceiver.permissionGranted(TRUE)
                    else if (requestPermissionCode == STORAGE_PERMISSION)
                        permissionReceiver.storagePermissionGranted(TRUE)
                } else {
                    if (requestPermissionCode == OTHER_PERMISSION) checkPermissionsMAndAbove(permissions)
                    else if (requestPermissionCode == STORAGE_PERMISSION) checkStoragePermission()
                }
            }
        }
    }
}