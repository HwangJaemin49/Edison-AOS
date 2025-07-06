package com.umc.edison.local.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettings(
        @ApplicationContext context: Context
    ): Settings {
        return SharedPreferencesSettings(context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE))
    }
}