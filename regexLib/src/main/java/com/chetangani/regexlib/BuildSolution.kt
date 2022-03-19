package com.chetangani.regexlib

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import java.util.*

open class BuildSolution {

    @SuppressLint("HardwareIds")
    fun getSecureID(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            .uppercase(Locale.ROOT)
    }

    @SuppressLint("DefaultLocale")
    fun getBuildDetails(): String {
        return String.format("%d%d%d%d%d%d%d%d%d%d%d%d",
            Build.BOARD.length % 10, Build.BRAND.length % 10, Build.DEVICE.length % 10, Build.DISPLAY.length % 10,
            Build.HOST.length % 10, Build.ID.length % 10, Build.MANUFACTURER.length % 10, Build.MODEL.length % 10,
            Build.PRODUCT.length % 10, Build.TAGS.length % 10, Build.TYPE.length % 10, Build.USER.length % 10
        )
    }
}