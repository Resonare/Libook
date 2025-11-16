package com.example.test3

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val isDark = getSharedPreferences("settings", MODE_PRIVATE)
            .getBoolean("dark_theme", false)

        AppCompatDelegate.setDefaultNightMode(
            if (isDark) MODE_NIGHT_YES else MODE_NIGHT_NO
        )
    }
}