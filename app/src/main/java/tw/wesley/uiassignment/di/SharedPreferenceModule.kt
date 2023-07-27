package tw.wesley.uiassignment.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {
    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("shared_preferences_name", Context.MODE_PRIVATE)
    }
}