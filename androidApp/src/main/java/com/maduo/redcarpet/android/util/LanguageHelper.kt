package com.maduo.redcarpet.android.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.annotation.RequiresApi
import com.maduo.redcarpet.android.util.Constants.Companion.GENERAL_STORAGE
import com.maduo.redcarpet.android.util.Constants.Companion.KEY_USER_LANGUAGE
import java.util.*

class LanguageHelper {

    companion object {

        fun updateLanguage(context: Context, language: String) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val res = context.resources
            val config = Configuration(res.configuration)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val localeList = LocaleList(locale)
                LocaleList.setDefault(localeList)
                config.setLocales(localeList)
                Log.d("LanguageHelper", "${Build.VERSION.SDK_INT}: ${config.locales}")
            } else {
                config.setLocale(locale)
                config.locale = locale
                Log.d("LanguageHelper", "${Build.VERSION.SDK_INT}: ${config.locale}")
            }
            res.updateConfiguration(config, res.displayMetrics)
            context.createConfigurationContext(config)
        }

        fun storeLanguage(context: Context, language: String) {
            Log.d("LanguageHelper", "language: $language")
            val sharedPreferences = context.getSharedPreferences(GENERAL_STORAGE, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(KEY_USER_LANGUAGE, language)
            editor.apply()
            Log.d("LanguageHelper", "shared perfs: ${sharedPreferences.all}")
        }

        fun getUserLanguage(context: Context): String {
            val defaultLanguage = Locale.getDefault().language
            val language = context.getSharedPreferences(GENERAL_STORAGE, Context.MODE_PRIVATE)
                .getString(KEY_USER_LANGUAGE, null)
            return language ?: defaultLanguage
        }

        fun recreateActivity(activity: Activity) {
            val intent = Intent(activity, activity.javaClass)
            activity.startActivity(intent)
            activity.finish()
        }

    }

}
