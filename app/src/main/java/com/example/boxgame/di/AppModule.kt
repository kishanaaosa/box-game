package com.example.boxgame.di

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.example.boxgame.utils.sharedpref.MyPreference
import com.example.boxgame.utils.sharedpref.PrefKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            PrefKey.PREFERENCE_NAME, Context.MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun provideMyPreference(mSharedPreferences: SharedPreferences) =
        MyPreference(mSharedPreferences)

}