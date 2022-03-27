package com.chetangani.regexlib.permissions

import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.provider.Settings
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import com.chetangani.regexlib.R
import com.chetangani.regexlib.constants.ConstantValues.ENABLED_NOTIFICATION_LISTENERS
import com.chetangani.regexlib.constants.ConstantValues.FALSE
import com.chetangani.regexlib.interfaces.PermissionReceiver

class NotificationListenerCall(private val context: Context,
                               private val permissionReceiver: PermissionReceiver) {

    val isNotificationServiceEnabled: Boolean
        get() {
            val pkgName = context.packageName
            val flat = Settings.Secure.getString(context.contentResolver, ENABLED_NOTIFICATION_LISTENERS)
            if (!TextUtils.isEmpty(flat)) {
                val names = flat.split(":".toRegex()).toTypedArray()
                for (name in names) {
                    val cn = ComponentName.unflattenFromString(name)
                    if (cn != null) if (TextUtils.equals(pkgName, cn.packageName)) return true
                }
            }
            return false
        }

    fun buildNotificationServiceAlertDialog(appName: String) = context.let {
        AlertDialog.Builder(it)
            .setTitle(R.string.notification_listener_service)
            .setCancelable(FALSE)
            .setMessage(String.format("%s %s %s", R.string.notification_alert_msg_1, appName,
                R.string.notification_alert_msg_2))
            .setPositiveButton(R.string.yes, DialogInterface.OnClickListener { _, _ ->
                permissionReceiver.openNotificationPermission() })
            .setNegativeButton(R.string.no, DialogInterface.OnClickListener { _, _ ->  })
            .create()
    }
}