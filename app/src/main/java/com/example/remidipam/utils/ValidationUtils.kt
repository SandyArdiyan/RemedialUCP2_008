package com.example.remidipam.utils


import android.text.TextUtils
import java.util.regex.Pattern

object ValidationUtils {

    fun isTextValid(text: String?): Boolean {
        return !text.isNullOrBlank()
    }


    fun isValidPhysicalId(id: String): Boolean {
        if (id.isBlank()) return false
        val idPattern = Pattern.compile("^[a-zA-Z0-9]*$")
        return idPattern.matcher(id).matches()
    }

    fun isNumeric(text: String): Boolean {
        return text.all { it.isDigit() }
    }
}