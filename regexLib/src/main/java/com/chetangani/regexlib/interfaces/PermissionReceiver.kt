package com.chetangani.regexlib.interfaces

interface PermissionReceiver {
    fun permissionGranted(result: Boolean)
    fun storagePermissionGranted(result: Boolean)
    fun openNotificationPermission()
    fun notificationCallResult()
    fun storageCallResult()
}