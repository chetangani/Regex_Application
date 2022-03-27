package com.chetangani.regexlib.permissions

import android.annotation.TargetApi
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.chetangani.regexlib.constants.ConstantValues.FALSE
import com.chetangani.regexlib.constants.ConstantValues.OTHER_PERMISSION
import com.chetangani.regexlib.constants.ConstantValues.STORAGE_PERMISSION
import com.chetangani.regexlib.constants.ConstantValues.TRUE
import com.chetangani.regexlib.interfaces.PermissionCallInterface
import com.chetangani.regexlib.interfaces.PermissionReceiver

class PermissionCall(private val activity: AppCompatActivity, private val permissions: Array<String>,
                     private val requestPermissionCode: Int,
                     private val permissionReceiver: PermissionReceiver): PermissionCallInterface {

    companion object {
        lateinit var permissionCallInterface: PermissionCallInterface
    }

    init {
        permissionCallInterface = this
        checkPermissionsMAndAbove()
    }

    @TargetApi(23)
    private fun checkPermissionsMAndAbove() {
        if (checkPermission()) permissionReceiver.permissionGranted(TRUE) else requestPermission()
    }

    @TargetApi(23)
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity, permissions, requestPermissionCode
        )
    }

    @TargetApi(23)
    private fun checkPermission(): Boolean {
        val resultList: MutableList<Boolean> = ArrayList()
        for (i in permissions.indices)
            resultList.add(ContextCompat.checkSelfPermission(activity.applicationContext, permissions[i]) == PackageManager.PERMISSION_GRANTED)
        return !resultList.contains(FALSE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray, requestedPermission: Int) {
        if ((requestCode == OTHER_PERMISSION) || (requestCode == STORAGE_PERMISSION)) {
            if (grantResults.isNotEmpty()) {
                val permissionList: MutableList<Boolean> = ArrayList()
                for (i in grantResults.indices)
                    permissionList.add(grantResults[i] == PackageManager.PERMISSION_GRANTED)
                if (!permissionList.contains(FALSE)) {
                    if (requestedPermission == OTHER_PERMISSION)
                        permissionReceiver.permissionGranted(TRUE)
                    else if (requestedPermission == STORAGE_PERMISSION)
                        permissionReceiver.storagePermissionGranted(TRUE)
                } else checkPermissionsMAndAbove()
            }
        }
    }
}