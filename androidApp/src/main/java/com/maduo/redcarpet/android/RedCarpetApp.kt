package com.maduo.redcarpet.android

import android.app.Application
import android.content.res.Configuration
import com.maduo.redcarpet.android.util.LanguageHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RedCarpetApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    fun initLanguage() {
        val language = LanguageHelper.getUserLanguage(this)
        LanguageHelper.updateLanguage(this, language)
    }
}