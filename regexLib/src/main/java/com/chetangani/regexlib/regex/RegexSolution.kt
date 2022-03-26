package com.chetangani.regexlib.regex

import android.text.TextUtils
import java.util.ArrayList
import java.util.regex.Matcher
import java.util.regex.Pattern

open class RegexSolution {

    private val regex: String
        get() = ("\\b\\d+(?=\\sis)\\b" + "|" +
                "\\b\\d+(?=\\s\\sis)\\b" + "|" +
                "\\b(?<=is\\s)\\d+\\b" + "|" +
                "\\b(?<=is\\s\\s)\\d+\\b" + "|" +
                "\\b(?<=is:\\s)\\d+\\b" + "|" +
                "\\b(?<=OTP\\s)\\d+\\b" + "|" +
                "\\b(?<=\\sOTP)\\d+\\b" + "|" +
                "\\b(?<=\\sOTP-)\\d+\\b" + "|" +
                "\\b\\d+(?=\\scode)\\b" + "|" +
                "\\b(?<=code\\s)\\d+\\b" + "|" +
                "\\b(?<=Code\\s)\\d+\\b" + "|" +
                "\\b\\d+(?=\\sas)\\b" + "|" +
                "\\b(?<=as\\s)\\d+\\b" + "|" +
                "\\b(?<=:\\s)\\d+\\b" + "|" +
                "\\b(?<=:)\\d+\\b" + "|" +
                "\\b(?<=use\\s)\\d+\\b" + "|" +
                "\\b(?<=PIN:\\s)\\d+\\b" + "|" +
                "\\b\\d+(?=\\n)\\b")

    private val spentRegex: String
        get() = ("\\b(?<=INR\\s)\\d+.\\d+\\b" + "|" +
                "\\b(?<=Rs.)\\d+,\\d+.\\d+\\b" + "|" +
                "\\b(?<=Rs.)\\d+\\b" + "|" +
                "\\b(?<=Rs.\\s)\\d+,\\d+.\\d+\\b")

    fun findRegex(otp: String): String {
        var value = ""
        val otpLength: Array<String> = arrayOf("4", "5", "6", "8")
        val otpList: ArrayList<String> = ArrayList()
        val pattern: Pattern = Pattern.compile(regex, Pattern.MULTILINE)
        val matcher: Matcher = pattern.matcher(otp)
        while (matcher.find()) otpList.add(matcher.group(0)!!)
        for (i in otpList.indices) if (listOf(*otpLength).contains(otpList[i].length.toString())) {
            value = otpList[i]
            break
        }
        return value
    }

    fun findSpentRegex(spent: String): String {
        var value = ""
        val pattern: Pattern = Pattern.compile(spentRegex, Pattern.MULTILINE)
        val matcher: Matcher = pattern.matcher(spent)
        if (matcher.find()) value = matcher.group(0)!!
        return value
    }

    fun findOtp(otp: String) = !TextUtils.isEmpty(findRegex(otp).trim { it <= ' ' })

    fun findSpent(spent: String) = !TextUtils.isEmpty(findSpentRegex(spent).trim { it <= ' ' })
}